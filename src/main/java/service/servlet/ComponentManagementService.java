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
     * @param screenId      the screen id
     * @param applicationId the application id
     * @return the components not in screen
     */
    List<ComponentResource> getComponentsNotInScreen(int screenId, int applicationId);

    /**
     * Add component to screen.
     *
     * @param componentResource the component resource
     * @param screen            the screen
     */
    public void addComponentToScreen(ComponentResource componentResource, Screen screen);

    /**
     * Update component connections.
     *
     * @param application the application
     */
    public void updateComponentConnections(Application application);

    /**
     * Update components in referenced screen linked components
     *
     * @param req                   the req
     * @param linkedComponentsCount the linked components count
     * @param screen                the screen
     */
    public void updateLinkedComponents(HttpServletRequest req, int linkedComponentsCount, Screen screen);

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
