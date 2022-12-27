package external.zmq.socket;

import external.zmq.Ctx;
import external.zmq.ZMQ;
import external.zmq.pipe.Pipe;
import external.zmq.socket.clientserver.Server;

public class Peer extends Server
{
    public Peer(Ctx parent, int tid, int sid)
    {
        super(parent, tid, sid);

        options.type = ZMQ.ZMQ_PEER;
        options.canSendHelloMsg = true;
        options.canReceiveDisconnectMsg = true;
    }

    @Override
    public void xattachPipe(Pipe pipe, boolean subscribe2all, boolean isLocallyInitiated)
    {
        super.xattachPipe(pipe, subscribe2all, isLocallyInitiated);
        options.peerLastRoutingId = pipe.getRoutingId();
    }
}
