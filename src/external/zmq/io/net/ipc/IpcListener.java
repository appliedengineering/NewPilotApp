package external.zmq.io.net.ipc;

import java.net.InetSocketAddress;

import external.zmq.Options;
import external.zmq.SocketBase;
import external.zmq.io.IOThread;
import external.zmq.io.net.tcp.TcpListener;

// fake Unix domain socket
public class IpcListener extends TcpListener
{
    private IpcAddress address;

    public IpcListener(IOThread ioThread, SocketBase socket, final Options options)
    {
        super(ioThread, socket, options);

    }

    // Get the bound address for use with wildcards
    @Override
    public String getAddress()
    {
        if (((InetSocketAddress) address.address()).getPort() == 0) {
            return address(address);
        }
        return address.toString();
    }

    //  Set address to listen on.
    @Override
    public boolean setAddress(String addr)
    {
        address = new IpcAddress(addr);

        InetSocketAddress sock = (InetSocketAddress) address.address();
        return super.setAddress(sock);
    }
}
