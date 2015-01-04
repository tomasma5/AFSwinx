package com.tomscz.afswinx.component.widget.builder;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This interface specify all operation which must implement of any layout builder
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public interface LayoutBuilder {

    /**
     * This method build concrete layout.
     * 
     * @param swingPanel panel on which will be build applicate
     */
    public void buildLayout(JPanel swingPanel);

    /**
     * This method add component to layout builder. You can use this method until
     * {@link LayoutBuilder#buildLayout(JPanel)} method.
     * 
     * @param component which will be add.
     */
    public void addComponent(JComponent component);

    /**
     * This method add label to layout builder. Label has its own setting.
     * 
     * @param label which will be add to layout builder.
     */
    public void addLabel(JTextArea label);

    /**
     * This method add validation message to layout builder.
     * 
     * @param messageComponent validation message which will be add to component.
     */
    public void addMessage(JComponent messageComponent);
}
