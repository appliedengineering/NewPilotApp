package external.org.zeromq;

import java.nio.charset.Charset;
import java.util.Arrays;

import external.org.zeromq.ZMQ.Socket;
import external.org.zeromq.util.ZData;
import external.zmq.Msg;
import external.zmq.SocketBase;

/**
 * ZFrame
 *
 * The ZFrame class provides methods to send and receive single message
 * frames across 0MQ sockets. A 'frame' corresponds to one underlying zmq_msg_t in the libzmq code.
 * When you read a frame from a socket, the more() method indicates if the frame is part of an
 * unfinished multipart message.  The send() method normally destroys the frame, but with the ZFRAME_REUSE flag, you can send
 * the same frame many times. Frames are binary, and this class has no special support for text data.
 *
 */

public class ZFrame
{
    public static final int MORE     = ZMQ.SNDMORE;
    public static final int REUSE    = 128;         // no effect at java
    public static final int DONTWAIT = ZMQ.DONTWAIT;

    private boolean more;
    private byte[] data;
    private int routingId;
    private String group;

    /**
     * Class Constructor
     * Creates an empty frame.
     * (Useful when reading frames from a 0MQ Socket)
     */
    protected ZFrame()
    {
    }

    /**
     * Class Constructor
     * Copies message data into ZFrame object
     * @param data
     *          Data to copy into ZFrame object
     */
    public ZFrame(byte[] data)
    {
        if (data != null) {
            this.data = data;
        }
    }

    /**
     * Class Constructor
     * Copies String into frame data
     * @param data
     *          String to copy into ZFrame object as bytes, decoded using {@link ZMQ#CHARSET}
     */
    public ZFrame(String data)
    {
        if (data != null) {
            this.data = data.getBytes(ZMQ.CHARSET);
        }
    }

    /**
     * Class Constructor
     * Uses internal Msg class to access routingId
     * @param msg internal Msg class to copy into Zframe
     */
    protected ZFrame(external.zmq.Msg msg)
    {
        if (msg == null) {
            return;
        }
        this.data = msg.data();
        this.more = msg.hasMore();
        this.routingId = msg.getRoutingId();
    }

    /**
     * Return frame routing ID, if the frame came from a ZMQ_SERVER socket.
     * Else returns zero.
     * @return the routing ID
     */
    public int getRoutingId()
    {
        return routingId;
    }

    /**
     * Set routing ID on frame. This is used if/when the frame is sent to a
     * ZMQ_SERVER socket.
     * @param routingId the routing ID
     */
    public void setRoutingId(int routingId)
    {
        this.routingId = routingId;
    }

    /**
     * Gets the group used for RADIO/DISH sockets.
     * @return the group name, or null.
     */
    public String getGroup()
    {
        return group;
    }

    /**
     * Sets the group used for RADIO/DISH sockets.
     * @param group the group name, or null to unset it.
     */
    public void setGroup(String group)
    {
        this.group = group;
    }

    /**
     * Destructor.
     */
    public void destroy()
    {
        if (hasData()) {
            data = null;
        }
    }

    /**
     * @return the data
     */
    public byte[] getData()
    {
        return data;
    }

    public String getString(Charset charset)
    {
        if (!hasData()) {
            return "";
        }
        return new String(data, charset);
    }

    /**
     * @return More flag, true if last read had MORE message parts to come
     */
    public boolean hasMore()
    {
        return more;
    }

    /**
     * Returns byte size of frame, if set, else 0
     * @return
     *          Number of bytes in frame data, else 0
     */
    public int size()
    {
        if (hasData()) {
            return data.length;
        }
        else {
            return 0;
        }
    }

    /**
     * Convenience method to ascertain if this frame contains some message data
     * @return
     *          True if frame contains data
     */
    public boolean hasData()
    {
        return data != null;
    }

    /**
     * Internal method to call org.zeromq.Socket send() method.
     * @param socket
     *          0MQ socket to send on
     * @param flags
     *          Valid send() method flags, defined in org.zeromq.ZMQ class
     * @return
     *          True if success, else False
     */
    public boolean send(Socket socket, int flags)
    {
        Utils.checkArgument(socket != null, "socket parameter must be set");
        final SocketBase base = socket.base();
        final external.zmq.Msg msg = new Msg(data);

        if (group != null) {
            msg.setGroup(group);
        }
        int sendFlags = (flags & ZFrame.MORE) == ZFrame.MORE ? external.zmq.ZMQ.ZMQ_SNDMORE : 0;
        sendFlags |= (flags & ZFrame.DONTWAIT) == ZFrame.DONTWAIT ? external.zmq.ZMQ.ZMQ_DONTWAIT : 0;

        // Only set the routerId if the socket is a ZMQ_Server
        if (base instanceof external.zmq.socket.clientserver.Server) {
            msg.setRoutingId(this.routingId);
        }
        return base.send(msg, sendFlags);
    }

