package com.tomscz.afswinx.unmarshal.builders;

import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.dto.AFFieldInfo;
import com.tomscz.afswinx.rest.dto.data.AFData;

public interface FieldBuilder {
    
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException;
    /**
     * This method set data to particular component.
     * @param panel Panel which holds structure and to which will be data set up.
     * @param data Data which will be set up
     */
    public void setData(AFSwinxPanel panel,AFData data);
    /**
     * This method will validate if build for component is available.
     * @param field
     * @return
     */
    public boolean isBuildAvailable(AFFieldInfo field);

}
