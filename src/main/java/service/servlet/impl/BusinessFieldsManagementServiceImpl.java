package service.servlet.impl;

import dao.BusinessFieldsDao;
import model.afclassification.BCField;
import model.afclassification.Purpose;
import model.afclassification.Severity;
import service.servlet.BusinessFieldsManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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
        return businessFieldsDao.getAll().stream().filter(bcField -> bcField.getPhase() != null && bcField.getPhase().getId() == businessPhaseId).collect(toList());
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

    @Override
    public void saveFieldConfigurationFromRequest(HttpServletRequest req, String businessPhaseIdString) {
        int businessPhaseId = Integer.parseInt(businessPhaseIdString);
        List<BCField> fields = findAllByPhase(businessPhaseId);

        for (int i = 0; i < fields.size(); i++) {
            String severity = Utils.trimString(req.getParameter(ParameterNames.FIELD_SEVERITY + i));
            String purpose = Utils.trimString(req.getParameter(ParameterNames.FIELD_PURPOSE + i));
            BCField field = fields.get(i);
            if (severity != null) {
                field.getFieldSpecification().setSeverity(Severity.valueOf(severity));
            }
            if (purpose != null) {
                field.getFieldSpecification().setPurpose(Purpose.valueOf(purpose));
            }
            createOrUpdate(field);
        }
    }
}
