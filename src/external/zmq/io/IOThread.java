package external.zmq.io;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SelectableChannel;

import external.zmq.Command;
import external.zmq.Ctx;
import external.zmq.Mailbox;
import external.zmq.ZObject;
import external.zmq.poll.IPollEvents;
import external.zmq.poll.Poller;

public class IOThread extends ZObject implements IPollEvents, Closeable
{
    //  I/O thread accesses incoming commands via this mailbox.
    private final Mailbox mailbox;

    //  Handle associated with mailbox' file descriptor.
    private final Poller.Handle mailboxHandle;

    //  I/O multiplexing is performed using a poller object.
    private final Poller poller;

    private final String name;

    public IOThread(Ctx ctx, int tid)
    {
        super(ctx, tid);
        name = "iothread-" + tid;
        poller = new Poller(ctx, name);

        mailbox = new Mailbox(ctx, name, tid);
        SelectableChannel fd = mailbox.getFd();
        mailboxHandle = poller.addHandle(fd, this);
        poller.setPollIn(mailboxHandle);
    }

    public void start()
    {
        poller.start();
    }

    @Override
    public void close() throws IOException
    {
        poller.destroy();
        mailbox.close();
    }

    public void stop()
    {
        sendStop();
    }

    public Mailbox getMailbox()
    {
        return mailbox;
    }

    public int getLoad()
    {
        return poller.getLoad();
    }

    @Override
    public void inEvent()
    {
        //  TODO: Do we want to limit number of commands I/O thread can
        //  process in a single go?

        while (true) {
            //  Get the next command. If there is none, exit.
            Command cmd = mailbox.recv(0);
            if (cmd == null) {
                break;
            }

            //  Process the command.
            cmd.process();
        }
    }

    Poller getPoller()
    {
        assert (poller != null);
        return poller;
    }

    @Override
    protected void processStop()
    {
        poller.removeHandle(mailboxHandle);

        poller.stop();
    }
}
