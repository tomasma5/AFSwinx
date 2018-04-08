package service.servlet.impl;

import dao.ApplicationDao;
import model.Application;
import service.servlet.ApplicationsManagementService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of application management service
 */
@Named("applicationsManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class ApplicationsManagementServiceImpl implements ApplicationsManagementService {

    @Inject
    private ApplicationDao applicationDao;

    /**
     * Instantiates a new Applications management service.
     */
    public ApplicationsManagementServiceImpl() {
    }

    @Override
    public void createOrUpdate(Application app) {
        String uuid = generateUuid();
        app.setUuid(uuid);
        applicationDao.createOrUpdate(app);
    }

    @Override
    public void removeApplication(int application) {
        applicationDao.delete(findById(application));
    }

    @Override
    public Application findById(int id) {
        return applicationDao.getById(id);
    }

    @Override
    public Application findByName(String name) {
        return applicationDao.findByName(name);
    }

    @Override
    public List<Application> getAll() {
        return applicationDao.getAll();
    }

    @Override
    public Application findByUuid(String uuid) {
        return applicationDao.findByUuid(uuid);
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Application findOrCreateApplication(String applicationId) {
        Application application;
        if (applicationId == null || applicationId.isEmpty()) {
            application = new Application();
        } else {
            application = findById(Integer.parseInt(applicationId));
        }
        return application;
    }
}
