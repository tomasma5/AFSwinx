package servlet.components;

import model.*;
import org.bson.types.ObjectId;
import service.ComponentManagementService;
import servlet.ParameterNames;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ComponentCreateServlet extends HttpServlet {


    private static final String CREATE_URL = "/WEB-INF/pages/components/create.jsp";

    @Inject
    private ComponentManagementService componentManagementService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String applicationId = request.getParameter(ParameterNames.APPLICATION_ID);
        String componentId = request.getParameter(ParameterNames.COMPONENT_ID);
        if (applicationId == null || applicationId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (componentId != null) {
            setExistingComponentToRequest(request, componentId);
        }
        List<String> options = new ArrayList<>();
        for (SupportedComponentType type: SupportedComponentType.class.getEnumConstants()) {
            options.add(type.getName());
        }
        request.setAttribute("componentTypeOptions", options);
        request.setAttribute(ParameterNames.APPLICATION_ID, applicationId);
        getServletContext().getRequestDispatcher(CREATE_URL).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO refactor???
        String appIdString = req.getParameter(ParameterNames.APPLICATION_ID);
        if (appIdString == null || appIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String componentId = req.getParameter(ParameterNames.COMPONENT_ID);
        String componentName = req.getParameter(ParameterNames.COMPONENT_NAME);
        SupportedComponentType componentType = SupportedComponentType.valueOf(req.getParameter(ParameterNames.COMPONENT_TYPE)); //TODO copy enum from value from afrest utils
        String modelConnectionProtocol = req.getParameter(ParameterNames.MODEL_CONNECTION_PROTOCOL);
        String modelConnectionAddress = req.getParameter(ParameterNames.MODEL_CONNECTION_ADDRESS);
        String modelConnectionPort = req.getParameter(ParameterNames.MODEL_CONNECTION_PORT);
        String modelConnectionParameters = req.getParameter(ParameterNames.MODEL_CONNECTION_PARAMETERS);
        //TODO header params and security params
        String dataConnectionProtocol = req.getParameter(ParameterNames.DATA_CONNECTION_PROTOCOL);
        String dataConnectionAddress = req.getParameter(ParameterNames.DATA_CONNECTION_ADDRESS);
        String dataConnectionPort = req.getParameter(ParameterNames.DATA_CONNECTION_PORT);
        String dataConnectionParameters = req.getParameter(ParameterNames.DATA_CONNECTION_PARAMETERS);
        //TODO header params and security params
        String sendConnectionProtocol = req.getParameter(ParameterNames.SEND_CONNECTION_PROTOCOL);
        String sendConnectionAddress = req.getParameter(ParameterNames.SEND_CONNECTION_ADDRESS);
        String sendConnectionPort = req.getParameter(ParameterNames.SEND_CONNECTION_PORT);
        String sendConnectionParameters = req.getParameter(ParameterNames.SEND_CONNECTION_PARAMETERS);
        //TODO header params and security params

        try {
            ComponentResource componentResource;
            if (componentId == null || componentId.isEmpty()) {
                componentResource = new ComponentResource();
                ComponentConnectionPack connectionPack = new ComponentConnectionPack();
                connectionPack.setModelConnection(new ComponentConnection()); //TODO make it optional
                connectionPack.setDataConnection(new ComponentConnection()); //TODO make it optional
                connectionPack.setSendConnection(new ComponentConnection()); //TODO make it optional
            } else {
                componentResource = componentManagementService.findById(new ObjectId(componentId));
            }
            componentResource.setName(componentName);
            componentResource.setType(componentType);
            componentResource.setApplicationId(new ObjectId(appIdString));
            try {
                setConnectionAttributes(modelConnectionAddress, modelConnectionParameters, modelConnectionProtocol,
                        modelConnectionPort, componentResource.getConnections().getModelConnection()
                );
            } catch (NumberFormatException ex) {
                req.setAttribute("modelConnectionPortError", "Port must be a number.");
            }
            try {
                setConnectionAttributes(dataConnectionAddress, dataConnectionParameters, dataConnectionProtocol,
                        dataConnectionPort, componentResource.getConnections().getDataConnection()
                );
            } catch (NumberFormatException ex) {
                req.setAttribute("dataConnectionPortError", "Port must be a number.");
            }
            try {
                setConnectionAttributes(sendConnectionAddress, sendConnectionParameters, sendConnectionProtocol,
                        sendConnectionPort, componentResource.getConnections().getSendConnection()
                );
            } catch (NumberFormatException ex) {
                req.setAttribute("sendConnectionPortError", "Port must be a number.");
            }

            if (componentId == null || componentId.isEmpty()) {
                componentManagementService.addComponent(componentResource);
            } else {
                componentManagementService.updateComponent(componentResource);
            }
            resp.sendRedirect("list?app=" + appIdString);
            return;
        } catch (MalformedURLException ex) {
            req.setAttribute("screenUrlError", "URL has bad format.");
        }

        //set failed form data
        setComponentInputToRequest(req, componentId, componentName, componentType);
        setModelConnectionInputToRequest(req, modelConnectionAddress, modelConnectionPort, modelConnectionParameters);
        //TODO header params and security params
        setDataConnectionInputToRequest(req, dataConnectionProtocol, dataConnectionAddress, dataConnectionPort, dataConnectionParameters);
        //TODO header params and security params
        setSendConnectionInputToRequest(req, sendConnectionProtocol, sendConnectionAddress, sendConnectionPort, sendConnectionParameters);
        //TODO header params and security params
        req.setAttribute(ParameterNames.APPLICATION_ID, appIdString);
        req.getRequestDispatcher(CREATE_URL).forward(req, resp);

    }

    private void setComponentInputToRequest(HttpServletRequest req, String componentId, String componentName, SupportedComponentType componentType) {
        req.setAttribute(ParameterNames.COMPONENT_ID, componentId);
        req.setAttribute(ParameterNames.COMPONENT_NAME, componentName);
        req.setAttribute(ParameterNames.COMPONENT_TYPE, componentType.getName());
    }

    private void setSendConnectionInputToRequest(HttpServletRequest req, String sendConnectionProtocol, String sendConnectionAddress, String sendConnectionPort, String sendConnectionParameters) {
        req.setAttribute(ParameterNames.SEND_CONNECTION_PROTOCOL, sendConnectionProtocol);
        req.setAttribute(ParameterNames.SEND_CONNECTION_ADDRESS, sendConnectionAddress);
        req.setAttribute(ParameterNames.SEND_CONNECTION_PORT, sendConnectionPort);
        req.setAttribute(ParameterNames.SEND_CONNECTION_PARAMETERS, sendConnectionParameters);
    }

    private void setDataConnectionInputToRequest(HttpServletRequest req, String dataConnectionProtocol, String dataConnectionAddress, String dataConnectionPort, String dataConnectionParameters) {
        req.setAttribute(ParameterNames.DATA_CONNECTION_PROTOCOL, dataConnectionProtocol);
        req.setAttribute(ParameterNames.DATA_CONNECTION_ADDRESS, dataConnectionAddress);
        req.setAttribute(ParameterNames.DATA_CONNECTION_PORT, dataConnectionPort);
        req.setAttribute(ParameterNames.DATA_CONNECTION_PARAMETERS, dataConnectionParameters);
    }

    private void setModelConnectionInputToRequest(HttpServletRequest req, String modelConnectionAddress, String modelConnectionPort, String modelConnectionParameters) {
        req.setAttribute(ParameterNames.MODEL_CONNECTION_PROTOCOL, modelConnectionPort);
        req.setAttribute(ParameterNames.MODEL_CONNECTION_ADDRESS, modelConnectionAddress);
        req.setAttribute(ParameterNames.MODEL_CONNECTION_PORT, modelConnectionPort);
        req.setAttribute(ParameterNames.MODEL_CONNECTION_PARAMETERS, modelConnectionParameters);
    }

    private void setConnectionAttributes(String address, String parameters, String protocol,
                                         String port, ComponentConnection connection) {
        if (connection != null) {
            connection.setProtocol(protocol);
            connection.setProtocol(address);
            connection.setPort(Integer.parseInt(port));
            connection.setParameters(parameters);
            //TODO security and header params
        }
    }

    private void setExistingComponentToRequest(HttpServletRequest request, String componentId) {
        ComponentResource component = componentManagementService.findById(new ObjectId(componentId));
        request.setAttribute(ParameterNames.COMPONENT_ID, component.getId());
        request.setAttribute(ParameterNames.COMPONENT_NAME, component.getName());
        request.setAttribute(ParameterNames.COMPONENT_TYPE, component.getType().getName());
        ComponentConnection modelConnection = component.getConnections().getModelConnection();
        if (modelConnection != null) {
            request.setAttribute(ParameterNames.MODEL_CONNECTION_PROTOCOL, modelConnection.getProtocol());
            request.setAttribute(ParameterNames.MODEL_CONNECTION_ADDRESS, modelConnection.getAddress());
            request.setAttribute(ParameterNames.MODEL_CONNECTION_PORT, modelConnection.getPort());
            request.setAttribute(ParameterNames.MODEL_CONNECTION_PARAMETERS, modelConnection.getParameters());
            //TODO header params and security params
        }
        ComponentConnection dataConnection = component.getConnections().getDataConnection();
        if (dataConnection != null) {
            request.setAttribute(ParameterNames.DATA_CONNECTION_PROTOCOL, dataConnection.getProtocol());
            request.setAttribute(ParameterNames.DATA_CONNECTION_ADDRESS, dataConnection.getAddress());
            request.setAttribute(ParameterNames.DATA_CONNECTION_PORT, dataConnection.getPort());
            request.setAttribute(ParameterNames.DATA_CONNECTION_PARAMETERS, dataConnection.getParameters());
            //TODO header params and security params
        }
        ComponentConnection sendConnection = component.getConnections().getSendConnection();
        if (sendConnection != null) {
            request.setAttribute(ParameterNames.SEND_CONNECTION_PROTOCOL, sendConnection.getProtocol());
            request.setAttribute(ParameterNames.SEND_CONNECTION_ADDRESS, sendConnection.getAddress());
            request.setAttribute(ParameterNames.SEND_CONNECTION_PORT, sendConnection.getPort());
            request.setAttribute(ParameterNames.SEND_CONNECTION_PARAMETERS, sendConnection.getParameters());
            //TODO header params and security params
        }
    }
}
