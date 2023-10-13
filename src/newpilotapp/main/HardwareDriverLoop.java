/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.main;

import external.org.msgpack.core.MessagePack;
import external.org.msgpack.core.MessageUnpacker;
import java.io.IOException;
import newpilotapp.data.BoatDataManager;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.drivers.GpsCalc;
import newpilotapp.drivers.SerialDriver;
import newpilotapp.drivers.SerialDriver.SerialData;
import newpilotapp.drivers.StepperDriver;
import newpilotapp.ground.data.GroundDataManager;
import newpilotapp.logging.Console;

/**
 * Real-time readings from physical hardware
 * @author jeffrey
 */
public class HardwareDriverLoop implements Runnable {
    
    private CompassDriver compassDriver = new CompassDriver(BoatDataManager.compassHeading, "1-1.1");

    private GpsDriver gpsDriver = new GpsDriver(BoatDataManager.localGpsData, "1-1.4");
    
    private StepperDriver stepperDriver = new StepperDriver("1-1.2");
    
    private SerialDriver motorControllerDriver = new SerialDriver();


    
    public volatile boolean isRunning = false;
    
    public volatile long runDelay = 10;
        
    private void init() {
        try {
        gpsDriver.init();
        compassDriver.init();
        stepperDriver.init();
        
//        motorControllerDriver.setSerialPortName("1-1.2");
//        motorControllerDriver.init();
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
//                if(System.currentTimeMillis()-gpsLastRead > 500) { // read gps values every second
//                    gpsDriver.recieveData();
//                    gpsLastRead = System.currentTimeMillis();
//                }
                
//                SerialData data = motorControllerDriver.recieveData(new byte[0]);
//                
//                parseData(data);
                
                
//                double compassHeading = BoatDataManager.compassHeading.getValue().compassHeading;
//                double headingOffset = GpsCalc.findHeading(
//                        BoatDataManager.remoteGpsData.getValue(), 
//                        BoatDataManager.localGpsData.getValue());
                
//                BoatDataManager.telemetryHeading.setValue(headingOffset);
//                if(BoatDataManager.isStepperCalibrateOn.getValue()) {
//                    stepperDriver.sendData(45);
//                } else {
//                    
//                    // ex. telemetryHeading = 0
//                    
//                    double direction = compassHeading-headingOffset;
//                    if(direction > 180) direction -= 360;
//                    stepperDriver.sendData(direction); // stepper motor updates itself based on current conditions
//                }//sector2aDriver.sendData();    

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

//    private void parseData(SerialData data) {
//        try{
//            String dataString = new String(data.byteData);
//            String[] tokens = dataString.split(",");
//            
//            double voltage = Double.parseDouble(tokens[0]);
//            double current = Double.parseDouble(tokens[1]);
//            BoatDataManager.elecVoltage.setValue(voltage);
//            BoatDataManager.elecCurrent.setValue(current);
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    
    
}
