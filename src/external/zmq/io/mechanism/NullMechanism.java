package external.zmq.io.mechanism;

import static external.zmq.io.Metadata.IDENTITY;
import static external.zmq.io.Metadata.SOCKET_TYPE;
import external.zmq.Msg;
import external.zmq.Options;
import external.zmq.ZError;
import external.zmq.ZMQ;
import external.zmq.io.SessionBase;
import external.zmq.io.net.Address;

class NullMechanism extends Mechanism
{
    private static final String OK    = "200";
    private static final String READY = "READY";
    private static final String ERROR = "ERROR";

    private boolean readyCommandSent;
    private boolean errorCommandSent;

    private boolean readyCommandReceived;
    private boolean errorCommandReceived;

    private boolean zapConnected;
    private boolean zapRequestSent;
    private boolean zapReplyReceived;

    NullMechanism(SessionBase session, Address peerAddress, Options options)
    {
        super(session, peerAddress, options);

        //  NULL mechanism only uses ZAP if there's a domain defined
        //  This prevents ZAP requests on naive sockets
        if (options.zapDomain != null && options.zapDomain.length() > 0 && session.zapConnect() == 0) {
            zapConnected = true;
        }
    }

    @Override
    public int nextHandshakeCommand(Msg msg)
    {
        if (readyCommandSent || errorCommandSent) {
            return ZError.EAGAIN;
        }

        if (zapConnected && !zapReplyReceived) {
            if (zapRequestSent) {
                return ZError.EAGAIN;
            }

            sendZapRequest(Mechanisms.NULL, false);
            zapRequestSent = true;

            int rc = receiveAndProcessZapReply();
            if (rc != 0) {
                return rc;
            }
            zapReplyReceived = true;
        }

        if (zapReplyReceived && !OK.equals(statusCode)) {
            msg.putShortString(ERROR);
            msg.putShortString(statusCode);

            errorCommandSent = true;
            return 0;
        }

        //  Add mechanism string
        msg.putShortString(READY);

        //  Add socket type property
        String socketType = socketType();
        addProperty(msg, SOCKET_TYPE, socketType);

        //  Add identity property
        if (options.type == ZMQ.ZMQ_REQ || options.type == ZMQ.ZMQ_DEALER || options.type == ZMQ.ZMQ_ROUTER) {
            addProperty(msg, IDENTITY, options.identity);
        }
        readyCommandSent = true;

        return 0;
    }

    @Override
    public int processHandshakeCommand(Msg msg)
    {
        if (readyCommandReceived || errorCommandReceived) {
            session.getSocket().eventHandshakeFailedProtocol(session.getEndpoint(), ZMQ.ZMQ_PROTOCOL_ERROR_ZMTP_UNEXPECTED_COMMAND);
            return ZError.EPROTO;
        }
        int dataSize = msg.size();

        int rc;
        if (dataSize >= 6 && compare(msg, READY, true)) {
            rc = processReadyCommand(msg);
        }
        else if (dataSize >= 6 && compare(msg, ERROR, true)) {
            rc = processErrorCommand(msg);
        }
        else {
            session.getSocket().eventHandshakeFailedProtocol(session.getEndpoint(), ZMQ.ZMQ_PROTOCOL_ERROR_ZMTP_UNEXPECTED_COMMAND);
            return ZError.EPROTO;
        }
        return rc;
    }

    private int processReadyCommand(Msg msg)
    {
        readyCommandReceived = true;
        return parseMetadata(msg, 6, false);
    }

    private int processErrorCommand(Msg msg)
    {
        errorCommandReceived = true;
        return parseErrorMessage(msg);
    }

    @Override
    public int zapMsgAvailable()
    {
        if (zapReplyReceived) {
            return ZError.EFSM;
        }
        int rc = receiveAndProcessZapReply();
        if (rc == 0) {
            zapReplyReceived = true;
        }

        return rc;
    }

    @Override
    public Status status()
    {
        boolean commandSent = readyCommandSent || errorCommandSent;
        boolean commandReceived = readyCommandReceived || errorCommandReceived;

        if (readyCommandSent && readyCommandReceived) {
            return Status.READY;
        }
        if (commandSent && commandReceived) {
            return Status.ERROR;
        }
        return Status.HANDSHAKING;
    }
}
