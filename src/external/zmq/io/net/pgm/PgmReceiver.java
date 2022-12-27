package external.zmq.io.net.pgm;

import external.zmq.Options;
import external.zmq.io.EngineNotImplemented;
import external.zmq.io.IOThread;
import external.zmq.io.net.Address;

//TODO V4 implement pgm receiver
public class PgmReceiver extends EngineNotImplemented
{
    public PgmReceiver(IOThread ioThread, Options options)
    {
        throw new UnsupportedOperationException();
    }

    public boolean init(boolean udpEncapsulation, Address addr)
    {
        return false;
    }
}
