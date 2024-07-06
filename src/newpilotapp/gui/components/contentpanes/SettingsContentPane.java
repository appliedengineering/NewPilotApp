/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
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
    private SettingsPanel networkPanel;
    private SettingsPanel pathPanel;

    
    public SettingsContentPane() {
        this.setLayout(new BorderLayout());
        
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        this.add(settingsPanel, BorderLayout.NORTH);
        
        Map<String, MutableLiveData<String>> portSettings = new HashMap<>();
        
        portSettings.put("Compass and GPS port", BoatDataManager.portCompassAndGps);
        portSettings.put("Stepper Driver port", BoatDataManager.portStepper);
        portSettings.put("Elec Data Boatstation port", BoatDataManager.portBoatstationElecData);
        portSettings.put("Elec Data port", BoatDataManager.portElec);

        Map<String, MutableLiveData<String>> networkSettings = new HashMap<>();
        
        networkSettings.put("Boatstation Address (ex. 192.168.1.23, ae-boat.local)", BoatDataManager.networkBoatAddress);
        
        Map<String, MutableLiveData<String>> pathSettings = new HashMap<>();
        
        pathSettings.put("Map Data Path", BoatDataManager.mapDataPath);


        
        

        
        portPanel = new SettingsPanel("Port Settings", portSettings);
        networkPanel = new SettingsPanel("Network Settings", networkSettings);
        pathPanel = new SettingsPanel("Path Settings", pathSettings);


        
//        compassOffsetPanel = new SettingsPanel("Compass Offsets", null);

        settingsPanel.add(portPanel);
        settingsPanel.add(networkPanel);
        settingsPanel.add(pathPanel);

        JLabel warning = new JLabel("Restart app after changes!!!");
        settingsPanel.add(warning);

//        settingsPanel.add(compassOffsetPanel);
        

    }
    
    
}
