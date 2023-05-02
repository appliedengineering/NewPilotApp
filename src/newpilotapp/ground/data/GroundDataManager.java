/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.ground.data;

import newpilotapp.drivers.CompassDriver;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.framework.data.MutableLiveData;

/**
 *
 * @author jeffrey
 */
public class GroundDataManager {
    
    // note the meaning of "local" in this context
    public static MutableLiveData<GpsDriver.GpsData> localGpsData = new MutableLiveData<>(new GpsDriver.GpsData(34.125914, -118.066081, 1));
    public static MutableLiveData<GpsDriver.GpsData> remoteGpsData = new MutableLiveData<>();
    public static MutableLiveData<CompassDriver.CompassData> compassHeading = new MutableLiveData<>(); 
    public static MutableLiveData<Double> telemetryHeading = new MutableLiveData<>(0d);




    
}
