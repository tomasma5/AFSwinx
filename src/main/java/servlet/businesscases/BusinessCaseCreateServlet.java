package servlet.businesscases;

import model.Application;
import model.afclassification.BusinessCase;
import service.servlet.ApplicationsManagementService;
import service.servlet.BusinessCaseManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlet.businesscases.BusinessCaseListServlet.LIST_ROUTE;

/**
 * Servlet for creating or editing {@link BusinessCase}s
 */
public class BusinessCaseCreateServlet extends HttpServlet {

    /**
     * The Create url.
     */
    static final String CREATE_URL = "/WEB-INF/pages/businesscases/create.jsp";
    /**
     * The Create route.
     */
    static final String CREATE_ROUTE = "create";

    @Inject
    private BusinessCaseManagementService bcManagementService;

    @Inject
    private ApplicationsManagementService applicationsManagementService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationId = request.getParameter(ParameterNames.APPLICATION_ID);
        String bcaseId = request.getParameter(ParameterNames.BUSINESS_CASE_ID);
        if (applicationId == null || applicationId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int appObjId = Integer.parseInt(applicationId);
        if (bcaseId != null) {
            int bcaseObjId = Integer.parseInt(bcaseId);

            BusinessCase businessCase = bcManagementService.findById(bcaseObjId);
            request.setAttribute(ParameterNames.BUSINESS_CASE_ID, businessCase.getId());
            request.setAttribute(ParameterNames.BUSINESS_CASE_NAME, businessCase.getName());
            request.setAttribute(ParameterNames.BUSINESS_CASE_DESCRIPTION, businessCase.getDescription());
        }
        request.setAttribute(ParameterNames.APPLICATION_ID, appObjId);

        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String appIdString = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID));
        if (appIdString == null || appIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String bCaseId = Utils.trimString(req.getParameter(ParameterNames.BUSINESS_CASE_ID));
        BusinessCase bcase = bcManagementService.findOrCreateBusinessCase(bCaseId);
        updateBcaseProperties(req, appIdString, bcase);
        createOrUpdateBusinessCase(bCaseId, bcase);
        resp.sendRedirect(LIST_ROUTE + "?"+ParameterNames.APPLICATION_ID+"=" + appIdString);
    }

    private void createOrUpdateBusinessCase(String bCaseId, BusinessCase bcase) {
        if (bCaseId == null || bCaseId.isEmpty()) {
            bcManagementService.createOrUpdate(bcase);
        } else {
            bcManagementService.createOrUpdate(bcase);
        }
    }

    private void updateBcaseProperties(HttpServletRequest req, String appIdString, BusinessCase bcase) {
        int appId = Integer.parseInt(appIdString);
        Application application = applicationsManagementService.findById(appId);
        String name = Utils.trimString(Utils.trimString(req.getParameter(ParameterNames.BUSINESS_CASE_NAME)));
        String description = Utils.trimString(Utils.trimString(req.getParameter(ParameterNames.BUSINESS_CASE_DESCRIPTION)));

        bcase.setApplication(application);
        bcase.setName(name);
        bcase.setDescription(description);
    }
}
