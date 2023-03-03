/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author jeffrey
 */
public class TabbedContentPane extends JTabbedPane {
    
    List<ContentPane> contentPanes = new ArrayList<>();

    public TabbedContentPane() {
        
    }
    
    public void addPane(ContentPane panel) {
        contentPanes.add(panel);
        this.addTab(panel.title, null, panel, panel.title);
    }

    public static class ContentPane extends JPanel{
        public String title;
        
    }
    
}
