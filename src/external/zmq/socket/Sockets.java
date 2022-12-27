package external.zmq.socket;

import java.util.Arrays;
import java.util.List;

import external.zmq.Ctx;
import external.zmq.Options;
import external.zmq.SocketBase;
import external.zmq.io.HelloMsgSession;
import external.zmq.io.IOThread;
import external.zmq.io.SessionBase;
import external.zmq.io.net.Address;
import external.zmq.socket.pipeline.Pull;
import external.zmq.socket.pipeline.Push;
import external.zmq.socket.pubsub.Pub;
import external.zmq.socket.pubsub.Sub;
import external.zmq.socket.pubsub.XPub;
import external.zmq.socket.pubsub.XSub;
import external.zmq.socket.radiodish.Dish;
import external.zmq.socket.radiodish.Radio;
import external.zmq.socket.reqrep.Dealer;
import external.zmq.socket.reqrep.Rep;
import external.zmq.socket.reqrep.Req;
import external.zmq.socket.reqrep.Router;
import external.zmq.socket.clientserver.Server;
import external.zmq.socket.clientserver.Client;
import external.zmq.socket.scattergather.Gather;
import external.zmq.socket.scattergather.Scatter;

public enum Sockets
{
    PAIR("PAIR") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Pair(parent, tid, sid);
        }
    },
    PUB("SUB", "XSUB") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Pub(parent, tid, sid);
        }
    },
    SUB("PUB", "XPUB") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Sub(parent, tid, sid);
        }
    },
    REQ("REP", "ROUTER") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Req(parent, tid, sid);
        }

        @Override
        public SessionBase create(IOThread ioThread, boolean connect, SocketBase socket, Options options, Address addr)
        {
            return new Req.ReqSession(ioThread, connect, socket, options, addr);
        }
    },
    REP("REQ", "DEALER") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Rep(parent, tid, sid);
        }
    },
    DEALER("REP", "DEALER", "ROUTER") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Dealer(parent, tid, sid);
        }
    },
    ROUTER("REQ", "DEALER", "ROUTER") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Router(parent, tid, sid);
        }
    },
    PULL("PUSH") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Pull(parent, tid, sid);
        }
    },
    PUSH("PULL") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Push(parent, tid, sid);
        }
    },
    XPUB("SUB", "XSUB") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new XPub(parent, tid, sid);
        }
    },
    XSUB("PUB", "XPUB") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new XSub(parent, tid, sid);
        }
    },
    STREAM {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Stream(parent, tid, sid);
        }
    },
    SERVER("CLIENT") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Server(parent, tid, sid);
        }
    },
    CLIENT("SERVER") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Client(parent, tid, sid);
        }
    },
    RADIO("DISH") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Radio(parent, tid, sid);
        }

        @Override
        public SessionBase create(IOThread ioThread, boolean connect, SocketBase socket, Options options, Address addr)
        {
            return new Radio.RadioSession(ioThread, connect, socket, options, addr);
        }
    },
    DISH("RADIO") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Dish(parent, tid, sid);
        }

        @Override
        public SessionBase create(IOThread ioThread, boolean connect, SocketBase socket, Options options, Address addr)
        {
            return new Dish.DishSession(ioThread, connect, socket, options, addr);
        }
    },
    CHANNEL("CHANNEL") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Channel(parent, tid, sid);
        }
    },
    PEER("PEER") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Peer(parent, tid, sid);
        }
    },
    RAW {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Raw(parent, tid, sid);
        }
    },
    SCATTER("GATHER") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Scatter(parent, tid, sid);
        }
    },
    GATHER("SCATTER") {
        @Override
        SocketBase create(Ctx parent, int tid, int sid)
        {
            return new Gather(parent, tid, sid);
        }
    };

    private final List<String> compatible;

    Sockets(String... compatible)
    {
        this.compatible = Arrays.asList(compatible);
    }

    //  Create a socket of a specified type.
    abstract SocketBase create(Ctx parent, int tid, int sid);

    public SessionBase create(IOThread ioThread, boolean connect, SocketBase socket, Options options, Address addr)
    {
        if (options.canSendHelloMsg && options.helloMsg != null) {
            return new HelloMsgSession(ioThread, connect, socket, options, addr);
        }
        else {
            return new SessionBase(ioThread, connect, socket, options, addr);
        }
    }

    public static SessionBase createSession(IOThread ioThread, boolean connect, SocketBase socket, Options options,
                                            Address addr)
    {
        return values()[options.type].create(ioThread, connect, socket, options, addr);
    }

    public static SocketBase create(int socketType, Ctx parent, int tid, int sid)
    {
        return values()[socketType].create(parent, tid, sid);
    }

    public static String name(int socketType)
    {
        return values()[socketType].name();
    }

    public static Sockets fromType(int socketType)
    {
        return values()[socketType];
    }

    public static boolean compatible(int self, String peer)
    {
        return values()[self].compatible.contains(peer);
    }
}
