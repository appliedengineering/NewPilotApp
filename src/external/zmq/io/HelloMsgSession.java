package external.zmq.io;

import external.zmq.Msg;
import external.zmq.Options;
import external.zmq.SocketBase;
import external.zmq.io.net.Address;

public class HelloMsgSession extends SessionBase
{
    boolean newPipe;

    public HelloMsgSession(IOThread ioThread, boolean connect, SocketBase socket, Options options, Address addr)
    {
        super(ioThread, connect, socket, options, addr);
        newPipe = true;
    }

    @Override
    protected Msg pullMsg()
    {
        if (newPipe) {
            newPipe = false;
            return options.helloMsg;
        }

        return super.pullMsg();
    }

    @Override
    protected void reset()
    {
        super.reset();
        newPipe = true;
    }
}
