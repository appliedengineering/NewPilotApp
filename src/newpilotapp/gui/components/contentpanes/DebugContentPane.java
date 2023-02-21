/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components.contentpanes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import newpilotapp.data.DataManager;
import newpilotapp.framework.data.LiveDataObserver;
import newpilotapp.gui.TabbedContentPane.ContentPane;

/**
 *
 * @author jeffrey
 */
public class DebugContentPane extends ContentPane {
    
    private JEditorPane debugOut;
    
    private JPanel toolBar;
    private JButton clearAll;
    

    public DebugContentPane() {
        init();
    }
    
    public void init() {
        
        this.setLayout(new BorderLayout());
        
        this.title = "Debug Pane";
        debugOut = new JEditorPane();
        
        debugOut.setForeground(Color.black);
        debugOut.setEditable(false);
        debugOut.setHighlighter(null);
        
        
        JScrollPane scrollPane = new JScrollPane(debugOut);

        
        DataManager.errorStatus.observe(new LiveDataObserver<StringBuffer>() {
            @Override
            public void update(StringBuffer data) {
                debugOut.setText(data.toString());
            }
        
        });
        
        this.add(scrollPane, BorderLayout.CENTER);
        
        
        toolBar = new JPanel();
        toolBar.setBorder(new EmptyBorder(8, 8, 8, 8));
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
        clearAll = new JButton("Clear All");
        clearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataManager.errorStatus.setValue(new StringBuffer());
            }
            
        });
        
        toolBar.add(clearAll);
        this.add(toolBar, BorderLayout.NORTH);
    }
    
    
    
}
