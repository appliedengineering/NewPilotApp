/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import newpilotapp.gui.TabbedContentPane.ContentPane;
import newpilotapp.gui.components.SettingsPanel;

/**
 *
 * @author jeffrey
 */
public class SettingsContentPane extends ContentPane {
    
    private JPanel settingsPanel;
    
    // panels by settings type
    private SettingsPanel networkingPanel;
    private SettingsPanel compassOffsetPanel;
    
    public SettingsContentPane() {
        this.setLayout(new BorderLayout());
        
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        this.add(settingsPanel, BorderLayout.NORTH);
        
        networkingPanel = new SettingsPanel("Network Settings", null);
        
        compassOffsetPanel = new SettingsPanel("Compass Offsets", null);

        settingsPanel.add(networkingPanel);
        settingsPanel.add(compassOffsetPanel);
        

    }
    
}
