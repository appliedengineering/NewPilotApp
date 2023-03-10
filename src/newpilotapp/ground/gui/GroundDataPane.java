/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.ground.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import newpilotapp.data.BoatDataManager;
import newpilotapp.data.DataPoint;
import newpilotapp.framework.data.LiveDataObserver;
import newpilotapp.gui.components.chart.LineChartPanel;

/**
 *
 * @author jeffrey
 */
public class GroundDataPane extends JPanel{
    private Map<String, LineChartPanel> chartList;
    private Map<String, JLabel> booleanList;

    private JPanel chartDataDisplay;
    private JPanel booleanDataDisplay;

    public GroundDataPane() {
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
                chart.start();
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
        
        BoatDataManager.dataFromBoatController.observe(new LiveDataObserver<Map<String, List<DataPoint>>>() {
            @Override
            public void update(Map<String, List<DataPoint>> data) {
                for(int i = 0; i < BoatDataManager.DATA_KEYS.length; i++) {
                    String key = BoatDataManager.DATA_KEYS[i];
                    List<DataPoint> points = data.get(key);
                    DataPoint lastPoint;
                    if(points != null && !points.isEmpty()) {
                        lastPoint = points.get(points.size() - 1);
                    } else {
                        lastPoint = new DataPoint();
                        lastPoint.valueDouble = 10;
                        lastPoint.valueBool = false;
                    }

                    if(BoatDataManager.isDataKeyNumerical(BoatDataManager.DATA_KEYS[i])) {
                        LineChartPanel chart = chartList.get(key);
                        chart.addPoint(lastPoint);
                    } else {
                        JLabel label = booleanList.get(BoatDataManager.DATA_KEYS[i]);
                        label.setText(BoatDataManager.getTitleForDataKey(key) + ((lastPoint.valueBool) ? " : TRUE" : " : FALSE"));
                    }
                }
            }
            
        });
    }
    
}
