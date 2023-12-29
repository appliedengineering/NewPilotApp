/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

import external.org.openstreetmap.gui.jmapviewer.JMapViewer;
import newpilotapp.data.BoatDataManager;

/**
 *
 * @author jeffrey
 */
public class GpsCalc {
    
    public static double findHeading(GpsDriver.GpsData remote, GpsDriver.GpsData local){
        if(remote == null || local == null) return 0; // aka don't move

        double deltaLat = remote.lat - local.lat;
        double deltaLon = remote.lon - local.lon;

        double bearing = Math.atan2(
                Math.cos(remote.lat) * Math.sin(deltaLon),
                Math.cos(local.lat) * Math.sin(remote.lat) - Math.sin(local.lat) * Math.cos(remote.lat) * Math.cos(deltaLon));

        bearing = -bearing/Math.PI*180;


        if(bearing < 0) {
            bearing = 360 + bearing;
        }


        return bearing;
    }
    
    public static GpsDriver.GpsData getCenter(GpsDriver.GpsData remote, GpsDriver.GpsData local) {
        return new GpsDriver.GpsData((remote.lat+local.lat)/2, (remote.lon + local.lon)/2, 0);
    }
    
    
}
