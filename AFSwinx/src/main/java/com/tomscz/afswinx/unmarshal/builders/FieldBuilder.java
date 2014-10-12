package com.tomscz.afswinx.unmarshal.builders;

import com.tomscz.afswinx.component.panel.AFSwinxPanel;
import com.tomscz.afswinx.rest.dto.AFFieldInfo;

public interface FieldBuilder {
    
    public AFSwinxPanel buildComponent(AFFieldInfo field) throws IllegalArgumentException;
    public boolean isBuildAvaiable(AFFieldInfo field);

}
