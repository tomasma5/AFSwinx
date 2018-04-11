package service.servlet;

import model.Application;
import model.ComponentConnection;
import model.ComponentResource;
import model.Screen;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This service should be used for {@link ComponentResource} management
 */
public interface ComponentManagementService {

    /**
     * Add component.
     *
     * @param componentResource the component resource
     */
    public void createOrUpdate(ComponentResource componentResource);

    /**
     * Remove component.
     *
     * @param componentResource the id
     */
    public void removeComponent(int componentResource);

    /**
     * Find component resource by id.
     *
     * @param id the id
     * @return the component resource
     */
    public ComponentResource findById(int id);

    /**
     * Gets all components in application.
     *
     * @param applicationId the application id
     * @return the all components by application
     */
    public List<ComponentResource> getAllComponentsByApplication(int applicationId);

    /**
     * Gets components, which are not in screen.
     *
     * @param screen      the screen
     * @param applicationId the application id
     * @return the components not in screen
     */
    List<ComponentResource> getComponentsNotInScreen(Screen screen, int applicationId);

    /**
     * Update component connections.
     *
     * @param application the application
     */
    public void updateComponentConnections(Application application);


    /**
     * Find or create component resource.
     *
     * @param req         the req
     * @param componentId the component id
     * @return the component resource
     */
    public ComponentResource findOrCreateComponentResource(HttpServletRequest req, String componentId);

    /**
     * Update component connections.
     *
     * @param req               the req
     * @param application       the application
     * @param componentResource the component resource
     */
    public void updateComponentConnections(HttpServletRequest req, Application application, ComponentResource componentResource);

}
