package service.servlet.impl;

import dao.ApplicationDao;
import dao.BusinessCaseDao;
import dao.BusinessPhaseDao;
import model.Application;
import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;
import service.exception.ServiceException;
import service.servlet.BusinessCaseManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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

    @Inject
    private ApplicationDao applicationDao;

    @Override
    public void createOrUpdate(BusinessCase bc) {
        businessCaseDao.createOrUpdate(bc);
    }

    @Override
    public void removeBusinessCase(Integer bcId) {
        businessCaseDao.delete(findById(bcId));
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
                .filter(businessCase -> businessCase.getApplication().getId().equals(id))
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
    public void saveBusinessCaseFromRequest(HttpServletRequest req, String bCaseId, String appIdString) {
        BusinessCase bcase = findOrCreateBusinessCase(bCaseId);
        updateBcaseProperties(req, appIdString, bcase);
        createOrUpdate(bcase);
    }

    private void updateBcaseProperties(HttpServletRequest req, String appIdString, BusinessCase bcase) {
        int appId = Integer.parseInt(appIdString);
        Application application = applicationDao.getById(appId);
        String name = Utils.trimString(Utils.trimString(req.getParameter(ParameterNames.BUSINESS_CASE_NAME)));
        String description = Utils.trimString(Utils.trimString(req.getParameter(ParameterNames.BUSINESS_CASE_DESCRIPTION)));
        bcase.setApplication(application);
        bcase.setName(name);
        bcase.setDescription(description);
    }

    @Override
    public List<BusinessCase> getAll() {
        return businessCaseDao.getAll();
    }

}
