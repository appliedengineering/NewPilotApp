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
    
    public void sendData(double difference) {
        try{
                        
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
