package servlet.businesscases;

import org.bson.types.ObjectId;
import service.servlet.ApplicationsManagementService;
import servlet.ParameterNames;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BusinessCaseListServlet extends HttpServlet {

    static final String LIST_URL = "/WEB-INF/pages/apps/list.jsp";
    static final String LIST_ROUTE = "list";

    @Inject
    private ApplicationsManagementService applicationsManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("applications", applicationsManagementService.getAll());
        getServletContext().getRequestDispatcher(LIST_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectId objectId = new ObjectId(req.getParameter(ParameterNames.APPLICATION_ID));
        applicationsManagementService.removeApplication(objectId);
        resp.sendRedirect(LIST_ROUTE);
    }
}
