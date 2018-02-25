package servlet.applications;

import model.Application;
import org.bson.types.ObjectId;
import service.servlet.ApplicationsManagementService;
import servlet.ParameterNames;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class AppCreateServlet extends HttpServlet {

    private static final String CREATE_URL = "/WEB-INF/pages/apps/create.jsp";



    @Inject
    private ApplicationsManagementService applicationsManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter(ParameterNames.APPLICATION_ID);
        if (id != null) {
            Application application = applicationsManagementService.findById(new ObjectId(id));
            request.setAttribute(ParameterNames.APPLICATION_ID, application.getId());
            request.setAttribute(ParameterNames.APPLICATION_NAME, application.getApplicationName());
            request.setAttribute(ParameterNames.APPLICATION_REMOTE_URL, application.getRemoteUrl());
            request.setAttribute(ParameterNames.APPLICATION_REMOTE_PORT, application.getRemotePort());
        }
        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO refactor???
        String applicationId = req.getParameter(ParameterNames.APPLICATION_ID);
        String applicationName = req.getParameter(ParameterNames.APPLICATION_NAME);
        String remoteUrl = req.getParameter(ParameterNames.APPLICATION_REMOTE_URL);
        String port = req.getParameter(ParameterNames.APPLICATION_REMOTE_PORT);
        try {
            new URL(remoteUrl); //check format of url with trying to create URL object

            Application application;
            if (applicationId == null || applicationId.isEmpty()) {
                application = new Application();
            } else {
                application = applicationsManagementService.findById(new ObjectId(applicationId));
            }

            application.setApplicationName(applicationName);
            application.setRemoteUrl(remoteUrl);
            application.setRemotePort(Integer.parseInt(port));

            if (applicationId == null || applicationId.isEmpty()) {
                applicationsManagementService.addNewApplication(application);
            } else {
                applicationsManagementService.updateApplication(application);
            }
            resp.sendRedirect("list");
            return;
        } catch (MalformedURLException ex) {
            req.setAttribute("remoteUrlError", "URL has bad format.");
        } catch (NumberFormatException ex) {
            req.setAttribute("remotePortError", "Port must be a number.");
        }

        req.setAttribute(ParameterNames.APPLICATION_NAME, applicationName);
        req.setAttribute(ParameterNames.APPLICATION_REMOTE_URL, remoteUrl);
        req.setAttribute(ParameterNames.APPLICATION_REMOTE_PORT, port);
        req.getRequestDispatcher(CREATE_URL).forward(req, resp);

    }
}
