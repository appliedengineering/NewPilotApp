/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import newpilotapp.data.BoatDataManager;
import newpilotapp.framework.data.LiveDataObserver;
import newpilotapp.gui.TabbedContentPane.ContentPane;

/**
 *
 * @author jeffrey
 */
public class DebugContentPane extends ContentPane {
    
    private JEditorPane debugOut;
    private JEditorPane portOut;

    
    private JPanel toolBar;
    private JButton clearAll;
    private JToggleButton generateDebugData;
    

    public DebugContentPane() {
        init();
    }
    
    public void init() {
        
        this.setLayout(new BorderLayout());
        
        
        
        this.title = "Debug Pane";
        debugOut = new JEditorPane();
        debugOut.setEditable(false);
        debugOut.setHighlighter(null);
        
        portOut = new JEditorPane();
        portOut.setEditable(false);
        portOut.setHighlighter(null); 
        
        JScrollPane scrollPaneLeft = new JScrollPane(debugOut);
        JScrollPane scrollPaneRight = new JScrollPane(portOut);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           scrollPaneLeft, scrollPaneRight);
        splitPane.setResizeWeight(.5f);


        
        BoatDataManager.errorStatus.observe(new LiveDataObserver<StringBuffer>() {
            @Override
            public void update(StringBuffer data) {
                debugOut.setText(data.toString());
            }
        
        });
        
        BoatDataManager.portData.observe(new LiveDataObserver<StringBuffer>() {
            @Override
            public void update(StringBuffer data) {
                portOut.setText(data.toString());
            }
        
        });
        
        this.add(splitPane, BorderLayout.CENTER);
        
        
        int p = 8;
        
        toolBar = new JPanel();
        toolBar.setBorder(new EmptyBorder(p, p, p, p));
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
        clearAll = new JButton("Clear All");
        clearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoatDataManager.errorStatus.setValue(new StringBuffer());
            }
            
        });
        
        generateDebugData = new JToggleButton();
        generateDebugData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoatDataManager.isDebugDataOn.setValue(generateDebugData.isSelected());
            }
            
        });
        BoatDataManager.isDebugDataOn.observe(new LiveDataObserver<Boolean>() {
            @Override
            public void update(Boolean isDebug) {
                generateDebugData.setText(isDebug ? "Debug Data ON" : "Debug Data OFF");
                generateDebugData.setSelected(isDebug);
            }
        });
        
        toolBar.add(clearAll);
        toolBar.add(Box.createRigidArea(new Dimension(p, 0)));
        toolBar.add(generateDebugData);
        this.add(toolBar, BorderLayout.NORTH);
    }
    
    
    
}
