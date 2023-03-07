/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import newpilotapp.data.BoatDataManager;
import newpilotapp.drivers.CompassDriver;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.framework.data.LiveDataObserver;
import newpilotapp.gui.TabbedContentPane;
import newpilotapp.gui.components.chart.CompassChart;
import newpilotapp.gui.components.chart.TelemetryMap;
import newpilotapp.logging.Console;

/**
 *
 * @author jeffrey
 */
public class TelemetryContentPane extends TabbedContentPane.ContentPane{
    
    private CompassChart compassHeadingChart;
    private CompassChart targetHeadingChart;
    private TelemetryMap telemetryMap;
    
    private JLabel gpsLocalData;
    private JLabel gpsRemoteData;
    

    
    public TelemetryContentPane() {
        compassHeadingChart = new CompassChart("Compass Heading");
        targetHeadingChart = new CompassChart("Target Heading");
        
        Dimension side = new Dimension(150, 0);
        compassHeadingChart.setPreferredSize(side);
        targetHeadingChart.setPreferredSize(side);

        
        telemetryMap = new TelemetryMap();
        
        // GridLayout experimentLayout = new GridLayout(0,3); // 3 columns, rows expand automatically
        this.setLayout(new BorderLayout());
        
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        
        gpsLocalData = new JLabel();
        gpsRemoteData = new JLabel();

        
        // testing
        
        
        main.add(compassHeadingChart, BorderLayout.WEST);
        main.add(targetHeadingChart, BorderLayout.EAST);
        main.add(telemetryMap, BorderLayout.CENTER);
        
        JPanel gpsPanel = new JPanel();
        gpsPanel.setLayout(new BoxLayout(gpsPanel, BoxLayout.X_AXIS));
        gpsPanel.add(gpsLocalData);
        gpsPanel.add(Box.createHorizontalGlue());
        gpsPanel.add(gpsRemoteData);

        main.add(gpsPanel, BorderLayout.NORTH);

        this.add(main, BorderLayout.CENTER);


        
//        for(int i=0; i<6; i++) {
//            CompassChart c = new CompassChart("Compass Reading " + i);
//            this.add(c);
//            BoatDataManager.compassHeading.observe(new LiveDataObserver<Sector2bDriver.CompassData> () {
//            @Override
//            public void update(Sector2bDriver.CompassData data) {
//                if(data == null) {
//                    c.setHasData(false);
//                } else {
//                    c.setHasData(true);
//                    c.setAngle(data.compassHeading);
//                }
//            }
//            
//        });
//        }
        
//        this.setLayout(new BorderLayout());
//        this.add(compassChart, BorderLayout.CENTER);
        
        BoatDataManager.compassHeading.observe(new LiveDataObserver<CompassDriver.CompassData> () {
            @Override
            public void update(CompassDriver.CompassData data) {
                if(data == null) {
                    compassHeadingChart.setHasData(false);
                } else {
                    compassHeadingChart.setHasData(true);
                    compassHeadingChart.setAngle(data.compassHeading);
                }
            }
            
        });
        
        BoatDataManager.telemetryHeading.observe(new LiveDataObserver<Double> () {
            @Override
            public void update(Double data) {
                if(data == null) {
                    targetHeadingChart.setHasData(false);
                } else {
                    targetHeadingChart.setHasData(true);
                    targetHeadingChart.setAngle(data);
                }
            }
            
        });
        
        BoatDataManager.localGpsData.observe(new LiveDataObserver<GpsDriver.GpsData> () {
            @Override
            public void update(GpsDriver.GpsData data) {
                if(data == null) {
                    gpsLocalData.setText("GPS LOCAL: disconnected");
                    return;
                }
                gpsLocalData.setText(String.format("GPS LOCAL: ( %.6f , %.6f ) SPEED: %.3f", data.lat, data.lon, data.speed));
            }
            
        });
        
        BoatDataManager.remoteGpsData.observe(new LiveDataObserver<GpsDriver.GpsData> () {
            @Override
            public void update(GpsDriver.GpsData data) {
                if(data == null) {
                    gpsRemoteData.setText("GPS REMOTE: disconnected");
                    return;
                }
                gpsRemoteData.setText(String.format("GPS REMOTE: ( %.6f , %.6f ) SPEED: %.3f", data.lat, data.lon, data.speed));
            }
            
        });
        
    }
    
}
