/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package newpilotapp.networking;

import external.org.msgpack.core.MessagePack;
import external.org.msgpack.core.MessagePacker;
import external.org.msgpack.core.MessageUnpacker;
import external.org.zeromq.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import newpilotapp.data.BoatDataManager;
import newpilotapp.data.DataPoint;
import newpilotapp.drivers.DebugDataCalc;
import newpilotapp.drivers.GpsCalc;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.logging.Console;

/**
 * Class that handles multi-threading for zeromq networking <br>
 * Acts as the *CLIENT*
 * @author Jeffrey
 */
public class GroundNetworkingDriver implements Runnable {
    
    // predefined connection settings
    public static final int PORT = 5555;
    
    private volatile boolean isRunning = false;
    
    private ZMQ.Socket alignSocket;
    
    private Thread alignmentThread;
    
    private final static int    REQUEST_TIMEOUT = 1000;                  //  msecs
    private final static int    REQUEST_RETRIES = 1000;                     //  Before we abandon
    private final static String SERVER_ENDPOINT = "tcp://localhost:5555";


    public GroundNetworkingDriver() {
        
    }

    public void start() {
        if(isRunning) throw new IllegalStateException("Must stop() before starting!");
        isRunning = true;
        alignmentThread = new Thread(this);
        alignmentThread.start();
    }
    
    public void stop() {
        isRunning = false;
        alignmentThread.interrupt();
    }
    
    public void restart() {
        stop();
        start();
    }
    
    @Override
    public void run() {
        try (ZContext ctx = new ZContext()) {
            System.out.println("I: connecting to server");
            ZMQ.Socket client = ctx.createSocket(SocketType.REQ);
            assert (client != null);
            client.connect(SERVER_ENDPOINT);

            ZMQ.Poller poller = ctx.createPoller(1);
            poller.register(client, ZMQ.Poller.POLLIN);

            int sequence = 0;
            int retriesLeft = REQUEST_RETRIES;
            
            boolean alignment = false; // variable to alternate between alignment and data requests
            
            while (retriesLeft > 0 && !Thread.currentThread().isInterrupted()) {
                //  We send a request, then we work to get a reply
                byte[] request;
                
                if(alignment){
                     request = BoatNetworkingDriver.getAlignmentDataToSend();
                } else {
                    request = BoatNetworkingDriver.REQUEST_DATA_ARR;
                }

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
                        //  We got a reply from the server
                        byte[] reply = client.recv();
                        if (reply == null) break; //  Interrupted

                        if(alignment){
                            GpsDriver.GpsData gps = BoatNetworkingDriver.parseAlignmentReply(reply);
                            BoatDataManager.remoteGpsData.setValue(gps);
                        } else {
                            parseTelemetryData(reply);
                        }
                        
                        System.out.println(
                                "I: got reply"
                            );
                        
                        try{Thread.sleep(1000);}catch(Exception e){}
                        
                        retriesLeft = REQUEST_RETRIES;
                        expect_reply = 0;
                        alignment =! alignment;

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
                        poller.register(client, ZMQ.Poller.POLLIN);
                        //  Send request again, on new socket
                        client.send(request);
                    }
                }
            }
        }
    }
    
    private void parseTelemetryData(byte[] reply) {
        try{
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(reply);
            Map<String, List<DataPoint>> data = BoatDataManager.dataFromBoatController.getValue();

            for(int i = 0; i < BoatDataManager.DATA_KEYS.length; i++) {
                List<DataPoint> points = data.get(BoatDataManager.DATA_KEYS[i]);
                DataPoint lastPoint;
                if(points == null) {
                    points = new ArrayList<>();
                    data.put(BoatDataManager.DATA_KEYS[i], points);
                }
                
                lastPoint = new DataPoint();

                    
                if(BoatDataManager.isDataKeyNumerical(BoatDataManager.DATA_KEYS[i])) {
                    lastPoint.valueDouble = unpacker.unpackFloat(); // reduce bytes
                } else {
                    lastPoint.valueBool = unpacker.unpackBoolean();
                }
                points.add(lastPoint);

            }
            BoatDataManager.dataFromBoatController.valueWasUpdated();
        }catch(IOException e) {
            Console.error("Failed to unpack alignment data");
        }
    
    }
 
    
}
