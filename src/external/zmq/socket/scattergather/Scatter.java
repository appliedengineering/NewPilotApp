package external.zmq.socket.scattergather;

import external.zmq.Ctx;
import external.zmq.Msg;
import external.zmq.SocketBase;
import external.zmq.ZError;
import external.zmq.ZMQ;
import external.zmq.pipe.Pipe;
import external.zmq.socket.LB;

public class Scatter extends SocketBase
{
    //  Load balancer managing the outbound pipes.
    private final LB lb;

    //  Holds the prefetched message.
    public Scatter(Ctx parent, int tid, int sid)
    {
        super(parent, tid, sid, true);

        options.type = ZMQ.ZMQ_SCATTER;

        lb = new LB();
    }

    @Override
    protected void xattachPipe(Pipe pipe, boolean subscribe2all, boolean isLocallyInitiated)
    {
        assert (pipe != null);

        //  Don't delay pipe termination as there is no one
        //  to receive the delimiter.
        pipe.setNoDelay();

        lb.attach(pipe);
    }

    @Override
    protected boolean xsend(Msg msg)
    {
        //  SCATTER sockets do not allow multipart data (ZMQ_SNDMORE)
        if (msg.hasMore()) {
            errno.set(ZError.EINVAL);
            return false;
        }
        return lb.sendpipe(msg, errno, null);
    }

    @Override
    protected boolean xhasOut()
    {
        return lb.hasOut();
    }

    @Override
    protected void xwriteActivated(Pipe pipe)
    {
        lb.activated(pipe);
    }

    @Override
    protected void xpipeTerminated(Pipe pipe)
    {
        lb.terminated(pipe);
    }
}
