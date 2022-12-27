package external.zmq.msg;

import external.zmq.Msg;

public interface MsgAllocator
{
    Msg allocate(int size);
}
