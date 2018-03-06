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

public class ComponentCreateServlet extends HttpServlet {


    private static final String CREATE_URL = "/WEB-INF/pages/components/create.jsp";

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
        Application application = applicationsManagementService.findById(new ObjectId(applicationId));
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
        //TODO maybe refactor
        String appIdString = req.getParameter(ParameterNames.APPLICATION_ID);
        if (appIdString == null || appIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Application application = applicationsManagementService.findById(new ObjectId(appIdString));
        //get base component information
        String componentId = req.getParameter(ParameterNames.COMPONENT_ID);
        String componentName = req.getParameter(ParameterNames.COMPONENT_NAME);
        SupportedComponentType componentType = SupportedComponentType.valueOf(req.getParameter(ParameterNames.COMPONENT_TYPE));

        //determine if connection is activated
        String modelConnectionActiveString = req.getParameter(ParameterNames.MODEL + ParameterNames.CONNECTION_ACTIVE);
        String dataConnectionActiveString = req.getParameter(ParameterNames.DATA + ParameterNames.CONNECTION_ACTIVE);
        String sendConnectionActiveString = req.getParameter(ParameterNames.SEND + ParameterNames.CONNECTION_ACTIVE);
        boolean modelConnectionActive = modelConnectionActiveString != null && Integer.parseInt(modelConnectionActiveString) == 1;
        boolean dataConnectionActive = dataConnectionActiveString != null && Integer.parseInt(dataConnectionActiveString) == 1;
        boolean sendConnectionActive = sendConnectionActiveString != null && Integer.parseInt(sendConnectionActiveString) == 1;

        //get connection info from request
        String modelConnectionParameters = null;
        String dataConnectionParameters = null;
        String sendConnectionParameters = null;
        int modelHeaderParamsCount = 0, modelSecurityParamsCount = 0;
        int dataHeaderParamsCount = 0, dataSecurityParamsCount = 0;
        int sendHeaderParamsCount = 0, sendSecurityParamsCount = 0;

        if (modelConnectionActive) {
            modelConnectionParameters = req.getParameter(ParameterNames.MODEL + ParameterNames.CONNECTION + ParameterNames.PARAMETERS);
            modelHeaderParamsCount = Integer.parseInt(req.getParameter(ParameterNames.MODEL + ParameterNames.HEADER_PARAMS_COUNT));
            modelSecurityParamsCount = Integer.parseInt(req.getParameter(ParameterNames.MODEL + ParameterNames.SECURITY_PARAMS_COUNT));
        }

        if (dataConnectionActive) {
            dataConnectionParameters = req.getParameter(ParameterNames.DATA + ParameterNames.CONNECTION + ParameterNames.PARAMETERS);
            dataHeaderParamsCount = Integer.parseInt(req.getParameter(ParameterNames.DATA + ParameterNames.HEADER_PARAMS_COUNT));
            dataSecurityParamsCount = Integer.parseInt(req.getParameter(ParameterNames.DATA + ParameterNames.SECURITY_PARAMS_COUNT));
        }
        if (sendConnectionActive) {
            sendConnectionParameters = req.getParameter(ParameterNames.SEND + ParameterNames.CONNECTION + ParameterNames.PARAMETERS);
            sendHeaderParamsCount = Integer.parseInt(req.getParameter(ParameterNames.SEND + ParameterNames.HEADER_PARAMS_COUNT));
            sendSecurityParamsCount = Integer.parseInt(req.getParameter(ParameterNames.SEND + ParameterNames.SECURITY_PARAMS_COUNT));
        }


        ComponentResource componentResource = getComponentResource(componentId, modelConnectionActive, dataConnectionActive, sendConnectionActive);

        //set attributes to component resource
        componentResource.setName(componentName);
        componentResource.setType(componentType);
        componentResource.setApplicationId(new ObjectId(appIdString));

        //set connection data
        if (modelConnectionActive) {
            setConnectionAttributes(req, ParameterNames.MODEL, application,
                    modelConnectionParameters, modelHeaderParamsCount, modelSecurityParamsCount,
                    componentResource.getId(),
                    componentResource.getRealConnections().getModelConnection(),
                    componentResource.getProxyConnections().getModelConnection());
        } else {
            componentResource.getRealConnections().setModelConnection(null);
            componentResource.getProxyConnections().setModelConnection(null);
        }
        if (dataConnectionActive) {
            setConnectionAttributes(req, ParameterNames.DATA, application, dataConnectionParameters,
                    dataHeaderParamsCount, dataSecurityParamsCount,
                    componentResource.getId(),
                    componentResource.getRealConnections().getDataConnection(),
                    componentResource.getProxyConnections().getDataConnection());
        } else {
            componentResource.getRealConnections().setDataConnection(null);
            componentResource.getProxyConnections().setDataConnection(null);
        }
        if (sendConnectionActive) {
            setConnectionAttributes(req, ParameterNames.SEND, application, sendConnectionParameters,
                    sendHeaderParamsCount, sendSecurityParamsCount,
                    componentResource.getId(),
                    componentResource.getRealConnections().getSendConnection(),
                    componentResource.getProxyConnections().getSendConnection());
        } else {
            componentResource.getRealConnections().setSendConnection(null);
            componentResource.getProxyConnections().setSendConnection(null);
        }

        //create or update component
        if (componentId == null || componentId.isEmpty()) {
            componentManagementService.addComponent(componentResource);
        } else {
            componentManagementService.updateComponent(componentResource);
        }
        resp.sendRedirect("list?app=" + appIdString);
    }

