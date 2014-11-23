package com.tomscz.afswinx.rest.rebuild;

import com.tomscz.afrest.rest.dto.data.AFDataPack;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

public interface Reselization {

    public Object reselialize(AFDataHolder componentData);
    
    public AFDataPack serialize(Object componentData);
}
