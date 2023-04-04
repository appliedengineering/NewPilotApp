/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import external.org.openstreetmap.gui.jmapviewer.Coordinate;
import external.org.openstreetmap.gui.jmapviewer.JMapViewerTree;
import external.org.openstreetmap.gui.jmapviewer.tilesources.BingAerialTileSource;
import external.org.openstreetmap.gui.jmapviewer.tilesources.BingTileSource;
import external.org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
import java.awt.BorderLayout;
import newpilotapp.gui.TabbedContentPane.ContentPane;

/**
 *
 * @author jeffrey
 */
public class MapContentPane extends ContentPane {
    
    private final JMapViewerTree treeMap;

    
    public MapContentPane() {
        this.setLayout(new BorderLayout());

        treeMap = new JMapViewerTree("map");
        
        this.title = "Map";
        treeMap.getViewer().setDisplayPosition(new Coordinate(38.3376571,-121.0969088), 11);
        treeMap.getViewer().setTileSource(new OsmTileSource.Mapnik());
        
        this.add(treeMap, BorderLayout.CENTER);

    }
    
}
