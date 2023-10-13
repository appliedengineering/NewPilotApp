/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.main;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import newpilotapp.gui.AppWindow;
import newpilotapp.networking.BoatNetworkingDriver;

/**
 *
 * @author Jeffrey
 */
public class AppLauncher {
    public static AppWindow appWindow;
    public static BoatNetworkingDriver alignmentNetworking;
    
    public static HardwareDriverLoop hardwareLoop;
    public static LowRefreshDriverLoop lowRefreshLoop;
    
    public static Thread hardwareThread;
    public static Thread lowRefreshThread;


    public static void main(String[] args) {
        
        if(args.length == 0) {
            return;
        }
        if(!args[0].equals("run")) {
            return;
        }

        FlatLightLaf.setup();
        
        hardwareLoop = new HardwareDriverLoop();
        hardwareThread = new Thread(hardwareLoop);
        hardwareThread.start();
        
        lowRefreshLoop = new LowRefreshDriverLoop();
        lowRefreshThread = new Thread(lowRefreshLoop);
        lowRefreshThread.start();
        
        alignmentNetworking = new BoatNetworkingDriver();
        alignmentNetworking.start();
        
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
