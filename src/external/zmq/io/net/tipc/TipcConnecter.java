package external.zmq.io.net.tipc;

import external.zmq.Options;
import external.zmq.io.IOThread;
import external.zmq.io.SessionBase;
import external.zmq.io.net.Address;
import external.zmq.io.net.tcp.TcpConnecter;

public class TipcConnecter extends TcpConnecter
{
    public TipcConnecter(IOThread ioThread, SessionBase session, final Options options, final Address addr,
            boolean wait)
    {
        super(ioThread, session, options, addr, wait);
        // TODO V4 implement Tipc
        throw new UnsupportedOperationException("TODO implement Tipc");
    }
}
