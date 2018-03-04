package service.servlet;

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

    List<ComponentResource> getComponentsNotInScreen(ObjectId screenId, ObjectId applicationId);

    public void addComponentToScreen(ComponentResource componentResource, Screen screen);

    public void filterComponentsScreenReferences(ComponentResource componentResource);

}
