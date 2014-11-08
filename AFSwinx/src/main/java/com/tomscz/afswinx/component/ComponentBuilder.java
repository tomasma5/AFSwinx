package com.tomscz.afswinx.component;

import java.util.ResourceBundle;

import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;

/**
 * This interface specify operation which must provide every builder which is used to build {@link AFSwinxTopLevelComponent}
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public interface ComponentBuilder {

    /**
     * This method set localization to builder. The generic is used because you have to return any type of builder. 
     * @param localization resource bundle with  localization
     * @return actual builder class which will be used to another builder settings or to return object which was build
     */
    public <T> T setLocalization(ResourceBundle localization);
    
    /**
     * This method  build concrete component. The generic is used because you can build various components.
     * @return component which will be build based on initial and current settings
     * @throws AFSwinxBuildException
     */
    public <T> T buildComponent() throws AFSwinxBuildException; 

}
