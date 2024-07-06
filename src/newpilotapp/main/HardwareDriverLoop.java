/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.main;

import external.org.msgpack.core.MessagePack;
import external.org.msgpack.core.MessageUnpacker;
import java.io.IOException;
import newpilotapp.data.BoatDataManager;
import newpilotapp.drivers.BatteryVoltageDriver;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.drivers.ElecDataDriver;
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
    
    private SerialDriver compassAndGpsSerial = new SerialDriver();
    private String compassAndGpsSerialPort = BoatDataManager.portCompassAndGps.getValue();
    
    private CompassDriver compassDriver = new CompassDriver(BoatDataManager.compassHeading, compassAndGpsSerialPort, compassAndGpsSerial);
    private GpsDriver gpsDriver = new GpsDriver(BoatDataManager.localGpsData, compassAndGpsSerialPort, compassAndGpsSerial);

    
    
    private SerialDriver stepperAndBattSerial = new SerialDriver();
    private String stepperAndBattSerialPort = BoatDataManager.portStepper.getValue();
    private StepperDriver stepperDriver = new StepperDriver(stepperAndBattSerialPort, stepperAndBattSerial);
    private BatteryVoltageDriver battDriver = new BatteryVoltageDriver(BoatDataManager.battVoltage, stepperAndBattSerialPort, stepperAndBattSerial);

    private String elecSerialPort = BoatDataManager.portElec.getValue();

    private SerialDriver elecSerialDriver = new SerialDriver();
    
    private ElecDataDriver elecDriver = new ElecDataDriver(BoatDataManager.portElec.getValue() ,elecSerialDriver);
    
//    private SerialDriver motorControllerDriver = new SerialDriver();
    
    


    
    public volatile boolean isRunning = false;
    
    public volatile long runDelay = 0;
        
    private void init() {
        try {
        elecDriver.init();
        } catch (Exception e) {
            Console.error(e.getMessage());
        }
        try {
        compassDriver.init();
        } catch (Exception e) {
            Console.error(e.getMessage());
        }
        
        // DO NOT INIT THE GPS DRIVER because the compass driver has already been intialized and they share
        // a SerialDriver
//        try {
//            gpsDriver.init();
//        } catch (Exception e) {
//            Console.error(e.getMessage());
//        }
        try {
        stepperDriver.init(); // DO NOT INIT THE BATT DRIVER because the compass driver has already been intialized and they share
        } catch (Exception e) {
            Console.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        
        long gpsLastRead = System.currentTimeMillis();
        long battLastRead = System.currentTimeMillis();

        
        init();
        isRunning = true;
        while(isRunning){
            try {
                compassDriver.recieveData();
                elecDriver.recieveData();

                if(System.currentTimeMillis()-gpsLastRead > 500) { // read gps values every half second
                    gpsDriver.recieveData();
                    gpsLastRead = System.currentTimeMillis();
                }
                
                if(System.currentTimeMillis()-battLastRead > 5000) { // read battery voltage every second
                    battDriver.recieveData();
                    battLastRead = System.currentTimeMillis();

                }
                
//                SerialData data = motorControllerDriver.recieveData(new byte[0]);
//                
//                parseData(data);
                
                
//                double compassHeading = BoatDataManager.compassHeading.getValue().compassHeading;
//                double headingOffset = GpsCalc.findHeading(
//                        BoatDataManager.remoteGpsData.getValue(), 
//                        BoatDataManager.localGpsData.getValue());
                
//                BoatDataManager.telemetryHeading.setValue(headingOffset);
                if(BoatDataManager.isStepperCalibrateOn.getValue()) {
                    stepperDriver.sendData(45);
                } else {
//                    
//                    // ex. telemetryHeading = 0

                if(BoatDataManager.remoteGpsData.getValue() != null && BoatDataManager.localGpsData.getValue() != null){
                    
//                    double direction = 360-BoatDataManager.compassHeading.getValue().compassHeading;
                    double heading = GpsCalc.findHeading(BoatDataManager.remoteGpsData.getValue(), BoatDataManager.localGpsData.getValue())-BoatDataManager.compassHeading.getValue().compassHeading;
                    if(heading < 0) heading += 360;
                    
                    BoatDataManager.telemetryHeading.setValue(heading);
        
                    stepperDriver.sendData(heading); // stepper motor updates itself based on current conditions
                }
                }//sector2aDriver.sendData();    

            } catch (Exception e) {
                // make error visible on display (bc hardware issues need to be resolved physically)
//                Console.error(e.getMessage());
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
