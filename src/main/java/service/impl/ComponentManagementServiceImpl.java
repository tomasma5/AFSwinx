package service.impl;

import dao.ComponentResourceDao;
import dao.ScreenDao;
import model.ComponentResource;
import model.Screen;
import org.bson.types.ObjectId;
import service.ComponentManagementService;

import javax.inject.Inject;
import java.util.List;

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
        componentResourceDao.update(componentResource);
    }

    @Override
    public ComponentResource findById(ObjectId id) {
        return componentResourceDao.findByObjectId(id);
    }

    @Override
    public void addComponentToScreen(ObjectId componentResourceId, ObjectId screenId) {
        Screen screen = screenDao.findByObjectId(screenId);
        ComponentResource componentResource = componentResourceDao.findByObjectId(componentResourceId);
        screen.addComponentResource(componentResource);
        screenDao.update(screen);
    }

    private void addComponentToReferencedScreens(ComponentResource componentResource) {
        List<ObjectId> updatedReferencedScreens = componentResource.getReferencedScreensIds();
        if(updatedReferencedScreens != null) {
            for (ObjectId screenId: updatedReferencedScreens) {
                addComponentToScreen(componentResource.getId(), screenId);
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
