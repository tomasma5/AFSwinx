package servlet.screens;

import org.bson.types.ObjectId;
import service.ApplicationsManagementService;
import service.ScreenManagementService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ScreenListServlet extends HttpServlet {

    @Inject
    private ScreenManagementService screenManagementService;

    @Inject
    private ApplicationsManagementService applicationsManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationIdString = request.getParameter("app");
        if(applicationIdString == null || applicationIdString.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        ObjectId applicationId = new ObjectId(applicationIdString);
        request.setAttribute("screens", screenManagementService.getAllScreensByApplication(applicationId));
        request.setAttribute("applicationName", applicationsManagementService.findById(applicationId).getApplicationName());
        request.setAttribute("applicationId", applicationId);
        getServletContext().getRequestDispatcher("/WEB-INF/pages/screens/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectId objectId = new ObjectId(req.getParameter("screen"));
        screenManagementService.removeScreen(objectId);
        resp.sendRedirect("list");
    }
}
