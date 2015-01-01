package com.tomscz.afserver.ws.resources.mapping;

public class RootMapping {
    private String resourceName;
    private String resourceUrl;
    
    public RootMapping(){
        
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
    
}
