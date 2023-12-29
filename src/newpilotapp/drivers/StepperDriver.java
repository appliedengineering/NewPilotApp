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
    
    
    private double stepsTaken = 0;


    
    SerialDriver stepperSerial = new SerialDriver();

    public StepperDriver(String port) {
        stepperSerial = new SerialDriver();
        stepperSerial.setReadTimeout(50);
        stepperSerial.setSerialPortName(port); // port location
    }
    
     public void init(){
        stepperSerial.init();
    }
    
    public void stop(){
        stepperSerial.stop();
    }
    
    public void sendData(double angleToReach) {
        
        double currentAngle = stepsTaken/200.0*360.0;
        double differenceAngle = angleToReach - currentAngle;
        if(differenceAngle > 180) differenceAngle -= 360;
        
        double differenceSteps = differenceAngle/360*200;

        
        try{
            stepperSerial.recieveData(new byte[]{(byte) (angleToReach/360*200)});

//            if(Math.abs(differenceSteps) < 2) {
//                stepperSerial.recieveData(STEPPER_COMMAND_STOP);
//                // Thread.sleep(100);
//                System.out.println("STOP");
//                return;
//            }
//            
//            if(differenceSteps > 0) {
//                stepperSerial.recieveData(STEPPER_COMMAND_RIGHT);
//                System.out.println("RIGHT");
//                stepsTaken += 1;
//            } else if(differenceSteps < 0) {
//                stepperSerial.recieveData(STEPPER_COMMAND_LEFT);
//                System.out.println("LEFT");
//                stepsTaken -= 1;
//            }
            
            
        } catch (Exception e) {
            
        }
    }
    
}
