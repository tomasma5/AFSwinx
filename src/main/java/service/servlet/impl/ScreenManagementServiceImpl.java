package service.servlet.impl;

import dao.ComponentResourceDao;
import dao.ScreenDao;
import model.Application;
import model.ComponentResource;
import model.Screen;
import org.bson.types.ObjectId;
import service.servlet.ScreenManagementService;
import utils.HttpUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Named("screenManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class ScreenManagementServiceImpl implements ScreenManagementService {

    @Inject
    private ScreenDao screenDao;

    @Inject
    private ComponentResourceDao componentResourceDao;

    public ScreenManagementServiceImpl() {
    }

    @Override
    public void addNewScreen(Screen screen) {
        screenDao.create(screen);
    }

    @Override
    public void removeScreen(ObjectId id) {
        screenDao.deleteByObjectId(id);
    }

    @Override
    public void updateScreen(Screen updatedScreen) {
        List<ComponentResource> componentResources = componentResourceDao.findAll().stream()
                .filter(componentResource -> componentResource.getReferencedScreensIds() != null && componentResource.getReferencedScreensIds().contains(updatedScreen.getId()))
                .collect(Collectors.toList());

        for (ComponentResource componentResource : componentResources) {
            if (!updatedScreen.getComponents().contains(componentResource)) {
                componentResource.getReferencedScreensIds().remove(updatedScreen.getId());
                componentResourceDao.update(componentResource);
            }
        }
        screenDao.update(updatedScreen);
    }

    @Override
    public List<Screen> getAllScreensByApplication(ObjectId applicationId) {
        return screenDao.findAll().stream()
                .filter(screen -> screen.getApplicationId().equals(applicationId))
                .collect(Collectors.toList());
    }

    @Override
    public Screen findScreenById(ObjectId id) {
        return screenDao.findById(id);
    }

    @Override
    public int getScreenCount(ObjectId applicationId) {
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
            updateScreen(screen);
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
            screen.setId(new ObjectId());
        } else {
            screen = findScreenById(new ObjectId(screenId));
        }
        return screen;
    }
}
