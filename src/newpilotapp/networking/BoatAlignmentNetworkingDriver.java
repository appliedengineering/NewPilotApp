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
import newpilotapp.data.BoatDataManager;
import newpilotapp.drivers.GpsCalc;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.ground.data.GroundDataManager;
import newpilotapp.logging.Console;

/**
 * Class that handles multi-threading for zeromq networking <br>
 * Acts as the *SERVER*
 * @author Jeffrey
 */
public class BoatAlignmentNetworkingDriver implements Runnable {
    
    // predefined connection settings
    public static final int PORT = 5555;
    
    private volatile boolean isRunning = false;
    
    private ZMQ.Socket alignSocket;
    
    private Thread alignmentThread;

    public BoatAlignmentNetworkingDriver() {
        
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
            alignSocket.bind("tcp://*:" + PORT);
            
            while (isRunning) {
                byte[] request = alignSocket.recv();
           
                // TODO: get the data and send
                
                Console.log("Got data: " + request.length);
                
                // set
                BoatDataManager.remoteGpsData.setValue(parseReply(request));
                
                GpsDriver.GpsData remote = BoatDataManager.remoteGpsData.getValue();
                GpsDriver.GpsData local = BoatDataManager.localGpsData.getValue();
                BoatDataManager.telemetryHeading.setValue(GpsCalc.findHeadingOffset(remote, local));

                
                alignSocket.send(getDataToSend());
            }
        } catch (Exception e) {
            Console.error(e.toString());
        }
       
    }
    
    
    private GpsDriver.GpsData parseReply(byte[] reply) {
        GpsDriver.GpsData data = new GpsDriver.GpsData();
        try{
            MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(reply);
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

    private byte[] getDataToSend() {
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MessagePacker packer = MessagePack.newDefaultPacker(out);

            GpsDriver.GpsData gps = BoatDataManager.localGpsData.getValue();

            // pack map (key -> value) elements
            // packer.packMapHeader(2); // the number of (key, value) pairs

            // packer.packString("la"); 
            packer.packDouble(gps.lat); // latitude

            // packer.packString("lo"); 
            packer.packDouble(gps.lon); // longitude
            
            packer.close();
            
            return out.toByteArray();

        } catch (IOException e) {
            return new byte[]{}; // empty array
        }
    }
    
}
