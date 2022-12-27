package external.zmq.msg;

import external.zmq.Msg;

public class MsgAllocatorHeap implements MsgAllocator
{
    @Override
    public Msg allocate(int size)
    {
        return new Msg(size);
    }
}
