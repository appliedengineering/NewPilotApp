/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

import newpilotapp.data.BoatDataManager;
import newpilotapp.drivers.SerialDriver.SerialData;
import newpilotapp.framework.data.MutableLiveData;
import newpilotapp.logging.Console;

/**
 *
 * @author Jeffrey
 */
public class BatteryVoltageDriver { // for sector2b, labeled on the diagram in the software folder
    
    
    SerialDriver sector2bSerial;
    
    
    private static final byte[] BATT_COMMAND_BYTES = new byte[] {(byte) 222};
    
    private MutableLiveData<Double> battVoltage;
    

    public BatteryVoltageDriver(MutableLiveData<Double> battVoltage, String port) {
        this.battVoltage = battVoltage;
        sector2bSerial = new SerialDriver();
        sector2bSerial.setReadTimeout(50);
        sector2bSerial.setSerialPortName(port); // port location
    }
    
    public BatteryVoltageDriver(MutableLiveData<Double> battVoltage, String port, SerialDriver ser) {
        this.battVoltage = battVoltage;
        sector2bSerial = ser;
        sector2bSerial.setReadTimeout(50);
        sector2bSerial.setSerialPortName(port); // port location
        
    }
   
    
    public void init(){
        sector2bSerial.init();
    }
    
    public void stop(){
        sector2bSerial.stop();
    }

    public void recieveData() {
        SerialData serialData = sector2bSerial.recieveData(BATT_COMMAND_BYTES);
        // try to parse data, catch exceptions
        try {
            String s = new String(serialData.byteData, "UTF-8");
            
            battVoltage.setValue(Double.parseDouble(s));
            
        } catch (Exception e) {
//            e.printStackTrace();
            // corrupted data
            battVoltage.setValue(-1.0);
        }
    }
    
}
