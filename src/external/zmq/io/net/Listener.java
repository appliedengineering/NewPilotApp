package external.zmq.io.net;

import external.zmq.Options;
import external.zmq.Own;
import external.zmq.SocketBase;
import external.zmq.io.IOThread;
import external.zmq.poll.IPollEvents;

public abstract class Listener extends Own implements IPollEvents
{
    //  Socket the listener belongs to.
    protected final SocketBase socket;

    protected Listener(IOThread ioThread, SocketBase socket, final Options options)
    {
        super(ioThread, options);
        this.socket = socket;
    }

    public abstract boolean setAddress(String addr);

    public abstract String getAddress();
}
