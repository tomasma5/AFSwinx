package service.servlet.impl;

import dao.ComponentResourceDao;
import dao.ScreenDao;
import model.Application;
import model.ComponentResource;
import model.Screen;
import service.servlet.ScreenManagementService;
import utils.HttpUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of screen management service.
 */
@Named("screenManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class ScreenManagementServiceImpl implements ScreenManagementService {

    @Inject
    private ScreenDao screenDao;

    @Inject
    private ComponentResourceDao componentResourceDao;

    /**
     * Instantiates a new Screen management service.
     */
    public ScreenManagementServiceImpl() {
    }

    @Override
    public void createOrUpdate(Screen screen) {
        List<ComponentResource> componentResources = componentResourceDao.getAll().stream()
                .filter(componentResource -> componentResource.getReferencedScreens() != null &&
                        componentResource.getReferencedScreens().contains(screen))
                .collect(Collectors.toList());

        for (ComponentResource componentResource : componentResources) {
            if (!screen.getComponents().contains(componentResource)) {
                componentResource.getReferencedScreens().remove(screen);
                componentResourceDao.createOrUpdate(componentResource);
            }
        }
        screenDao.createOrUpdate(screen);
    }

    @Override
    public void removeScreen(int screen) {
        screenDao.delete(findScreenById(screen));
    }

    @Override
    public List<Screen> getAllScreensByApplication(int applicationId) {
        return screenDao.getAll().stream()
                .filter(screen -> screen.getApplication().getId() == applicationId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Screen> getAllUnassignedScreensByApplication(int applicationId) {
        return screenDao.getAll().stream()
                .filter(screen -> (screen.getApplication().getId() == applicationId) &&
                        screen.getPhase() == null)
                .collect(Collectors.toList());
    }

    @Override
    public Screen findScreenById(int id) {
        return screenDao.getById(id);
    }

    @Override
    public Screen findScreenByKey(String screenKey) {
        return screenDao.findByKey(screenKey);
    }

    @Override
    public int getScreenCount(int applicationId) {
        return getAllScreensByApplication(applicationId).size();
    }

    @Override
    public void updateScreenConnections(Application app, String contextPath) {
        for (Screen screen : getAllScreensByApplication(app.getId())) {
            screen.setScreenUrl(HttpUtils.buildUrl(
                    app.getProxyProtocol(),
                    app.getProxyHostname(),
                    app.getProxyPort(),
                    contextPath,
                    "/api/screens/" + screen.getId()
            ));
            createOrUpdate(screen);
        }
    }

    @Override
    public String buildScreenUrl(Application app, Screen screen, String screenUrl, String contextPath) {
        if (screenUrl == null || screenUrl.isEmpty()) {
            screenUrl = HttpUtils.buildUrl(
                    app.getProxyProtocol(),
                    app.getProxyHostname(),
                    app.getProxyPort(),
                    contextPath,
                    "/api/screens/" + screen.getId()
            );
        }
        return screenUrl;
    }

    @Override
    public Screen findOrCreateNewScreen(String screenId) {
        Screen screen;
        if (screenId == null || screenId.isEmpty()) {
            screen = new Screen();
        } else {
            screen = findScreenById(Integer.parseInt(screenId));
        }
        return screen;
    }


}
