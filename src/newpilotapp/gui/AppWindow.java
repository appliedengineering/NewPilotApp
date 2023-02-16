package newpilotapp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import newpilotapp.gui.TabbedContentPane.ContentPane;
import newpilotapp.gui.components.StatusBar;
import newpilotapp.gui.components.contentpanes.DebugContentPane;
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
    private TabbedContentPane content;
    static GraphicsDevice device = GraphicsEnvironment
        .getLocalGraphicsEnvironment().getScreenDevices()[0];
    
    private DebugContentPane debugPane;
    private TelemetryContentPane telemetry;

    
    
    public AppWindow() {
        frame = new JFrame();
                
    }

    public void showFrame() {
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        frame.setSize(800, 480); // simulate boat screen
        frame.setUndecorated(true);

        
        content = new TabbedContentPane();
        debugPane = new DebugContentPane();
        content.addPane(debugPane);
        
        telemetry = new TelemetryContentPane();
        telemetry.title = "Telemetry Pane";
        content.addPane(telemetry);
        
        frame.add(content, BorderLayout.CENTER);

        frame.add(new StatusBar(), BorderLayout.SOUTH);
        
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
              int confirmed = JOptionPane.showConfirmDialog(null, 
                  "Are you sure you want to exit the program?", "Applied Engineering 2023",
                  JOptionPane.YES_NO_OPTION);

              if (confirmed == JOptionPane.YES_OPTION) {
                frame.dispose();
                System.exit(0);
              }
            }
          });
        
        device.setFullScreenWindow(frame);

    }
    
}
