package com.tomscz.afswinx.rest.rebuild;

import java.util.List;

import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataPack;

public interface Reselization {

    public Object reselialize(AFDataHolder componentData);
    
    public List<AFDataPack> serialize(Object componentData);
}
