/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.chart;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author jeffrey
 */
public class CompassChart extends JPanel {
    
    private double angle = 90;
    private String title = "Chart";
    private boolean hasData = false;
    
    private JLabel label;

    public CompassChart(String titleNew) {
        this.title = titleNew;
        this.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
        label = new JLabel();
        //this.add(label);
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Insets border = this.getInsets();
        
        int padding = 8;
        
        int boundsWidth = getWidth()-border.left-border.right-padding;
        int boundsHeight = getHeight()-border.top-border.bottom-padding;
        
        int width = Math.min(boundsWidth, boundsHeight);
        
        int widthPadding = (getWidth()-width)/2;
        int heightPadding = (getHeight()-width)/2;
        
        g.clearRect(widthPadding, heightPadding, width, width);
        
        if(!hasData) {    
            Graphics2D g2 = (Graphics2D) g;
                g2.drawString("No Data", widthPadding + padding, heightPadding + padding);
            return;
        }
        
        g.setColor(Color.BLACK);
        
        // g.drawRect(widthPadding, heightPadding, width, width);
        g.setColor(Color.BLUE);
        g.fillArc(widthPadding, heightPadding, width, width, 0, (int) (angle));
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