    private ComponentResource getComponentResource(String componentId, boolean modelConnectionActive, boolean dataConnectionActive, boolean sendConnectionActive) {
        ComponentResource componentResource;
        if (componentId == null || componentId.isEmpty()) {
            componentResource = new ComponentResource();
            componentResource.setId(new ObjectId());
            componentResource.setRealConnections(createNewConnectionPack(modelConnectionActive, dataConnectionActive, sendConnectionActive));
            componentResource.setProxyConnections(createNewConnectionPack(modelConnectionActive, dataConnectionActive, sendConnectionActive));
        } else {
            componentResource = componentManagementService.findById(new ObjectId(componentId));
            resetConnectionsInExistingComponent(modelConnectionActive, dataConnectionActive, sendConnectionActive, componentResource);
        }
        return componentResource;
    }

    private void resetConnectionsInExistingComponent(boolean modelConnectionActive, boolean dataConnectionActive, boolean sendConnectionActive, ComponentResource componentResource) {
        if (modelConnectionActive &&
                componentResource.getRealConnections().getModelConnection() == null &&
                componentResource.getProxyConnections().getModelConnection() == null) {
            componentResource.getRealConnections().setModelConnection(new ComponentConnection());
            componentResource.getProxyConnections().setModelConnection(new ComponentConnection());
        }

        if (dataConnectionActive &&
                componentResource.getRealConnections().getDataConnection() == null &&
                componentResource.getProxyConnections().getDataConnection() == null) {
            componentResource.getRealConnections().setModelConnection(new ComponentConnection());
            componentResource.getProxyConnections().setModelConnection(new ComponentConnection());
        }

        if (sendConnectionActive &&
                componentResource.getRealConnections().getSendConnection() == null &&
                componentResource.getProxyConnections().getSendConnection() == null) {
            componentResource.getRealConnections().setModelConnection(new ComponentConnection());
            componentResource.getProxyConnections().setModelConnection(new ComponentConnection());
        }
    }

    private ComponentConnectionPack createNewConnectionPack(boolean modelConnectionActive, boolean dataConnectionActive, boolean sendConnectionActive) {
        ComponentConnectionPack connectionPack = new ComponentConnectionPack();
        if (modelConnectionActive) {
            connectionPack.setModelConnection(new ComponentConnection());
        }
        if (dataConnectionActive) {
            connectionPack.setDataConnection(new ComponentConnection());
        }
        if (sendConnectionActive) {
            connectionPack.setSendConnection(new ComponentConnection());
        }

        return connectionPack;
    }

    private void setComponentInputToRequest(HttpServletRequest req, String componentId, String componentName, SupportedComponentType componentType) {
        req.setAttribute(ParameterNames.COMPONENT_ID, componentId);
        req.setAttribute(ParameterNames.COMPONENT_NAME, componentName);
        req.setAttribute(ParameterNames.COMPONENT_TYPE, componentType.getName());
    }


