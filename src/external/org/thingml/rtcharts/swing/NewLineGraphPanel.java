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
package external.org.thingml.rtcharts.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import newpilotapp.logging.Console;

/**
 * Created by IntelliJ IDEA.
 * User: franck
 * Date: 01/07/12
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */
public class NewLineGraphPanel extends GraphPanel {

    protected boolean saturate = true;
    
    public NewLineGraphPanel(GraphBuffer buffer, String name, int ymin, int ymax, int yminor, int xminor, Color color) {
        super(buffer, name, ymin, ymax, yminor, color);
        setXminor(xminor);
    }
    
    public NewLineGraphPanel(GraphBuffer buffer, String name, int ymin, int ymax, int yminor, Color color) {
        super(buffer, name, ymin, ymax, yminor, color);
    }
    
    public NewLineGraphPanel(GraphBuffer buffer, String name, int ymin, int ymax, int yminor, int xminor, Color color, double scale, String labelFormatString, String avgValText, String lastValText) {
        super(buffer, name, ymin, ymax, yminor, color);
        setXminor(xminor);
        setScaleAndFormat(scale, labelFormatString);
        setAvgValText(avgValText);
        setLastValText(lastValText);
    }
    
    public NewLineGraphPanel(GraphBuffer buffer, String name, int ymin, int ymax, int yminor, Color color, double scale, String labelFormatString, String avgValText, String lastValText) {
        super(buffer, name, ymin, ymax, yminor, color);
        setScaleAndFormat(scale, labelFormatString);
        setAvgValText(avgValText);
        setLastValText(lastValText);
    }
    
    @Override
    protected void drawData(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
         if(graphValues == null) return;

            int X, Y;
            int lastX = 0, lastY = Integer.MIN_VALUE;
            int highestValue = bufferYmax;
            int lowestValue = bufferYmin;
            
            if (lowestValue <= highestValue){ 
                //jLabelVMin.setText("" + lowestValue);
                jLabelVMin.setText("" + labelFormat.format(lowestValue * scale));
                //jLabelVMax.setText("" + highestValue);
                jLabelVMax.setText("" + labelFormat.format(highestValue * scale));
            }

            g2.setColor(color);
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int[] pointsX = new int[graphValues.length+2];
            int[] pointsY = new int[graphValues.length+2];
            
            int yval;
            
            int realCount = 0;

            for(int i = 0; i < graphValues.length; i++) {

                yval = graphValues[i];
                
                if(yval != graphBuffer.getInvalidNumber()) {

                    if (saturate) { // Plot out of bound value with the maximum visible value
                        if (yval < ymin) yval = ymin;
                        if (yval > ymax) yval = ymax;
                    }
                    else { // do not plot "out of range" values
                        if (yval < ymin || yval > ymax) {
                            lastY = Integer.MIN_VALUE;
                            continue;
                        }
                    }
                    
                    if(lastY != Integer.MIN_VALUE) {
                        yval = (int) (lastY*0.5 + yval*0.5);
                    }


                    X = computeX(i);
                    Y = computeY(yval);
                    pointsX[i] = X;
                    pointsY[i] = Y;
                    

//                    if(lastY == Integer.MIN_VALUE) {
//                        g.drawLine(X, Y, X , Y);
//                    }
//                    else {
//                        g.drawLine(lastX, lastY, X , Y);
//                    }
                    lastY = yval;
                    lastX = i;
                    
                    realCount++;
                }
            }
            
            pointsX[realCount] = computeX(realCount-1);
            pointsY[realCount] = computeY(ymin);
            
            pointsX[realCount+1] = computeX(0);
            pointsY[realCount+1] = computeY(ymin);
            


            GradientPaint gp = new GradientPaint(0,computeY(ymin),color.brighter().brighter(),0,computeY(ymax),Color.WHITE); 
            g2.setPaint(gp);
            
            Polygon poly = new Polygon(pointsX, pointsY, realCount+2);
            Polygon polyLine = new Polygon(pointsX, pointsY, graphValues.length);

            
            g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            g2.fillPolygon(poly);
            
            g2.setColor(Color.BLUE);
            g2.drawPolyline(pointsX, pointsY, graphValues.length);


    }
}
