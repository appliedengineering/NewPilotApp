/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package newpilotapp.networking;

import external.org.zeromq.*;
import newpilotapp.logging.Console;

/**
 * Class that handles multi-threading for zeromq networking <br>
 * Acts as the *SERVER*
 * @author Jeffrey
 */
public class AlignmentNetworkingDriver implements Runnable {
    
    // predefined connection settings
    public static final int PORT = 5555;
    
    private volatile boolean isRunning = false;
    
    private ZMQ.Socket alignSocket;
    
    private Thread alignmentThread;

    public AlignmentNetworkingDriver() {
        
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
                String request = alignSocket.recvStr();
           
                // TODO: get the data and send
                
                Console.log("Got data: " + request);

                // currently just echo server for testing
                alignSocket.send(request);
            }
        } catch (Exception e) {
            Console.error(e.toString());
        }
       
    }
    
}
