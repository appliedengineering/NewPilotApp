/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.main;

import newpilotapp.data.BoatDataManager;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.drivers.GpsCalc;
import newpilotapp.drivers.StepperDriver;
import newpilotapp.logging.Console;

/**
 * Real-time readings from physical hardware
 * @author jeffrey
 */
public class HardwareDriverLoop implements Runnable {
    
    private CompassDriver compassDriver = new CompassDriver(BoatDataManager.compassHeading, "1-1.1");

    private GpsDriver gpsDriver = new GpsDriver(BoatDataManager.localGpsData, "1-1.4");
    
    private StepperDriver stepperDriver = new StepperDriver("1-1.2");


    
    public volatile boolean isRunning = false;
    
    public volatile long runDelay = 5;
        
    private void init() {
        try {
        gpsDriver.init();
        compassDriver.init();
        stepperDriver.init();
        // sector2aDriver.init();

        } catch (Exception e) {
            // make error visible on display (bc hardware issues need to be resolved physically)
            Console.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        
        long gpsLastRead = System.currentTimeMillis();
        
        init();
        isRunning = true;
        while(isRunning){
            try {
                compassDriver.recieveData();
                if(System.currentTimeMillis()-gpsLastRead > 1000) { // read gps values every second
                    gpsDriver.recieveData();
                    gpsLastRead = System.currentTimeMillis();
                }
                
                double compassHeading = BoatDataManager.compassHeading.getValue().compassHeading;
                double headingOffset = GpsCalc.findHeadingOffset(
                        BoatDataManager.remoteGpsData.getValue(), 
                        BoatDataManager.localGpsData.getValue(),
                        compassHeading);
                
                BoatDataManager.telemetryHeading.setValue(compassHeading+headingOffset);
                
                stepperDriver.sendData(headingOffset); // stepper motor updates itself based on current conditions
                //sector2aDriver.sendData();    

            } catch (Exception e) {
                // make error visible on display (bc hardware issues need to be resolved physically)
                // Console.error(e.getMessage());
            }

            try {Thread.sleep(runDelay);} catch (InterruptedException ex) {}
        }
        
       
    }
    
    public void stop() {
        isRunning = false;
    }

    public void stopAllRunningTasks() {
        compassDriver.stop();
    }
    
    
    
}
