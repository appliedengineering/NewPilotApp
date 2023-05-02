/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.ground;

import newpilotapp.ground.networking.GroundNetworkingDriver;

/**
 *
 * @author jeffrey
 */
public class GroundLauncher {
    
    public static void main(String[] args) {
        
        if(args.length == 0) {
            return;
        }
        if(!args[0].equals("run")) {
            return;
        }
        
        GroundMain main = new GroundMain();
        Thread groundThread = new Thread(main);
        groundThread.start();
        
        GroundNetworkingDriver networking = new GroundNetworkingDriver();
        Thread networkingThread = new Thread(networking);
        networkingThread.start();
    }
    
}
