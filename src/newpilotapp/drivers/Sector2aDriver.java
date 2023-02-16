/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

/**
 *
 * @author jeffrey
 */
public class Sector2aDriver {
    
    SerialDriver sector2aSerial = new SerialDriver();

    public Sector2aDriver() {
        sector2aSerial = new SerialDriver();
        sector2aSerial.setReadTimeout(20);
        sector2aSerial.setSerialPortName("1-1.1"); // port location
    }
    
     public void init(){
        sector2aSerial.init();
    }
    
    public void stop(){
        sector2aSerial.stop();
    }
    
    public void recieveData() {
        // get gps values
    }
    
    public void sendData() {
        
        // TODO: send the commands to the servo based on compass vs telemetry heading
        
    }
    
    public static class GpsData extends SerialDriver.SerialData {
        public double lat, lon; // lattitude, longitude
    }
    
}
