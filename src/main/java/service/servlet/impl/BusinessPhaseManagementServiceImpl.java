package service.servlet.impl;

import dao.*;
import model.ComponentResource;
import model.Screen;
import model.afclassification.*;
import org.json.JSONArray;
import org.json.JSONObject;
import service.afclassification.computational.ccm.SupportedClassificationUnit;
import service.afclassification.computational.scm.SupportedScoringUnit;
import service.exception.ServiceException;
import service.servlet.BusinessPhaseManagementService;
import servlet.ParameterNames;
import utils.Constants;
import utils.HttpUtils;
import utils.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Implementation of business phase management service
 */
@Named("businessPhaseManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class BusinessPhaseManagementServiceImpl implements BusinessPhaseManagementService {

    @Inject
    private BusinessPhaseDao businessPhaseDao;
    @Inject
    private BusinessCaseDao businessCaseDao;
    @Inject
    private BusinessFieldsDao businessFieldsDao;
    @Inject
    private FieldSeverityDao fieldSeverityDao;
    @Inject
    private FieldsDao fieldsDao;
    @Inject
    private ConfigurationPackDao configurationPackDao;
    @Inject
    private ScreenDao screenDao;

    @Override
    public void createOrUpdate(BCPhase phase) {
        businessPhaseDao.createOrUpdate(phase);
    }

    @Override
    public void removeBusinessPhase(Integer phaseId) {
        BCPhase toBeRemoved = findById(phaseId);
        removePhaseFromLinkedScreens(toBeRemoved);
        businessPhaseDao.createOrUpdate(toBeRemoved);
        businessPhaseDao.delete(toBeRemoved);
    }

    private void removePhaseFromLinkedScreens(BCPhase toBeRemoved) {
        List<Screen> linkedScreens = toBeRemoved.getLinkedScreens();
        if (linkedScreens != null) {
            for (Screen linkedScreen : linkedScreens) {
                linkedScreen.setPhase(null);
                screenDao.createOrUpdate(linkedScreen);
            }
            toBeRemoved.getLinkedScreens().clear();
        }
    }

    private void removePhaseFromLinkedFields(BCPhase toBeRemoved) {
        List<BCField> linkedFields = toBeRemoved.getFields();
        if (linkedFields != null) {
            for (BCField field : linkedFields) {
                field.setPhase(null);
                businessFieldsDao.createOrUpdate(field);
            }
            toBeRemoved.getFields().clear();
        }
    }

    @Override
    public BCPhase findById(int id) {
        return businessPhaseDao.getBusinessPhaseByIdWithLoadedScreens(id);
    }

    @Override
    public BCPhase findOrCreateBusinessPhase(String phaseId) {
        BCPhase bcPhase;
        if (phaseId == null || phaseId.isEmpty()) {
            bcPhase = new BCPhase();
        } else {
            bcPhase = findById(Integer.parseInt(phaseId));
        }
        return bcPhase;
    }

    @Override
    public List<BCPhase> getAll() {
        return businessPhaseDao.getBusinessPhasesWithLoadedScreens();
    }

    @Override
    public void updateLinkedScreensInBusinessPhase(HttpServletRequest req, BCPhase phase, int linkedScreensCount) throws ServiceException {
        removePhaseFromLinkedScreens(phase);
        for (int i = 0; i < linkedScreensCount; i++) {
            String screenId = Utils.trimString(req.getParameter(ParameterNames.BUSINESS_PHASE_LINKED_SCREEN_ID + (i + 1)));
            Screen screen = screenDao.getById(Integer.parseInt(screenId));
            screen.setPhase(phase);
            phase.addLinkedScreen(screen);
            screenDao.createOrUpdate(screen);
        }
        try {
            updatePhaseBCFields(phase);
        } catch (IOException e) {
            System.err.println("Cannot get component fields from server.");
            e.printStackTrace();
        }
    }

    @Override
    public List<BCPhase> findPhasesByBusinessCase(int bcId) {
        return businessPhaseDao.getBusinessPhasesWithLoadedScreens().stream().filter(phase -> phase.getBusinessCase().getId() == bcId).collect(toList());
    }

    @Override
    public void updatePhaseBCFields(BCPhase phase) throws IOException {
        if (phase.getFields() != null) {
            removePhaseFromLinkedFields(phase);
        }
        if (phase.getLinkedScreens() != null) {
            for (Screen screen : phase.getLinkedScreens()) {
                for (ComponentResource componentResource : screen.getComponents()) {
                    String classInfoString = HttpUtils.getRequest(HttpUtils.buildUrl(
                            componentResource.getFieldInfoUrlProtocol(),
                            componentResource.getFieldInfoUrlHostname(),
                            componentResource.getFieldInfoUrlPort(),
                            null,
                            componentResource.getFieldInfoUrlParameters()
                    ), null);
                    JSONObject classInfo = new JSONObject(classInfoString);
                    String className = classInfo.getString(Constants.CLASS_NAME);
                    prepareBCFields(classInfo, 0, false, className,
                            new StringBuilder(), phase, screen, componentResource);
                }
            }
        }
    }

    private void prepareBCFields(JSONObject classInfo, int numberOfInnerClasses, boolean parsingInnerClass,
                                 String className, StringBuilder road, BCPhase phase, Screen screen, ComponentResource componentResource) {
        if (parsingInnerClass) {
            numberOfInnerClasses = 0;
        }
        if (classInfo != null) {
            JSONArray innerClasses = classInfo.optJSONArray(Constants.INNER_CLASSES);
            JSONArray fieldsInfo = classInfo.optJSONArray(Constants.FIELD_INFO);
            if (fieldsInfo != null) {
                for (int i = 0; i < fieldsInfo.length(); i++) {
                    JSONObject fieldInfo = fieldsInfo.getJSONObject(i);
                    boolean isClassType = fieldInfo.getBoolean(Constants.FIELD_CLASS_TYPE);
                    if (isClassType && innerClasses != null) {
                        String roadBackup = road.toString();
                        road.append(innerClasses.getJSONObject(numberOfInnerClasses).getString(Constants.CLASS_NAME));
                        road.append(".");
                        prepareBCFields(innerClasses.getJSONObject(numberOfInnerClasses), numberOfInnerClasses++,
                                true, className, road, phase, screen, componentResource);
                        road = new StringBuilder(roadBackup);
                    } else {
                        if (fieldInfo.getBoolean(Constants.FIELD_VISIBILITY)) {
                            String fieldName = road.toString() + fieldInfo.getString(Constants.FIELD_ID);
                            String fieldType = fieldInfo.getString(Constants.WIDGET_TYPE);
                            Field field = fieldsDao.getByClassAndFieldNameAndType(className, fieldName, fieldType);
                            if (field == null) {
                                //if field is already here
                                field = new Field();
                                field.setClassName(className);
                                field.setFieldName(fieldName);
                                field.setType(fieldType);
                                BCField bcField = new BCField();
                                bcField.setPhase(phase);
                                bcField.setScreen(screen);
                                bcField.setComponent(componentResource);
                                BCFieldSeverity fieldSeverity = new BCFieldSeverity();
                                fieldSeverityDao.createOrUpdate(fieldSeverity);
                                bcField.setFieldSpecification(fieldSeverity);
                                field.addBCField(bcField);
                                fieldsDao.createOrUpdate(field);
                                bcField.setField(field);
                                businessFieldsDao.createOrUpdate(bcField);
                                phase.addBCField(bcField);
                            } else {
                                for(BCField bcField : field.getBCFields()){
                                    bcField.setPhase(phase);
                                    businessFieldsDao.createOrUpdate(bcField);
                                    phase.addBCField(bcField);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Integer saveBCPhaseFromRequest(HttpServletRequest req, String businessPhaseIdString,
                                          String businessCaseId, String linkedScreensCountString) throws ServiceException {
        BCPhase phase = findOrCreateBusinessPhase(businessPhaseIdString);
        updateBCPhaseProperties(req, Integer.parseInt(businessCaseId), phase);
        updateLinkedScreensInBusinessPhase(req, phase, Integer.parseInt(linkedScreensCountString));
        createOrUpdate(phase);
        return phase.getId();
    }

    private void updateBCPhaseProperties(HttpServletRequest req, int bcaseId, BCPhase phase) {
        String name = Utils.trimString(Utils.trimString(req.getParameter(ParameterNames.BUSINESS_PHASE_NAME)));
        SupportedClassificationUnit classificationUnitType =
                SupportedClassificationUnit.valueOf(Utils.trimString(req.getParameter(ParameterNames.SELECTED_CLASSIFICATION_UNIT)));
        SupportedScoringUnit supportedScoringUnit =
                SupportedScoringUnit.valueOf(Utils.trimString(req.getParameter(ParameterNames.SELECTED_SCORING_UNIT)));
        phase.setBusinessCase(businessCaseDao.getById(bcaseId));
        phase.setName(name);
        phase.setClassificationUnit(classificationUnitType);
        phase.setScoringUnit(supportedScoringUnit);
        String selectedConfiguration = Utils.trimString(req.getParameter(ParameterNames.SELECTED_CONFIGURATION));
        ConfigurationPack configurationModel = configurationPackDao.getById(Integer.parseInt(selectedConfiguration));
        phase.setConfiguration(configurationModel);
    }

}
