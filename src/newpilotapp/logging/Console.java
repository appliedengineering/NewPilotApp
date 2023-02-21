/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.logging;

import newpilotapp.data.DataManager;

/**
 * Simple Console functions
 * @author Jeffrey
 */
public class Console {
    
    public static void error(String error) {
        System.out.println("Error - " + error);
        DataManager.errorStatus.getValue().append("\nError - ").append(error);
        DataManager.errorStatus.valueWasUpdated();
    }
    
    public static void warn(String warn) {
        System.out.println("Warn - " + warn);
        DataManager.errorStatus.getValue().append("\nWarn - ").append(warn);
        DataManager.errorStatus.valueWasUpdated();    
    }
    
    public static void log(String log) {
        System.out.println("Log - " + log);
        DataManager.errorStatus.getValue().append("\nLog - ").append(log);
        DataManager.errorStatus.valueWasUpdated();    
    }
    
}
