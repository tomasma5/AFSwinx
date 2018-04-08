package service.servlet.impl;

import dao.BusinessFieldsDao;
import model.afclassification.BCField;
import service.servlet.BusinessFieldsManagementService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Implementation of business fields management service
 */
@Named("businessFieldsManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class BusinessFieldsManagementServiceImpl implements BusinessFieldsManagementService {

    @Inject
    private BusinessFieldsDao businessFieldsDao;

    /**
     * Instantiates a new Business fields management service.
     */
    public BusinessFieldsManagementServiceImpl() {
    }

    @Override
    public void createOrUpdate(BCField bcField) {
        businessFieldsDao.createOrUpdate(bcField);
    }

    @Override
    public void removeBusinessField(BCField bcField) {
        businessFieldsDao.delete(bcField);
    }

    @Override
    public BCField findById(int id) {
        return businessFieldsDao.getById(id);
    }

    @Override
    public List<BCField> getAll() {
        return businessFieldsDao.getAll();
    }

    @Override
    public List<BCField> findAllByPhase(int businessPhaseId) {
        return businessFieldsDao.getAll().stream().filter(bcField -> bcField.getPhase().getId() == businessPhaseId).collect(toList());
    }

    @Override
    public BCField findOrCreateBusinessField(String businessFieldId) {
        BCField bcField;
        if (businessFieldId == null || businessFieldId.isEmpty()) {
            bcField = new BCField();
        } else {
            bcField = findById(Integer.parseInt(businessFieldId));
        }
        return bcField;
    }
}
