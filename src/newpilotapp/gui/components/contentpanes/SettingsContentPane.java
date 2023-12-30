/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import newpilotapp.data.BoatDataManager;
import newpilotapp.framework.data.MutableLiveData;
import newpilotapp.gui.TabbedContentPane.ContentPane;
import newpilotapp.gui.components.SettingsPanel;

/**
 *
 * @author jeffrey
 */
public class SettingsContentPane extends ContentPane {
    
    private JPanel settingsPanel;
    
    // panels by settings type
    private SettingsPanel portPanel;
    private SettingsPanel compassOffsetPanel;
    
    public SettingsContentPane() {
        this.setLayout(new BorderLayout());
        
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        this.add(settingsPanel, BorderLayout.NORTH);
        
        Map<String, MutableLiveData<String>> portSettings = new HashMap<>();
        
        portSettings.put("Compass and GPS port", BoatDataManager.portCompassAndGps);
        portSettings.put("Stepper Driver port", BoatDataManager.portStepper);
        portSettings.put("Elec Data Boatstation port", BoatDataManager.portBoatstationElecData);


        
        

        
        portPanel = new SettingsPanel("Port Settings", portSettings);
        
//        compassOffsetPanel = new SettingsPanel("Compass Offsets", null);

        settingsPanel.add(portPanel);
//        settingsPanel.add(compassOffsetPanel);
        

    }
    
    
}
