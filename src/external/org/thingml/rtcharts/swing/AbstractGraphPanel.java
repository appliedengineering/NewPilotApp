/**
 * Copyright (C) 2012 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * ../HEADER
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GraphPanel.java
 *
 * Created on 1 juil. 2012, 13:01:21
 */
package external.org.thingml.rtcharts.swing;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
import javax.swing.JDialog;

/**
 *
 * @author franck
 */
abstract public class AbstractGraphPanel extends javax.swing.JPanel {

    /** Creates new form GraphPanel */
    public AbstractGraphPanel() {
        initComponents();
        //System.out.println("After AbstractGraphPanel::initComponents");
        setDarkBackground();
    }
    
    protected static Color dark = new java.awt.Color(31, 31, 31);
    protected static Color bright = new java.awt.Color(255, 255, 255);

    protected static JColorChooser cchooser = new JColorChooser();
    
    protected boolean autoScale = false;
    
    public void setDarkBackground() {
         this.setBackground(dark);
        jLabelTitle.setBackground(dark);
        jLabelAVG.setBackground(dark);
        jLabelValue.setBackground(dark);
        jLabelVMax.setForeground(bright);
        jLabelVMin.setForeground(bright);
        jLabelYMax.setForeground(bright);
        jLabelYMin.setForeground(bright);   
    }
    
    public void setBrightBackground() {
         this.setBackground(bright);
        jLabelTitle.setBackground(bright);
        jLabelAVG.setBackground(bright);
        jLabelValue.setBackground(bright);
        jLabelVMax.setForeground(dark);
        jLabelVMin.setForeground(dark);
        jLabelYMax.setForeground(dark);
        jLabelYMin.setForeground(dark);   
    }
    
    public void setColor(Color fg) {
        jLabelTitle.setForeground(fg);
        jLabelAVG.setForeground(fg);
        jLabelValue.setForeground(fg);
    }
    
    public Color getColor() {
        return Color.white;
    }

    abstract void mouseClicked(java.awt.event.MouseEvent evt);

    abstract void fitToContent();

    public void setAutoScale(boolean val) {
        this.autoScale = val;
        if (autoScale == false ) jMenuItemAutoScale.setText("Auto scale");
           else jMenuItemAutoScale.setText("Auto scale (ON)");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItemBrightBG = new javax.swing.JMenuItem();
        jMenuItemDarkBG = new javax.swing.JMenuItem();
        jMenuItemColor = new javax.swing.JMenuItem();
        jMenuItemAutoScale = new javax.swing.JMenuItem();
        jMenuItemFit = new javax.swing.JMenuItem();
        jLabelTitle = new javax.swing.JLabel();
        jLabelYMax = new javax.swing.JLabel();
        jLabelYMin = new javax.swing.JLabel();
        jLabelVMax = new javax.swing.JLabel();
        jLabelVMin = new javax.swing.JLabel();
        jLabelValue = new javax.swing.JLabel();
        jLabelAVG = new javax.swing.JLabel();

        jMenuItemBrightBG.setText("Bright Background");
        jMenuItemBrightBG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBrightBGActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemBrightBG);

