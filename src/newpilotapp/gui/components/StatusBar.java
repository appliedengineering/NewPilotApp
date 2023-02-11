/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import newpilotapp.data.DataManager;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.drivers.CompassDriver.CompassData;
import newpilotapp.framework.data.LiveDataObserver;
import newpilotapp.gui.constants.GuiConstants;

/**
 *
 * @author Jeffrey
 */
public class StatusBar extends JPanel {
    
    JLabel networkStatusLabel;
    JLabel currentStatusLabel;

    public StatusBar() {
        setDoubleBuffered(true);
        setBackground(new Color(0x0f5cd9));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(GuiConstants.DEFAULT_PADDING));
        
        JLabel label = new JLabel("Applied Engineering 2023");
        label.setForeground(Color.white);
        add(label, BorderLayout.WEST);
        
        setUpNetworkStatus();
        
    }

    private void setUpNetworkStatus() {
        networkStatusLabel  = new JLabel();
        networkStatusLabel.setForeground(Color.white);
        
        currentStatusLabel  = new JLabel();
        currentStatusLabel.setForeground(Color.white);
        
        // add(currentStatusLabel, BorderLayout.CENTER);

  
        add(networkStatusLabel, BorderLayout.EAST);
        
        DataManager.networkStatus.observe(new LiveDataObserver<Boolean>() {
            @Override
            public void update(Boolean network) {
                if(network) {
                    networkStatusLabel.setText("Network Up");
                } else {
                    networkStatusLabel.setText("Network Down");
                }
            }
        
        }
        );
        
//        DataManager.errorStatus.observe(new LiveDataObserver<String>() {
//            @Override
//            public void update(String network) {
//                currentStatusLabel.setText(network);
//            }
//        
//        }
//        );
        
        
                
    }
    
    
    
}
