package external.org.zeromq.timer;

import external.zmq.util.Draft;
import external.zmq.util.Timers;

/**
 * Called when the time has come to perform some action.
 * This is a DRAFT class, and may change without notice.
 */
@Draft
public interface TimerHandler extends Timers.Handler
{
    @Override
    void time(Object... args);
}
