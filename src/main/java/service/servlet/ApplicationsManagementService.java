package service.servlet;

import model.Application;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * This service is used for management of {@link Application}
 */
public interface ApplicationsManagementService {

    /**
     * Add new application.
     *
     * @param app the app
     */
    public void addNewApplication(Application app);

    /**
     * Remove application.
     *
     * @param id the id
     */
    public void removeApplication(ObjectId id);

    /**
     * Update application.
     *
     * @param app the app
     */
    public void updateApplication(Application app);

    /**
     * Find application by id.
     *
     * @param id the id
     * @return the application
     */
    public Application findById(ObjectId id);

    /**
     * Find application by name.
     *
     * @param name the name
     * @return the application
     */
    public Application findByName(String name);

    /**
     * Gets all applcations.
     *
     * @return the all
     */
    public List<Application> getAll();

    /**
     * Find application by uuid.
     *
     * @param uuid the uuid
     * @return the application
     */
    public Application findByUuid(String uuid);

    /**
     * Find or create application application.
     *
     * @param applicationId the application id
     * @return the application
     */
    public Application findOrCreateApplication(String applicationId);
}
