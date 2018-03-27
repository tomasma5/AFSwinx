package model;


import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Screen extends MongoDocumentEntity {

    private String key;
    private String name;
    private String screenUrl;
    private int menuOrder;
    private List<ComponentResource> components;
    private ObjectId applicationId;

    private ObjectId phaseId;
    private ObjectId businessCaseId;

    public Screen() {
    }

    public Screen(String key, String name, String screenUrl, int menuOrder, List<ComponentResource> components,
                  ObjectId applicationId, ObjectId phaseId, ObjectId businessCaseId) {
        this.key = key;
        this.name = name;
        this.screenUrl = screenUrl;
        this.menuOrder = menuOrder;
        this.components = components;
        this.applicationId = applicationId;
        this.phaseId = phaseId;
        this.businessCaseId = businessCaseId;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(ObjectId phaseId) {
        this.phaseId = phaseId;
    }

    public ObjectId getBusinessCaseId() {
        return businessCaseId;
    }

    public void setBusinessCaseId(ObjectId businessCaseId) {
        this.businessCaseId = businessCaseId;
    }
}
