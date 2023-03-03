/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.chart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.drivers.CompassDriver;

/**
 *
 * @author jeffrey
 */
public class TelemetryMap extends JComponent {
    
    private GpsDriver.GpsData gpsLocal;
    private GpsDriver.GpsData gpsRemote;
    
    private CompassDriver.CompassData compassLocal;
    private CompassDriver.CompassData compassRemote;
    
    private static final Color bgColor = new Color(148, 181, 235);
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;

        // write the drawing code here
        g2.setColor(bgColor);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    public GpsDriver.GpsData getGpsLocal() {
        return gpsLocal;
    }

    public void setGpsLocal(GpsDriver.GpsData gpsLocal) {
        this.gpsLocal = gpsLocal;
        repaint();
    }

    public GpsDriver.GpsData getGpsRemote() {
        return gpsRemote;
    }

    public void setGpsRemote(GpsDriver.GpsData gpsRemote) {
        this.gpsRemote = gpsRemote;
        repaint();
    }

    public CompassDriver.CompassData getCompassLocal() {
        return compassLocal;
    }

    public void setCompassLocal(CompassDriver.CompassData compassLocal) {
        this.compassLocal = compassLocal;
        repaint();
    }

    public CompassDriver.CompassData getCompassRemote() {
        return compassRemote;
    }

    public void setCompassRemote(CompassDriver.CompassData compassRemote) {
        this.compassRemote = compassRemote;
        repaint();
    }
    
}
