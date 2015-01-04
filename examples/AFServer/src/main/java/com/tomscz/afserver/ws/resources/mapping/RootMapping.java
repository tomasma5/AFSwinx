package com.tomscz.afserver.ws.resources.mapping;

/**
 * This class give client resource overview. This return resource name and url.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class RootMapping {
    private String resourceName;
    private String resourceUrl;

    public RootMapping() {

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
