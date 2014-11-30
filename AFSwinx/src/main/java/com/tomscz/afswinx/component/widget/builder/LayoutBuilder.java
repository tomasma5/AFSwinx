package com.tomscz.afswinx.component.widget.builder;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This interface specify all operation which must implement any of layout builder
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public interface LayoutBuilder {

    public void buildLayout(JPanel swingPanel);
    
    public void addComponent(JComponent component);
    
    public void addLabel(JTextArea label);
    
    public void addMessage(JComponent messageComponent);
}
