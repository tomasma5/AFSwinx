package service.servlet.impl;

import dao.ApplicationDao;
import dao.ComponentResourceDao;
import dao.ScreenDao;
import model.Application;
import model.ComponentResource;
import model.Screen;
import service.servlet.ScreenManagementService;
import servlet.ParameterNames;
import utils.HttpUtils;
import utils.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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

    @Inject
    private ApplicationDao applicationDao;

    /**
     * Instantiates a new Screen management service.
     */
    public ScreenManagementServiceImpl() {
    }

    @Override
    public void createOrUpdate(Screen screen) {
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
    public String buildScreenUrl(Application app, Integer screen, String screenUrl, String contextPath) {
        if (screenUrl == null || screenUrl.isEmpty()) {
            screenUrl = HttpUtils.buildUrl(
                    app.getProxyProtocol(),
                    app.getProxyHostname(),
                    app.getProxyPort(),
                    contextPath,
                    "/api/screens/" + screen
            );
        }
        return screenUrl;
    }

    @Override
    public void addComponentToScreen(ComponentResource componentResource, Screen screen) {
        componentResource.referencedByScreen(screen);
        screen.addComponentResource(componentResource);
        componentResourceDao.createOrUpdate(componentResource);
    }

    @Override
    public void fillAndCreateScreen (HttpServletRequest req, String screenId, String appIdString, String linkedComponentsCountString){
        Screen screen = findOrCreateNewScreen(screenId);
        updateScreenProperties(req, appIdString, screen);
        updateLinkedComponents(req, Integer.parseInt(linkedComponentsCountString), screen);
        createOrUpdate(screen);
        String screenUrl = Utils.trimString(req.getParameter(ParameterNames.SCREEN_URL));
        screenUrl = buildScreenUrl(applicationDao.getById(Integer.parseInt(appIdString)), screen.getId(), screenUrl, req.getContextPath());
        screen.setScreenUrl(screenUrl);
        createOrUpdate(screen);
    }

    public Screen findOrCreateNewScreen(String screenId) {
        Screen screen;
        if (screenId == null || screenId.isEmpty()) {
            screen = new Screen();
        } else {
            screen = findScreenById(Integer.parseInt(screenId));
        }
        return screen;
    }

    private void updateScreenProperties(HttpServletRequest req, String appIdString, Screen screen) {
        int appId = Integer.parseInt(appIdString);
        Application application = applicationDao.getById(appId);
        screen.setApplication(application);
        String screenName = Utils.trimString(req.getParameter(ParameterNames.SCREEN_NAME));
        String key = Utils.trimString(req.getParameter(ParameterNames.SCREEN_KEY));
        String menuOrder = Utils.trimString(req.getParameter(ParameterNames.SCREEN_MENU_ORDER));
        screen.setKey(key);
        if (screenName != null && !screenName.isEmpty()) {
            screen.setName(screenName);
        }
        screen.setMenuOrder(Integer.parseInt(menuOrder));
    }

    public void updateLinkedComponents(HttpServletRequest req, int linkedComponentsCount, Screen screen) {
        if (screen.getComponents() != null) {
            screen.getComponents().clear();
        }
        for (int i = 0; i < linkedComponentsCount; i++) {
            String componentId = Utils.trimString(req.getParameter(ParameterNames.LINKED_COMPONENT_ID + (i + 1)));
            ComponentResource componentResource = componentResourceDao.getById(Integer.parseInt(componentId));
            screen.addComponentResource(componentResource);
        }
    }

}
