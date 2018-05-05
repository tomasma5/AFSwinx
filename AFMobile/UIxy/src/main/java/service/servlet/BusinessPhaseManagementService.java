package service.servlet;

import model.afclassification.BCPhase;
import service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * This service is used for {@link BCPhase} management
 */
public interface BusinessPhaseManagementService {

    /**
     * Create business phase.
     *
     * @param phase the bc
     */
    public void createOrUpdate(BCPhase phase);

    /**
     * Remove business case.
     *
     * @param phaseId the id
     */
    public void removeBusinessPhase(Integer phaseId);

    /**
     * Find business phase by id .
     *
     * @param id the id
     * @return the business case
     */
    public BCPhase findById(int id);

    /**
     * Find or create business case.
     *
     * @param phaseId the business case id
     * @return the business case
     */
    public BCPhase findOrCreateBusinessPhase(String phaseId);

    /**
     * Gets all business cases.
     *
     * @return the all business cases
     */
    public List<BCPhase> getAll();

    /**
     * Update linked screens in business phase.
     *
     * @param req                the servlet request
     * @param phase              business phase
     * @param linkedScreensCount linked screens count
     * @throws ServiceException the service exception
     */
    public void updateLinkedScreensInBusinessPhase(HttpServletRequest req, BCPhase phase, int linkedScreensCount) throws ServiceException;

    /**
     * Find list of phases by business case id.
     *
     * @param bcId the id of business case
     * @return the list of business phases which belongs to this business case
     */
    public List<BCPhase> findPhasesByBusinessCase(int bcId);

    /**
     * Update phase business case fields.
     *
     * @param phase business phase
     * @throws IOException thrown if fields cannot be got from server
     */
    public void updatePhaseBCFields(BCPhase phase) throws IOException;

    /**
     * Creates or updates business phase and fills it with data from request
     *
     * @param req the request
     * @param businessPhaseIdString string representation of business phase id
     * @param businessCaseId string representation of business case id
     * @param linkedScreensCountString string representation of number of linked screens
     * @return id of bc phase
     * @throws ServiceException thrown if phase cannot be saved
     */
    public Integer saveBCPhaseFromRequest(HttpServletRequest req, String businessPhaseIdString,
                                       String businessCaseId, String linkedScreensCountString) throws ServiceException;

}
