/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.framework.data.MutableLiveData;
import newpilotapp.logging.Console;

/**
 * All data displayed on the screen will be based on this data, this allows for
 * separation of the view from the data
 * Think of this like a ViewModel from Android
 * @author Jeffrey
 */

public class BoatDataManager {
    
    public static boolean isBoatstationMode = true; // true=boatstation, false=groundstation

    
    public static MutableLiveData<Boolean> networkStatus = new MutableLiveData<>(false); // true = online, false = offline
    
    // GUI
    public static MutableLiveData<StringBuffer> errorStatus = new MutableLiveData<>(new StringBuffer("--- START OF LOG ---"));
    public static MutableLiveData<Boolean> isDebugDataOn = new MutableLiveData<>(false);
    public static MutableLiveData<Boolean> isStepperCalibrateOn = new MutableLiveData<>(false);
    public static MutableLiveData<StringBuffer> portData = new MutableLiveData<>(new StringBuffer("No ports connected"));
    
    // Sensor Data
    public static MutableLiveData<Double> battVoltage = new MutableLiveData<>(0.0);

    public static MutableLiveData<CompassDriver.CompassData> compassHeading = new MutableLiveData<>(new CompassDriver.CompassData(20)); 
    public static MutableLiveData<Double> ambientTemp = new MutableLiveData<>();
    public static MutableLiveData<GpsDriver.GpsData> localGpsData = new MutableLiveData<>(new GpsDriver.GpsData(34.126914, -118.066082, 0));
    
    public static MutableLiveData<GpsDriver.GpsData> remoteGpsData = new MutableLiveData<>(new GpsDriver.GpsData(34.125914, -118.066082, 0)); // testing
    
    public static MutableLiveData<Double> telemetryHeading = new MutableLiveData<>(0d);

    public static MutableLiveData<Map<String, List<DataPoint>>> dataFromBoatController = new MutableLiveData<>(new HashMap<>()); // data provided by elec

    // Elec
    public static MutableLiveData<Double> elecVoltage = new MutableLiveData<>(0d);
    public static MutableLiveData<Double> elecCurrent = new MutableLiveData<>(0d);


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
    
    
    // PROPERTIES
    
    // Port Settings (MUST NOT BE NULL)
    public static MutableLiveData<String> portCompassAndGps = new MutableLiveData<>("");
    public static MutableLiveData<String> portStepper = new MutableLiveData<>("");
    public static MutableLiveData<String> portBoatstationElecData = new MutableLiveData<>("");
    public static MutableLiveData<String> networkBoatAddress = new MutableLiveData<>("");

    
    
    public static void loadAllProperties(){
        InputStream inputStream = null;
        try {
            String path = isBoatstationMode ? "boatSettings.properties" : "groundSettings.properties";
            inputStream = new FileInputStream(path);
            Properties props = new Properties();
            props.load(inputStream);
            portCompassAndGps.setValue(props.getProperty("portCompassAndGps"));
            portStepper.setValue(props.getProperty("portStepper"));
            portBoatstationElecData.setValue(props.getProperty("portBoatstationElecData"));
            networkBoatAddress.setValue(props.getProperty("networkBoatAddress"));

            
            Console.log("Settings Loaded!");
        } catch (FileNotFoundException ex) {
            Console.log("Settings Not Found!");

            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(inputStream != null)
                inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void saveAllProperties(){
        FileOutputStream outputStream = null;
        try {
            Properties props = new Properties();
            props.put("portCompassAndGps", portCompassAndGps.getValue());
            props.put("portStepper", portStepper.getValue());
            props.put("portBoatstationElecData", portBoatstationElecData.getValue());
            props.put("networkBoatAddress", networkBoatAddress.getValue());

            
            String path = isBoatstationMode ? "boatSettings.properties" : "groundSettings.properties";
           

            outputStream = new FileOutputStream(path);
            //Storing the properties file
            props.store(outputStream, "Program Settings");
            Console.log("Settings Saved!");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(outputStream != null)
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
