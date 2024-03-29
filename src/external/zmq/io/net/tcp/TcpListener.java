package external.zmq.io.net.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Locale;

import external.zmq.Options;
import external.zmq.SocketBase;
import external.zmq.ZError;
import external.zmq.io.IOObject;
import external.zmq.io.IOThread;
import external.zmq.io.SessionBase;
import external.zmq.io.StreamEngine;
import external.zmq.io.net.Address.IZAddress;
import external.zmq.io.net.Listener;
import external.zmq.io.net.StandardProtocolFamily;
import external.zmq.poll.Poller;
import external.zmq.socket.Sockets;

public class TcpListener extends Listener
{
    private static final boolean isWindows;
    static {
        String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        isWindows = os.contains("win");
    }

    //  Address to listen on.
    private TcpAddress address;

    //  Underlying socket.
    private ServerSocketChannel fd;
    private Poller.Handle       handle;

    // String representation of endpoint to bind to
    private String endpoint;

    private final IOObject ioObject;

    public TcpListener(IOThread ioThread, SocketBase socket, final Options options)
    {
        super(ioThread, socket, options);

        ioObject = new IOObject(ioThread, this);
        fd = null;
    }

    @Override
    public void destroy()
    {
        assert (fd == null);
        assert (handle == null);
        ioObject.unplug();
    }

    @Override
    protected void processPlug()
    {
        //  Start polling for incoming connections.
        ioObject.plug();
        handle = ioObject.addFd(fd);
        ioObject.setPollAccept(handle);
    }

    @Override
    protected void processTerm(int linger)
    {
        ioObject.removeHandle(handle);
        handle = null;
        close();
        super.processTerm(linger);
    }

    @Override
    public void acceptEvent()
    {
        SocketChannel channel;

        try {
            channel = accept();

            //  If connection was reset by the peer in the meantime, just ignore it.
            if (channel == null) {
                socket.eventAcceptFailed(endpoint, ZError.EADDRNOTAVAIL);
                return;
            }
            TcpUtils.tuneTcpSocket(channel);
            TcpUtils.tuneTcpKeepalives(
                                       channel,
                                       options.tcpKeepAlive,
                                       options.tcpKeepAliveCnt,
                                       options.tcpKeepAliveIdle,
                                       options.tcpKeepAliveIntvl);
        }
        catch (IOException e) {
            //  If connection was reset by the peer in the meantime, just ignore it.
            //  TODO: Handle specific errors like ENFILE/EMFILE etc.
            socket.eventAcceptFailed(endpoint, ZError.exccode(e));
            return;
        }

        // remember our fd for ZMQ_SRCFD in messages
        //        socket.setFd(channel);

        //  Create the engine object for this connection.
        StreamEngine engine = null;
        try {
            engine = new StreamEngine(channel, options, endpoint);
        }
        catch (ZError.InstantiationException e) {
            socket.eventAcceptFailed(endpoint, ZError.EINVAL);
            return;
        }

        //  Choose I/O thread to run connecter in. Given that we are already
        //  running in an I/O thread, there must be at least one available.
        IOThread ioThread = chooseIoThread(options.affinity);
        assert (ioThread != null);

        //  Create and launch a session object.
        SessionBase session = Sockets.createSession(ioThread, false, socket, options, null);
        assert (session != null);

        session.incSeqnum();
        launchChild(session);
        sendAttach(session, engine, false);
        socket.eventAccepted(endpoint, channel);
    }

    //  Close the listening socket.
    private void close()
    {
        assert (fd != null);

        try {
            fd.close();
            socket.eventClosed(endpoint, fd);
        }
        catch (IOException e) {
            socket.eventCloseFailed(endpoint, ZError.exccode(e));
        }
        fd = null;
    }

    public String getAddress()
    {
        return address(address);
    }

    protected String address(IZAddress address)
    {
        int port = fd.socket().getLocalPort();
        return address.toString(port);
    }

    //  Set address to listen on.
    public boolean setAddress(final String addr)
    {
        //  Convert the textual address into address structure.
        address = new TcpAddress(addr, options.ipv6);
        return setAddress();
    }

    //  Set address to listen on, used by IpcListener that already resolved the address.
    protected boolean setAddress(InetSocketAddress addr)
    {
        //  Convert the textual address into address structure.
        address = new TcpAddress(addr);
        return setAddress();
    }

    private boolean setAddress()
    {
        endpoint = address.toString();

        //  Create a listening socket.
        try {
            if (options.selectorChooser == null) {
                fd = ServerSocketChannel.open();
            }
            else {
                fd = options.selectorChooser.choose(address, options).openServerSocketChannel();
            }

            // On some systems, IPv4 mapping in IPv6 sockets is disabled by default.
            // Switch it on in such cases.
            // The method enableIpv4Mapping is empty. Still to be written
            if (address.family() == StandardProtocolFamily.INET6) {
                TcpUtils.enableIpv4Mapping(fd);
            }

            TcpUtils.unblockSocket(fd);

            //  Set the socket buffer limits for the underlying socket.
            if (options.sndbuf != 0) {
                TcpUtils.setTcpSendBuffer(fd, options.sndbuf);
            }
            if (options.rcvbuf != 0) {
                TcpUtils.setTcpReceiveBuffer(fd, options.rcvbuf);
            }

            if (!isWindows) {
                TcpUtils.setReuseAddress(fd, true);
            }

            //  Bind the socket to the network interface and port.
            // NB: fd.socket().bind(...) for Android environments
            fd.socket().bind(address.address(), options.backlog);
            // find the address in case of wildcard
            endpoint = getAddress();
        }
        catch (IOException e) {
            close();
            errno.set(ZError.EADDRINUSE);
            return false;
        }
        socket.eventListening(endpoint, fd);
        return true;
    }

    //  Accept the new connection. Returns the file descriptor of the
    //  newly created connection. The function may throw IOException
    //  if the connection was dropped while waiting in the listen backlog
    //  or was denied because of accept filters.
    private SocketChannel accept() throws IOException
    {
        //  The situation where connection cannot be accepted due to insufficient
        //  resources is considered valid and treated by ignoring the connection.
        //  Accept one connection and deal with different failure modes.
        assert (fd != null);

        SocketChannel sock = fd.accept();

        if (!options.tcpAcceptFilters.isEmpty()) {
            boolean matched = false;
            for (TcpAddress.TcpAddressMask am : options.tcpAcceptFilters) {
                if (am.matchAddress(address.address())) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                try {
                    sock.close();
                }
                catch (IOException e) {
                }
                return null;
            }
        }
        if (options.tos != 0) {
            TcpUtils.setIpTypeOfService(sock, options.tos);
        }
        //  Set the socket buffer limits for the underlying socket.
        if (options.sndbuf != 0) {
            TcpUtils.setTcpSendBuffer(sock, options.sndbuf);
        }
        if (options.rcvbuf != 0) {
            TcpUtils.setTcpReceiveBuffer(sock, options.rcvbuf);
        }

        if (!isWindows) {
            TcpUtils.setReuseAddress(sock, true);
        }

        return sock;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "[" + options.socketId + "]";
    }
}
