package service;

import model.Application;
import model.Screen;
import org.bson.types.ObjectId;

public interface ApplicationsManagementService {

    public void addNewApplication(Application app);

    public void removeApplication(ObjectId id);

    public void updateApplication(Application updatedScreen);

}
