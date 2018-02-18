package service.impl;

import dao.ScreenDao;
import model.Screen;
import org.bson.types.ObjectId;
import service.ScreenManagementService;

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
        screenDao.update(updatedScreen);
    }

    @Override
    public List<Screen> getAllScreensByApplication(ObjectId applicationId) {
        return screenDao.findAll().stream().filter(screen -> screen.getApplicationId().equals(applicationId)).collect(Collectors.toList());
    }

    @Override
    public Screen findScreenById(ObjectId id) {
        return screenDao.findByObjectId(id);
    }
}
