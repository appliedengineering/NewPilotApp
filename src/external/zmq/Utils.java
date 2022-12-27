package external.zmq;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SocketChannel;

import external.zmq.io.net.Address;
import external.zmq.io.net.tcp.TcpUtils;

@Deprecated
public class Utils
{
    private Utils()
    {
    }

    public static int randomInt()
    {
        return external.zmq.util.Utils.randomInt();
    }

    public static byte[] randomBytes(int length)
    {
        return external.zmq.util.Utils.randomBytes(length);
    }

    public static int findOpenPort() throws IOException
    {
        return external.zmq.util.Utils.findOpenPort();
    }

    public static void unblockSocket(SelectableChannel... channels) throws IOException
    {
        TcpUtils.unblockSocket(channels);
    }

    public static <T> T[] realloc(Class<T> klass, T[] src, int size, boolean ended)
    {
        return external.zmq.util.Utils.realloc(klass, src, size, ended);
    }

    public static byte[] bytes(ByteBuffer buf)
    {
        return external.zmq.util.Utils.bytes(buf);
    }

    public static byte[] realloc(byte[] src, int size)
    {
        return external.zmq.util.Utils.realloc(src, size);
    }

    public static boolean delete(File path)
    {
        return external.zmq.util.Utils.delete(path);
    }

    public static Address getPeerIpAddress(SocketChannel fd)
    {
        return external.zmq.util.Utils.getPeerIpAddress(fd);
    }
}
