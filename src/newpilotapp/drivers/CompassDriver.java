/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

import newpilotapp.logging.Console;

/**
 *
 * @author Jeffrey
 */
public class CompassDriver extends SerialDriver {
    
    private CompassData compassData = new CompassData();

    public CompassDriver() {
        setCommand(new byte[] {67});
        setReadTimeout(100);
        setSerialPortName("/dev/ttyACM0");
    }

    @Override
    public CompassData recieveData() {
        SerialData serialData = super.recieveData();
        // try to parse data, catch exceptions
        try {
            String s = new String(serialData.byteData, "UTF-8");
            String[] tokens = s.substring(0, s.length()-2).split(",");
            compassData.compassHeading = Double.parseDouble(tokens[0]);
            compassData.systemCalibration = Integer.parseInt(tokens[1]);
            compassData.magneticCalibration = Integer.parseInt(tokens[2]);

        } catch (Exception e) {
            // corrupted data
            Console.warn("Corrupted compass data");
        }
        return compassData;
    }
    
    
    
    
    public static class CompassData extends SerialData {
        public double compassHeading;
        public int systemCalibration, magneticCalibration;
    }
    
}
