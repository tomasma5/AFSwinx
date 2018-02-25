package servlet.screens;

import org.bson.types.ObjectId;
import service.servlet.ApplicationsManagementService;
import service.servlet.ScreenManagementService;
import servlet.ParameterNames;

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
        String applicationIdString = request.getParameter(ParameterNames.APPLICATION_ID);
        if(applicationIdString == null || applicationIdString.isEmpty()){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        ObjectId applicationId = new ObjectId(applicationIdString);
        request.setAttribute("screens", screenManagementService.getAllScreensByApplication(applicationId));
        request.setAttribute(ParameterNames.APPLICATION_NAME, applicationsManagementService.findById(applicationId).getApplicationName());
        request.setAttribute(ParameterNames.APPLICATION_ID, applicationId);
        getServletContext().getRequestDispatcher("/WEB-INF/pages/screens/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectId objectId = new ObjectId(req.getParameter(ParameterNames.SCREEN_ID));
        String appString = req.getParameter(ParameterNames.APPLICATION_ID);
        screenManagementService.removeScreen(objectId);
        resp.sendRedirect("list?app="+appString);
    }
}
