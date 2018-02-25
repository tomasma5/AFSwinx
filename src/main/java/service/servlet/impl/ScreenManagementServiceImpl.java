package service.servlet.impl;

import dao.ComponentResourceDao;
import dao.ScreenDao;
import model.ComponentResource;
import model.Screen;
import org.bson.types.ObjectId;
import rest.security.RequestContext;
import service.servlet.ScreenManagementService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
                .filter(componentResource -> componentResource.getReferencedScreensIds().contains(updatedScreen.getId()))
                .collect(Collectors.toList());

        for (ComponentResource componentResource : componentResources){
            if(!updatedScreen.getComponents().contains(componentResource)){
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
        return screenDao.findByObjectId(id);
    }
}
