package com.tomscz.afswinx.component.panel;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class AFSwinxPanel extends JPanel{
    
    private static final long serialVersionUID = 1L;
    private JComponent dataHolder;
    private JComponent labelHolder;

    public AFSwinxPanel(JComponent dataHolder){
        this.dataHolder = dataHolder;
    }
    
    public AFSwinxPanel(JComponent dataHolder, JComponent labelHolder){
        this.dataHolder = dataHolder;
        this.labelHolder = labelHolder;
    }
    
    public AFSwinxPanel(JComponent dataHolder, JComponent labelHolder, JPanel content){
        this.dataHolder = dataHolder;
        this.labelHolder = labelHolder;
        this.add(content);
    }
    
    public JComponent getDataHolder() {
        return dataHolder;
    }

    public void setDataHolder(JComponent dataHolder) {
        this.dataHolder = dataHolder;
    }
}
