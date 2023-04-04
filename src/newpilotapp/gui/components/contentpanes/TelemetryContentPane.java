/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import external.org.openstreetmap.gui.jmapviewer.Coordinate;
import external.org.openstreetmap.gui.jmapviewer.JMapViewer;
import external.org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import external.org.openstreetmap.gui.jmapviewer.Layer;
import external.org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import external.org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import external.org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import external.org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import external.org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import external.org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import external.org.openstreetmap.gui.jmapviewer.tilesources.BingTileSource;
import external.org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
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
import newpilotapp.drivers.GpsCalc;
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
    
    private JMapViewerTree treeMap;
    

    
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
        
        treeMap = new JMapViewerTree("map");
        
        this.title = "Map";
        treeMap.getViewer().setTileSource(new OsmTileSource.Mapnik());
        // Layer boatLayer = treeMap.addLayer("boat");

        
        BoatDataManager.localGpsData.observe(new LiveDataObserver<GpsDriver.GpsData>() {
            @Override
            public void update(GpsDriver.GpsData coordinate) {
                updateMapDisplay();
            }
        
        });

        
        // testing
        
        JPanel charts = new JPanel();
        charts.setLayout(new BoxLayout(charts, BoxLayout.Y_AXIS));
        charts.add(compassHeadingChart, BorderLayout.NORTH);
        charts.add(targetHeadingChart, BorderLayout.SOUTH);
        
        main.add(charts, BorderLayout.WEST);
        main.add(treeMap, BorderLayout.CENTER);
        
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
    
    private void updateMapDisplay() {
        treeMap.getViewer().removeAllMapMarkers();
        treeMap.getViewer().removeAllMapPolygons();
        treeMap.getViewer().removeAllMapRectangles();
        
        GpsDriver.GpsData local = BoatDataManager.localGpsData.getValue(), 
                remote = BoatDataManager.remoteGpsData.getValue();
        
        if(local == null || remote == null) return;
        
        treeMap.getViewer().setDisplayPosition(GpsCalc.getCenter(remote, local), 16);
        
        MapPolygon path = new MapPolygonImpl(local, remote, local);
        treeMap.getViewer().addMapPolygon(path);
        
        MapMarker localMark = new MapMarkerDot(new Coordinate(local.lat, local.lon));
        MapMarker remoteMark = new MapMarkerDot(new Coordinate(remote.lat, remote.lon));

        treeMap.getViewer().addMapMarker(localMark);
        treeMap.getViewer().addMapMarker(remoteMark);



    }
    
}
