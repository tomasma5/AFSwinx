package servlet.components;

import model.Application;
import model.ComponentConnection;
import model.ComponentResource;
import model.SupportedComponentType;
import service.servlet.ApplicationsManagementService;
import service.servlet.ComponentManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static servlet.components.ComponentListServlet.LIST_ROUTE;

/**
 * Servlet for creating or editing {@link ComponentResource}
 */
public class ComponentCreateServlet extends HttpServlet {

    /**
     * The Create url.
     */
    static final String CREATE_URL = "/WEB-INF/pages/components/create.jsp";
    /**
     * The Create route.
     */
    static final String CREATE_ROUTE = "create";

    @Inject
    private ComponentManagementService componentManagementService;

    @Inject
    private ApplicationsManagementService applicationsManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationId = request.getParameter(ParameterNames.APPLICATION_ID);
        String componentId = request.getParameter(ParameterNames.COMPONENT_ID);
        if (applicationId == null || applicationId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (componentId != null) {
            setExistingComponentToRequest(request, componentId);
        } else {
            request.setAttribute(ParameterNames.MODEL + ParameterNames.CONNECTION_ACTIVE, 1);
            request.setAttribute(ParameterNames.DATA + ParameterNames.CONNECTION_ACTIVE, 1);
            request.setAttribute(ParameterNames.SEND + ParameterNames.CONNECTION_ACTIVE, 1);
        }
        List<String> options = new ArrayList<>();
        for (SupportedComponentType type : SupportedComponentType.class.getEnumConstants()) {
            options.add(type.getName());
        }
        request.setAttribute("componentTypeOptions", options);
        request.setAttribute(ParameterNames.APPLICATION_ID, applicationId);
        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String appIdString = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID));
        if (appIdString == null || appIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Application application = applicationsManagementService.findById(Integer.parseInt(appIdString));

        String componentId = Utils.trimString(req.getParameter(ParameterNames.COMPONENT_ID));
        ComponentResource componentResource = componentManagementService.findOrCreateComponentResource(req, componentId);
        //set attributes to component resource
        updateComponentProperties(req, componentResource, application);
        //set connection data
        componentManagementService.createOrUpdate(componentResource);
        componentManagementService.updateComponentConnections(req, application, componentResource);
        resp.sendRedirect(LIST_ROUTE + "?" + ParameterNames.APPLICATION_ID + "=" + appIdString);
    }

    private void updateComponentProperties(HttpServletRequest req, ComponentResource componentResource, Application application) {
        String componentName = Utils.trimString(req.getParameter(ParameterNames.COMPONENT_NAME));
        SupportedComponentType componentType = SupportedComponentType.valueOf(Utils.trimString(req.getParameter(ParameterNames.COMPONENT_TYPE)));
        String fieldInfoUrlParameters = Utils.trimString(req.getParameter(ParameterNames.COMPONENT_FIELD_INFO_URL_PARAMETERS));
        componentResource.setName(componentName);
        componentResource.setType(componentType);
        //set field info url
        componentResource.setFieldInfoUrlProtocol(application.getRemoteProtocol() != null ? application.getRemoteProtocol() : "http");
        componentResource.setFieldInfoUrlHostname(application.getRemoteHostname());
        componentResource.setFieldInfoUrlPort(application.getRemotePort());
        componentResource.setFieldInfoUrlParameters(fieldInfoUrlParameters);

        componentResource.setApplication(application);

    }

    private void setComponentInputToRequest(HttpServletRequest req, ComponentResource component) {
        req.setAttribute(ParameterNames.COMPONENT_ID, component.getId());
        req.setAttribute(ParameterNames.COMPONENT_NAME, component.getName());
        req.setAttribute(ParameterNames.COMPONENT_TYPE, component.getType().getName());
        req.setAttribute(ParameterNames.COMPONENT_FIELD_INFO_URL_PARAMETERS, component.getFieldInfoUrlParameters());
    }

    private void setConnectionInputToRequest(HttpServletRequest req, String type, ComponentConnection connection) {
        if (connection != null) {
            req.setAttribute(type + ParameterNames.CONNECTION_ACTIVE, 1);
            req.setAttribute(type + ParameterNames.CONNECTION + ParameterNames.PARAMETERS, connection.getRealParameters());
            if (connection.getHeaderParams() != null) {
                req.setAttribute(type + ParameterNames.CONNECTION + ParameterNames.HEADER_PARAMS, connection.getHeaderParams());
            }
            if (connection.getSecurityParams() != null) {
                req.setAttribute(type + ParameterNames.CONNECTION + ParameterNames.SECURITY_PARAMS, connection.getSecurityParams());
            }
        } else {
            req.setAttribute(type + ParameterNames.CONNECTION_ACTIVE, 0);
        }
    }

    private void setExistingComponentToRequest(HttpServletRequest request, String componentId) {
        ComponentResource component = componentManagementService.findById(Integer.parseInt(componentId));
        setComponentInputToRequest(request, component);
        setConnectionInputToRequest(request, ParameterNames.MODEL, component.getProxyConnections().getModelConnection());
        setConnectionInputToRequest(request, ParameterNames.DATA, component.getProxyConnections().getDataConnection());
        setConnectionInputToRequest(request, ParameterNames.SEND, component.getProxyConnections().getSendConnection());
    }


}
