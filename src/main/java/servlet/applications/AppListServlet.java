package servlet.applications;

import model.Application;
import service.servlet.ApplicationsManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for listing all {@link model.Application}s
 */
public class AppListServlet extends HttpServlet {

    /**
     * The List url.
     */
    static final String LIST_URL = "/WEB-INF/pages/apps/list.jsp";
    /**
     * The List route.
     */
    static final String LIST_ROUTE = "list";

    @Inject
    private ApplicationsManagementService applicationsManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("applications", applicationsManagementService.getAll());
        getServletContext().getRequestDispatcher(LIST_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID)));
        applicationsManagementService.removeApplication(id);
        resp.sendRedirect(LIST_ROUTE);
    }
}
