package servlet.components;

import model.*;
import org.bson.types.ObjectId;
import service.servlet.ApplicationsManagementService;
import service.servlet.ComponentManagementService;
import servlet.ParameterNames;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static servlet.components.ComponentListServlet.LIST_ROUTE;

public class ComponentCreateServlet extends HttpServlet {

    static final String CREATE_URL = "/WEB-INF/pages/components/create.jsp";
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
        String appIdString = req.getParameter(ParameterNames.APPLICATION_ID);
        if (appIdString == null || appIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Application application = applicationsManagementService.findById(new ObjectId(appIdString));

        ComponentResource componentResource = getComponentResource(req);
        //set attributes to component resource
        updateComponentProperties(req, componentResource);
        //set connection data
        updateComponentConnections(req, application, componentResource);

        createOrUpdateComponent(req, componentResource);
        resp.sendRedirect(LIST_ROUTE+"?app=" + appIdString);
    }

    //component set & update methods
    private void createOrUpdateComponent(HttpServletRequest req, ComponentResource componentResource) {
        String componentId = req.getParameter(ParameterNames.COMPONENT_ID);
        if (componentId == null || componentId.isEmpty()) {
            componentManagementService.addComponent(componentResource);
        } else {
            componentManagementService.updateComponent(componentResource);
        }
    }

    private void updateComponentProperties(HttpServletRequest req, ComponentResource componentResource) {
        String componentName = req.getParameter(ParameterNames.COMPONENT_NAME);
        SupportedComponentType componentType = SupportedComponentType.valueOf(req.getParameter(ParameterNames.COMPONENT_TYPE));
        componentResource.setName(componentName);
        componentResource.setType(componentType);
        componentResource.setApplicationId(new ObjectId(req.getParameter(ParameterNames.APPLICATION_ID)));
    }

    private void updateComponentConnections(HttpServletRequest req, Application application, ComponentResource componentResource) {
        if (isConnectionActive(req, ParameterNames.MODEL)) {
            updateConnectionAttributes(req, ParameterNames.MODEL, application, componentResource.getId(),
                    componentResource.getProxyConnections().getModelConnection());
        } else {
            componentResource.getProxyConnections().setModelConnection(null);
        }
        if (isConnectionActive(req, ParameterNames.DATA)) {
            updateConnectionAttributes(req, ParameterNames.DATA, application, componentResource.getId(),
                    componentResource.getProxyConnections().getDataConnection());
        } else {
            componentResource.getProxyConnections().setDataConnection(null);
        }
        if (isConnectionActive(req, ParameterNames.SEND)) {
            updateConnectionAttributes(req, ParameterNames.SEND, application, componentResource.getId(),
                    componentResource.getProxyConnections().getSendConnection());
        } else {
            componentResource.getProxyConnections().setSendConnection(null);
        }
    }

    private void updateConnectionAttributes(HttpServletRequest req, String type, Application application, ObjectId componentResource, ComponentConnection proxyConnection) {
        String parameters = req.getParameter(type + ParameterNames.CONNECTION + ParameterNames.PARAMETERS);
        if (proxyConnection != null && (parameters != null && !parameters.isEmpty()) && (application != null)) {
            int headerParamsCount = Integer.parseInt(req.getParameter(type + ParameterNames.HEADER_PARAMS_COUNT));
            int securityParamsCount = Integer.parseInt(req.getParameter(type + ParameterNames.SECURITY_PARAMS_COUNT));
            String protocol = application.getRemoteUrl().substring(0, application.getRemoteUrl().indexOf(":"));
            String address = application.getRemoteUrl().substring(application.getRemoteUrl().indexOf("://") + 3);
            proxyConnection.setRealProtocol(protocol);
            proxyConnection.setRealAddress(address);
            proxyConnection.setRealPort(application.getRemotePort());
            proxyConnection.setRealParameters(parameters);
            proxyConnection.setHeaderParams(getParams(req, type, ParameterNames.HEADER_PARAM, headerParamsCount));
            proxyConnection.setSecurityParams(getParams(req, type, ParameterNames.SECURITY_PARAM, securityParamsCount));
            setProxyUrl(req, type, componentResource, proxyConnection);
        }
    }

