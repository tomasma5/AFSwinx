package service.servlet;

import model.afclassification.BCPhase;
import model.afclassification.BusinessCase;
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
    public void createOrUpdate(BusinessCase bc);

    /**
     * Remove business case.
     *
     * @param bcId id of business case to be removed
     */
    public void removeBusinessCase(Integer bcId);

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
    public List<BusinessCase> getAllByApplication(Integer id);

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
     * Creates or updates business case and fill it with data from request
     *
     * @param req the request
     * @param bCaseId string representation of business case id
     * @param appIdString string representation of application id
     */
    public void saveBusinessCaseFromRequest(HttpServletRequest req, String bCaseId, String appIdString);

}
