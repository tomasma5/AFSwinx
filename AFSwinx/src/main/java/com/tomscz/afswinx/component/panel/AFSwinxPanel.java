package com.tomscz.afswinx.component.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.validation.AFValidations;
import com.tomscz.afswinx.validation.ValidatorPriorityComparator;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * This is AFSwinx implementation of {@link JPanel}. It holds data holder, message, and another
 * fields which are needed to construct component and maintain it during life cycle.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    // Components in this panel, which are displayed to user
    private List<JComponent> dataHolder;
    // Label which is displayed to user
    private JTextArea labelHolder;
    // Validation message
    private JTextArea message;
    private SupportedWidgets widgetType;
    // Unique panel id
    private String panelId;
    private boolean retype = false;

    private PriorityQueue<AFValidations> validators = new PriorityQueue<AFValidations>(10,
            new ValidatorPriorityComparator());

    // Parent of this component, its usually form, table, etc
    private AFSwinxTopLevelComponent afParent;

    public AFSwinxPanel(String panelId, SupportedWidgets widgetType, JComponent dataHolder) {
        this.panelId = panelId;
        this.widgetType = widgetType;
        addDataHolderComponent(dataHolder);
    }

    public AFSwinxPanel(String panelId, SupportedWidgets widgetType, JComponent dataHolder,
            JTextArea labelHolder) {
        this.panelId = panelId;
        this.widgetType = widgetType;
        this.labelHolder = labelHolder;
        addDataHolderComponent(dataHolder);
    }

    public AFSwinxPanel(String panelId, SupportedWidgets widgetType, JTextArea labelHolder,
            JTextArea message) {
        this.panelId = panelId;
        this.widgetType = widgetType;
        this.labelHolder = labelHolder;
        this.message = message;
    }

    public AFSwinxPanel(String panelId, SupportedWidgets widgetType, JComponent dataHolder,
            JTextArea labelHolder, JTextArea message) {
        this.panelId = panelId;
        this.widgetType = widgetType;
        this.labelHolder = labelHolder;
        this.message = message;
        addDataHolderComponent(dataHolder);
    }

    /**
     * This method validate this panel. For each registered validatator is validation make. If
     * failed, then no other validation are not performed on this field.
     * 
     * @throws ValidationException
     */
    public void validateModel() throws ValidationException {
        // Validate only if components are visible
        if (this.isVisible()) {
            for (AFValidations validator : validators) {
                validator.validate(AFSwinx.getInstance(), this, WidgetBuilderFactory.getInstance()
                        .createWidgetBuilder(widgetType).getData(this));
            }
        }
    }

    public List<JComponent> getDataHolder() {
        return dataHolder;
    }

    public void setDataHolder(List<JComponent> dataHolder) {
        this.dataHolder = dataHolder;
    }

    public void addDataHolderComponent(JComponent dataHolder) {
        if (this.dataHolder == null) {
            this.dataHolder = new ArrayList<JComponent>();
        }
        this.dataHolder.add(dataHolder);
    }

    public JTextArea getLabelHolder() {
        return labelHolder;
    }

    public SupportedWidgets getWidgetType() {
        return widgetType;
    }

    public String getPanelId() {
        return panelId;
    }

    public void addValidator(AFValidations validator) {
        this.validators.add(validator);
    }

    public JTextArea getMessage() {
        return message;
    }

    public void setMessage(JTextArea message) {
        this.message = message;
    }

    public AFSwinxTopLevelComponent getAfParent() {
        return afParent;
    }

    public void setAfParent(AFSwinxTopLevelComponent afParent) {
        this.afParent = afParent;
    }

    public boolean isRetype() {
        return retype;
    }

    public void setRetype(boolean retype) {
        this.retype = retype;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

}
