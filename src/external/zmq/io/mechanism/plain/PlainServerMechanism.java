package external.zmq.io.mechanism.plain;

import static external.zmq.io.Metadata.IDENTITY;
import static external.zmq.io.Metadata.SOCKET_TYPE;
import external.zmq.Msg;
import external.zmq.Options;
import external.zmq.ZError;
import external.zmq.ZMQ;
import external.zmq.io.SessionBase;
import external.zmq.io.mechanism.Mechanism;
import external.zmq.io.mechanism.Mechanisms;
import external.zmq.io.net.Address;

public class PlainServerMechanism extends Mechanism
{
    private enum State
    {
        WAITING_FOR_HELLO,
        SENDING_WELCOME,
        WAITING_FOR_INITIATE,
        SENDING_READY,
        WAITING_FOR_ZAP_REPLY,
        SENDING_ERROR,
        ERROR_COMMAND_SENT,
        READY
    }

    private State state;

    public PlainServerMechanism(SessionBase session, Address peerAddress, Options options)
    {
        super(session, peerAddress, options);
        this.state = State.WAITING_FOR_HELLO;
    }

    @Override
    public int nextHandshakeCommand(Msg msg)
    {
        int rc;
        switch (state) {
        case SENDING_WELCOME:
            rc = produceWelcome(msg);
            if (rc == 0) {
                state = State.WAITING_FOR_INITIATE;
            }
            break;
        case SENDING_READY:
            rc = produceReady(msg);
            if (rc == 0) {
                state = State.READY;
            }
            break;
        case SENDING_ERROR:
            rc = produceError(msg);
            if (rc == 0) {
                state = State.ERROR_COMMAND_SENT;
            }
            break;
        default:
            rc = ZError.EAGAIN;
            break;

        }
        return rc;
    }

    @Override
    public int processHandshakeCommand(Msg msg)
    {
        int rc;
        switch (state) {
        case WAITING_FOR_HELLO:
            rc = produceHello(msg);
            break;
        case WAITING_FOR_INITIATE:
            rc = produceInitiate(msg);
            break;
        default:
            rc = ZError.EPROTO;
            break;

        }
        return rc;
    }

    @Override
    public Status status()
    {
        if (state == State.READY) {
            return Status.READY;
        }
        else if (state == State.ERROR_COMMAND_SENT) {
            return Status.ERROR;
        }
        else {
            return Status.HANDSHAKING;
        }
    }

    @Override
    public int zapMsgAvailable()
    {
        if (state != State.WAITING_FOR_ZAP_REPLY) {
            return ZError.EFSM;
        }

        int rc = receiveAndProcessZapReply();
        if (rc == 0) {
            state = "200".equals(statusCode) ? State.SENDING_WELCOME : State.SENDING_ERROR;
        }
        return rc;
    }

    private int produceHello(Msg msg)
    {
        int bytesLeft = msg.size();
        int index = 0;
        if (bytesLeft < 6 || !compare(msg, "HELLO", true)) {
            return ZError.EPROTO;
        }
        bytesLeft -= 6;
        index += 6;
        if (bytesLeft < 1) {
            return ZError.EPROTO;
        }
        byte length = msg.get(index);
        bytesLeft -= 1;
        if (bytesLeft < length) {
            return ZError.EPROTO;
        }
        byte[] tmp = new byte[length];
        index += 1;
        msg.getBytes(index, tmp, 0, length);
        byte[] username = tmp;
        bytesLeft -= length;
        index += length;

        length = msg.get(index);
        bytesLeft -= 1;
        if (bytesLeft < length) {
            return ZError.EPROTO;
        }
        tmp = new byte[length];
        index += 1;
        msg.getBytes(index, tmp, 0, length);
        byte[] password = tmp;
        bytesLeft -= length;
        //        index += length;

        if (bytesLeft > 0) {
            return ZError.EPROTO;
        }

        //  Use ZAP protocol (RFC 27) to authenticate the user.
        int rc = session.zapConnect();
        if (rc == 0) {
            sendZapRequest(username, password);
            rc = receiveAndProcessZapReply();
            if (rc == 0) {
                state = "200".equals(statusCode) ? State.SENDING_WELCOME : State.SENDING_ERROR;
            }
            else if (rc == ZError.EAGAIN) {
                state = State.WAITING_FOR_ZAP_REPLY;
            }
            else {
                return -1;
            }
        }
        else {
            state = State.SENDING_WELCOME;
        }

        return 0;
    }

    private int produceWelcome(Msg msg)
    {
        msg.putShortString("WELCOME");
        return 0;
    }

    private int produceInitiate(Msg msg)
    {
        int bytesLeft = msg.size();
        if (bytesLeft < 9 || !compare(msg, "INITIATE", true)) {
            return ZError.EPROTO;
        }

        int rc = parseMetadata(msg, 9, false);
        if (rc == 0) {
            state = State.SENDING_READY;
        }
        return rc;
    }

    private int produceReady(Msg msg)
    {
        //  Add command name
        msg.putShortString("READY");

        //  Add socket type property
        String socketType = socketType();
        addProperty(msg, SOCKET_TYPE, socketType);

        //  Add identity property
        if (options.type == ZMQ.ZMQ_REQ || options.type == ZMQ.ZMQ_DEALER || options.type == ZMQ.ZMQ_ROUTER) {
            addProperty(msg, IDENTITY, options.identity);
        }

        return 0;
    }

    private int produceError(Msg msg)
    {
        assert (statusCode != null && statusCode.length() == 3);

        msg.putShortString("ERROR");
        msg.putShortString(statusCode);

        return 0;
    }

    private void sendZapRequest(byte[] username, byte[] password)
    {
        sendZapRequest(Mechanisms.PLAIN, true);

        //  Username frame
        Msg msg = new Msg(username.length);
        msg.setFlags(Msg.MORE);
        msg.put(username);
        boolean rc = session.writeZapMsg(msg);
        assert (rc);

        //  Password frame
        msg = new Msg(password.length);
        msg.put(password);
        rc = session.writeZapMsg(msg);
        assert (rc);
    }
}
