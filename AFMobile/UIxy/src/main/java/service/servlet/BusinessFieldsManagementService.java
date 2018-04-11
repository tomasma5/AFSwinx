package service.servlet;

import model.Application;
import model.afclassification.BCField;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This service is used for management of {@link Application}
 */
public interface BusinessFieldsManagementService {

    /**
     * Add new business field.
     *
     * @param bcField the business field
     */
    public void createOrUpdate(BCField bcField);

    /**
     * Remove business field.
     *
     * @param bcField the id
     */
    public void removeBusinessField(BCField bcField);

    /**
     * Find business field by id.
     *
     * @param id the id
     * @return the application
     */
    public BCField findById(int id);

    /**
     * Gets all business fields.
     *
     * @return the all
     */
    public List<BCField> getAll();

    /**
     * Gets all business fields by given business phase
     *
     * @param businessPhaseId given business phase
     * @return business phase's fields
     */
    public List<BCField> findAllByPhase(int businessPhaseId);

    /**
     * Find or create business field.
     *
     * @param businessFieldId the business field id
     * @return the business field
     */
    public BCField findOrCreateBusinessField(String businessFieldId);

    /**
     * Updates fields severities and purposes of phase from request
     *
     * @param req the request
     * @param businessPhaseIdString string representaion of business phase id
     */
    public void saveFieldConfigurationFromRequest(HttpServletRequest req, String businessPhaseIdString);
}
