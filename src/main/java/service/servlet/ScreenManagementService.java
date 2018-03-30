package service.servlet;

import model.Application;
import model.ComponentResource;
import model.Screen;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * This service should be used for {@link Screen} management.
 */
public interface ScreenManagementService {

    /**
     * Add new screen.
     *
     * @param screen the screen
     */
    public void addNewScreen(Screen screen);

    /**
     * Remove screen.
     *
     * @param id the id
     */
    public void removeScreen(ObjectId id);

    /**
     * Update screen.
     *
     * @param updatedScreen the updated screen
     */
    public void updateScreen(Screen updatedScreen);

    /**
     * Gets all screens by application.
     *
     * @param applicationId the application id
     * @return the all screens by application
     */
    public List<Screen> getAllScreensByApplication(ObjectId applicationId);

    /**
     * Gets all unassigned screens by application.
     *
     * @param applicationId the application id
     * @return the all unassigned screens by application
     */
    public List<Screen> getAllUnassignedScreensByApplication(ObjectId applicationId);

    /**
     * Find screen by id .
     *
     * @param id the id
     * @return the screen
     */
    public Screen findScreenById(ObjectId id);

    /**
     * Find screen by key .
     *
     * @param screenKey the screen key
     * @return the screen
     */
    public Screen findScreenByKey(String screenKey);

    /**
     * Gets screen count.
     *
     * @param applicationId the application id
     * @return the screen count
     */
    public int getScreenCount(ObjectId applicationId);

    /**
     * Update screen connections.
     *
     * @param application the application
     * @param contextPath the context path
     */
    public void updateScreenConnections(Application application, String contextPath);

    /**
     * Build screen url.
     *
     * @param app         the app
     * @param screen      the screen
     * @param screenUrl   the screen url
     * @param contextPath the context path
     * @return the string
     */
    public String buildScreenUrl(Application app, Screen screen, String screenUrl, String contextPath);

    /**
     * Find or create new screen.
     *
     * @param screenId the screen id
     * @return the screen
     */
    public Screen findOrCreateNewScreen(String screenId);
}
