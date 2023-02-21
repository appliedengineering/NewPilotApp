/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import newpilotapp.data.DataManager;

import newpilotapp.framework.data.LiveDataObserver;
import newpilotapp.gui.constants.GuiConstants;
import newpilotapp.logging.Console;

/**
 *
 * @author Jeffrey
 */
public class StatusBar extends JPanel {
    
    private JLabel networkStatusLabel;
    private JLabel heapStatusLabel;
    private JPanel vitals;

    public StatusBar() {
        setDoubleBuffered(true);
        setBackground(new Color(0x0f5cd9));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(GuiConstants.DEFAULT_PADDING));
        
        vitals = new JPanel();
        vitals.setLayout(new BoxLayout(vitals, BoxLayout.X_AXIS));
        vitals.setOpaque(false);
        add(vitals, BorderLayout.EAST);
        
        JLabel label = new JLabel("Applied Engineering 2023");
        label.setForeground(Color.white);
        add(label, BorderLayout.WEST);
        
        setUpNetworkStatus();
        
    }

    private void setUpNetworkStatus() {
        networkStatusLabel  = new JLabel();
        networkStatusLabel.setForeground(Color.white);
        
        heapStatusLabel  = new JLabel();
        heapStatusLabel.setForeground(Color.white);
        
        // add(currentStatusLabel, BorderLayout.CENTER);

  
        vitals.add(networkStatusLabel, BorderLayout.EAST);
        vitals.add(Box.createRigidArea(new Dimension(8,0)));
        vitals.add(heapStatusLabel, BorderLayout.EAST);
        vitals.add(Box.createRigidArea(new Dimension(2,0))); // right end

        
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
        
        DataManager.heapSpaceFree.observe(new LiveDataObserver<Long>() {
            @Override
            public void update(Long heapFree) {
                long total = DataManager.heapSpaceTotal.getValue();

                heapStatusLabel.setText(String.format("Memory: %d/%d MB", total-heapFree, total));
            }
        
        }
        );
        
        
                
    }
    
    
    
}
