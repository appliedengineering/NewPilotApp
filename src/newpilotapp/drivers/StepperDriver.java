/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

import newpilotapp.data.BoatDataManager;
import newpilotapp.drivers.GpsDriver.GpsData;
import newpilotapp.logging.Console;

/**
 *
 * @author jeffrey
 */
public class StepperDriver { // part of Sector2a
    
    private static final byte[] STEPPER_COMMAND_LEFT = new byte[] {76};
    
    private static final byte[] STEPPER_COMMAND_RIGHT = new byte[] {82};
    
    private static final byte[] STEPPER_COMMAND_STOP = new byte[] {84};


    
    SerialDriver stepperSerial = new SerialDriver();

    public StepperDriver() {
        stepperSerial = new SerialDriver();
        stepperSerial.setReadTimeout(50);
        stepperSerial.setSerialPortName("0-1.3"); // port location
    }
    
     public void init(){
        stepperSerial.init();
    }
    
    public void stop(){
        stepperSerial.stop();
    }
    
    public void sendData() {
        try{
            // calculate bearing
            
            GpsData remote = BoatDataManager.remoteGpsData.getValue();
            GpsData local = BoatDataManager.localGpsData.getValue();
            
            if(remote == null || local == null) return;
            if(BoatDataManager.compassHeading.getValue() == null) return;

            double deltaLat = remote.lat - local.lat;
            double deltaLon = remote.lon - local.lon;
            
            double bearing = Math.atan2(
                    Math.cos(remote.lat) * Math.sin(deltaLon),
                    Math.cos(local.lat) * Math.sin(remote.lat) - Math.sin(local.lat) * Math.cos(remote.lat) * Math.cos(deltaLon));

            bearing = -bearing/Math.PI*180;
            
            
            if(bearing < 0) {
                bearing = 360 + bearing;
            }
            
            BoatDataManager.telemetryHeading.getValue().compassHeading = bearing;
            BoatDataManager.telemetryHeading.valueWasUpdated();
            
            int difference = (int) (bearing - BoatDataManager.compassHeading.getValue().compassHeading);
//            Console.log(String.format("diff: %d bearing: %f", difference, bearing));
            
            if(Math.abs(difference) < 5) {
                return;
            }
            
            if(difference > 0) {
                stepperSerial.recieveData(STEPPER_COMMAND_RIGHT);
            } else if(difference < 0) {
                stepperSerial.recieveData(STEPPER_COMMAND_LEFT);
            }
            
            
        } catch (Exception e) {
            
        }
    }
    
}
