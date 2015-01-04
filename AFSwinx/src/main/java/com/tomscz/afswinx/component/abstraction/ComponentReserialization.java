package com.tomscz.afswinx.component.abstraction;

import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

public interface ComponentReserialization {

    /**
     * This method do back serialization. It create object which will hold all actual data in
     * component.
     * 
     * @return Object which hold all actual data in component. This object also preserve their hierarchy. 
     */
    public AFDataHolder resealize();
    
}
