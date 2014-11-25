package com.tomscz.afswinx.component.widget.builder;

import java.util.ResourceBundle;

import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.data.AFData;
import com.tomscz.afswinx.component.panel.AFSwinxPanel;

/**
 * This interface specify operation which must implement each field builder.    
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public interface FieldBuilder {
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException;
    /**
     * This method set data to particular component.
     * @param panel Panel which holds structure and to which will be data set up.
     * @param data Data which will be set up
     */
    public void setData(AFSwinxPanel panel,AFData data);
    
    /**
     * This method retrieve data from input panel.
     * @param panel from which will be data extracted
     * @return data representation of panel
     */
    public Object getData(AFSwinxPanel panel);
    /**
     * This method will validate if build for component is available.
     * @param field on which will be done inspection which determine if build is available or not
     * @return true if build is available false otherwise
     */
    public boolean isBuildAvailable(AFFieldInfo field);
    
    /**
     * This method set localization resource bundle, which will be used.
     * @param localization which will be used to translate text
     */
    public void setLocalization(ResourceBundle localization);
    
    public SupportedWidgets getWidgetType();

}