        jMenuItemDarkBG.setText("Dark Background");
        jMenuItemDarkBG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDarkBGActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemDarkBG);

        jMenuItemColor.setText("Change Color...");
        jMenuItemColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemColorActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemColor);

        jMenuItemAutoScale.setText("Auto scale");
        jMenuItemAutoScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAutoScaleActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemAutoScale);

        jMenuItemFit.setText("Fit to content");
        jMenuItemFit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemFit);

        setBackground(new java.awt.Color(51, 51, 51));
        setMinimumSize(new java.awt.Dimension(200, 100));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        jLabelTitle.setBackground(new java.awt.Color(217, 217, 217));
        jLabelTitle.setFont(jLabelTitle.getFont().deriveFont(jLabelTitle.getFont().getStyle() | java.awt.Font.BOLD));
        jLabelTitle.setForeground(java.awt.Color.gray);
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Graph Title (unit)");
        jLabelTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabelYMax.setFont(jLabelYMax.getFont().deriveFont(jLabelYMax.getFont().getSize()-2f));
        jLabelYMax.setForeground(java.awt.Color.lightGray);
        jLabelYMax.setText("max");

        jLabelYMin.setFont(jLabelYMin.getFont().deriveFont(jLabelYMin.getFont().getSize()-2f));
        jLabelYMin.setForeground(java.awt.Color.lightGray);
        jLabelYMin.setText("min");

        jLabelVMax.setFont(jLabelVMax.getFont().deriveFont(jLabelVMax.getFont().getSize()-2f));
        jLabelVMax.setForeground(java.awt.Color.lightGray);
        jLabelVMax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelVMax.setText("max");

        jLabelVMin.setFont(jLabelVMin.getFont().deriveFont(jLabelVMin.getFont().getSize()-2f));
        jLabelVMin.setForeground(java.awt.Color.lightGray);
        jLabelVMin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelVMin.setText("min");

        jLabelValue.setFont(jLabelValue.getFont().deriveFont(jLabelValue.getFont().getStyle() | java.awt.Font.BOLD));
        jLabelValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelValue.setText("0");

        jLabelAVG.setFont(jLabelAVG.getFont().deriveFont(jLabelAVG.getFont().getStyle() | java.awt.Font.BOLD));
        jLabelAVG.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelAVG.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelYMax, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                            .addComponent(jLabelYMin, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelVMax, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                            .addComponent(jLabelVMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelAVG, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelValue, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTitle)
                    .addComponent(jLabelValue)
                    .addComponent(jLabelAVG))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelYMax)
                    .addComponent(jLabelVMax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 408, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelVMin)
                    .addComponent(jLabelYMin)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        mouseClicked( evt );
        System.out.println("Mouse clicked " + jLabelTitle.getText());
    }//GEN-LAST:event_formMouseClicked

    private void jMenuItemBrightBGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBrightBGActionPerformed
       setBrightBackground();
        
    }//GEN-LAST:event_jMenuItemBrightBGActionPerformed

    private void jMenuItemDarkBGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDarkBGActionPerformed
       setDarkBackground();
    }//GEN-LAST:event_jMenuItemDarkBGActionPerformed

    private void jMenuItemColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemColorActionPerformed
        Color newc = JColorChooser.showDialog(this, "Change Graph Color...", getColor());
        if (newc != null) setColor(newc);
    }//GEN-LAST:event_jMenuItemColorActionPerformed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        if (evt.getButton() == MouseEvent.BUTTON3) {
            jPopupMenu1.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_formMouseReleased

    private void jMenuItemFitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFitActionPerformed
        //if ( autoScale == true ) fitToContent();
        fitToContent();
    }//GEN-LAST:event_jMenuItemFitActionPerformed

    private void jMenuItemAutoScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAutoScaleActionPerformed
        autoScale = !autoScale;
        if (autoScale == false ) jMenuItemAutoScale.setText("Auto scale");
           else jMenuItemAutoScale.setText("Auto scale (ON)");
    }//GEN-LAST:event_jMenuItemAutoScaleActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel jLabelAVG;
    protected javax.swing.JLabel jLabelTitle;
    protected javax.swing.JLabel jLabelVMax;
    protected javax.swing.JLabel jLabelVMin;
    protected javax.swing.JLabel jLabelValue;
    protected javax.swing.JLabel jLabelYMax;
    protected javax.swing.JLabel jLabelYMin;
    private javax.swing.JMenuItem jMenuItemAutoScale;
    private javax.swing.JMenuItem jMenuItemBrightBG;
    private javax.swing.JMenuItem jMenuItemColor;
    private javax.swing.JMenuItem jMenuItemDarkBG;
    private javax.swing.JMenuItem jMenuItemFit;
    private javax.swing.JPopupMenu jPopupMenu1;
    // End of variables declaration//GEN-END:variables
}
