/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.main;

import java.util.logging.Level;
import java.util.logging.Logger;
import newpilotapp.data.DataManager;
import newpilotapp.gui.AppWindow;
import newpilotapp.networking.AlignmentNetworkingDriver;

/**
 *
 * @author Jeffrey
 */
public class AppLauncher {
    public static AppWindow appWindow;
    public static AlignmentNetworkingDriver alignmentNetworking;
    public static void main(String[] args) {
        
        alignmentNetworking = new AlignmentNetworkingDriver();
        alignmentNetworking.start();
        
        appWindow = new AppWindow();
        
        appWindow.showFrame();
        
//        new Thread(() -> {
//            while(true) {
//                DataManager.networkStatus.setData(Boolean.TRUE);
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException ex) {
//                }
//                DataManager.networkStatus.setData(Boolean.FALSE);
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException ex) {
//                }            }
//        }).start();
    }
    
}
