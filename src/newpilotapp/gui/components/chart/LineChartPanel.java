/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.chart;

import external.org.thingml.rtcharts.swing.BarGraphPanel;
import external.org.thingml.rtcharts.swing.GraphBuffer;
import external.org.thingml.rtcharts.swing.GraphPanel;
import external.org.thingml.rtcharts.swing.LineGraphPanel;
import external.org.thingml.rtcharts.swing.NewLineGraphPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import newpilotapp.data.DataPoint;

/**
 *
 * @author jeffrey
 */
public class LineChartPanel extends JPanel {
    
    private GraphBuffer graphBuffer = new GraphBuffer(50);
    private GraphPanel graphPanel;
    
    private JLabel titleLabel;
    private String title;


    
    public LineChartPanel(String title) {
        graphPanel = new NewLineGraphPanel(graphBuffer, title, -10, 100, 25, Color.BLUE);
        graphPanel.setBrightBackground();
        titleLabel = new JLabel();
        
        this.title = title;
        titleLabel.setText(title);
        
        this.setLayout(new BorderLayout());
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(titleLabel, BorderLayout.NORTH);
    }
    
    public void start() {
        graphPanel.start();
    }
    
    public void stop() {
        graphPanel.stop();
    }
    
    public void addPoint(DataPoint point){
        titleLabel.setText(String.format("%s : %.2f", title, point.valueDouble));
        graphBuffer.insertData((int) (point.valueDouble));
    }
    
    
    
}
