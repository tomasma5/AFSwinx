package servlet.businesscases;

import model.afclassification.BCPhase;
import model.afclassification.Purpose;
import model.afclassification.Severity;
import org.bson.types.ObjectId;
import service.exception.ServiceException;
import service.servlet.BusinessCaseManagementService;
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

public class BCPhaseFieldsConfigurationServlet extends HttpServlet {

    static final String CONFIGURE_URL = "/WEB-INF/pages/bcphases/fieldconfiguration.jsp";
    static final String CONFIGURE_ROUTE = "configure";

    @Inject
    private BusinessCaseManagementService bcManagementService;


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

        try {
            BCPhase phase = bcManagementService.findPhaseById(new ObjectId(businessCaseIdString), new ObjectId(businessPhaseIdString));

            List<String> severityOptions = new ArrayList<>();
            for (Severity severity : Severity.class.getEnumConstants()) {
                severityOptions.add(severity.toString());
            }
            List<String> purposeOptions = new ArrayList<>();
            for (Purpose purpose : Purpose.class.getEnumConstants()) {
                purposeOptions.add(purpose.toString());
            }

            request.setAttribute("fields", phase.getFields());
            request.setAttribute("severityOptions", severityOptions);
            request.setAttribute("purposeOptions", purposeOptions);
            request.setAttribute(ParameterNames.APPLICATION_ID, applicationIdString);
            request.setAttribute(ParameterNames.BUSINESS_CASE_ID, businessCaseIdString);
            request.setAttribute(ParameterNames.BUSINESS_PHASE_ID, businessPhaseIdString);
        } catch (ServiceException e) {
            System.err.println("Phase not found");
            e.printStackTrace();
        }
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
        ObjectId businessCaseId = new ObjectId(businessCaseIdString);
        ObjectId businessPhaseId = new ObjectId(businessPhaseIdString);
        try {
            BCPhase phase = bcManagementService.findPhaseById(businessCaseId, businessPhaseId);
            for (int i = 0; i < phase.getFields().size(); i++) {
                String severity = Utils.trimString(req.getParameter(ParameterNames.FIELD_SEVERITY + i));
                String purpose = Utils.trimString(req.getParameter(ParameterNames.FIELD_PURPOSE + i));
                if (severity != null) {
                    phase.getFields().get(i).getFieldSpecification().setSeverity(Severity.valueOf(severity));
                }
                if (purpose != null) {
                    phase.getFields().get(i).getFieldSpecification().setPurpose(Purpose.valueOf(purpose));
                }
            }
            bcManagementService.replaceBusinessPhaseInCaseById(businessCaseId, phase);
        } catch (ServiceException e) {
            System.err.println("Phase not found");
            e.printStackTrace();
        }
        resp.sendRedirect(LIST_ROUTE + "?" + ParameterNames.APPLICATION_ID + "=" + applicationIdString + "&" + ParameterNames.BUSINESS_CASE_ID + "=" + businessCaseIdString);
    }

}