    private void setConnectionInputToRequest(HttpServletRequest req, String type,
                                             Map<String, String> headerParams, Map<String, String> securityParams,
                                             String connectionParameters) {
        req.setAttribute(type + ParameterNames.CONNECTION_ACTIVE, 1);
        req.setAttribute(type + ParameterNames.CONNECTION + ParameterNames.PARAMETERS, connectionParameters);
        if (headerParams != null) {
            req.setAttribute(type + ParameterNames.CONNECTION + ParameterNames.HEADER_PARAMS, headerParams);
        }
        if (securityParams != null) {
            req.setAttribute(type + ParameterNames.CONNECTION + ParameterNames.SECURITY_PARAMS, securityParams);
        }
    }

    private void setConnectionAttributes(HttpServletRequest req, String type,
                                         Application application, String parameters,
                                         int headerParamsCount,
                                         int securityParamsCount, ObjectId componentResource,
                                         ComponentConnection connection, ComponentConnection proxyConnection) {
        if (connection != null && (parameters != null && !parameters.isEmpty()) && (application != null)) {
            String protocol = application.getRemoteUrl().substring(0, application.getRemoteUrl().indexOf(":"));
            String address = application.getRemoteUrl().substring(application.getRemoteUrl().indexOf("://") + 3);
            connection.setProtocol(protocol);
            connection.setAddress(address);
            connection.setPort(application.getRemotePort());
            connection.setParameters(parameters);
            if (headerParamsCount > 0) {
                Map<String, String> headerParams = new HashMap<>();
                for (int i = 1; i <= headerParamsCount; i++) {
                    String key = req.getParameter(type + ParameterNames.HEADER_PARAM + ParameterNames.KEY + i);
                    String value = req.getParameter(type + ParameterNames.HEADER_PARAM + ParameterNames.VALUE + i);
                    headerParams.put(key, value);
                }
                connection.setHeaderParams(headerParams);
            }
            if (securityParamsCount > 0) {
                Map<String, String> securityParams = new HashMap<>();
                for (int i = 1; i <= securityParamsCount; i++) {
                    String key = req.getParameter(type + ParameterNames.SECURITY_PARAM + ParameterNames.KEY + i);
                    String value = req.getParameter(type + ParameterNames.SECURITY_PARAM + ParameterNames.VALUE + i);
                    securityParams.put(key, value);
                }
                connection.setSecurityParams(securityParams);
            }
            //generate proxy url
            proxyConnection.setProtocol(req.getScheme());
            proxyConnection.setAddress(req.getServerName());
            proxyConnection.setPort(req.getServerPort());
            proxyConnection.setParameters(req.getContextPath() + "/api/connections/" + type + "/component/" + componentResource);
            proxyConnection.setHeaderParams(connection.getHeaderParams());
            proxyConnection.setSecurityParams(connection.getSecurityParams());
        }
    }


    private void setExistingComponentToRequest(HttpServletRequest request, String componentId) {
        ComponentResource component = componentManagementService.findById(new ObjectId(componentId));
        setComponentInputToRequest(request, component.getId().toString(), component.getName(), component.getType());

        ComponentConnection modelConnection = component.getRealConnections().getModelConnection();
        ComponentConnection dataConnection = component.getRealConnections().getDataConnection();
        ComponentConnection sendConnection = component.getRealConnections().getSendConnection();

        if (modelConnection != null) {
            setConnectionInputToRequest(request, ParameterNames.MODEL,
                    modelConnection.getHeaderParams(),
                    modelConnection.getSecurityParams(),
                    modelConnection.getParameters());
        } else {
            request.setAttribute(ParameterNames.MODEL + ParameterNames.CONNECTION_ACTIVE, 0);
        }
        if (dataConnection != null) {
            setConnectionInputToRequest(request, ParameterNames.DATA,
                    dataConnection.getHeaderParams(),
                    dataConnection.getSecurityParams(),
                    dataConnection.getParameters());
        } else {
            request.setAttribute(ParameterNames.DATA + ParameterNames.CONNECTION_ACTIVE, 0);
        }
        if (sendConnection != null) {
            setConnectionInputToRequest(request, ParameterNames.SEND,
                    sendConnection.getHeaderParams(),
                    sendConnection.getSecurityParams(),
                    sendConnection.getParameters());
        } else {
            request.setAttribute(ParameterNames.SEND + ParameterNames.CONNECTION_ACTIVE, 0);
        }
    }
}
