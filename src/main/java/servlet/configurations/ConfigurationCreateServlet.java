package servlet.configurations;

import model.Application;
import model.afclassification.Behavior;
import model.afclassification.ConfigurationPack;
import service.servlet.ApplicationsManagementService;
import service.servlet.ConfigurationManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import java.io.IOException;

import static servlet.configurations.ConfigurationListServlet.LIST_ROUTE;


/**
 * Servlet for creating or edition a {@link ConfigurationPack}
 */
public class ConfigurationCreateServlet extends HttpServlet {

    /**
     * The Create url.
     */
    static final String CREATE_URL = "/WEB-INF/pages/configurations/create.jsp";
    /**
     * The Create route.
     */
    static final String CREATE_ROUTE = "create";

    @Inject
    private ConfigurationManagementService configurationManagementService;

    @Inject
    private ApplicationsManagementService applicationsManagementService;

    @Context
    private ResourceInfo resourceInfo;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationId = request.getParameter(ParameterNames.APPLICATION_ID);
        String configurationId = request.getParameter(ParameterNames.CONFIGURATION_ID);
        if (applicationId == null || applicationId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        ConfigurationPack configuration;
        if (configurationId != null) {
            int configurationObjId = Integer.parseInt(configurationId);

            configuration = configurationManagementService.findConfigurationById(configurationObjId);
            request.setAttribute(ParameterNames.CONFIGURATION_ID, configuration.getId());
            request.setAttribute(ParameterNames.CONFIGURATION_NAME, configuration.getConfigurationName());
        } else {
            configuration = new ConfigurationPack(true);
        }
        request.setAttribute(ParameterNames.APPLICATION_ID, applicationId);
        request.setAttribute(ParameterNames.CONFIGURATION_LIST, configuration.getConfigurations());
        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String appIdString = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID));
        if (appIdString == null || appIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String configId = Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_ID));
        String configMapRecordsCount = Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_RECORDS_COUNT));

        ConfigurationPack configuration = configurationManagementService.findOrCreateNewConfiguration(configId);
        updateConfigurationProperties(req, appIdString, configuration, Integer.parseInt(configMapRecordsCount));
        createOrUpdateScreen(configId, configuration);
        resp.sendRedirect(LIST_ROUTE + "?" + ParameterNames.APPLICATION_ID + "=" + appIdString);
    }

    private void createOrUpdateScreen(String screenId, ConfigurationPack configuration) {
        if (screenId == null || screenId.isEmpty()) {
            configurationManagementService.createOrUpdate(configuration);
        } else {
            configurationManagementService.createOrUpdate(configuration);
        }
    }

    private void updateConfigurationProperties(
            HttpServletRequest req, String appIdString, ConfigurationPack configuration, int configPropertiesCount
    ) {
        int appId = Integer.parseInt(appIdString);
        Application application = applicationsManagementService.findById(appId);
        configuration.setApplication(application);
        String configurationName = Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_NAME));
        configuration.setConfigurationName(configurationName);
        for (int i = 0; i < configPropertiesCount; i++) {
            String behaviour = Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_BEHAVIOUR + i));
            String thresholdStart = Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_THRESHOLD_START + i));
            String thresholdEnd = Utils.trimString(req.getParameter(ParameterNames.CONFIGURATION_THRESHOLD_END + i));
            configuration.addConfiguration(Behavior.valueOf(behaviour), Double.parseDouble(thresholdStart), Double.parseDouble(thresholdEnd));
        }

    }


}
