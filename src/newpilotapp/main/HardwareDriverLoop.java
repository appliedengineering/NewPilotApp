/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.main;

import java.util.logging.Level;
import java.util.logging.Logger;
import newpilotapp.data.DataManager;
import newpilotapp.drivers.Sector2aDriver;
import newpilotapp.drivers.Sector2bDriver;
import newpilotapp.drivers.Sector2bDriver.CompassData;
import newpilotapp.logging.Console;

/**
 *
 * @author jeffrey
 */
public class HardwareDriverLoop implements Runnable {
    
    private Sector2bDriver sector2bDriver = new Sector2bDriver();

    private Sector2aDriver sector2aDriver = new Sector2aDriver();

    
    public volatile boolean isRunning = false;
    
    public volatile long runDelay = 5;
    
    private void init() {
        try {
        sector2bDriver.init();
        sector2aDriver.init();

        } catch (Exception e) {
            // make error visible on display (bc hardware issues need to be resolved physically)
            Console.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        
            init();
            isRunning = true;
            while(isRunning){
                try {
                    sector2bDriver.recieveData();    
                    sector2aDriver.recieveData();    
                    sector2aDriver.sendData();    
                                        
                } catch (Exception e) {
                    // make error visible on display (bc hardware issues need to be resolved physically)
                    Console.error(e.getMessage());
                }

                try {Thread.sleep(runDelay);} catch (InterruptedException ex) {}
            }
        
       
    }
    
    public void stop() {
        isRunning = false;
    }

    public void stopAllRunningTasks() {
        sector2bDriver.stop();
    }
    
    
    
}