    private void resetConnectionsInExistingComponent(HttpServletRequest req, ComponentResource componentResource) {
        if (isConnectionActive(req, ParameterNames.MODEL) &&
                componentResource.getProxyConnections().getModelConnection() == null) {
            componentResource.getProxyConnections().setModelConnection(new ComponentConnection());
        }

        if (isConnectionActive(req, ParameterNames.DATA) &&
                componentResource.getProxyConnections().getDataConnection() == null) {
            componentResource.getProxyConnections().setModelConnection(new ComponentConnection());
        }

        if (isConnectionActive(req, ParameterNames.SEND) &&
                componentResource.getProxyConnections().getSendConnection() == null) {
            componentResource.getProxyConnections().setModelConnection(new ComponentConnection());
        }
    }

    private void setComponentInputToRequest(HttpServletRequest req, String componentId, String componentName, SupportedComponentType componentType) {
        req.setAttribute(ParameterNames.COMPONENT_ID, componentId);
        req.setAttribute(ParameterNames.COMPONENT_NAME, componentName);
        req.setAttribute(ParameterNames.COMPONENT_TYPE, componentType.getName());
    }

    private void setConnectionInputToRequest(HttpServletRequest req, String type, ComponentConnection connection) {
        if (connection != null) {
            req.setAttribute(type + ParameterNames.CONNECTION_ACTIVE, 1);
            req.setAttribute(type + ParameterNames.CONNECTION + ParameterNames.PARAMETERS, connection.getParameters());
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
        ComponentResource component = componentManagementService.findById(new ObjectId(componentId));
        setComponentInputToRequest(request, component.getId().toString(), component.getName(), component.getType());
        setConnectionInputToRequest(request, ParameterNames.MODEL, component.getProxyConnections().getModelConnection())
        ;
        setConnectionInputToRequest(request, ParameterNames.DATA, component.getProxyConnections().getDataConnection());
        setConnectionInputToRequest(request, ParameterNames.SEND, component.getProxyConnections().getSendConnection());
    }

    //helper methods
    private ComponentResource getComponentResource(HttpServletRequest req) {
        String componentId = req.getParameter(ParameterNames.COMPONENT_ID);
        ComponentResource componentResource;
        if (componentId == null || componentId.isEmpty()) {
            componentResource = new ComponentResource();
            componentResource.setId(new ObjectId());
            componentResource.setProxyConnections(createNewConnectionPack(req));
        } else {
            componentResource = componentManagementService.findById(new ObjectId(componentId));
            resetConnectionsInExistingComponent(req, componentResource);
        }
        return componentResource;
    }

    private ComponentConnectionPack createNewConnectionPack(HttpServletRequest req) {
        ComponentConnectionPack connectionPack = new ComponentConnectionPack();
        if (isConnectionActive(req, ParameterNames.MODEL)) {
            connectionPack.setModelConnection(new ComponentConnection());
        }
        if (isConnectionActive(req, ParameterNames.DATA)) {
            connectionPack.setDataConnection(new ComponentConnection());
        }
        if (isConnectionActive(req, ParameterNames.SEND)) {
            connectionPack.setSendConnection(new ComponentConnection());
        }

        return connectionPack;
    }

    private boolean isConnectionActive(HttpServletRequest req, String type) {
        String connectionActiveString = req.getParameter(type + ParameterNames.CONNECTION_ACTIVE);
        return connectionActiveString != null && Integer.parseInt(connectionActiveString) == 1;
    }

    private void setProxyUrl(HttpServletRequest req, String type, ObjectId componentResource, ComponentConnection proxyConnection) {
        //generate proxy url
        proxyConnection.setProtocol(req.getScheme());
        proxyConnection.setAddress(req.getServerName());
        proxyConnection.setPort(req.getServerPort());
        proxyConnection.setParameters(req.getContextPath() + "/api/connections/" + type + "/component/" + componentResource);
        proxyConnection.setHeaderParams(proxyConnection.getHeaderParams());
        proxyConnection.setSecurityParams(proxyConnection.getSecurityParams());
    }

    private Map<String, String> getParams(HttpServletRequest req, String type, String paramType, int paramsCount) {
        Map<String, String> params = null;
        if (paramsCount > 0) {
            params = new HashMap<>();
            for (int i = 1; i <= paramsCount; i++) {
                String key = req.getParameter(type + paramType + ParameterNames.KEY + i);
                String value = req.getParameter(type + paramType + ParameterNames.VALUE + i);
                params.put(key, value);
            }
        }
        return params;
    }
}