    /**
     * Sends frame to socket if it contains any data.
     * Frame contents are kept after the send.
     * @param socket
     *          0MQ socket to send frame
     * @param flags
     *          Valid send() method flags, defined in org.zeromq.ZMQ class
     * @return
     *          True if success, else False
     */
    public boolean sendAndKeep(Socket socket, int flags)
    {
        return send(socket, flags);
    }

    /**
     * Sends frame to socket if it contains any data.
     * Frame contents are kept after the send.
     * Uses default behaviour of Socket.send() method, with no flags set
     * @param socket
     *          0MQ socket to send frame
     * @return
     *          True if success, else False
     */
    public boolean sendAndKeep(Socket socket)
    {
        return sendAndKeep(socket, 0);
    }

    /**
     * Sends frame to socket if it contains data.
     * Use this method to send a frame and destroy the data after.
     * @param socket
     *          0MQ socket to send frame
     * @param flags
     *          Valid send() method flags, defined in org.zeromq.ZMQ class
     * @return
     *          True if success, else False
     */
    public boolean sendAndDestroy(Socket socket, int flags)
    {
        boolean ret = send(socket, flags);
        if (ret) {
            destroy();
        }
        return ret;
    }

    /**
     * Sends frame to socket if it contains data.
     * Use this method to send an isolated frame and destroy the data after.
     * Uses default behaviour of Socket.send() method, with no flags set
     * @param socket
     *          0MQ socket to send frame
     * @return
     *          True if success, else False
     */
    public boolean sendAndDestroy(Socket socket)
    {
        return sendAndDestroy(socket, 0);
    }

    /**
     * Creates a new frame that duplicates an existing frame
     * @return
     *          Duplicate of frame; message contents copied into new byte array
     */
    public ZFrame duplicate()
    {
        return new ZFrame(this.data);
    }

    /**
     * Returns true if both frames have byte - for byte identical data
     * @param other
     *          The other ZFrame to compare
     * @return
     *          True if both ZFrames have same byte-identical data, else false
     */
    public boolean hasSameData(ZFrame other)
    {
        if (other == null) {
            return false;
        }

        if (size() == other.size()) {
            return Arrays.equals(data, other.data);
        }
        return false;
    }

    /**
     * Sets new contents for frame
     * @param data
     *          New byte array contents for frame
     */
    public void reset(String data)
    {
        this.data = data.getBytes(ZMQ.CHARSET);
    }

    /**
     * Sets new contents for frame
     * @param data
     *          New byte array contents for frame
     */
    public void reset(byte[] data)
    {
        this.data = data;
    }

    /**
     * @return frame data as a printable hex string
     */
    public String strhex()
    {
        return ZData.strhex(data);
    }

    /**
     * String equals.
     * Uses String compareTo for the comparison (lexigraphical)
     * @param str
     *          String to compare with frame data
     * @return
     *          True if frame body data matches given string
     */
    public boolean streq(String str)
    {
        return ZData.streq(data, str);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ZFrame zFrame = (ZFrame) o;
        return Arrays.equals(data, zFrame.data);
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(data);
    }

    /**
     * Returns a human - readable representation of frame's data
     * @return
     *          A text string or hex-encoded string if data contains any non-printable ASCII characters
     */
    @Override
    public String toString()
    {
        return ZData.toString(data);
    }

    /**
     * Receives single frame from socket, returns the received frame object, or null if the recv
     * was interrupted. Does a blocking recv, if you want to not block then use
     * recvFrame(socket, ZMQ.DONTWAIT);
     *
     * @param   socket
     *              Socket to read from
     * @return
     *              received frame, else null
     */
    public static ZFrame recvFrame(Socket socket)
    {
        return ZFrame.recvFrame(socket, 0);
    }

    /**
     * Receive a new frame off the socket, Returns newly-allocated frame, or
     * null if there was no input waiting, or if the read was interrupted.
     * @param   socket
     *              Socket to read from
     * @param   flags
     *              Pass flags to 0MQ socket.recv call
     * @return
     *              received frame, else null
     */
    public static ZFrame recvFrame(Socket socket, int flags)
    {
        final SocketBase base = socket.base();
        external.zmq.Msg msg = base.recv(flags);
        if (msg == null) {
            // Check to see if there was an error in recv
            socket.mayRaise();
            return null;
        }
        ZFrame frame = new ZFrame(msg);
        frame.setGroup(msg.getGroup());
        return frame;
    }

    public void print(String prefix)
    {
        ZData.print(System.out, prefix, getData(), size());
    }
}
