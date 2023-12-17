package newpilotapp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import newpilotapp.gui.components.StatusBar;
import newpilotapp.gui.components.contentpanes.DataContentPane;
import newpilotapp.gui.components.contentpanes.DebugContentPane;
import newpilotapp.gui.components.contentpanes.DriverDisplayContentPane;
import newpilotapp.gui.components.contentpanes.MapContentPane;
import newpilotapp.gui.components.contentpanes.SettingsContentPane;
import newpilotapp.gui.components.contentpanes.TelemetryContentPane;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jeffrey
 */
public class AppWindow  {
    private double compassReading = 0;
    private JFrame frame;
    private TabbedContentPane tabbedPane;
    static GraphicsDevice device = GraphicsEnvironment
        .getLocalGraphicsEnvironment().getScreenDevices()[0];
    
    private DebugContentPane debugPane;
    private TelemetryContentPane telemetryPane;
    private DataContentPane dataPane;
    private SettingsContentPane settingsPane;
    private MapContentPane mapPane;
    private DriverDisplayContentPane driverPane;

    
    
    public AppWindow() {
        frame = new JFrame();
                
    }

    public void showFrame() {
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        frame.setSize(800, 480); // simulate boat screen
        frame.setUndecorated(true);

        
        tabbedPane = new TabbedContentPane();
        
        debugPane = new DebugContentPane();
        tabbedPane.addPane(debugPane);
        
        telemetryPane = new TelemetryContentPane();
        telemetryPane.title = "Telemetry Pane";
        tabbedPane.addPane(telemetryPane);
        
        dataPane = new DataContentPane();
        dataPane.title = "Realtime Data";
        tabbedPane.addPane(dataPane);
        
        settingsPane = new SettingsContentPane();
        settingsPane.title = "Settings";
        tabbedPane.addPane(settingsPane);
        
        driverPane = new DriverDisplayContentPane();
        driverPane.title = "Driver";
        tabbedPane.addPane(driverPane);
        
        // mapPane = new MapContentPane();
        // tabbedPane.addPane(mapPane);

        
        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.add(new StatusBar(), BorderLayout.SOUTH);
        
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);
        
//        frame.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//              int confirmed = JOptionPane.showConfirmDialog(null, 
//                  "Are you sure you want to exit the program?", "Applied Engineering 2023",
//                  JOptionPane.YES_NO_OPTION);
//
//              if (confirmed == JOptionPane.YES_OPTION) {
//                frame.dispose();
//                System.exit(0);
//              }
//            }
//          });
        
//         device.setFullScreenWindow(frame);

    }
    
}
