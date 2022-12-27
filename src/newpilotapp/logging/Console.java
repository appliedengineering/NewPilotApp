/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.logging;

/**
 *
 * @author Jeffrey
 */
public class Console {
    
    public static void error(String error) {
        System.out.println("Error - " + error);
    }
    
    public static void warn(String warn) {
        System.out.println("Warn - " + warn);
    }
    
    public static void log(String log) {
        System.out.println("Log - " + log);
    }
    
}
