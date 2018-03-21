package servlet.businesscases;

import model.afclassification.BCPhase;
import model.afclassification.Configuration;
import model.afclassification.ConfigurationPack;
import org.bson.types.ObjectId;
import service.exception.ServiceException;
import service.servlet.BusinessCaseManagementService;
import service.servlet.ConfigurationManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static servlet.businesscases.BusinessCaseListServlet.LIST_ROUTE;

public class BCPhaseCreateServlet extends HttpServlet {

    static final String CREATE_URL = "/WEB-INF/pages/bcphases/create.jsp";
    static final String CREATE_ROUTE = "phases/create";

    @Inject
    private BusinessCaseManagementService bcManagementService;

    @Inject
    private ConfigurationManagementService configurationManagementService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationIdString = Utils.trimString(request.getParameter(ParameterNames.APPLICATION_ID));
        String businessCaseIdString = Utils.trimString(request.getParameter(ParameterNames.BUSINESS_CASE_ID));
        if (applicationIdString == null || applicationIdString.isEmpty() || businessCaseIdString == null || businessCaseIdString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String bcPhaseIdString = Utils.trimString(request.getParameter(ParameterNames.BUSINESS_PHASE_ID));
        ObjectId appObjId = new ObjectId(applicationIdString);
        ObjectId businessCaseObjId = new ObjectId(businessCaseIdString);
        if (bcPhaseIdString != null) {
            ObjectId businessPhaseObjId = new ObjectId(bcPhaseIdString);
            try {
                BCPhase businessPhase = bcManagementService.findPhaseById(businessCaseObjId, businessPhaseObjId);
                request.setAttribute(ParameterNames.BUSINESS_PHASE_ID, businessPhase.getId());
                request.setAttribute(ParameterNames.BUSINESS_PHASE_NAME, businessPhase.getName());
                if(businessPhase.getConfiguration() != null) {
                    request.setAttribute(ParameterNames.SELECTED_CONFIGURATION, businessPhase.getConfiguration().getId());
                }
            } catch (ServiceException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                e.printStackTrace();
                return;
            }
        }

        List<ConfigurationPack> availableConfiguration = configurationManagementService.getAllConfigurationsByApplication(appObjId);
        request.setAttribute(ParameterNames.CONFIGURATION_LIST, availableConfiguration);

        request.setAttribute(ParameterNames.APPLICATION_ID, appObjId);
        request.setAttribute(ParameterNames.BUSINESS_CASE_ID, businessCaseObjId);

        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
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
        try {
            BCPhase phase = bcManagementService.findOrCreateBusinessPhaseInCase(new ObjectId(businessCaseIdString), businessPhaseIdString);
            updateBCPhaseProperties(req, new ObjectId(businessCaseIdString), phase);
            createOrUpdateScreen(businessCaseIdString, businessPhaseIdString, phase);
        } catch (ServiceException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            e.printStackTrace();
        }
        resp.sendRedirect(LIST_ROUTE + "?" + ParameterNames.APPLICATION_ID + "=" + applicationIdString + "&" + ParameterNames.BUSINESS_CASE_ID + "=" + businessCaseIdString);
    }

    private void createOrUpdateScreen(String caseId, String phaseId, BCPhase phase) throws ServiceException {
        if (phaseId == null || phaseId.isEmpty()) {
            bcManagementService.addBusinessPhaseToCaseById(new ObjectId(caseId), phase);
        } else {
            bcManagementService.replaceBusinessPhaseInCaseById(new ObjectId(caseId), phase);
        }
    }

    private void updateBCPhaseProperties(HttpServletRequest req, ObjectId bcaseId, BCPhase phase) {
        String name = Utils.trimString(Utils.trimString(req.getParameter(ParameterNames.BUSINESS_PHASE_NAME)));
        phase.setBusinessCase(bcManagementService.findById(bcaseId));
        phase.setName(name);
        String selectedConfiguration =  Utils.trimString(req.getParameter(ParameterNames.SELECTED_CONFIGURATION));
        ConfigurationPack configurationModel= configurationManagementService.findConfigurationById(new ObjectId(selectedConfiguration));
        phase.setConfiguration(configurationModel);
    }
}
