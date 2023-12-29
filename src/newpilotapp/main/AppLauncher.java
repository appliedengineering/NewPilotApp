/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.main;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import newpilotapp.networking.GroundNetworkingDriver;
import newpilotapp.gui.AppWindow;
import newpilotapp.networking.BoatNetworkingDriver;

/**
 *
 * @author Jeffrey
 */
public class AppLauncher {
    public static AppWindow appWindow;
    public static BoatNetworkingDriver boatAlignmentNetworking;
    public static GroundNetworkingDriver groundAlignmentNetworking;

    
    public static HardwareDriverLoop hardwareLoop;
    public static LowRefreshDriverLoop lowRefreshLoop;
    
    public static Thread hardwareThread;
    public static Thread lowRefreshThread;


    public static void main(String[] args) {
        
        if(args.length == 0) {
            return;
        }
        if(args[0].equals("runScript")) {
            try {
                ProcessBuilder pb = new ProcessBuilder("/home/ae-boatstation/Desktop/runJava.sh");
                Map<String, String> env = pb.environment();
                env.put("DISPLAY", "0:0");
//                pb.directory(new File("/home/ae-boatsation/Desktop/"));
                Process p = pb.start();
            } catch (IOException ex) {
                Logger.getLogger(AppLauncher.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        if(!args[0].equals("run")) {
            return;
        }

        FlatLightLaf.setup();
        
        if(args.length == 2) {
            if(args[1].equals("boatNetwork")){
                hardwareLoop = new HardwareDriverLoop();
                hardwareThread = new Thread(hardwareLoop);
                hardwareThread.start();
            } else if(args[1].equals("groundNetwork")){}
        }
        
        
        
        lowRefreshLoop = new LowRefreshDriverLoop();
        lowRefreshThread = new Thread(lowRefreshLoop);
        lowRefreshThread.start();
        
        
        if(args.length == 2) {
            if(args[1].equals("boatNetwork")){
                boatAlignmentNetworking = new BoatNetworkingDriver();
                boatAlignmentNetworking.start();
            } else if(args[1].equals("groundNetwork")){
                groundAlignmentNetworking = new GroundNetworkingDriver();
                groundAlignmentNetworking.start();
            }
        }
        
        try {
            javax.swing.SwingUtilities.invokeAndWait(
                new Runnable() {

                  @Override
                  public void run() {
                      appWindow = new AppWindow();

                      appWindow.showFrame();
                  }
                }
            );
        } catch (Exception e) {
        }
        
    }
    
}
