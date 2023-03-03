/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import newpilotapp.data.BoatDataManager;
import newpilotapp.gui.TabbedContentPane.ContentPane;
import newpilotapp.gui.components.chart.LineChartPanel;

/**
 *
 * @author jeffrey
 */
public class DataContentPane extends ContentPane{
    private Map<String, LineChartPanel> chartList;
    private Map<String, JLabel> booleanList;

    private JPanel chartDataDisplay;
    private JPanel booleanDataDisplay;

    public DataContentPane() {
        init();
    }
    
    private void init() {
        
        this.setLayout(new BorderLayout());
        
        chartList = new HashMap<>();
        booleanList = new HashMap<>();
        
        booleanDataDisplay = new JPanel();
        chartDataDisplay = new JPanel();
        
        this.add(booleanDataDisplay, BorderLayout.NORTH);
        this.add(chartDataDisplay, BorderLayout.CENTER);
        
        booleanDataDisplay.setLayout(new BoxLayout(booleanDataDisplay, BoxLayout.X_AXIS));
        chartDataDisplay.setLayout(new GridLayout(0, 2));

        

        for(String key : BoatDataManager.DATA_KEYS) {
            if(BoatDataManager.isDataKeyNumerical(key)) {
                LineChartPanel chart = new LineChartPanel(BoatDataManager.getTitleForDataKey(key));
                chartList.put(key, chart);
                chartDataDisplay.add(chart);
            }
        }
        
        // display boolean data differently
        
        this.add(booleanDataDisplay, BorderLayout.NORTH);
        
        for(String key : BoatDataManager.DATA_KEYS) {
            if(!BoatDataManager.isDataKeyNumerical(key)) {
                JLabel booleanValue = new JLabel(BoatDataManager.getTitleForDataKey(key) + " : FALSE");
                booleanList.put(key, booleanValue);
                booleanDataDisplay.add(booleanValue);
                booleanDataDisplay.add(Box.createHorizontalStrut(10));
            }
        }
    }
    
}
