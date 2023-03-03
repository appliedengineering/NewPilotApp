/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.ground.networking;

import external.org.zeromq.SocketType;
import external.org.zeromq.ZContext;
import external.org.zeromq.ZMQ;

/**
 *  Check this:
 * https://zguide.zeromq.org/docs/chapter4/#Client-Side-Reliability-Lazy-Pirate-Pattern
 */
public class GroundNetworkingDriver implements Runnable {
    
    private final static int    REQUEST_TIMEOUT = 1000;                  //  msecs
    private final static int    REQUEST_RETRIES = Integer.MAX_VALUE;     //  retry infinitely
    private final static String SERVER_ENDPOINT = "tcp://localhost:5555";

    @Override
    public void run() {
        try (ZContext ctx = new ZContext()) {
            ZMQ.Socket client = ctx.createSocket(SocketType.REQ);
            assert (client != null);
            client.connect(SERVER_ENDPOINT);
            
            ZMQ.Poller poller = ctx.createPoller(1);
            poller.register(client, ZMQ.Poller.POLLIN);
            
            int sequence = 0;
            int retriesLeft = REQUEST_RETRIES;
            while (retriesLeft > 0 && !Thread.currentThread().isInterrupted()) {
                // FINISH
                String request = String.format("%d", ++sequence);
                client.send(request);

                int expect_reply = 1;
                while (expect_reply > 0) {
                    //  Poll socket for a reply, with timeout
                    int rc = poller.poll(REQUEST_TIMEOUT);
                    if (rc == -1)
                        break; //  Interrupted


                    if (poller.pollin(0)) {
                        //  We got a reply from the server, must match
                        //  getSequence
                        byte[] reply = client.recv();
                        if (reply == null)
                            break; //  Interrupted
                        boolean success = parseReply(reply);
                        if (success) {
                            retriesLeft = REQUEST_RETRIES;
                            expect_reply = 0;
                        }

                    } else if (--retriesLeft == 0) {
                        System.out.println("E: server seems to be offline, abandoning\n");
                        break;
                    } else {
                        System.out.println("W: no response from server, retrying\n");
                        //  Old socket is confused; close it and open a new one
                        poller.unregister(client);
                        ctx.destroySocket(client);
                        System.out.println("I: reconnecting to server\n");
                        client = ctx.createSocket(SocketType.REQ);
                        client.connect(SERVER_ENDPOINT);
                        poller.register(client, ZMQ.Poller.POLLIN);
                        //  Send request again, on new socket
                        client.send(request);
                    }
                }
            }
        }
    }

    
    /**
     * Parses data from boatstation
     * @param reply messagepack data in bytes
     * @return true if valid data, false if corrupted
     */
    private boolean parseReply(byte[] reply) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
