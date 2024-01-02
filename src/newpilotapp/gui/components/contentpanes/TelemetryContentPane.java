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
import external.org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import external.org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import external.org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;
import external.org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import external.org.openstreetmap.gui.jmapviewer.tilesources.BingTileSource;
import external.org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
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
    
    private JLabel gpsLocalData;
    private JLabel gpsRemoteData;
    
    private JMapViewerTree treeMap;
    private JMapViewer viewer;
    
    public JButton zoomIn, zoomOut;
    
    private boolean trackingEnabled = false;
    

    
    public TelemetryContentPane() {
         compassHeadingChart = new CompassChart("Compass Heading");
         targetHeadingChart = new CompassChart("Target Heading");
        
        Dimension side = new Dimension(150, 150);
         compassHeadingChart.setPreferredSize(side);
         targetHeadingChart.setPreferredSize(side);

                
        this.setLayout(new BorderLayout());
        
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        
        gpsLocalData = new JLabel();
        gpsRemoteData = new JLabel();
                
        setUpMap();
        
        
        BoatDataManager.localGpsData.observe(new LiveDataObserver<GpsDriver.GpsData>() {
            @Override
            public void update(GpsDriver.GpsData coordinate) {
                updateMapDisplay();
            }
        
        });
        
        BoatDataManager.remoteGpsData.observe(new LiveDataObserver<GpsDriver.GpsData>() {
            @Override
            public void update(GpsDriver.GpsData coordinate) {
                updateMapDisplay();
            }
        
        });
        
        
        // testing
        
        JPanel buttons = new JPanel();
        buttons.setLayout(new BorderLayout());
        
        JToggleButton tracking = new JToggleButton("Tracking OFF");
        tracking.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e) {
                 trackingEnabled = !trackingEnabled;
                 if(trackingEnabled) {
                     tracking.setText("Tracking ON");
                 } else {
                     tracking.setText("Tracking OFF");
                 }
                 updateMapDisplay();

             }
            
        });
        buttons.add(tracking, BorderLayout.NORTH);
        JPanel controls = new JPanel();
        
        setUpMapControls(controls);
        buttons.add(controls, BorderLayout.CENTER);
        
        JPanel charts = new JPanel();
        charts.setLayout(new BorderLayout());
        
//        setUpMapControls(charts);
        
        charts.add(compassHeadingChart, BorderLayout.NORTH);
        charts.add(targetHeadingChart, BorderLayout.SOUTH);
        charts.add(buttons, BorderLayout.CENTER);
        
        main.add(charts, BorderLayout.WEST);
        main.add(treeMap, BorderLayout.CENTER);
        
        JPanel gpsPanel = new JPanel();
        gpsPanel.setLayout(new BoxLayout(gpsPanel, BoxLayout.X_AXIS));
        gpsPanel.add(gpsLocalData);
        gpsPanel.add(Box.createHorizontalGlue());
        gpsPanel.add(gpsRemoteData);

        main.add(gpsPanel, BorderLayout.NORTH);

        this.add(main, BorderLayout.CENTER);

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
                if(data.lon == 0 || data.lat == 0) {
                    gpsLocalData.setText("GPS LOCAL: acquiring signal");
                    return;

                }
                gpsLocalData.setText(String.format("GPS LOCAL: ( %.6f , %.6f ) SPEED: %.3f", data.lat, data.lon, data.speed));
            }
            
        });
        
        BoatDataManager.remoteGpsData.observe(new LiveDataObserver<GpsDriver.GpsData> () {
            @Override
            public void update(GpsDriver.GpsData data) {
                if(data == null || data.lon == 0 || data.lat == 0) {
                    gpsRemoteData.setText("GPS REMOTE: disconnected");
                    return;
                }
                
                gpsRemoteData.setText(String.format("GPS REMOTE: ( %.6f , %.6f ) SPEED: %.3f", data.lat, data.lon, data.speed));
            }
            
        });
        
    }
    
    private void updateMapDisplay() {
        viewer.removeAllMapMarkers();
        viewer.removeAllMapPolygons();
        viewer.removeAllMapRectangles();
        
        // Point point = viewer.getCenter();
        // ICoordinate centerPoint = viewer.getPosition();
        
        // MapMarker centerMark = new MapMarkerDot(Color.BLUE, centerPoint.getLat(), centerPoint.getLon());
        // viewer.addMapMarker(centerMark);

        
        GpsDriver.GpsData local = BoatDataManager.localGpsData.getValue(), 
                remote = BoatDataManager.remoteGpsData.getValue();
        
        if(local != null) {
            if(local.lat == 0 || local.lon == 0) local = null;
        }
        if(remote != null) {
            if(remote.lat == 0 || remote.lon == 0) local = null;
        }
        

        if(trackingEnabled){
            if(remote != null && local != null){
                GpsDriver.GpsData center = GpsCalc.getCenter(remote, local);
                treeMap.getViewer().setDisplayPosition(center, treeMap.getViewer().getZoom());
            } else if(remote != null && local == null){
                treeMap.getViewer().setDisplayPosition(remote, treeMap.getViewer().getZoom());
            } else if(remote == null && local != null){
                treeMap.getViewer().setDisplayPosition(local, treeMap.getViewer().getZoom());
            }
            
        }
        
//        while(treeMap.getViewer().getMapPosition(local, true) == null || treeMap.getViewer().getMapPosition(remote, true) == null) {
//            treeMap.getViewer().zoomOut();
//            System.out.println("hey");
//        }
//        
        
        MapPolygon path = new MapPolygonImpl(local, remote, local);
        viewer.addMapPolygon(path);
        
        if(local != null) {
            MapMarker localMark = new MapMarkerDot(Color.BLUE, local.lat, local.lon);
            
            viewer.addMapMarker(localMark);
        }

        if(remote != null) {
            MapMarker remoteMark = new MapMarkerDot(Color.RED, remote.lat, remote.lon);
            viewer.addMapMarker(remoteMark);
        }
        


    }

    private void setUpMap() {
        treeMap = new JMapViewerTree("map");
        
        this.title = "Map";
        treeMap.getViewer().setZoomContolsVisible(false); // remove the annoyingly small zoom controls
        treeMap.getViewer().setTileSource(new OsmTileSource.Mapnik());
        treeMap.getViewer().setDisplayPosition(BoatDataManager.remoteGpsData.getValue(), 18);
        
        viewer = treeMap.getViewer();
    }

    private void setUpMapControls(JPanel panel) {
        panel.setLayout(new BorderLayout());
        zoomIn = new JButton("Zoom In");
        zoomOut = new JButton("Zoom Out");
        
        panel.add(zoomIn, BorderLayout.WEST);
        panel.add(zoomOut, BorderLayout.EAST);
        
        
        zoomIn.addActionListener((ActionEvent e) -> {
            viewer.zoomIn();
        });
        zoomOut.addActionListener((ActionEvent e) -> {
            viewer.zoomOut();
        });
        
        

    }
    
}
