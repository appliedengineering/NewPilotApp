/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.framework.data.MutableLiveData;

/**
 * All data displayed on the screen will be based on this data, this allows for
 * separation of the view from the data
 * Think of this like a ViewModel from Android
 * @author Jeffrey
 */
public class BoatDataManager {
    public static MutableLiveData<Boolean> networkStatus = new MutableLiveData<>(false); // true = online, false = offline
    
    // GUI
    public static MutableLiveData<StringBuffer> errorStatus = new MutableLiveData<>(new StringBuffer("--- START OF LOG ---"));
    public static MutableLiveData<Boolean> isDebugDataOn = new MutableLiveData<>(false);
    
    // Sensor Data
    public static MutableLiveData<CompassDriver.CompassData> compassHeading = new MutableLiveData<>(); 
    public static MutableLiveData<Double> ambientTemp = new MutableLiveData<>();
    public static MutableLiveData<GpsDriver.GpsData> localGpsData = new MutableLiveData<>();
    
    public static MutableLiveData<GpsDriver.GpsData> remoteGpsData = new MutableLiveData<>(new GpsDriver.GpsData(34.12414384566719, -118.07275692865537, 0)); // testing
    
    public static MutableLiveData<Double> telemetryHeading = new MutableLiveData<>(0d);

    public static MutableLiveData<Map<String, List<DataPoint>>> dataFromBoatController = new MutableLiveData<>(new HashMap<>()); // data provided by elec


    // Hardware Vitals
    public static MutableLiveData<Long> heapSpaceTotal = new MutableLiveData<>();
    public static MutableLiveData<Long> heapSpaceFree = new MutableLiveData<>();
    
    
    // DATA KEYS
    public static final String[] DATA_KEYS = new String[] {
        "TP", // Throttle Percentage
        "DP", // Duty Percentage
        "BV", // Battery Voltage
        "SM", // Solar Mode
        "EN", // Motor Enabled
        "BC", // Battery Current
    };
    
    public static final String[] DATA_KEY_TITLES = new String[] {
        "Throttle Percentage",
        "Duty Percentage",
        "Battery Voltage",
        "Solar Mode",
        "Motor Enabled",
        "Battery Current"
    };
    
    public static final boolean[] IS_DATA_KEY_NUMERICAL = new boolean[] {
        true, // Throttle Percentage
        true, // Duty Percentage
        true, // Battery Voltage
        false, // Solar Mode
        false, // Motor Enabled
        true  // Battery Current
    };
    
    public static String getTitleForDataKey(String key) {
        
        for(int i = 0; i < DATA_KEYS.length; i++) {
            if(DATA_KEYS[i].equals(key)) return DATA_KEY_TITLES[i];
        }
        return "error";
    }
    
    public static boolean isDataKeyNumerical(String key) {
        
        for(int i = 0; i < DATA_KEYS.length; i++) {
            if(DATA_KEYS[i].equals(key)) return IS_DATA_KEY_NUMERICAL[i];
        }
        return false;
    }
    
}
