package service.servlet.impl;

import dao.ComponentResourceDao;
import dao.ScreenDao;
import model.ComponentResource;
import model.Screen;
import org.bson.types.ObjectId;
import service.servlet.ComponentManagementService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Named("componentManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
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
        ComponentResource componentResource = componentResourceDao.findById(id);
        removeComponentFromReferencedScreens(componentResource);
        componentResourceDao.deleteByObjectId(id);

    }

    @Override
    public void updateComponent(ComponentResource updated) {
        ComponentResource componentResource = componentResourceDao.findById(updated.getId());
        removeComponentFromReferencedScreens(componentResource);
        addComponentToReferencedScreens(updated);
        componentResourceDao.update(updated);
    }

    @Override
    public ComponentResource findById(ObjectId id) {
        return componentResourceDao.findById(id);
    }

    @Override
    public List<ComponentResource> getAllComponentsByApplication(ObjectId applicationId) {
        return componentResourceDao.findAll().stream()
                .filter(componentResource -> componentResource.getApplicationId().equals(applicationId))
                .collect(Collectors.toList());
    }

    @Override
    public void addComponentToScreen(ComponentResource componentResource, Screen screen) {
        screen.addComponentResource(componentResource);
        screenDao.update(screen);
        componentResourceDao.update(componentResource);
    }

    @Override
    public void filterComponentsScreenReferences(ComponentResource componentResource) {
        List<ObjectId> screenIds = screenDao.findAll().stream()
                .filter(screen -> screen.getApplicationId().equals(componentResource.getApplicationId()) &&
                        (screen.getComponents() != null && screen.getComponents().contains(componentResource)))
                .map(Screen::getId)
                .collect(Collectors.toList());
        componentResource.setReferencedScreensIds(screenIds);
        componentResourceDao.update(componentResource);
    }


    private void addComponentToReferencedScreens(ComponentResource componentResource) {
        List<ObjectId> updatedReferencedScreens = componentResource.getReferencedScreensIds();
        if (updatedReferencedScreens != null) {
            for (ObjectId screenId : updatedReferencedScreens) {
                addComponentToScreen(componentResource, screenDao.findById(screenId));
            }
        }
    }

    private void removeComponentFromReferencedScreens(ComponentResource componentResource) {
        List<ObjectId> referencedScreens = componentResource.getReferencedScreensIds();
        if (referencedScreens != null) {
            Screen screen;
            for (ObjectId screenId : referencedScreens) {
                screen = screenDao.findById(screenId);
                if(screen != null) {
                    screen.removeComponentResource(componentResource);
                    screenDao.update(screen);
                }
            }
        }
    }


}
