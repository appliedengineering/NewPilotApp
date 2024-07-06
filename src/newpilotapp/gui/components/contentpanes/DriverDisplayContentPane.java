/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import newpilotapp.data.BoatDataManager;
import newpilotapp.drivers.GpsDriver;
import newpilotapp.framework.data.LiveDataObserver;
import newpilotapp.gui.TabbedContentPane.ContentPane;

/**
 *
 * @author jeffrey
 */
public class DriverDisplayContentPane extends ContentPane {
    
    private GpsDriver.GpsData data = null;
    
    Font boldFont = new Font ("Arial", Font.BOLD, 150);

    public DriverDisplayContentPane() {
        BoatDataManager.remoteGpsData.observe(new LiveDataObserver<GpsDriver.GpsData>() {
            @Override
            public void update(GpsDriver.GpsData data) {
                DriverDisplayContentPane.this.data = data;
                repaint();
            }
        
        });
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(data == null) return;
        
        Graphics2D g2 = (Graphics2D) g;
        
        RenderingHints rh = new RenderingHints(
             RenderingHints.KEY_ANTIALIASING,
             RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        
        g2.setColor(Color.BLACK);
        int r = getWidth()/3;
        int border = 20;
        g2.fillOval(getWidth()/2 - r/2, 0, r, r);
        g2.setColor(Color.WHITE);
        g2.fillOval(getWidth()/2 - (r-border)/2, border/2, (r-border), (r-border));
        
        g2.setColor(Color.BLACK);
        g2.fillOval(getWidth()/2 - r*3/2, 0, r, r);
        g2.setColor(Color.WHITE);
        g2.fillOval(getWidth()/2 - (r-border/3)*3/2, border/2, (r-border), (r-border));
        
        g2.setColor(Color.BLACK);
        g2.fillOval(getWidth()/2 + r/2, 0, r, r);
        g2.setColor(Color.WHITE);
        g2.fillOval(getWidth()/2 + (r+border)/2, border/2, (r-border), (r-border));
        
        
        g2.setFont(boldFont);
        
        g2.setStroke(new BasicStroke(border, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        
        
        double drawAngle = data.speed; // bearing
        
        String drawResult = String.format("%.1f", drawAngle);

                
        g2.setColor(Color.BLUE);
        g2.drawLine(
                getWidth()/2, 
                r/2,
                (int) (getWidth()/2+(r-border)/2*Math.cos(drawAngle/180*Math.PI)), 
                (int) (r/2+(r-border)/2*Math.sin(drawAngle/180*Math.PI)));
        
        g2.drawString(drawResult, getWidth()/2-r/2, r*3/2);
        
        g2.setColor(Color.BLACK);
        
        g2.drawLine(
                getWidth()/2 - r, 
                r/2,
                (int) (getWidth()/2 - r +(r-border)/2*Math.cos(drawAngle/180*Math.PI)), 
                (int) (r/2+(r-border)/2*Math.sin(drawAngle/180*Math.PI)));
        
        g2.drawString(drawResult, getWidth()/2-r*3/2, r*3/2);
        
        g2.setColor(Color.RED);
        
        g2.drawLine(
                getWidth()/2 + r, 
                r/2,
                (int) (getWidth()/2 + r +(r-border)/2*Math.cos(drawAngle/180*Math.PI)), 
                (int) (r/2+(r-border)/2*Math.sin(drawAngle/180*Math.PI)));
        
        g2.drawString(drawResult, getWidth()/2+r/2, r*3/2);
        
        // g2.drawString(drawAngle+" deg", getWidth()/2-r/2, r*2);
        //g2.drawArc(getWidth()/2 - (r-border)/2, getHeight()/2 - (r-border)/2, r, r, 0, 360);
    }
    
    
    
}
