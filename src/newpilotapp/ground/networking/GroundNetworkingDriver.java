/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.ground.networking;

import external.org.msgpack.core.MessagePack;
import external.org.msgpack.core.MessagePacker;
import external.org.msgpack.core.MessageUnpacker;
import external.org.zeromq.SocketType;
import external.org.zeromq.ZContext;
import external.org.zeromq.ZMQ;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import newpilotapp.data.BoatDataManager;
import newpilotapp.ground.data.GroundDataManager;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.logging.Console;
import newpilotapp.networking.BoatNetworkingDriver;

/**
 *  Check this:
 * https://zguide.zeromq.org/docs/chapter4/#Client-Side-Reliability-Lazy-Pirate-Pattern
 */
public class GroundNetworkingDriver implements Runnable {
    
    private final static int    REQUEST_TIMEOUT = 1000;                  //  msecs
    private final static int    REQUEST_RETRIES = 10; // Integer.MAX_VALUE;     //  retry infinitely
    private final static String SERVER_ENDPOINT = "tcp://169.254.225.222:5555";
    
    public volatile long runDelay = 0;


    @Override
    public void run() {
        int count = 0;
        try (ZContext ctx = new ZContext()) {
            ZMQ.Socket client = ctx.createSocket(SocketType.REQ);
            assert (client != null);
            client.setIPv6(true);
            client.connect(SERVER_ENDPOINT);
            
            ZMQ.Poller poller = ctx.createPoller(1);
            poller.register(client, ZMQ.Poller.POLLIN);
            
            int retriesLeft = REQUEST_RETRIES;
            while (retriesLeft > 0 && !Thread.currentThread().isInterrupted()) {
                // FINISH
                byte[] request = getDataToSend();
                if(request.length == 0) continue;
                long start = System.currentTimeMillis();
                client.send(request);

                int expect_reply = 1;
                while (expect_reply > 0) {
                    //  Poll socket for a reply, with timeout
                    int rc = poller.poll(REQUEST_TIMEOUT);
                    if (rc == -1)
                        break; //  Interrupted


                    if (poller.pollin(0)) {
                        //  We got a reply from the server
                        byte[] reply = client.recv();
                        
                        parseReply(reply);

                        if (reply == null)
                            break; //  Interrupted
                        boolean success = parseReply(reply);
                        if (success) {
                            count++;
                            // System.out.println("Success! " + count);
                            System.out.println(System.currentTimeMillis()-start);
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
                
                try {Thread.sleep(runDelay);} catch (InterruptedException ex) {}
            }
        }
    }

    
    /**
     * Parses data from boatstation
     * @param reply messagepack data in bytes
     * @return true if valid data, false if corrupted
     */
    private boolean parseReply(byte[] reply) {
        GpsDriver.GpsData data = GroundDataManager.remoteGpsData.getValue();
        if(data == null) {
            data = new GpsDriver.GpsData();
        }
        try{
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(reply);
            double lat = unpacker.unpackDouble();
            double lon = unpacker.unpackDouble();
            data.lat = lat;
            data.lon = lon;
            GroundDataManager.remoteGpsData.setValue(data);
        }catch(IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * Data (command) that is to be sent to boatstation
     * @return 
     */

    private byte[] getDataToSend() {
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            
            out.write(BoatNetworkingDriver.REQUEST_ALIGNMENT);
            
            MessagePacker packer = MessagePack.newDefaultPacker(out);

            GpsDriver.GpsData gps = GroundDataManager.localGpsData.getValue();

            // pack map (key -> value) elements
            // packer.packMapHeader(2); // the number of (key, value) pairs

            // packer.packString("la"); // latitude
            packer.packDouble(Math.random());

            // packer.packString("lo"); // longitude
            packer.packDouble(Math.random());
            
            packer.close();
            
            return out.toByteArray();

        } catch (IOException e) {
            return new byte[]{}; // empty array
        }


    }
    
}
