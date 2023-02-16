/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import newpilotapp.gui.AppWindow;
import newpilotapp.networking.AlignmentNetworkingDriver;

/**
 *
 * @author Jeffrey
 */
public class AppLauncher {
    public static AppWindow appWindow;
    public static AlignmentNetworkingDriver alignmentNetworking;
    
    public static HardwareDriverLoop hardwareLoop;
    public static Thread hardwareThread;


    public static void main(String[] args) {
        if(args.length == 0) {
            return;
        }
        if(!args[0].equals("run")) {
            return;
        }

        
        // TESTING
            try {
                System.setProperty("sun.java2d.opengl", "true");
                    // Set System L&F
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            } 
            catch (UnsupportedLookAndFeelException e) {
               // handle exception
            }
            catch (ClassNotFoundException e) {
               // handle exception
            }
            catch (InstantiationException e) {
               // handle exception
            }
            catch (IllegalAccessException e) {
               // handle exception
            }
        // END TESTING
        
        
        hardwareLoop = new HardwareDriverLoop();
        hardwareThread = new Thread(hardwareLoop);
        hardwareThread.start();
        
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
