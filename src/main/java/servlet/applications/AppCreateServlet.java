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
import utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static servlet.applications.AppListServlet.LIST_ROUTE;

/**
 * Servlet for creating or editing {@link Application}
 */
public class AppCreateServlet extends HttpServlet {

    /**
     * The Create url.
     */
    static final String CREATE_URL = "/WEB-INF/pages/apps/create.jsp";
    /**
     * The Create route.
     */
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
            request.setAttribute(ParameterNames.APPLICATION_CONSUMER_PROTOCOL, application.getConsumerProtocol());
            request.setAttribute(ParameterNames.APPLICATION_CONSUMER_HOSTNAME, application.getConsumerHostname());
            request.setAttribute(ParameterNames.APPLICATION_CONSUMER_PORT, application.getConsumerPort());
            request.setAttribute(ParameterNames.APPLICATION_CONSUMER_CONTEXT_PATH, application.getConsumerContextPath());
        }
        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String applicationId = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID));
        String applicationName = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_NAME));
        String remoteProtocol = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_REMOTE_PROTOCOL));
        String remoteHostname = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_REMOTE_HOSTNAME));
        String port = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_REMOTE_PORT));
        String proxyProtocol = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_PROXY_PROTOCOL));
        String proxyHostname = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_PROXY_HOSTNAME));
        String proxyPort = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_PROXY_PORT));
        String consumerProtocol = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_CONSUMER_PROTOCOL));
        String consumerHostname = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_CONSUMER_HOSTNAME));
        String consumerPort = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_CONSUMER_PORT));
        String consumerContextPath = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_CONSUMER_CONTEXT_PATH));
        try {
            Application application = applicationsManagementService.findOrCreateApplication(applicationId);
            updateApplicationProperties(application, applicationName);
            updateApplicationRemoteConnections(application, remoteProtocol, remoteHostname, port);
            updateApplicationProxyConnections(req, application, proxyProtocol, proxyHostname, proxyPort);
            updateApplicationConsumerConnections(application, consumerProtocol, consumerHostname, consumerPort, consumerContextPath);
            createOrUpdateApplication(req, applicationId, application);
            resp.sendRedirect(LIST_ROUTE);
            return;
        } catch (MalformedURLException ex) {
            req.setAttribute("urlError", "Remote or proxy URL has bad format.");
        } catch (NumberFormatException ex) {
            req.setAttribute("portError", "Port must be a number.");
        }

        req.setAttribute(ParameterNames.APPLICATION_ID, applicationId);
        req.setAttribute(ParameterNames.APPLICATION_NAME, applicationName);
        req.setAttribute(ParameterNames.APPLICATION_REMOTE_PROTOCOL, remoteProtocol);
        req.setAttribute(ParameterNames.APPLICATION_REMOTE_HOSTNAME, remoteHostname);
        req.setAttribute(ParameterNames.APPLICATION_REMOTE_PORT, port);
        req.setAttribute(ParameterNames.APPLICATION_PROXY_PROTOCOL, proxyProtocol);
        req.setAttribute(ParameterNames.APPLICATION_PROXY_HOSTNAME, proxyHostname);
        req.setAttribute(ParameterNames.APPLICATION_PROXY_PORT, proxyPort);
        req.setAttribute(ParameterNames.APPLICATION_CONSUMER_PROTOCOL, consumerProtocol);
        req.setAttribute(ParameterNames.APPLICATION_CONSUMER_HOSTNAME, consumerHostname);
        req.setAttribute(ParameterNames.APPLICATION_CONSUMER_PORT, consumerPort);
        req.setAttribute(ParameterNames.APPLICATION_CONSUMER_CONTEXT_PATH, consumerContextPath);
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


    private void updateApplicationProperties(Application application, String applicationName) {
        application.setApplicationName(Utils.trimString(applicationName));
    }

    private void updateApplicationRemoteConnections(Application application,
                                                    String remoteProtocol, String remoteHostname, String port) throws MalformedURLException {
        String remoteUrl =
                HttpUtils.buildUrl(remoteProtocol, remoteHostname, port, null, null);
        new URL(remoteUrl); //just to check format of url with trying to create URL object
        application.setRemoteHostname(Utils.trimString(remoteHostname));
        application.setRemoteProtocol(Utils.trimString(remoteProtocol));
        application.setRemotePort(port != null ? Integer.parseInt(Utils.trimString(port)) : 0);
    }

    private void updateApplicationProxyConnections(HttpServletRequest request, Application application, String proxyProtocol,
                                                   String proxyHostname, String proxyPort) throws MalformedURLException {
        if (proxyProtocol == null || proxyProtocol.isEmpty()) {
            proxyProtocol = request.getScheme();
        }
        if (proxyHostname == null || proxyHostname.isEmpty()) {
            proxyHostname = request.getServerName();
        }
        if (proxyPort == null || proxyPort.isEmpty()) {
            proxyPort = String.valueOf(request.getServerPort());
        }
        String proxyUrl = HttpUtils.buildUrl(proxyProtocol, proxyHostname, proxyPort, null, null);
        new URL(proxyUrl); //just to check format of url with trying to create URL object
        application.setProxyHostname(Utils.trimString(proxyProtocol));
        application.setProxyHostname(Utils.trimString(proxyHostname));
        application.setProxyPort(Integer.parseInt(Utils.trimString(proxyPort)));
    }

    private void updateApplicationConsumerConnections(Application application, String consumerProtocol,
                                                      String consumerHostname, String consumerPort, String consumerContextPath) throws MalformedURLException {
        String consumerUrl = HttpUtils.buildUrl(consumerProtocol, consumerHostname, consumerPort, consumerContextPath, null);
        new URL(consumerUrl);
        application.setConsumerProtocol(consumerProtocol);
        application.setConsumerHostname(consumerHostname);
        application.setConsumerPort(consumerPort != null ? Integer.parseInt(Utils.trimString(consumerPort)) : 0);
        if (!consumerContextPath.startsWith("/")) {
            consumerContextPath = "/" + consumerContextPath;
        }
        application.setConsumerContextPath(consumerContextPath);

    }


}
