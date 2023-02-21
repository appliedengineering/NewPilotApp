/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
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
    
    public CompassChart(String titleNew) {
        this.title = titleNew;
        this.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
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
        
        
        g2.drawLine(getWidth()/2, 
                getHeight()/2, 
                (int) (getWidth()/2+width/2*Math.cos(angle/180*Math.PI)), 
                (int) (getHeight()/2+width/2*Math.sin(angle/180*Math.PI)));
        g2.drawArc(widthPadding, heightPadding, width, width, 0, 360);
        // Console.log("REPAINT " + System.currentTimeMillis());

    }
    
    // GETTERS AND SETTERS

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.repaint();
        //label.setText(String.valueOf(angle));
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
        if(hasData == false) { repaint();}

    }
    
    
}
