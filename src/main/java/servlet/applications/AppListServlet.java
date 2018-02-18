package servlet.applications;

import org.bson.types.ObjectId;
import service.ApplicationsManagementService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AppListServlet extends HttpServlet {

    @Inject
    private ApplicationsManagementService applicationsManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("applications", applicationsManagementService.getAll());
        getServletContext().getRequestDispatcher("/WEB-INF/pages/apps/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectId objectId = new ObjectId(req.getParameter("applicationId"));
        applicationsManagementService.removeApplication(objectId);
        resp.sendRedirect("list");
    }
}
