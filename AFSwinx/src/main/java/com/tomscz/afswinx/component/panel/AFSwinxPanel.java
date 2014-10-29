package com.tomscz.afswinx.component.panel;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.unmarshal.factory.WidgetBuilderFactory;
import com.tomscz.afswinx.validation.AFValidations;
import com.tomscz.afswinx.validation.exception.ValidationException;

/**
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class AFSwinxPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComponent dataHolder;
    private JComponent labelHolder;
    private JComponent message;
    private SupportedWidgets widgetType;
    private String panelId;
    private List<AFValidations> validators = new ArrayList<AFValidations>();

    public AFSwinxPanel(String panelId, SupportedWidgets widgetType, JComponent dataHolder) {
        this.panelId = panelId;
        this.widgetType = widgetType;
        this.dataHolder = dataHolder;
    }

    public AFSwinxPanel(String panelId, SupportedWidgets widgetType, JComponent dataHolder,
            JComponent labelHolder) {
        this.panelId = panelId;
        this.widgetType = widgetType;
        this.dataHolder = dataHolder;
        this.labelHolder = labelHolder;
    }

    public void validateModel () throws ValidationException{
        //Validate only if components are visible
        if(this.isVisible()){
            for(AFValidations validator:validators){
                validator.validate(AFSwinx.getInstance(), this, WidgetBuilderFactory.getInstance().createWidgetBuilder(widgetType).getData(this));
            }
        }
    }

    public JComponent getDataHolder() {
        return dataHolder;
    }
    
    public JComponent getLabelHolder() {
        return labelHolder;
    }

    public SupportedWidgets getWidgetType() {
        return widgetType;
    }

    public String getPanelId() {
        return panelId;
    }
    
    public void addValidator(AFValidations validator){
        this.validators.add(validator);
    }

    public JComponent getMessage() {
        return message;
    }

    public void setMessage(JComponent message) {
        this.message = message;
    }

}
