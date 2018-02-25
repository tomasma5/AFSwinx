package service.servlet.impl;

import dao.ApplicationDao;
import model.Application;
import org.bson.types.ObjectId;
import service.servlet.ApplicationsManagementService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Named("applicationsManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class ApplicationsManagementServiceImpl implements ApplicationsManagementService {

    @Inject
    private ApplicationDao applicationDao;

    public ApplicationsManagementServiceImpl() {
    }

    @Override
    public void addNewApplication(Application app) {
        String uuid = generateUuid(app);
        app.setUuid(uuid);
        applicationDao.create(app);
    }

    private String generateUuid(Application app) {
        return UUID.fromString(app.getApplicationName()).toString();
    }

    @Override
    public void removeApplication(ObjectId id) {
        applicationDao.deleteByObjectId(id);
    }

    @Override
    public void updateApplication(Application app) {
        applicationDao.update(app);
    }

    @Override
    public Application findById(ObjectId id) {
        return applicationDao.findByObjectId(id);
    }

    @Override
    public Application findByName(String name) {
        return applicationDao.findByName(name);
    }

    @Override
    public List<Application> getAll() {
        return applicationDao.findAll();
    }

    @Override
    public Application findByUuid(String uuid) {
        return applicationDao.findByUuid(uuid);
    }
}
