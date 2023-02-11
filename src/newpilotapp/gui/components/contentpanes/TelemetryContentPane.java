/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import newpilotapp.data.DataManager;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.framework.data.LiveDataObserver;
import newpilotapp.gui.TabbedContentPane;
import newpilotapp.gui.components.chart.CompassChart;

/**
 *
 * @author jeffrey
 */
public class TelemetryContentPane extends TabbedContentPane.ContentPane{
    
    CompassChart compassChart;
    
    public TelemetryContentPane() {
        compassChart = new CompassChart("Compass Reading");
        
        GridLayout experimentLayout = new GridLayout(0,3); // 3 columns, rows expand automatically
        this.setLayout(experimentLayout);
        
        this.add(compassChart);
        for(int i=0; i<5; i++) {
            this.add(new JButton("Button " + i));
        }
        
//        this.setLayout(new BorderLayout());
//        this.add(compassChart, BorderLayout.CENTER);
        
        DataManager.compassHeading.observe(new LiveDataObserver<CompassDriver.CompassData> () {
            @Override
            public void update(CompassDriver.CompassData data) {
                if(data != null)
                compassChart.setAngle(data.compassHeading);
            }
            
        });
        
    }
    
}
