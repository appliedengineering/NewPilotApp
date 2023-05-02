/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import newpilotapp.logging.Console;

/**
 *
 * @author jeffrey
 */
public class CompassChart extends JPanel {
    
    private double angle = 90;
    private String title = "Chart";
    private boolean hasData = false;
    
    private Dimension preferredSize;
    
    public CompassChart(String titleNew) {
        this.title = titleNew;
        this.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
    }

    @Override
    public Dimension getPreferredSize() {
        return this.preferredSize == null ? super.getPreferredSize() : preferredSize;
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        this.preferredSize = preferredSize;
    }
    
    
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        RenderingHints rh = new RenderingHints(
             RenderingHints.KEY_ANTIALIASING,
             RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints rh1 = new RenderingHints(
             RenderingHints.KEY_RENDERING,
             RenderingHints.VALUE_RENDER_SPEED);
        g2.setRenderingHints(rh);
        
        Insets border = this.getInsets();
        
        int padding = 8;
        
        int boundsWidth = getWidth()-border.left-border.right-padding;
        int boundsHeight = getHeight()-border.top-border.bottom-padding;
        
        int width = Math.min(boundsWidth, boundsHeight);
        
        int widthPadding = (getWidth()-width)/2;
        int heightPadding = (getHeight()-width)/2;
        
        g.clearRect(0, 0, getWidth(), getHeight());
        
        if(!hasData) {    
                g2.drawString("No Data", widthPadding + padding, heightPadding + padding);
            return;
        }
        
        g2.setStroke(new BasicStroke(5));
        
        g2.setColor(Color.BLACK);
        
        
        double drawAngle = angle-90; // bearing
        
        g2.drawLine(getWidth()/2, 
                getHeight()/2, 
                (int) (getWidth()/2+width/2*Math.cos(drawAngle/180*Math.PI)), 
                (int) (getHeight()/2+width/2*Math.sin(drawAngle/180*Math.PI)));
        g2.drawArc(widthPadding, heightPadding, width, width, 0, 360);
        // Console.log("REPAINT " + System.currentTimeMillis());
        
        g2.setColor(Color.red);
        g2.drawString(String.format("%.2f", angle), 50, 50);

    }
    
    // GETTERS AND SETTERS

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.repaint();
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
        if(hasData == false) { repaint();}

    }
    
    
}
