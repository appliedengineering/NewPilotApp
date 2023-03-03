/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.logging;

import newpilotapp.data.BoatDataManager;

/**
 * Simple Console functions
 * @author Jeffrey
 */
public class Console {
    
    public static void error(String error) {
        System.out.println("Error - " + error);
        BoatDataManager.errorStatus.getValue().append("\nError - ").append(error);
        BoatDataManager.errorStatus.valueWasUpdated();
    }
    
    public static void warn(String warn) {
        System.out.println("Warn - " + warn);
        BoatDataManager.errorStatus.getValue().append("\nWarn - ").append(warn);
        BoatDataManager.errorStatus.valueWasUpdated();    
    }
    
    public static void log(String log) {
        System.out.println("Log - " + log);
        BoatDataManager.errorStatus.getValue().append("\nLog - ").append(log);
        BoatDataManager.errorStatus.valueWasUpdated();    
    }
    
}
