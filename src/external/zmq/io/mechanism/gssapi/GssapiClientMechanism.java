package external.zmq.io.mechanism.gssapi;

import external.zmq.Msg;
import external.zmq.Options;
import external.zmq.io.SessionBase;
import external.zmq.io.mechanism.Mechanism;

// TODO V4 implement GSSAPI
public class GssapiClientMechanism extends Mechanism
{
    public GssapiClientMechanism(SessionBase session, Options options)
    {
        super(session, null, options);
        throw new UnsupportedOperationException("GSSAPI mechanism is not yet implemented");
    }

    @Override
    public Status status()
    {
        return null;
    }

    @Override
    public int zapMsgAvailable()
    {
        return 0;
    }

    @Override
    public int processHandshakeCommand(Msg msg)
    {
        return 0;
    }

    @Override
    public int nextHandshakeCommand(Msg msg)
    {
        return 0;
    }
}
