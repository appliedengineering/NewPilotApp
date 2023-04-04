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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import newpilotapp.data.BoatDataManager;
import newpilotapp.drivers.DebugDataCalc;
import newpilotapp.drivers.GpsCalc;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.ground.data.GroundDataManager;
import newpilotapp.logging.Console;

/**
 * Class that handles multi-threading for zeromq networking <br>
 * Acts as the *SERVER*
 * @author Jeffrey
 */
public class BoatNetworkingDriver implements Runnable {
    
    // predefined connection settings
    public static final int PORT = 5555;
    
    private volatile boolean isRunning = false;
    
    private ZMQ.Socket alignSocket;
    
    private Thread alignmentThread;
    
    public static final byte REQUEST_ALIGNMENT = 0x01;
    public static final byte REQUEST_DATA = 0x11;


    public BoatNetworkingDriver() {
        
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
        try (ZContext context = new ZContext()) {
            alignSocket = context.createSocket(SocketType.REP);
            alignSocket.setIPv6(true);
            alignSocket.bind("tcp://*:" + PORT);
            Console.log("Networking Started");
            while (isRunning) {
                byte requestCode = 0x00;
                try {
                    byte[] request = alignSocket.recv();
                    // Console.log("Networking Request Recieved");
                    if(request.length>0) {
                        // Console.log("Got data");
                        requestCode = request[0];

                        if(requestCode == REQUEST_ALIGNMENT){

                            // Console.log("Got alignment data: " + request.length);

                            // set
                            BoatDataManager.remoteGpsData.setValue(parseAlignmentReply(request));

                            GpsDriver.GpsData remote = BoatDataManager.remoteGpsData.getValue();
                            GpsDriver.GpsData local = BoatDataManager.localGpsData.getValue();
                            BoatDataManager.telemetryHeading.setValue(GpsCalc.findHeadingOffset(
                                    remote,
                                    local,
                            BoatDataManager.compassHeading.getValue().compassHeading));


                        }
                    }
                } catch (Exception e) {
                    // don't crash networking entirely if something fails LOL
                    Console.log(e.getMessage());
                } finally {
                    if(requestCode == REQUEST_ALIGNMENT){
                       alignSocket.send(getAlignmentDataToSend());

                    } else if(requestCode == REQUEST_DATA) {
                        if(BoatDataManager.isDebugDataOn.getValue()){
                            alignSocket.send(DebugDataCalc.generateDebugData());
                        } else {
                            // send real data

                        }
                    }
                        
                }
            }
        } catch (Exception e) {
            Console.error(e.toString());
        }
       
    }
    
    
    private GpsDriver.GpsData parseAlignmentReply(byte[] reply) {
        GpsDriver.GpsData data = new GpsDriver.GpsData();
        try{
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(reply, 1, reply.length-1); // trim the first byte
            double lat = unpacker.unpackDouble();
            double lon = unpacker.unpackDouble();
            data.lat = lat;
            data.lon = lon;
        }catch(IOException e) {
            Console.error("Failed to unpack alignment data");
        }
        
        return data;

    }
    
    /**
     * Data (command) that is to be sent to boatstation
     * @return 
     */

    private byte[] getAlignmentDataToSend() {
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MessagePacker packer = MessagePack.newDefaultPacker(out);

            GpsDriver.GpsData gps = BoatDataManager.localGpsData.getValue();
            
            if(gps == null) {
                packer.packDouble(0); // latitude
                packer.packDouble(0); // longitude
            } else {
                packer.packDouble(gps.lat); // latitude
                packer.packDouble(gps.lon); // longitude
            }
            
            
            
            packer.close();
            
            return out.toByteArray();

        } catch (IOException e) {
            return new byte[]{}; // empty array
        }
    }
    
//    private byte[] getTelemetryDataToSend() {
//        try {
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            MessagePacker packer = MessagePack.newDefaultPacker(out);
//            
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("int", 1);
//            map.put("long", 1L);
//            map.put("date", new Date());
//            map.put("string", "test");
//
//            // why can i not pack a map???
//            // just use a for loop i guess...
//            
//        } catch (IOException e) {
//            return new byte[]{}; // empty array
//
//        }
//    }
    
}
