package service.servlet;

import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;
import org.bson.types.ObjectId;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This service is used for {@link BusinessCase} and {@link BCPhase} management
 */
public interface BusinessCaseManagementService {

    /**
     * Create business case.
     *
     * @param bc the bc
     */
    public void createBusinessCase(BusinessCase bc);

    /**
     * Remove business case.
     *
     * @param id the id
     */
    public void removeBusinessCase(ObjectId id);

    /**
     * Update business case.
     *
     * @param bc the bc
     */
    public void updateBusinessCase(BusinessCase bc);

    /**
     * Find business case by id .
     *
     * @param id the id
     * @return the business case
     */
    public BusinessCase findById(ObjectId id);

    /**
     * Gets all by application.
     *
     * @param objectId the object id
     * @return the all by application
     */
    public List<BusinessCase> getAllByApplication(ObjectId objectId);

    /**
     * Find or create business case.
     *
     * @param businessCaseId the business case id
     * @return the business case
     */
    public BusinessCase findOrCreateBusinessCase(String businessCaseId);

    /**
     * Gets all business cases.
     *
     * @return the all business cases
     */
    public List<BusinessCase> getAll();

    //phases

    /**
     * Add business phase to case by id.
     *
     * @param caseId the case id
     * @param phase  the phase
     * @throws ServiceException the service exception
     */
    public void addBusinessPhaseToCaseById(ObjectId caseId, BCPhase phase) throws ServiceException;

    /**
     * Replace business phase in case by id.
     *
     * @param caseId the case id
     * @param phase  the phase
     * @throws ServiceException the service exception
     */
    public void replaceBusinessPhaseInCaseById(ObjectId caseId, BCPhase phase) throws ServiceException;

    /**
     * Remove business phase from case by id.
     *
     * @param caseId  the case id
     * @param phaseId the phase id
     * @throws ServiceException the service exception
     */
    public void removeBusinessPhaseFromCaseById(ObjectId caseId, ObjectId phaseId) throws ServiceException;

    /**
     * Find phase by id bc phase.
     *
     * @param caseId  the case id
     * @param phaseId the phase id
     * @return the bc phase
     * @throws ServiceException the service exception
     */
    public BCPhase findPhaseById(ObjectId caseId, ObjectId phaseId) throws ServiceException;

    /**
     * Find list of phases by business case id.
     *
     * @param bcId the bc id
     * @return the list
     */
    public List<BCPhase> findPhases(ObjectId bcId);

    /**
     * Find or create business phase in case by case id.
     *
     * @param businessCaseId  the business case id
     * @param businessPhaseId the business phase id
     * @return the bc phase
     * @throws ServiceException the service exception
     */
    public BCPhase findOrCreateBusinessPhaseInCase(ObjectId businessCaseId, String businessPhaseId) throws ServiceException;

    /**
     * Update linked screens in business phase.
     *
     * @param req                the req
     * @param phase              the phase
     * @param businessCaseId     the business case id
     * @param linkedScreensCount the linked screens count
     * @throws ServiceException the service exception
     */
    public void updateLinkedScreensInBusinessPhase(HttpServletRequest req, BCPhase phase, ObjectId businessCaseId, int linkedScreensCount) throws ServiceException;

}
