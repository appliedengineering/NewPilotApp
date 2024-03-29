// License: GPL. For details, see Readme.txt file.
package external.org.openstreetmap.gui.jmapviewer.interfaces;

import java.util.EventListener;

import external.org.openstreetmap.gui.jmapviewer.events.JMVCommandEvent;

/**
 * Must be implemented for processing commands while user
 * interacts with map viewer.
 *
 * @author Jason Huntley
 *
 */
public interface JMapViewerEventListener extends EventListener {
    void processCommand(JMVCommandEvent command);
}
