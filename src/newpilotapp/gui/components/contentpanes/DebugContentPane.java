/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import newpilotapp.data.DataManager;
import newpilotapp.framework.data.LiveDataObserver;
import newpilotapp.gui.TabbedContentPane.ContentPane;

/**
 *
 * @author jeffrey
 */
public class DebugContentPane extends ContentPane {
    
    private JTextArea debugOut;
    

    public DebugContentPane() {
        init();
    }
    
    public void init() {
        
        this.setLayout(new BorderLayout());
        
        this.title = "Debug Pane";
        debugOut = new JTextArea();
        debugOut.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(debugOut);

        
        DataManager.errorStatus.observe(new LiveDataObserver<String>() {
            @Override
            public void update(String data) {
                debugOut.setText(data);
            }
        
        });
        
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    
    
}
