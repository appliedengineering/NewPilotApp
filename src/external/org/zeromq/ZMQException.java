package external.org.zeromq;

import external.zmq.ZError;

public class ZMQException extends UncheckedZMQException
{
    private static final long serialVersionUID = -978820750094924644L;

    private final int code;

    public ZMQException(int errno)
    {
        super("Errno " + errno);
        code = errno;
    }

    public ZMQException(String message, int errno)
    {
        super(message);
        code = errno;
    }

    public ZMQException(String message, int errno, Throwable cause)
    {
        super(message, cause);
        code = errno;
    }

    public int getErrorCode()
    {
        return code;
    }

    @Override
    public String toString()
    {
        return super.toString() + " : " + ZError.toString(code);
    }
}
