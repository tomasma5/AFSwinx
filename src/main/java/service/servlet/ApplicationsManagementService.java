package service.servlet;

import model.Application;
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
    public void createOrUpdate(Application app);

    /**
     * Remove application.
     *
     * @param app application to be removed
     */
    public void removeApplication(int app);

    /**
     * Find application by id.
     *
     * @param id the id
     * @return the application
     */
    public Application findById(int id);

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
