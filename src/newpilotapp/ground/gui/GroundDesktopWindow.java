/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.ground.gui;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import newpilotapp.ground.networking.GroundDesktopNetworkingDriver;

/**
 *
 * @author jeffrey
 */
public class GroundDesktopWindow {
    
    private JFrame frame;
    private GroundDataPane pane;
    
    public static void main(String[] args) {
        GroundDesktopNetworkingDriver desktopNetworking = new GroundDesktopNetworkingDriver("localhost", "5555");
        Thread networkThread = new Thread(desktopNetworking);
        
        new GroundDesktopWindow();
        
        networkThread.start();

    }

    public GroundDesktopWindow() {
        frame = new JFrame();
        frame.setSize(400, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        pane = new GroundDataPane();
        
        frame.add(pane);
        frame.setVisible(true);
    }

    
        
    
}
