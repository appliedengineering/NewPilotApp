package newpilotapp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.drivers.CompassDriver.CompassData;
import newpilotapp.gui.components.StatusBar;


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
    static GraphicsDevice device = GraphicsEnvironment
        .getLocalGraphicsEnvironment().getScreenDevices()[0];

    
    
    public AppWindow() {
        frame = new JFrame();
                
    }

    public void showFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(false);
        frame.setLayout(new BorderLayout());
        
        frame.setSize(800, 480); // simulate boat screen
        
        frame.add(new StatusBar(), BorderLayout.SOUTH);
        
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);

//        JButton btn1 = new JButton("Full-Screen");
//        btn1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                device.setFullScreenWindow(frame);
//            }
//        });
//        JButton btn2 = new JButton("Normal");
//        btn2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                device.setFullScreenWindow(null);
//            }
//        });
//        
//        JLabel label = new JLabel("compass data");
//        
//
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        panel.add(btn1);
//        panel.add(btn2);
//        panel.add(label);
//        frame.add(panel, BorderLayout.NORTH);
//        
//        JComponent dial = new JComponent() {
//            @Override
//            public void paint(Graphics g) {
//                g.setColor(Color.black);
//                g.fillRect(0, 0, 200, 200);
//                g.setColor(Color.white);
//                
//                g.fillArc(0, 0, 200, 200, 0, (int) (360-compassReading));
//            }
//            
//        };
//        frame.add(dial, BorderLayout.CENTER);
//
        
        // device.setFullScreenWindow(frame);

//        CompassDriver compassDriver = new CompassDriver();
//        compassDriver.init();
//        // int i = 0;
//        boolean isRunning = true;
//        while(isRunning) {
//            // i++;
//            CompassData cd = compassDriver.recieveData();
//            compassReading = cd.compassHeading;
//            label.setText(String.format("compass: %.2f, cal: %d, %d\n", cd.compassHeading, cd.systemCalibration, cd.magneticCalibration));
//            label.repaint();
//            dial.repaint();
//            // System.out.printf("compass: %.2f, cal: %d, %d\n", cd.compassHeading, cd.systemCalibration, cd.magneticCalibration);
//        }
//        compassDriver.stop();
    }
    
}
