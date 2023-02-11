/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.chart;

import external.org.knowm.xchart.QuickChart;
import external.org.knowm.xchart.SwingWrapper;
import external.org.knowm.xchart.XYChart;

/**
 *
 * @author jeffrey
 */
public class RealtimeChart {
    
    public static void main(String[] args) {
        new RealtimeChart();
    }

    public RealtimeChart() {
        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };

        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

        
        // Show it
        new SwingWrapper(chart).displayChart();
    }

    
}
