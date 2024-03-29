package external.zmq.socket;

import external.zmq.Ctx;
import external.zmq.ZMQ;

public class Raw extends Peer
{
    public Raw(Ctx parent, int tid, int sid)
    {
        super(parent, tid, sid);

        options.type = ZMQ.ZMQ_RAW;
        options.canSendHelloMsg = true;
        options.rawSocket = true;
    }
}
