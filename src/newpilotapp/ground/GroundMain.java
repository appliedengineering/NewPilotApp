/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.ground;

import newpilotapp.data.BoatDataManager;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.drivers.StepperDriver;
import newpilotapp.ground.data.GroundDataManager;
import newpilotapp.logging.Console;

/**
 *
 * @author jeffrey
 */
public class GroundMain implements Runnable {
    
    public volatile boolean isRunning = false;
    
    private CompassDriver compassDriver = new CompassDriver(GroundDataManager.compassHeading, "1-1.2");

    // private GpsDriver gpsDriver = new GpsDriver(GroundDataManager.localGpsData, "port");
    
    private StepperDriver stepperDriver = new StepperDriver("port");
    
    public volatile long runDelay = 5;

    
    private void init() {
        try {
        compassDriver.init();
        // gpsDriver.init();
        
        stepperDriver.init();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        
        long gpsLastRead = System.currentTimeMillis();

        init();
        
        isRunning = true;
        while(isRunning) {
            try {
                compassDriver.recieveData();     
                if(System.currentTimeMillis()-gpsLastRead > 1000) { // read gps values every second
                    // gpsDriver.recieveData();
                    gpsLastRead = System.currentTimeMillis();
                }
                stepperDriver.sendData(BoatDataManager.telemetryHeading.getValue()); // stepper motor updates itself based on current conditions
                //sector2aDriver.sendData();    

            } catch (Exception e) {
                // make error visible on display (bc hardware issues need to be resolved physically)
                e.printStackTrace();
            }

            try {Thread.sleep(runDelay);} catch (InterruptedException ex) {}
        }
    }
    
}
