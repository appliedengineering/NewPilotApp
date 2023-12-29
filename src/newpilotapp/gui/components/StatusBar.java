/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import newpilotapp.data.BoatDataManager;

import newpilotapp.framework.data.LiveDataObserver;
import newpilotapp.gui.constants.GuiConstants;
import newpilotapp.logging.Console;

/**
 *
 * @author Jeffrey
 */
public class StatusBar extends JPanel {
    
    private JLabel networkStatusLabel;
    private JLabel debugStatusLabel;
    private JLabel heapStatusLabel;
    private JLabel stepperCalibrateLabel;
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
        
        JLabel label = new JLabel("Applied Engineering 2024");
        label.setForeground(Color.white);
        add(label, BorderLayout.WEST);
        
        setUpNetworkStatus();
        
    }

    private void setUpNetworkStatus() {
        networkStatusLabel  = new JLabel();
        networkStatusLabel.setForeground(Color.white);
        
        debugStatusLabel = new JLabel();
        debugStatusLabel.setForeground(Color.white);
        
        heapStatusLabel  = new JLabel();
        heapStatusLabel.setForeground(Color.white);
        
        stepperCalibrateLabel = new JLabel();
        stepperCalibrateLabel.setForeground(Color.white);
        
        // add(currentStatusLabel, BorderLayout.CENTER);

  
        vitals.add(stepperCalibrateLabel);
        vitals.add(Box.createRigidArea(new Dimension(8,0))); 
        vitals.add(debugStatusLabel);
        vitals.add(Box.createRigidArea(new Dimension(8,0))); 
        vitals.add(networkStatusLabel);
        vitals.add(Box.createRigidArea(new Dimension(8,0)));
        vitals.add(heapStatusLabel);
        vitals.add(Box.createRigidArea(new Dimension(8,0))); // right end

        
        BoatDataManager.networkStatus.observe(new LiveDataObserver<Boolean>() {
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
        
        BoatDataManager.networkStatus.observe(new LiveDataObserver<Boolean>() {
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
        
        BoatDataManager.isDebugDataOn.observe(new LiveDataObserver<Boolean>() {
            @Override
            public void update(Boolean debug) {
                if(debug) {
                    debugStatusLabel.setText("Debug ON");
                } else {
                    debugStatusLabel.setText("Debug OFF");
                }
            }
        
        }
        );
        
        BoatDataManager.heapSpaceFree.observe(new LiveDataObserver<Long>() {
            @Override
            public void update(Long heapFree) {
                long total = BoatDataManager.heapSpaceTotal.getValue();

                heapStatusLabel.setText(String.format("Memory: %d/%d MB", total-heapFree, total));
            }
        
        }
        );
        
        BoatDataManager.isStepperCalibrateOn.observe(new LiveDataObserver<Boolean>() {
            @Override
            public void update(Boolean stepper) {
                if(stepper) {
                    stepperCalibrateLabel.setText("Stepper Calibrate ON");
                } else {
                    stepperCalibrateLabel.setText("Stepper Calibrate OFF");
                }
            }
        
        }
        );
        
        
        // MOUSE CLICK LISTENERS
        debugStatusLabel.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                BoatDataManager.isDebugDataOn.setValue(!BoatDataManager.isDebugDataOn.getValue());
            }

            // ignore these
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}
        });
        
        heapStatusLabel.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                System.gc();
            }
            
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}

        });
        
        stepperCalibrateLabel.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                BoatDataManager.isStepperCalibrateOn.setValue(!BoatDataManager.isStepperCalibrateOn.getValue());
            }
            
            public void mousePressed(MouseEvent e) {}public void mouseReleased(MouseEvent e) {}public void mouseEntered(MouseEvent e) {}public void mouseExited(MouseEvent e) {}

        });
        
                
    }
    
    
    
}
