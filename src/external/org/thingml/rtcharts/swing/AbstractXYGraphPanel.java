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

/**
 *
 * @author franck
 */
public class AbstractXYGraphPanel extends javax.swing.JPanel {

    /** Creates new form GraphPanel */
    public AbstractXYGraphPanel() {
        initComponents();
        System.out.println("After AbstractGraphPanel::initComponents");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitle = new javax.swing.JLabel();
        jLabelYMax = new javax.swing.JLabel();
        jLabelXYMin = new javax.swing.JLabel();
        jLabelXMax = new javax.swing.JLabel();
        jLabelYAVG = new javax.swing.JLabel();
        jLabelXAVG = new javax.swing.JLabel();

        setBackground(java.awt.Color.darkGray);
        setMinimumSize(new java.awt.Dimension(200, 100));

        jLabelTitle.setBackground(new java.awt.Color(217, 217, 217));
        jLabelTitle.setFont(jLabelTitle.getFont().deriveFont(jLabelTitle.getFont().getStyle() | java.awt.Font.BOLD));
        jLabelTitle.setForeground(java.awt.Color.gray);
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Graph Title (unit)");
        jLabelTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabelYMax.setFont(jLabelYMax.getFont().deriveFont(jLabelYMax.getFont().getSize()-2f));
        jLabelYMax.setForeground(java.awt.Color.lightGray);
        jLabelYMax.setText("ymax");

        jLabelXYMin.setFont(jLabelXYMin.getFont().deriveFont(jLabelXYMin.getFont().getSize()-2f));
        jLabelXYMin.setForeground(java.awt.Color.lightGray);
        jLabelXYMin.setText("ymin | xmin");

        jLabelXMax.setFont(jLabelXMax.getFont().deriveFont(jLabelXMax.getFont().getSize()-2f));
        jLabelXMax.setForeground(java.awt.Color.lightGray);
        jLabelXMax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelXMax.setText("xmax");

        jLabelYAVG.setFont(jLabelYAVG.getFont().deriveFont(jLabelYAVG.getFont().getStyle() | java.awt.Font.BOLD));
        jLabelYAVG.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelYAVG.setText("0");

        jLabelXAVG.setFont(jLabelXAVG.getFont().deriveFont(jLabelXAVG.getFont().getStyle() | java.awt.Font.BOLD));
        jLabelXAVG.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelXAVG.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelYMax, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                            .addComponent(jLabelXYMin, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelXMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelXAVG, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelYAVG, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTitle)
                    .addComponent(jLabelYAVG)
                    .addComponent(jLabelXAVG))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelYMax)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 408, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelXMax)
                    .addComponent(jLabelXYMin)))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel jLabelTitle;
    protected javax.swing.JLabel jLabelXAVG;
    protected javax.swing.JLabel jLabelXMax;
    protected javax.swing.JLabel jLabelXYMin;
    protected javax.swing.JLabel jLabelYAVG;
    protected javax.swing.JLabel jLabelYMax;
    // End of variables declaration//GEN-END:variables
}
