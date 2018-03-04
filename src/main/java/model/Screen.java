package model;


import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Screen extends MongoDocumentEntity {

    private String heading;
    private String screenUrl;
    private int menuOrder;
    private List<ComponentResource> components;
    private ObjectId applicationId;

    public Screen() {
    }

    public Screen(String heading, String screenUrl, int menuOrder, List<ComponentResource> components, ObjectId applicationId) {
        this.heading = heading;
        this.screenUrl = screenUrl;
        this.menuOrder = menuOrder;
        this.components = components;
        this.applicationId = applicationId;
    }

    public void addComponentResource(ComponentResource componentResource){
        if(components == null) {
            components = new ArrayList<>();
        }
        if(!components.contains(componentResource)) {
            components.add(componentResource);
        }
    }

    public void removeComponentResource(ComponentResource componentResource) {
        if(components == null){
            return;
        }
        components.remove(componentResource);
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getScreenUrl() {
        return screenUrl;
    }

    public void setScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
    }

    public List<ComponentResource> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentResource> components) {
        this.components = components;
    }

    public ObjectId getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(ObjectId applicationId) {
        this.applicationId = applicationId;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }
}
