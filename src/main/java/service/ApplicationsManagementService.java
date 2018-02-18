package service;

import model.Application;
import org.bson.types.ObjectId;

import java.util.List;

public interface ApplicationsManagementService {

    public void addNewApplication(Application app);

    public void removeApplication(ObjectId id);

    public void updateApplication(Application app);

    public Application findById(ObjectId id);

    public List<Application> getAll();

}
