package service.impl;

import dao.ApplicationDao;
import model.Application;
import org.bson.types.ObjectId;
import service.ApplicationsManagementService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

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
        applicationDao.create(app);
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
    public List<Application> getAll() {
        return applicationDao.findAll();
    }
}
