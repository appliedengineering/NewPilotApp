package external.zmq.io.net.tipc;

import external.zmq.Options;
import external.zmq.SocketBase;
import external.zmq.io.IOThread;
import external.zmq.io.net.tcp.TcpListener;

public class TipcListener extends TcpListener
{
    public TipcListener(IOThread ioThread, SocketBase socket, final Options options)
    {
        super(ioThread, socket, options);
        // TODO V4 implement tipc
        throw new UnsupportedOperationException("TODO implement tipc");
    }
}
