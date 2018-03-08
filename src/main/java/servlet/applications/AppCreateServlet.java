package servlet.applications;

import model.Application;
import model.ComponentConnection;
import model.ComponentConnectionPack;
import model.ComponentResource;
import org.bson.types.ObjectId;
import service.rest.ComponentResourceService;
import service.servlet.ApplicationsManagementService;
import service.servlet.ComponentManagementService;
import servlet.ParameterNames;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static servlet.applications.AppListServlet.LIST_ROUTE;

public class AppCreateServlet extends HttpServlet {

    static final String CREATE_URL = "/WEB-INF/pages/apps/create.jsp";
    static final String CREATE_ROUTE = "create";

    @Inject
    private ApplicationsManagementService applicationsManagementService;

    @Inject
    private ComponentManagementService componentManagementService;

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
        String applicationId = req.getParameter(ParameterNames.APPLICATION_ID);
        String applicationName = req.getParameter(ParameterNames.APPLICATION_NAME);
        String remoteUrl = req.getParameter(ParameterNames.APPLICATION_REMOTE_URL);
        String port = req.getParameter(ParameterNames.APPLICATION_REMOTE_PORT);
        try {
            new URL(remoteUrl); //just to check format of url with trying to create URL object
            Application application = getApplication(applicationId);
            updateApplicationProperties(applicationName, remoteUrl, port, application);
            createOrUpdateApplication(applicationId, application);
            resp.sendRedirect(LIST_ROUTE);
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

    private void createOrUpdateApplication(String applicationId, Application application) {
        if (applicationId == null || applicationId.isEmpty()) {
            applicationsManagementService.addNewApplication(application);
        } else {
            updateComponentConnections(application);
            applicationsManagementService.updateApplication(application);
        }
    }

    private void updateApplicationProperties(String applicationName, String remoteUrl, String port, Application application) {
        application.setApplicationName(applicationName);
        application.setRemoteUrl(remoteUrl);
        application.setRemotePort(Integer.parseInt(port));
    }

    private Application getApplication(String applicationId) {
        Application application;
        if (applicationId == null || applicationId.isEmpty()) {
            application = new Application();
            application.setId(new ObjectId());
        } else {
            application = applicationsManagementService.findById(new ObjectId(applicationId));
        }
        return application;
    }

    private void updateComponentConnections(Application application) {
        for(ComponentResource componentResource : componentManagementService.getAllComponentsByApplication(application.getId())){
            ComponentConnectionPack realConnectionsPack = componentResource.getProxyConnections();
            updateConnectionParameters(application, realConnectionsPack.getModelConnection());
            updateConnectionParameters(application, realConnectionsPack.getModelConnection());
            updateConnectionParameters(application, realConnectionsPack.getModelConnection());
            componentManagementService.updateComponent(componentResource);
        }
    }

    private void updateConnectionParameters(Application application, ComponentConnection connection) {
        if(connection != null){
            //TODO rozdelit i v aplikace na protocol, adresu a port at nedelam takove veci
            connection.setRealProtocol(application.getRemoteUrl().substring(0, application.getRemoteUrl().indexOf(":")));
            connection.setRealAddress(application.getRemoteUrl().substring(application.getRemoteUrl().indexOf("://") + 3));
            connection.setRealPort(application.getRemotePort());
        }
    }
}
