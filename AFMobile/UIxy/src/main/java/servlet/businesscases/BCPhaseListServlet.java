package servlet.businesscases;

import service.exception.ServiceException;
import service.servlet.ApplicationsManagementService;
import service.servlet.BusinessCaseManagementService;
import service.servlet.BusinessPhaseManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for displaying a list of {@link model.afclassification.BCPhase}s
 */
public class BCPhaseListServlet extends HttpServlet {

    /**
     * The List url.
     */
    static final String LIST_URL = "/WEB-INF/pages/bcphases/list.jsp";
    /**
     * The List route.
     */
    static final String LIST_ROUTE = "list";

    @Inject
    private BusinessCaseManagementService businessCaseManagementService;

    @Inject
    BusinessPhaseManagementService businessPhaseManagementService;

    @Inject
    private ApplicationsManagementService applicationsManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationIdString = request.getParameter(ParameterNames.APPLICATION_ID);
        String businessCaseIdString = request.getParameter(ParameterNames.BUSINESS_CASE_ID);
        if (applicationIdString == null || applicationIdString.isEmpty() || businessCaseIdString == null || businessCaseIdString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int businessCaseId = Integer.parseInt(businessCaseIdString);
        int applicationId = Integer.parseInt(applicationIdString);
        request.setAttribute("businessPhases", businessPhaseManagementService.findPhasesByBusinessCase(businessCaseId));
        request.setAttribute(ParameterNames.APPLICATION_NAME, applicationsManagementService.findById(applicationId).getApplicationName());
        request.setAttribute(ParameterNames.BUSINESS_CASE_NAME, businessCaseManagementService.findById(businessCaseId).getName());
        request.setAttribute(ParameterNames.BUSINESS_CASE_ID, businessCaseId);
        request.setAttribute(ParameterNames.APPLICATION_ID, applicationId);
        getServletContext().getRequestDispatcher(LIST_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int phaseId = Integer.parseInt(Utils.trimString(req.getParameter(ParameterNames.BUSINESS_PHASE_ID)));
        String applicationIdString = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID));
        String businessCaseIdString = Utils.trimString(req.getParameter(ParameterNames.BUSINESS_CASE_ID));
        if (applicationIdString == null || applicationIdString.isEmpty() || businessCaseIdString == null || businessCaseIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        businessPhaseManagementService.removeBusinessPhase(phaseId);
        resp.sendRedirect(LIST_ROUTE + "?" + ParameterNames.APPLICATION_ID + "=" + applicationIdString + "&" + ParameterNames.BUSINESS_CASE_ID + "=" + businessCaseIdString);
    }
}
