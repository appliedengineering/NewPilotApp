/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.main;

import java.util.logging.Level;
import java.util.logging.Logger;
import newpilotapp.data.DataManager;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.drivers.CompassDriver.CompassData;
import newpilotapp.logging.Console;

/**
 *
 * @author jeffrey
 */
public class HardwareDriverLoop implements Runnable {
    
    private CompassDriver compassDriver = new CompassDriver();

    public volatile boolean isRunning = false;
    
    public volatile long runDelay = 10;
    
    private void init() {
        compassDriver.init();
    }

    @Override
    public void run() {
        try {
            init();
            isRunning = true;
            while(isRunning){
                compassDriver.recieveData();              

                try {Thread.sleep(runDelay);} catch (InterruptedException ex) {}
            }
        } catch (Exception e) {
            // make error visible on display (bc hardware issues need to be resolved physically)
            Console.error(e.getMessage());
        }
       
    }
    
    public void stop() {
        isRunning = false;
    }

    public void stopAllRunningTasks() {
        compassDriver.stop();
    }
    
    
    
}
