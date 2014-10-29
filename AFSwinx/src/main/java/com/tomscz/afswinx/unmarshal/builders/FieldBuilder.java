package com.tomscz.afswinx.unmarshal.builders;

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
     * @param field
     * @return
     */
    public boolean isBuildAvailable(AFFieldInfo field);

}
