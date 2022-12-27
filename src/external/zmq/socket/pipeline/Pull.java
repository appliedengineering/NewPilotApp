package external.zmq.socket.pipeline;

import external.zmq.Ctx;
import external.zmq.Msg;
import external.zmq.SocketBase;
import external.zmq.ZMQ;
import external.zmq.pipe.Pipe;
import external.zmq.socket.FQ;
import external.zmq.util.Blob;

public class Pull extends SocketBase
{
    //  Fair queueing object for inbound pipes.
    private final FQ fq;

    public Pull(Ctx parent, int tid, int sid)
    {
        super(parent, tid, sid);
        options.type = ZMQ.ZMQ_PULL;

        fq = new FQ();
    }

    @Override
    protected void xattachPipe(Pipe pipe, boolean subscribe2all, boolean isLocallyInitiated)
    {
        assert (pipe != null);
        fq.attach(pipe);
    }

    @Override
    protected void xreadActivated(Pipe pipe)
    {
        fq.activated(pipe);
    }

    @Override
    protected void xpipeTerminated(Pipe pipe)
    {
        fq.terminated(pipe);
    }

    @Override
    public Msg xrecv()
    {
        return fq.recv(errno);
    }

    @Override
    protected boolean xhasIn()
    {
        return fq.hasIn();
    }

    @Override
    protected Blob getCredential()
    {
        return fq.getCredential();
    }
}
