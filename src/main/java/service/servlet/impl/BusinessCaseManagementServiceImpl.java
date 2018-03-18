package service.servlet.impl;

import dao.BusinessCaseDao;
import model.Application;
import model.afclassification.BusinessCase;
import org.bson.types.ObjectId;
import service.servlet.BusinessCaseManagementService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Named("businessCaseManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class BusinessCaseManagementServiceImpl implements BusinessCaseManagementService {

    @Inject
    private BusinessCaseDao businessCaseDao;


    @Override
    public void createBusinessCase(BusinessCase bc) {
        businessCaseDao.create(bc);
    }

    @Override
    public void removeBusinessCase(ObjectId id) {
        businessCaseDao.deleteByObjectId(id);
    }

    @Override
    public void updateBusinessCase(BusinessCase bc) {
        businessCaseDao.update(bc);
    }

    @Override
    public BusinessCase findById(ObjectId id) {
        return businessCaseDao.findById(id);
    }

    @Override
    public List<BusinessCase> getAllByApplication(ObjectId applicationId) {
        //TODO make more effective?
        return businessCaseDao.findAll().stream()
                .filter(screen -> screen.getApplicationId().equals(applicationId))
                .collect(Collectors.toList());
    }

    @Override
    public BusinessCase findOrCreateBusinessCase(String businessCaseId) {
        if(businessCaseId != null && !businessCaseId.isEmpty()){
            return findById(new ObjectId(businessCaseId));
        }
        BusinessCase businessCase = new BusinessCase();
        businessCase.setId(new ObjectId());
        return businessCase;
    }

    @Override
    public List<BusinessCase> getAll() {
        return businessCaseDao.findAll();
    }

}
