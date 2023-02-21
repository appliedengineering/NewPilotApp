/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.main;

import newpilotapp.data.DataManager;
import newpilotapp.drivers.Sector2aDriver;
import newpilotapp.drivers.Sector2bDriver;
import newpilotapp.logging.Console;

/**
 * For data that only needs to be refreshed at a low speed
 * @author jeffrey
 */
public class LowRefreshDriverLoop implements Runnable {
    
    
    public volatile boolean isRunning = false;
    
    public volatile long runDelay = 100;
    
    private void init() {
        
    }

    @Override
    public void run() {
        
            init();
            isRunning = true;
            while(isRunning){
                try {
                    // Get current size of heap in bytes
                    long heapSize = Runtime.getRuntime().totalMemory()/1_000_000; 
                    long heapFreeSize = Runtime.getRuntime().freeMemory()/1_000_000; 
                    
                    DataManager.heapSpaceTotal.setValue(heapSize);
                    DataManager.heapSpaceFree.setValue(heapFreeSize);

                    
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
        
    }
    
    
    
}
