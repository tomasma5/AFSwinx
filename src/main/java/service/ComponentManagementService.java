package service;

import model.Application;
import model.ComponentResource;
import model.Screen;
import org.bson.types.ObjectId;

import java.util.List;

public interface ComponentManagementService {

    public void addComponent(ComponentResource componentResource);

    public void removeComponent(ObjectId id);

    public void updateComponent(ComponentResource componentResource);

    public ComponentResource findById(ObjectId id);

    public List<ComponentResource> getAllComponentsByApplication(ObjectId applicationId);

    public void addComponentToScreen(ObjectId componentResourceId, ObjectId screenId);

}
