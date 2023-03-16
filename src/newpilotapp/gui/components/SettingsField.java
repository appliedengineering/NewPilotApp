/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jeffrey
 */
public class SettingsField extends JPanel {
    
    private JLabel label;
    private JTextField field;
    
    public SettingsField(String label) {
        this.label = new JLabel(label);
        this.field = new JTextField();
        this.add(this.label);
        this.add(this.field);
    }
    
}
