/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.networking.test;

import external.org.zeromq.SocketType;
import external.org.zeromq.ZContext;
import external.org.zeromq.ZMQ.Poller;
import external.org.zeromq.ZMQ.Socket;
import newpilotapp.networking.BoatNetworkingDriver;

/**
 *  Check this:
 * https://zguide.zeromq.org/docs/chapter4/#Client-Side-Reliability-Lazy-Pirate-Pattern
 */
public class GroundStationClientAlignment {
    
    private final static int    REQUEST_TIMEOUT = 1000;                  //  msecs
    private final static int    REQUEST_RETRIES = 3;                     //  Before we abandon
    private final static String SERVER_ENDPOINT = "tcp://localhost:5555";

    public static void main(String[] argv)
    {
        try (ZContext ctx = new ZContext()) {
            System.out.println("I: connecting to server");
            Socket client = ctx.createSocket(SocketType.REQ);
            assert (client != null);
            client.connect(SERVER_ENDPOINT);

            Poller poller = ctx.createPoller(1);
            poller.register(client, Poller.POLLIN);

            int sequence = 0;
            int retriesLeft = REQUEST_RETRIES;
            while (retriesLeft > 0 && !Thread.currentThread().isInterrupted()) {
                //  We send a request, then we work to get a reply
                byte[] request = new byte[]{BoatNetworkingDriver.REQUEST_ALIGNMENT};
                client.send(request);

                int expect_reply = 1;
                while (expect_reply > 0) {
                    //  Poll socket for a reply, with timeout
                    int rc = poller.poll(REQUEST_TIMEOUT);
                    if (rc == -1)
                        break; //  Interrupted

                    //  Here we process a server reply and exit our loop if the
                    //  reply is valid. If we didn't a reply we close the client
                    //  socket and resend the request. We try a number of times
                    //  before finally abandoning:

                    if (poller.pollin(0)) {
                        //  We got a reply from the server, must match
                        //  getSequence
                        String reply = client.recvStr();
                        if (reply == null)
                            break; //  Interrupted
//                        if (Integer.parseInt(reply) == sequence) {
                            System.out.printf(
                                "I: server replied OK (%s)\n", reply
                            );
                            retriesLeft = REQUEST_RETRIES;
                            expect_reply = 0;
//                        }
                       

                    }
                    else if (--retriesLeft == 0) {
                        System.out.println(
                            "E: server seems to be offline, abandoning\n"
                        );
                        break;
                    }
                    else {
                        System.out.println(
                            "W: no response from server, retrying\n"
                        );
                        //  Old socket is confused; close it and open a new one
                        poller.unregister(client);
                        ctx.destroySocket(client);
                        System.out.println("I: reconnecting to server\n");
                        client = ctx.createSocket(SocketType.REQ);
                        client.connect(SERVER_ENDPOINT);
                        poller.register(client, Poller.POLLIN);
                        //  Send request again, on new socket
                        client.send(request);
                    }
                }
            }
        }
    }
    
}
