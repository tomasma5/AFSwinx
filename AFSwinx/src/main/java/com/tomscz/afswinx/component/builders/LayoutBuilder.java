package com.tomscz.afswinx.component.builders;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This interface specify all operation which must implement any of layout builder
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public interface LayoutBuilder {

    public void buildLayout(JPanel swingPanel);
    
    public void addComponent(JComponent component);
    
    public void addLabel(JLabel label);
    
    public void addMessage(JComponent messageComponent);
}
