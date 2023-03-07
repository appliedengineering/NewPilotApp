/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.ground;

/**
 *
 * @author jeffrey
 */
public class GroundLauncher {
    
    public static void main(String[] args) {
        GroundMain main = new GroundMain();
        Thread groundThread = new Thread(main);
        main.init();
        groundThread.start();
    }
    
}
