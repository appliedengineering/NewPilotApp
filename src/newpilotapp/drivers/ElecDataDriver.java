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
public class ElecDataDriver { // for sector2b, labeled on the diagram in the software folder
    
    
    SerialDriver sector2bSerial;
    
    
    private static final byte[] ELEC_COMMAND_BYTES = new byte[] {'E'};
        

    public ElecDataDriver(String port) {
        sector2bSerial = new SerialDriver();
        sector2bSerial.setReadTimeout(50);
        sector2bSerial.setSerialPortName(port); // port location
    }
    
    public ElecDataDriver(String port, SerialDriver ser) {
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
        System.out.println("elec data");

        SerialData serialData = sector2bSerial.recieveData(ELEC_COMMAND_BYTES);
        System.out.println(serialData.byteData);

        // try to parse data, catch exceptions
        try {
             System.out.println(serialData.byteData.length);

            String s = new String(serialData.byteData, "UTF-8");
            String[] tokens = s.split("/");
            System.out.println(tokens[1]);

            if(serialData.byteData.length >= 4) {
                BoatDataManager.elecVoltage.setValue((double) Double.parseDouble(tokens[0]));
                BoatDataManager.elecCurrent.setValue((double) Double.parseDouble(tokens[1]));

//                BoatDataManager.elecCurrent.setValue((double) serialData.byteData[1]);

            }
            
            
        } catch (Exception e) {
//            e.printStackTrace();
            // corrupted data
        }
    }
    
}
