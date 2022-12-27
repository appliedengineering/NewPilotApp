package external.zmq.io.net.norm;

import external.zmq.Options;
import external.zmq.io.EngineNotImplemented;
import external.zmq.io.IOThread;
import external.zmq.io.net.Address;

// TODO V4 implement NORM engine
public class NormEngine extends EngineNotImplemented
{
    public NormEngine(IOThread ioThread, Options options)
    {
        throw new UnsupportedOperationException();
    }

    public boolean init(Address addr, boolean b, boolean c)
    {
        return false;
    }
}
