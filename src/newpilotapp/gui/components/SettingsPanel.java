/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jeffrey
 */
public class SettingsPanel extends JPanel {
    private String[] fields;
    
    public SettingsPanel(String title, String[] fields) {
        this.setLayout(new GridLayout(0, 2));
        this.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
        
        this.fields = fields;
        
        init();
        
    }
    
    public void init() {
        this.add(new JTextField(30));
    }
    
    
}
