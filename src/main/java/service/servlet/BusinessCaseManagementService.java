package service.servlet;

import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;
import org.bson.types.ObjectId;
import service.exception.ServiceException;

import java.util.List;

public interface BusinessCaseManagementService {

    public void createBusinessCase(BusinessCase bc);

    public void removeBusinessCase(ObjectId id);

    public void updateBusinessCase(BusinessCase bc);

    public BusinessCase findById(ObjectId id);

    public List<BusinessCase> getAllByApplication(ObjectId objectId);

    public BusinessCase findOrCreateBusinessCase(String businessCaseId);

    public List<BusinessCase> getAll();

    //phases

    public void addBusinessPhaseToCaseById(ObjectId caseId, BCPhase phase) throws ServiceException;

    public void replaceBusinessPhaseInCaseById(ObjectId caseId, BCPhase phase) throws ServiceException;

    public void removeBusinessPhaseFromCaseById(ObjectId caseId, ObjectId phaseId) throws ServiceException;

    public BCPhase findPhaseById(ObjectId caseId, ObjectId phaseId) throws ServiceException;

    public List<BCPhase> findPhases(ObjectId bcId);

    public BCPhase findOrCreateBusinessPhaseInCase(ObjectId businessCaseId, String businessPhaseId) throws ServiceException;



}
