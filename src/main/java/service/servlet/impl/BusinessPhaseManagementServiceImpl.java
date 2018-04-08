package service.servlet.impl;

import dao.BusinessPhaseDao;
import dao.ScreenDao;
import model.ComponentResource;
import model.Screen;
import model.afclassification.*;
import org.json.JSONArray;
import org.json.JSONObject;
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
    private ScreenDao screenDao;


    @Override
    public void createOrUpdate(BCPhase phase) {
        businessPhaseDao.createOrUpdate(phase);
    }

    @Override
    public void removeBusinessPhase(BCPhase phase) {
        businessPhaseDao.delete(phase);
    }

    @Override
    public BCPhase findById(int id) {
        return businessPhaseDao.getById(id);
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
        return businessPhaseDao.getAll();
    }

    @Override
    public void updateLinkedScreensInBusinessPhase(HttpServletRequest req, BCPhase phase, int linkedScreensCount) throws ServiceException {
        if (phase.getLinkedScreens() != null) {
            for (Screen screen : phase.getLinkedScreens()) {
                screen.setPhase(null);
                screenDao.createOrUpdate(screen);
            }
            phase.getLinkedScreens().clear();
        }
        for (int i = 0; i < linkedScreensCount; i++) {
            String screenId = Utils.trimString(req.getParameter(ParameterNames.BUSINESS_PHASE_LINKED_SCREEN_ID + (i + 1)));
            Screen screen = screenDao.getById(Integer.parseInt(screenId));
            phase.addLinkedScreen(screen);
            screen.setPhase(phase);
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
        return businessPhaseDao.getAll().stream().filter(phase -> phase.getBusinessCase().getId() == bcId).collect(toList());
    }

    @Override
    public void updatePhaseBCFields(BCPhase phase) throws IOException {
        if (phase.getFields() != null) {
            phase.getFields().clear();
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
                            Field field = new Field();
                            field.setFieldName(road.toString() + fieldInfo.getString(Constants.FIELD_ID));
                            field.setClassName(className);
                            field.setType(fieldInfo.getString(Constants.WIDGET_TYPE));
                            BCField bcField = new BCField();
                            bcField.setField(field);
                            bcField.setPhase(phase);
                            bcField.setScreen(screen);
                            bcField.setComponent(componentResource);
                            bcField.setFieldSpecification(new BCFieldSeverity());
                            phase.addBCField(bcField);
                        }
                    }
                }
            }
        }
    }

}
