/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.chart;

import external.org.thingml.rtcharts.swing.BarGraphPanel;
import external.org.thingml.rtcharts.swing.GraphBuffer;
import external.org.thingml.rtcharts.swing.GraphPanel;
import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author jeffrey
 */
public class LineChartPanel extends JPanel{
    
    private GraphBuffer graphBuffer = new GraphBuffer(500);
    private GraphPanel graphPanel;


    
    private LineChartPanel(String title) {
        graphPanel = new BarGraphPanel(graphBuffer, title, -100, 100, 25, Color.RED);
//        this.getChart().getStyler()
//                .setAntiAlias(true);
        
    }
    
    
    
}
