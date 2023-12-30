/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.gui.components;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import newpilotapp.data.BoatDataManager;
import newpilotapp.framework.data.MutableLiveData;

/**
 *
 * @author jeffrey
 */
public class SettingsPanel extends JPanel {
    private Map<String, MutableLiveData<String>> fields;
    
    public SettingsPanel(String title, Map<String, MutableLiveData<String>> fields) {
        this.setLayout(new GridLayout(0, 3));
        this.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP))
        );
        
        this.fields = fields;
        
        init();
        
    }
    
    public void init() {
        for(String key : fields.keySet()){
            MutableLiveData<String> liveData = fields.get(key);
            JTextField textField = new JTextField();
            
            if(liveData.getValue() != null){
                textField.setText(liveData.getValue() );
            }
            
            JButton btn = new JButton("Save");
            
            btn.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    liveData.setValue(textField.getText());
                    BoatDataManager.saveAllProperties();
                }
            
            });
            
            this.add(new JLabel(key));
            this.add(textField);
            this.add(btn);

        }
    }
    
    
}
