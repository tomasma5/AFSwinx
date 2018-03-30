package servlet.configurations;

import org.bson.types.ObjectId;
import service.servlet.ApplicationsManagementService;
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

/**
 * Servlet for displaying a list of {@link model.afclassification.ConfigurationPack}s
 */
public class ConfigurationListServlet extends HttpServlet {

    /**
     * The List url.
     */
    static final String LIST_URL = "/WEB-INF/pages/configurations/list.jsp";
    /**
     * The List route.
     */
    static final String LIST_ROUTE = "list";

    @Inject
    private ConfigurationManagementService configurationManagementService;

    @Inject
    private ApplicationsManagementService applicationsManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationIdString = request.getParameter(ParameterNames.APPLICATION_ID);
        if (applicationIdString == null || applicationIdString.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ObjectId applicationId = new ObjectId(applicationIdString);
        request.setAttribute("configurations", configurationManagementService.getAllConfigurationsByApplication(applicationId));
        request.setAttribute(ParameterNames.APPLICATION_NAME, applicationsManagementService.findById(applicationId).getApplicationName());
        request.setAttribute(ParameterNames.APPLICATION_ID, applicationId);
        getServletContext().getRequestDispatcher(LIST_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectId objectId = new ObjectId(Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_ID)));
        String appString = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID));
        configurationManagementService.removeConfigurationById(objectId);
        resp.sendRedirect(LIST_ROUTE + "?" + ParameterNames.APPLICATION_ID + "=" + appString);
    }
}
