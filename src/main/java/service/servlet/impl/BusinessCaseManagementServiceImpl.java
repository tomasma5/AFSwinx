package service.servlet.impl;

import dao.BusinessCaseDao;
import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;
import org.bson.types.ObjectId;
import service.exception.ServiceException;
import service.servlet.BusinessCaseManagementService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
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
    public void removeBusinessPhaseFromCaseById(ObjectId caseId, ObjectId phaseId) throws ServiceException {
        BusinessCase businessCase = findById(caseId);
        if (businessCase == null) {
            throw new ServiceException("Cannot find business case with id: " + caseId);
        }
        if (businessCase.getPhases() == null || businessCase.getPhases().isEmpty()) {
            throw new ServiceException("Business case does not have any phases. Cannot delete phase with id " + phaseId);
        }
        int indexToRemove = -1;
        int i = 0;
        for (BCPhase phase : businessCase.getPhases()) {
            if (phase.getId().equals(phaseId)) {
                indexToRemove = i;
                break;
            }
            i++;
        }
        if (indexToRemove == -1) {
            throw new ServiceException("Phase not found among business case phases, therefore cannot be deleted." +
                    " Bcase id:" + caseId + ", BCPhase id: " + phaseId);
        }
        businessCase.getPhases().remove(indexToRemove);
        updateBusinessCase(businessCase);
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
    public BCPhase findPhaseById(ObjectId caseId, ObjectId phaseId) throws ServiceException {
        BusinessCase businessCase = findById(caseId);
        if (businessCase == null) {
            throw new ServiceException("Cannot find business case with id " + caseId);
        }
        if (businessCase.getPhases() != null) {
            for (BCPhase phase : businessCase.getPhases()) {
                if (phase.getId().equals(phaseId)) {
                    return phase;
                }
            }
        }
        throw new ServiceException("Phase with id " + phaseId + " cannot be found in case with id " + caseId);
    }

    @Override
    public List<BusinessCase> getAllByApplication(ObjectId applicationId) {
        //TODO make more effective?
        return businessCaseDao.findAll().stream()
                .filter(screen -> screen.getApplicationId().equals(applicationId))
                .collect(Collectors.toList());
    }

    @Override
    public List<BCPhase> findPhases(ObjectId bcId) {
        return businessCaseDao.getPhases(bcId);
    }

    @Override
    public BusinessCase findOrCreateBusinessCase(String businessCaseId) {
        if (businessCaseId != null && !businessCaseId.isEmpty()) {
            return findById(new ObjectId(businessCaseId));
        }
        BusinessCase businessCase = new BusinessCase();
        businessCase.setId(new ObjectId());
        return businessCase;
    }

    public BCPhase findOrCreateBusinessPhaseInCase(ObjectId businessCaseId, String businessPhaseId) throws ServiceException {
        BusinessCase businessCase = findById(businessCaseId);
        if (businessCase == null) {
            throw new ServiceException("Cannot find business case with id " + businessCaseId);
        }
        if (businessPhaseId != null && !businessPhaseId.isEmpty()) {
            return findPhaseById(businessCaseId, new ObjectId(businessPhaseId));
        }
        BCPhase businessPhase = new BCPhase();
        businessPhase.setId(new ObjectId());
        businessPhase.setBusinessCase(businessCase);
        return businessPhase;
    }

    @Override
    public List<BusinessCase> getAll() {
        return businessCaseDao.findAll();
    }

    @Override
    public void addBusinessPhaseToCaseById(ObjectId caseId, BCPhase phase) throws ServiceException {
        BusinessCase businessCase = findById(caseId);
        if (businessCase == null) {
            throw new ServiceException("Cannot find business case with id " + caseId);
        }
        businessCase.addPhase(phase);
        updateBusinessCase(businessCase);
    }

    @Override
    public void replaceBusinessPhaseInCaseById(ObjectId caseId, BCPhase phase) throws ServiceException {
        BusinessCase businessCase = findById(caseId);
        if (businessCase == null) {
            throw new ServiceException("Cannot find business case with id " + caseId);
        }
        removeBusinessPhaseFromCaseById(caseId, phase.getId());
        addBusinessPhaseToCaseById(caseId, phase);
    }

}
