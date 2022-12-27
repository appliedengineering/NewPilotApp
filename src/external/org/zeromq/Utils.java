package external.org.zeromq;

import java.io.IOException;

public class Utils
{
    private Utils()
    {
    }

    public static int findOpenPort() throws IOException
    {
        return external.zmq.util.Utils.findOpenPort();
    }

    public static void checkArgument(boolean expression, String errorMessage)
    {
        external.zmq.util.Utils.checkArgument(expression, errorMessage);
    }
}
