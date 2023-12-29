/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

import external.org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import newpilotapp.data.BoatDataManager;
import newpilotapp.framework.data.MutableLiveData;

/**
 *
 * @author jeffrey
 */
public class GpsDriver { // part of Sector2a
    
    private static final byte[] GPS_COMMAND_BYTES = new byte[] {71};

    private GpsData gpsData = new GpsData();
    
    private MutableLiveData<GpsData> localGpsData;
    
    SerialDriver gpsSerial = new SerialDriver();

    public GpsDriver(MutableLiveData<GpsData> localGpsData, String port) {
        this.localGpsData = localGpsData;
        gpsSerial = new SerialDriver();
        gpsSerial.setReadTimeout(50);
        gpsSerial.setSerialPortName(port); // port location
    }
    
    public GpsDriver(MutableLiveData<GpsData> localGpsData, String port, SerialDriver ser) {
        this.localGpsData = localGpsData;
        gpsSerial = ser;
        gpsSerial.setReadTimeout(50);
        gpsSerial.setSerialPortName(port); // port location
        
    }
    
    public void init(){
        gpsSerial.init();
    }
    
    public void stop(){
        gpsSerial.stop();
    }
    
    public void recieveData() {
        SerialDriver.SerialData serialData = gpsSerial.recieveData(GPS_COMMAND_BYTES);
        // get gps values
        try {
            String s = new String(serialData.byteData, "UTF-8");
            String[] tokens = s.substring(0, s.length()-2).split(","); // removes the \n at the end
            
            gpsData.speed = Double.parseDouble(tokens[4]) * 1.15078; // convert knots to mph
            
            
            boolean latNegative = false, lonNegative = false;
            
            double latD = Double.parseDouble(tokens[0]);
            if(tokens[1].equals("S")) {
                latNegative = true;
            }
            
            double lonD = Double.parseDouble(tokens[2]);
            if(tokens[3].equals("W")) {
                lonNegative = true;
            }
            
            double latDegrees = ((int)latD)/100;
            double latMinutes = latD-100*latDegrees;
            double lonDegrees = ((int)lonD)/100;
            double lonMinutes = lonD-100*lonDegrees;
            
            double lat = latDegrees + latMinutes/60d;
            double lon = lonDegrees + lonMinutes/60d;

            if(latNegative) {
                lat = -lat;
            }
            if(lonNegative) {
                lon = -lon;
            }
            
            gpsData.lat = lat;
            gpsData.lon = lon;
            
            localGpsData.setValue(gpsData);
            
        } catch (Exception e) {
            localGpsData.setValue(null);
        }
    }
    
    public static class GpsData extends SerialDriver.SerialData implements ICoordinate{
        public double lat, lon; // lattitude, longitude (in decimal degrees)
        public double speed; // in mph

        public GpsData() {}
        
        public GpsData(double lat, double lon, double speed) {
            this.lat = lat;
            this.lon = lon;
            this.speed = speed;
        }

        @Override
        public double getLat() {
            return this.lat;
        }

        @Override
        public void setLat(double lat) {
            this.lat = lat;
        }

        @Override
        public double getLon() {
            return this.lon;
        }

        @Override
        public void setLon(double lon) {
            this.lon = lon;
        }
    }
    
}
