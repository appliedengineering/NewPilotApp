/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.data;

import newpilotapp.framework.data.LiveData;

/**
 * All data displayed on the screen will be based on this data, this allows for
 * separation of the view from the data
 * @author Jeffrey
 */
public class DataManager {
    public static LiveData<Boolean> networkStatus = new LiveData<>(false); // true = online, false = offline
            
}
