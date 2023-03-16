// License: GPL. For details, see Readme.txt file.
package external.org.openstreetmap.gui.jmapviewer.interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import external.org.openstreetmap.gui.jmapviewer.Layer;
import external.org.openstreetmap.gui.jmapviewer.Style;

public interface MapObject {

    Layer getLayer();

    void setLayer(Layer layer);

    Style getStyle();

    Style getStyleAssigned();

    Color getColor();

    Color getBackColor();

    Stroke getStroke();

    Font getFont();

    String getName();

    boolean isVisible();
}
