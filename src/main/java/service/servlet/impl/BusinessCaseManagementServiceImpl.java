package service.servlet.impl;

import dao.BusinessCaseDao;
import dao.BusinessPhaseDao;
import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;
import service.exception.ServiceException;
import service.servlet.BusinessCaseManagementService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of business case management service
 */
@Named("businessCaseManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class BusinessCaseManagementServiceImpl implements BusinessCaseManagementService {

    @Inject
    private BusinessCaseDao businessCaseDao;

    @Inject
    private BusinessPhaseDao businessPhaseDao;

    @Override
    public void createOrUpdate(BusinessCase bc) {
        businessCaseDao.createOrUpdate(bc);
    }

    @Override
    public void removeBusinessCase(BusinessCase bc) {
        businessCaseDao.delete(bc);
    }

    @Override
    public void removeBusinessPhaseFromCaseById(int caseId, int phaseId) throws ServiceException {
        BusinessCase businessCase = findById(caseId);
        if (businessCase == null) {
            throw new ServiceException("Cannot find business case with id: " + caseId);
        }
        if (businessCase.getPhases() == null || businessCase.getPhases().isEmpty()) {
            System.err.println("Business case does not have any phases. Cannot delete phase with id " + phaseId);
            return;
        }
        businessCase.getPhases().remove(businessPhaseDao.getById(phaseId));
        createOrUpdate(businessCase);
    }

    @Override
    public BusinessCase findById(int id) {
        return businessCaseDao.getById(id);
    }

    @Override
    public List<BusinessCase> getAllByApplication(int id) {
        return businessCaseDao.getAll().stream()
                .filter(screen -> screen.getId() == (id))
                .collect(Collectors.toList());
    }

    @Override
    public BusinessCase findOrCreateBusinessCase(String businessCaseId) {
        if (businessCaseId != null && !businessCaseId.isEmpty()) {
            return findById(Integer.parseInt(businessCaseId));
        }
        return new BusinessCase();
    }

    @Override
    public void addBusinessPhaseToCaseById(int caseId, BCPhase phase) throws ServiceException {
        BusinessCase businessCase = findById(caseId);
        if (businessCase == null) {
            throw new ServiceException("Cannot find business case with id " + caseId);
        }
        businessCase.addPhase(phase);
        createOrUpdate(businessCase);
    }

    @Override
    public List<BusinessCase> getAll() {
        return businessCaseDao.getAll();
    }

}
