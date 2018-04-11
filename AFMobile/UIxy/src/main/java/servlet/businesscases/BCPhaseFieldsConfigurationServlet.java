package servlet.businesscases;

import model.afclassification.BCField;
import model.afclassification.BCPhase;
import model.afclassification.Purpose;
import model.afclassification.Severity;
import service.servlet.BusinessFieldsManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static servlet.businesscases.BCPhaseListServlet.LIST_ROUTE;

/**
 * Servlet for configuring severity and purpose of {@link BCPhase}'s {@link model.afclassification.BCField}s
 */
public class BCPhaseFieldsConfigurationServlet extends HttpServlet {

    /**
     * The Configure url.
     */
    static final String CONFIGURE_URL = "/WEB-INF/pages/bcphases/fieldconfiguration.jsp";
    /**
     * The Configure route.
     */
    static final String CONFIGURE_ROUTE = "configure";


    @Inject
    private BusinessFieldsManagementService businessFieldsManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationIdString = Utils.trimString(request.getParameter(ParameterNames.APPLICATION_ID));
        String businessCaseIdString = Utils.trimString(request.getParameter(ParameterNames.BUSINESS_CASE_ID));
        String businessPhaseIdString = Utils.trimString(request.getParameter(ParameterNames.BUSINESS_PHASE_ID));
        if (applicationIdString == null || applicationIdString.isEmpty() ||
                businessCaseIdString == null || businessCaseIdString.isEmpty() ||
                businessPhaseIdString == null || businessPhaseIdString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<BCField> fields = businessFieldsManagementService.findAllByPhase(Integer.parseInt(businessPhaseIdString));

        List<String> severityOptions = new ArrayList<>();
        for (Severity severity : Severity.class.getEnumConstants()) {
            severityOptions.add(severity.toString());
        }
        List<String> purposeOptions = new ArrayList<>();
        for (Purpose purpose : Purpose.class.getEnumConstants()) {
            purposeOptions.add(purpose.toString());
        }

        request.setAttribute("fields", fields);
        request.setAttribute("severityOptions", severityOptions);
        request.setAttribute("purposeOptions", purposeOptions);
        request.setAttribute(ParameterNames.APPLICATION_ID, applicationIdString);
        request.setAttribute(ParameterNames.BUSINESS_CASE_ID, businessCaseIdString);
        request.setAttribute(ParameterNames.BUSINESS_PHASE_ID, businessPhaseIdString);
        getServletContext().getRequestDispatcher(CONFIGURE_URL).forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String applicationIdString = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID));
        String businessCaseIdString = Utils.trimString(req.getParameter(ParameterNames.BUSINESS_CASE_ID));
        String businessPhaseIdString = Utils.trimString(req.getParameter(ParameterNames.BUSINESS_PHASE_ID));
        if (applicationIdString == null || applicationIdString.isEmpty() ||
                businessCaseIdString == null || businessCaseIdString.isEmpty() ||
                businessPhaseIdString == null || businessPhaseIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        businessFieldsManagementService.saveFieldConfigurationFromRequest(req, businessPhaseIdString);
        resp.sendRedirect(LIST_ROUTE + "?" + ParameterNames.APPLICATION_ID + "=" + applicationIdString + "&" + ParameterNames.BUSINESS_CASE_ID + "=" + businessCaseIdString);
    }

}
