package servlet.applications;

import model.*;
import org.bson.types.ObjectId;
import service.exception.ServiceException;
import service.rest.ComponentResourceService;
import service.servlet.ApplicationsManagementService;
import service.servlet.ComponentManagementService;
import service.servlet.ScreenManagementService;
import servlet.ParameterNames;
import utils.HttpUtils;

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

    @Inject
    private ScreenManagementService screenManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter(ParameterNames.APPLICATION_ID);
        if (id != null) {
            Application application = applicationsManagementService.findById(new ObjectId(id));
            request.setAttribute(ParameterNames.APPLICATION_ID, application.getId());
            request.setAttribute(ParameterNames.APPLICATION_NAME, application.getApplicationName());
            request.setAttribute(ParameterNames.APPLICATION_REMOTE_PROTOCOL, application.getRemoteProtocol());
            request.setAttribute(ParameterNames.APPLICATION_REMOTE_HOSTNAME, application.getRemoteHostname());
            request.setAttribute(ParameterNames.APPLICATION_REMOTE_PORT, application.getRemotePort());
            request.setAttribute(ParameterNames.APPLICATION_PROXY_PROTOCOL, application.getProxyProtocol());
            request.setAttribute(ParameterNames.APPLICATION_PROXY_HOSTNAME, application.getProxyHostname());
            request.setAttribute(ParameterNames.APPLICATION_PROXY_PORT, application.getProxyPort());

        }
        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String applicationId = req.getParameter(ParameterNames.APPLICATION_ID);
        String applicationName = req.getParameter(ParameterNames.APPLICATION_NAME);
        String remoteProtocol = req.getParameter(ParameterNames.APPLICATION_REMOTE_PROTOCOL);
        String remoteHostname = req.getParameter(ParameterNames.APPLICATION_REMOTE_HOSTNAME);
        String port = req.getParameter(ParameterNames.APPLICATION_REMOTE_PORT);
        String proxyProtocol = req.getParameter(ParameterNames.APPLICATION_PROXY_PROTOCOL);
        String proxyHostname = req.getParameter(ParameterNames.APPLICATION_PROXY_HOSTNAME);
        String proxyPort = req.getParameter(ParameterNames.APPLICATION_PROXY_PORT);
        try {
            Application application = applicationsManagementService.findOrCreateApplication(applicationId);
            updateApplicationProperties(req, applicationName, application, remoteProtocol, remoteHostname, port,
                    proxyProtocol, proxyHostname, proxyPort);
            createOrUpdateApplication(req, applicationId, application);
            resp.sendRedirect(LIST_ROUTE);
            return;
        } catch (MalformedURLException ex) {
            req.setAttribute("urlError", "Remote or proxy URL has bad format.");
        } catch (NumberFormatException ex) {
            req.setAttribute("portError", "Port must be a number.");
        }

        req.setAttribute(ParameterNames.APPLICATION_NAME, applicationName);
        req.setAttribute(ParameterNames.APPLICATION_REMOTE_PROTOCOL, remoteProtocol);
        req.setAttribute(ParameterNames.APPLICATION_REMOTE_HOSTNAME, remoteHostname);
        req.setAttribute(ParameterNames.APPLICATION_REMOTE_PORT, port);
        req.setAttribute(ParameterNames.APPLICATION_PROXY_PROTOCOL, proxyProtocol);
        req.setAttribute(ParameterNames.APPLICATION_PROXY_HOSTNAME, proxyHostname);
        req.setAttribute(ParameterNames.APPLICATION_PROXY_PORT, proxyPort);
        req.getRequestDispatcher(CREATE_URL).forward(req, resp);

    }

    private void createOrUpdateApplication(HttpServletRequest req, String applicationId, Application application) {
        if (applicationId == null || applicationId.isEmpty()) {
            applicationsManagementService.addNewApplication(application);
        } else {
            componentManagementService.updateComponentConnections(application);
            screenManagementService.updateScreenConnections(application, req.getContextPath());
            applicationsManagementService.updateApplication(application);
        }
    }

    private void updateApplicationProperties(HttpServletRequest req, String applicationName, Application application,
                                             String remoteProtocol, String remoteHostname, String port,
                                             String proxyProtocol, String proxyHostname, String proxyPort) throws MalformedURLException {
        String remoteUrl =
                HttpUtils.buildUrl(remoteProtocol, remoteHostname, port, null, null);
        new URL(remoteUrl); //just to check format of url with trying to create URL object
        application.setApplicationName(applicationName);
        application.setRemoteHostname(remoteHostname);
        application.setRemoteProtocol(remoteProtocol);
        application.setRemotePort(port != null ? Integer.parseInt(port) : 0);

        if (proxyProtocol == null || proxyProtocol.isEmpty()) {
            proxyProtocol = req.getScheme();
        }
        if (proxyHostname == null || proxyHostname.isEmpty()) {
            proxyHostname = req.getServerName();
        }
        if (proxyPort == null || proxyPort.isEmpty()) {
            proxyPort = String.valueOf(req.getServerPort());
        }
        String proxyUrl = HttpUtils.buildUrl(proxyProtocol, proxyHostname, proxyPort, null, null);
        new URL(proxyUrl); //just to check format of url with trying to create URL object
        application.setProxyHostname(proxyProtocol);
        application.setProxyHostname(proxyHostname);
        application.setProxyPort(Integer.parseInt(proxyPort));
    }


}
