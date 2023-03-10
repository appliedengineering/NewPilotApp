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
public class CompassDriver { // for sector2b, labeled on the diagram in the software folder
    
    
    SerialDriver sector2bSerial = new SerialDriver();
    
    private CompassData compassData = new CompassData();
    
    private static final byte[] COMPASS_COMMAND_BYTES = new byte[] {67};
    
    private MutableLiveData<CompassData> compassHeading;

    public CompassDriver(MutableLiveData<CompassData> compassHeading) {
        this.compassHeading = compassHeading;
        sector2bSerial = new SerialDriver();
        sector2bSerial.setReadTimeout(50);
        sector2bSerial.setSerialPortName("1-1.4"); // port location
    }
    
    public void init(){
        sector2bSerial.init();
    }
    
    public void stop(){
        sector2bSerial.stop();
    }

    public void recieveData() {
        SerialData serialData = sector2bSerial.recieveData(COMPASS_COMMAND_BYTES);
        // try to parse data, catch exceptions
        try {
            String s = new String(serialData.byteData, "UTF-8");
            String[] tokens = s.substring(0, s.length()-2).split(",");
            compassData.compassHeading = Double.parseDouble(tokens[0]);
            compassData.systemCalibration = Integer.parseInt(tokens[1]);
            compassData.magneticCalibration = Integer.parseInt(tokens[2]);
            
            compassHeading.setValue(compassData);

        } catch (Exception e) {
            // corrupted data
            compassHeading.setValue(null);
        }
    }
    
    public static class CompassData extends SerialData {
        public double compassHeading;
        public int systemCalibration, magneticCalibration;
    }
    
}
