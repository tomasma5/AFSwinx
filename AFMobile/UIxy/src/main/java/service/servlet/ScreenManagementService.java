package service.servlet;

import model.Application;
import model.ComponentResource;
import model.Screen;

import javax.servlet.http.HttpServletRequest;
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
    public void createOrUpdate(Screen screen);

    /**
     * Remove screen.
     *
     * @param screen the id
     */
    public void removeScreen(int screen);

    /**
     * Gets all screens by application.
     *
     * @param applicationId the application id
     * @return the all screens by application
     */
    public List<Screen> getAllScreensByApplication(int applicationId);

    /**
     * Gets all unassigned screens by application.
     *
     * @param applicationId the application id
     * @return the all unassigned screens by application
     */
    public List<Screen> getAllUnassignedScreensByApplication(int applicationId);

    /**
     * Find screen by id .
     *
     * @param id the id
     * @return the screen
     */
    public Screen findScreenById(int id);

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
    public int getScreenCount(int applicationId);

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
    public String buildScreenUrl(Application app, Integer screen, String screenUrl, String contextPath);

    /**
     * Add component to screen.
     *
     * @param componentResource the component resource
     * @param screen            the screen
     */
    public void addComponentToScreen(ComponentResource componentResource, Screen screen);

    /**
     * Creates screen and fills it with information from request
     * @param req request
     * @param screenId id of screen
     * @param appId application id where screen belongs
     * @param linkedComponentCount count of linked components
     */
    public void fillAndCreateScreen(HttpServletRequest req, String screenId, String appId, String linkedComponentCount);
}
