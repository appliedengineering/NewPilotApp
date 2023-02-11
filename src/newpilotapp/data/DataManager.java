/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.data;

import newpilotapp.drivers.CompassDriver;
import newpilotapp.framework.data.MutableLiveData;

/**
 * All data displayed on the screen will be based on this data, this allows for
 * separation of the view from the data
 * Think of this like a ViewModel from Android
 * @author Jeffrey
 */
public class DataManager {
    public static MutableLiveData<Boolean> networkStatus = new MutableLiveData<>(false); // true = online, false = offline
    
    // GUI
    public static MutableLiveData<String> errorStatus = new MutableLiveData<>("No errors");
    
    // Sensor Data
    public static MutableLiveData<CompassDriver.CompassData> compassHeading = new MutableLiveData<>(); 

            
}
