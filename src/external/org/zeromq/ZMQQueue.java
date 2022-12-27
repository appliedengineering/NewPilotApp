package external.org.zeromq;

import external.org.zeromq.ZMQ.Context;
import external.org.zeromq.ZMQ.Socket;

public class ZMQQueue implements Runnable
{
    private final Socket inSocket;
    private final Socket outSocket;

    /**
     * Class constructor.
     *
     * @param context
     *            a 0MQ context previously created.
     * @param inSocket
     *            input socket
     * @param outSocket
     *            output socket
     */
    public ZMQQueue(Context context, Socket inSocket, Socket outSocket)
    {
        this.inSocket = inSocket;
        this.outSocket = outSocket;
    }

    @Override
    public void run()
    {
        external.zmq.ZMQ.proxy(inSocket.base(), outSocket.base(), null);
    }
}
