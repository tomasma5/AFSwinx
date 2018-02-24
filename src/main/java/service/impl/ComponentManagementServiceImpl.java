package service.impl;

import dao.ComponentResourceDao;
import dao.ScreenDao;
import model.ComponentResource;
import model.Screen;
import org.bson.types.ObjectId;
import service.ComponentManagementService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentManagementServiceImpl implements ComponentManagementService {

    @Inject
    private ComponentResourceDao componentResourceDao;

    @Inject
    private ScreenDao screenDao;

    public ComponentManagementServiceImpl() {
    }

    @Override
    public void addComponent(ComponentResource componentResource) {
        addComponentToReferencedScreens(componentResource);
        componentResourceDao.create(componentResource);
    }

    @Override
    public void removeComponent(ObjectId id) {
        ComponentResource componentResource = componentResourceDao.findByObjectId(id);
        removeComponentFromReferencedScreens(componentResource);
        componentResourceDao.deleteByObjectId(id);

    }

    @Override
    public void updateComponent(ComponentResource updated) {
        ComponentResource componentResource = componentResourceDao.findByObjectId(updated.getId());
        removeComponentFromReferencedScreens(componentResource);
        addComponentToReferencedScreens(updated);
        componentResourceDao.update(updated);
    }

    @Override
    public ComponentResource findById(ObjectId id) {
        return componentResourceDao.findByObjectId(id);
    }

    @Override
    public List<ComponentResource> getAllComponentsByApplication(ObjectId applicationId) {
        return componentResourceDao.findAll().stream()
                .filter(componentResource -> componentResource.getApplicationId().equals(applicationId))
                .collect(Collectors.toList());
    }

    @Override
    public void addComponentToScreen(ComponentResource componentResource, Screen screen) {
        componentResource.referencedByScreen(screen);
        screen.addComponentResource(componentResource);
        screenDao.update(screen);
        componentResourceDao.update(componentResource);
    }

    @Override
    public void filterComponentsScreenReferences(ComponentResource componentResource) {
        List<ObjectId> screenIds = screenDao.findAll().stream()
                .filter(screen -> screen.getApplicationId().equals(componentResource.getApplicationId()) &&
                                screen.getComponents().contains(componentResource))
                .map(Screen::getId)
                .collect(Collectors.toList());
        componentResource.setReferencedScreensIds(screenIds);
        componentResourceDao.update(componentResource);
    }


    private void addComponentToReferencedScreens(ComponentResource componentResource) {
        List<ObjectId> updatedReferencedScreens = componentResource.getReferencedScreensIds();
        if(updatedReferencedScreens != null) {
            for (ObjectId screenId: updatedReferencedScreens) {
                addComponentToScreen(componentResource, screenDao.findByObjectId(screenId));
            }
        }
    }

    private void removeComponentFromReferencedScreens(ComponentResource componentResource) {
        List<ObjectId> referencedScreens = componentResource.getReferencedScreensIds();
        if(referencedScreens != null) {
            Screen screen;
            for (ObjectId screenId : referencedScreens) {
                screen = screenDao.findByObjectId(screenId);
                screen.removeComponentResource(componentResource);
                screenDao.update(screen);
            }
        }
    }




}
