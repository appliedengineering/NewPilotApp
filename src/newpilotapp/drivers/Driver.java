/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

/**
 * Provides a standard way to interface with hardware devices
 * @author Jeffrey
 */
public abstract class Driver {
    
    /**
     * Returns any data received from this interface
     * @return 
     */
    // public abstract DriverData recieveData(byte[] command);
    
    /**
     * Notifies is data is ready to be read by recieveData() - this only applies to network connections
     * @return 
     */
    public abstract boolean isDataPresent();
    
        
    public static abstract class DriverData {
    }
}
