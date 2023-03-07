/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.ground;

/**
 *
 * @author jeffrey
 */
public class GroundMain implements Runnable {
    
    public volatile boolean isRunning = false;
    
    public void init() {
        
    }

    @Override
    public void run() {
        isRunning = true;
        while(isRunning) {
        
        }
    }
    
}
