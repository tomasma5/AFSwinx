package servlet.businesscases;

import model.Screen;
import model.afclassification.BCPhase;
import model.afclassification.ConfigurationPack;
import service.afclassification.computational.ccm.SupportedClassificationUnit;
import service.afclassification.computational.scm.SupportedScoringUnit;
import service.exception.ServiceException;
import service.servlet.BusinessCaseManagementService;
import service.servlet.BusinessPhaseManagementService;
import service.servlet.ConfigurationManagementService;
import service.servlet.ScreenManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static servlet.businesscases.BCPhaseFieldsConfigurationServlet.CONFIGURE_ROUTE;

/**
 * Servlet for creating {@link BCPhase} inside {@link model.afclassification.BusinessCase}
 */
public class BCPhaseCreateServlet extends HttpServlet {

    /**
     * The Create url.
     */
    static final String CREATE_URL = "/WEB-INF/pages/bcphases/create.jsp";
    /**
     * The Create route.
     */
    static final String CREATE_ROUTE = "phases/create";

    @Inject
    private BusinessCaseManagementService bcManagementService;

    @Inject
    private BusinessPhaseManagementService bcPhaseManagementService;

    @Inject
    private ConfigurationManagementService configurationManagementService;

    @Inject
    private ScreenManagementService screenManagementService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationIdString = Utils.trimString(request.getParameter(ParameterNames.APPLICATION_ID));
        String businessCaseIdString = Utils.trimString(request.getParameter(ParameterNames.BUSINESS_CASE_ID));
        if (applicationIdString == null || applicationIdString.isEmpty() || businessCaseIdString == null || businessCaseIdString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String bcPhaseIdString = Utils.trimString(request.getParameter(ParameterNames.BUSINESS_PHASE_ID));
        int appId = Integer.parseInt(applicationIdString);
        int businessCaseObjId = Integer.parseInt(businessCaseIdString);
        if (bcPhaseIdString != null) {
            try {
                setExistingPhaseToRequest(request, bcPhaseIdString, appId);
            } catch (ServiceException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                e.printStackTrace();
                return;
            }
        } else {
            //options for linking screens with phases
            request.setAttribute(ParameterNames.SCREEN_OPTIONS, screenManagementService.getAllUnassignedScreensByApplication(appId));
        }
        //options for configuration select
        List<ConfigurationPack> availableConfiguration = configurationManagementService.getAllConfigurationsByApplication(appId);
        request.setAttribute(ParameterNames.CONFIGURATION_LIST, availableConfiguration);
        request.setAttribute(ParameterNames.CLASSIFICATION_UNIT_LIST, SupportedClassificationUnit.class.getEnumConstants());
        request.setAttribute(ParameterNames.SCORING_UNIT_LIST, SupportedScoringUnit.class.getEnumConstants());

        request.setAttribute(ParameterNames.APPLICATION_ID, appId);
        request.setAttribute(ParameterNames.BUSINESS_CASE_ID, businessCaseObjId);

        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
    }

    private void setExistingPhaseToRequest(HttpServletRequest request, String bcPhaseIdString, int appId) throws IOException, ServiceException {
        int businessPhaseObjId = Integer.parseInt(bcPhaseIdString);

        BCPhase businessPhase = bcPhaseManagementService.findById(businessPhaseObjId);
        request.setAttribute(ParameterNames.BUSINESS_PHASE_ID, businessPhase.getId());
        request.setAttribute(ParameterNames.BUSINESS_PHASE_NAME, businessPhase.getName());
        request.setAttribute(ParameterNames.SELECTED_CONFIGURATION, businessPhase.getConfiguration().getId());
        request.setAttribute(ParameterNames.SELECTED_CLASSIFICATION_UNIT, businessPhase.getClassificationUnit());
        request.setAttribute(ParameterNames.SELECTED_SCORING_UNIT, businessPhase.getScoringUnit());
        request.setAttribute(ParameterNames.BUSINESS_PHASE_LINKED_SCREENS, businessPhase.getLinkedScreens());
        List<Screen> screensNotInThisPhaseYet = screenManagementService.getAllUnassignedScreensByApplication(appId).stream()
                .filter(screen -> !businessPhase.getLinkedScreens().contains(screen))
                .collect(Collectors.toList());
        request.setAttribute(ParameterNames.SCREEN_OPTIONS, screensNotInThisPhaseYet);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String applicationIdString = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID));
        String businessCaseIdString = Utils.trimString(req.getParameter(ParameterNames.BUSINESS_CASE_ID));
        if (applicationIdString == null || applicationIdString.isEmpty() || businessCaseIdString == null || businessCaseIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String businessPhaseIdString = Utils.trimString(req.getParameter(ParameterNames.BUSINESS_PHASE_ID));
        String linkedScreensCountString = Utils.trimString(req.getParameter(ParameterNames.BUSINESS_PHASE_LINKED_SCREENS_COUNT));
        try {
            int businessCaseId = Integer.parseInt(businessCaseIdString);
            BCPhase phase = bcPhaseManagementService.findOrCreateBusinessPhase(businessPhaseIdString);
            updateBCPhaseProperties(req, businessCaseId, phase);
            bcPhaseManagementService.updateLinkedScreensInBusinessPhase(req, phase, Integer.parseInt(linkedScreensCountString));
            bcPhaseManagementService.createOrUpdate(phase);
        } catch (ServiceException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            e.printStackTrace();
            return;
        }
        resp.sendRedirect(CONFIGURE_ROUTE + "?" + ParameterNames.APPLICATION_ID + "=" +
                applicationIdString + "&" + ParameterNames.BUSINESS_CASE_ID + "=" + businessCaseIdString + "&" +
                ParameterNames.BUSINESS_PHASE_ID + "=" + businessPhaseIdString);
    }

    private void updateBCPhaseProperties(HttpServletRequest req, int bcaseId, BCPhase phase) {
        String name = Utils.trimString(Utils.trimString(req.getParameter(ParameterNames.BUSINESS_PHASE_NAME)));
        SupportedClassificationUnit classificationUnitType =
                SupportedClassificationUnit.valueOf(Utils.trimString(req.getParameter(ParameterNames.SELECTED_CLASSIFICATION_UNIT)));
        SupportedScoringUnit supportedScoringUnit =
                SupportedScoringUnit.valueOf(Utils.trimString(req.getParameter(ParameterNames.SELECTED_SCORING_UNIT)));
        phase.setBusinessCase(bcManagementService.findById(bcaseId));
        phase.setName(name);
        phase.setClassificationUnit(classificationUnitType);
        phase.setScoringUnit(supportedScoringUnit);
        String selectedConfiguration = Utils.trimString(req.getParameter(ParameterNames.SELECTED_CONFIGURATION));
        ConfigurationPack configurationModel = configurationManagementService.findConfigurationById(Integer.parseInt(selectedConfiguration));
        phase.setConfiguration(configurationModel);
    }
}
