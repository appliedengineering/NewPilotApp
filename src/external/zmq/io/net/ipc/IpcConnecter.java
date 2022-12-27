package external.zmq.io.net.ipc;

import external.zmq.Options;
import external.zmq.io.IOThread;
import external.zmq.io.SessionBase;
import external.zmq.io.net.Address;
import external.zmq.io.net.tcp.TcpConnecter;

public class IpcConnecter extends TcpConnecter
{
    public IpcConnecter(IOThread ioThread, SessionBase session, final Options options, final Address addr, boolean wait)
    {
        super(ioThread, session, options, addr, wait);
    }
}
