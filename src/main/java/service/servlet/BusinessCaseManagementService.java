package service.servlet;

import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;
import service.exception.ServiceException;
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
    public void createOrUpdate(BusinessCase bc);

    /**
     * Remove business case.
     *
     * @param bc business case to be removed
     */
    public void removeBusinessCase(BusinessCase bc);

    /**
     * Find business case by id .
     *
     * @param id the id
     * @return the business case
     */
    public BusinessCase findById(int id);

    /**
     * Gets all by application.
     *
     * @param id the id
     * @return the all by application
     */
    public List<BusinessCase> getAllByApplication(int id);

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

    /**
     * Remove business phase from case by id.
     *
     * @param caseId  the case id
     * @param phaseId the phase id
     * @throws ServiceException the service exception
     */
    public void removeBusinessPhaseFromCaseById(int caseId, int phaseId) throws ServiceException;

    /**
     * Add business phase to case by id.
     *
     * @param caseId the case id
     * @param phase  the phase
     * @throws ServiceException the service exception
     */
    public void addBusinessPhaseToCaseById(int caseId, BCPhase phase) throws ServiceException;
}
