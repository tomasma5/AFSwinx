package servlet.components;

import model.Application;
import model.ComponentConnection;
import model.ComponentResource;
import model.SupportedComponentType;
import org.bson.types.ObjectId;
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
        String appIdString = Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID));
        if (appIdString == null || appIdString.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Application application = applicationsManagementService.findById(new ObjectId(appIdString));

        String componentId = Utils.trimString(req.getParameter(ParameterNames.COMPONENT_ID));
        ComponentResource componentResource = componentManagementService.findOrCreateComponentResource(req, componentId);
        //set attributes to component resource
        updateComponentProperties(req, componentResource);
        //set connection data
        componentManagementService.updateComponentConnections(req, application, componentResource);

        createOrUpdateComponent(req, componentResource);
        resp.sendRedirect(LIST_ROUTE + "?app=" + appIdString);
    }

    //component set & update methods
    private void createOrUpdateComponent(HttpServletRequest req, ComponentResource componentResource) {
        String componentId = Utils.trimString(req.getParameter(ParameterNames.COMPONENT_ID));
        if (componentId == null || componentId.isEmpty()) {
            componentManagementService.addComponent(componentResource);
        } else {
            componentManagementService.updateComponent(componentResource);
        }
    }

    private void updateComponentProperties(HttpServletRequest req, ComponentResource componentResource) {
        String componentName = Utils.trimString(req.getParameter(ParameterNames.COMPONENT_NAME));
        SupportedComponentType componentType = SupportedComponentType.valueOf(Utils.trimString(req.getParameter(ParameterNames.COMPONENT_TYPE)));
        componentResource.setName(Utils.trimString(componentName));
        componentResource.setType(componentType);
        componentResource.setApplicationId(new ObjectId(Utils.trimString(req.getParameter(ParameterNames.APPLICATION_ID))));
    }

    private void setComponentInputToRequest(HttpServletRequest req, String componentId, String componentName, SupportedComponentType componentType) {
        req.setAttribute(ParameterNames.COMPONENT_ID, componentId);
        req.setAttribute(ParameterNames.COMPONENT_NAME, componentName);
        req.setAttribute(ParameterNames.COMPONENT_TYPE, componentType.getName());
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
        ComponentResource component = componentManagementService.findById(new ObjectId(componentId));
        setComponentInputToRequest(request, component.getId().toString(), component.getName(), component.getType());
        setConnectionInputToRequest(request, ParameterNames.MODEL, component.getProxyConnections().getModelConnection());
        setConnectionInputToRequest(request, ParameterNames.DATA, component.getProxyConnections().getDataConnection());
        setConnectionInputToRequest(request, ParameterNames.SEND, component.getProxyConnections().getSendConnection());
    }


}
