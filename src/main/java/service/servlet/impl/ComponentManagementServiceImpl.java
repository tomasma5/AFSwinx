package service.servlet.impl;

import dao.ComponentResourceDao;
import dao.ConnectionDao;
import dao.ConnectionPackDao;
import dao.ScreenDao;
import model.*;
import service.servlet.ComponentManagementService;
import servlet.ParameterNames;
import utils.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Implementation of component management service.
 */
@Named("componentManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class ComponentManagementServiceImpl implements ComponentManagementService {

    @Inject
    private ComponentResourceDao componentResourceDao;

    @Inject
    private ScreenDao screenDao;

    @Inject
    private ConnectionPackDao connectionPackDao;

    @Inject
    private ConnectionDao connectionDao;

    /**
     * Instantiates a new Component management service.
     */
    public ComponentManagementServiceImpl() {
    }

    @Override
    public void createOrUpdate(ComponentResource componentResource) {
        addComponentToReferencedScreens(componentResource);
        componentResourceDao.createOrUpdate(componentResource);
    }

    @Override
    public void removeComponent(int componentResource) {
        ComponentResource toBeRemoved = componentResourceDao.getById(componentResource);
        removeComponentFromReferencedScreens(toBeRemoved);
        componentResourceDao.delete(toBeRemoved);
    }

    @Override
    public ComponentResource findById(int id) {
        return componentResourceDao.getById(id);
    }

    @Override
    public List<ComponentResource> getAllComponentsByApplication(int applicationId) {
        return componentResourceDao.getAll().stream()
                .filter(componentResource -> componentResource.getApplication().getId() == applicationId)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComponentResource> getComponentsNotInScreen(int screenId, int applicationId) {
        return getAllComponentsByApplication(applicationId).stream()
                .filter(componentResource -> componentResource.getReferencedScreens() == null ||
                        !componentResource.getReferencedScreens().contains(screenId))
                .collect(toList());
    }

    @Override
    public void addComponentToScreen(ComponentResource componentResource, Screen screen) {
        componentResource.referencedByScreen(screen);
        screen.addComponentResource(componentResource);
        componentResourceDao.createOrUpdate(componentResource);
        screenDao.createOrUpdate(screen);
    }


    private void addComponentToReferencedScreens(ComponentResource componentResource) {
        List<Screen> updatedReferencedScreens = componentResource.getReferencedScreens();
        if (updatedReferencedScreens != null) {
            for (Screen screen : updatedReferencedScreens) {
                addComponentToScreen(componentResource, screen);
            }
        }
    }

    private void removeComponentFromReferencedScreens(ComponentResource componentResource) {
        List<Screen> referencedScreens = componentResource.getReferencedScreens();
        List<Screen> toBeRemoved = new ArrayList<>();
        if (referencedScreens != null) {
            for (Screen screen : referencedScreens) {
                if (screen != null) {
                    screen.removeComponentResource(componentResource);
                    screenDao.createOrUpdate(screen);
                    toBeRemoved.add(screen);
                }
            }
            componentResource.getReferencedScreens().removeAll(toBeRemoved);
            componentResourceDao.createOrUpdate(componentResource);
        }
    }

    @Override
    public void updateComponentConnections(Application application) {
        for (ComponentResource componentResource : getAllComponentsByApplication(application.getId())) {
            componentResource.setFieldInfoUrlProtocol(application.getRemoteProtocol());
            componentResource.setFieldInfoUrlHostname(application.getRemoteHostname());
            componentResource.setFieldInfoUrlPort(application.getRemotePort());
            componentResourceDao.createOrUpdate(componentResource);
            ComponentConnectionPack realConnectionsPack = componentResource.getProxyConnections();
            updateConnectionParameters(application, realConnectionsPack.getModelConnection());
            updateConnectionParameters(application, realConnectionsPack.getDataConnection());
            updateConnectionParameters(application, realConnectionsPack.getSendConnection());
        }
    }

    @Override
    public void updateLinkedComponents(HttpServletRequest req, int linkedComponentsCount, Screen screen) {
        if (screen.getComponents() != null) {
            screen.getComponents().clear();
        }
        for (int i = 0; i < linkedComponentsCount; i++) {
            String componentId = Utils.trimString(req.getParameter(ParameterNames.LINKED_COMPONENT_ID + (i + 1)));
            ComponentResource componentResource = findById(Integer.parseInt(componentId));
            addComponentToScreen(componentResource, screen);
        }
    }

    @Override
    public ComponentResource findOrCreateComponentResource(HttpServletRequest req, String componentId) {
        ComponentResource componentResource;
        if (componentId == null || componentId.isEmpty()) {
            componentResource = new ComponentResource();
            componentResource.setProxyConnections(createNewConnectionPack(req));
        } else {
            componentResource = findById(Integer.parseInt(componentId));
            resetConnectionsInExistingComponent(req, componentResource);
        }
        return componentResource;
    }

    private ComponentConnectionPack createNewConnectionPack(HttpServletRequest req) {
        ComponentConnectionPack connectionPack = new ComponentConnectionPack();
        if (isConnectionActive(req, ParameterNames.MODEL)) {
            connectionPack.setModelConnection(createComponentConnection());
        }
        if (isConnectionActive(req, ParameterNames.DATA)) {
            connectionPack.setDataConnection(createComponentConnection());
        }
        if (isConnectionActive(req, ParameterNames.SEND)) {
            connectionPack.setSendConnection(createComponentConnection());
        }
        connectionPackDao.createOrUpdate(connectionPack);
        return connectionPack;
    }

    private ComponentConnection createComponentConnection() {
        ComponentConnection modelConnection = new ComponentConnection();
        connectionDao.createOrUpdate(modelConnection);
        return modelConnection;
    }


    private void resetConnectionsInExistingComponent(HttpServletRequest req, ComponentResource componentResource) {
        if (isConnectionActive(req, ParameterNames.MODEL) &&
                componentResource.getProxyConnections().getModelConnection() == null) {
            componentResource.getProxyConnections().setModelConnection(createComponentConnection());
        }

        if (isConnectionActive(req, ParameterNames.DATA) &&
                componentResource.getProxyConnections().getDataConnection() == null) {
            componentResource.getProxyConnections().setModelConnection(createComponentConnection());
        }

        if (isConnectionActive(req, ParameterNames.SEND) &&
                componentResource.getProxyConnections().getSendConnection() == null) {
            componentResource.getProxyConnections().setModelConnection(createComponentConnection());
        }
    }

    @Override
    public void updateComponentConnections(HttpServletRequest req, Application application, ComponentResource componentResource) {
        if (isConnectionActive(req, ParameterNames.MODEL)) {
            updateConnectionAttributes(req, ParameterNames.MODEL, application, componentResource.getId(),
                    componentResource.getProxyConnections().getModelConnection());
        } else {
            componentResource.getProxyConnections().setModelConnection(null);
            connectionPackDao.createOrUpdate(componentResource.getProxyConnections());
        }
        if (isConnectionActive(req, ParameterNames.DATA)) {
            updateConnectionAttributes(req, ParameterNames.DATA, application, componentResource.getId(),
                    componentResource.getProxyConnections().getDataConnection());
        } else {
            componentResource.getProxyConnections().setDataConnection(null);
            connectionPackDao.createOrUpdate(componentResource.getProxyConnections());
        }
        if (isConnectionActive(req, ParameterNames.SEND)) {
            updateConnectionAttributes(req, ParameterNames.SEND, application, componentResource.getId(),
                    componentResource.getProxyConnections().getSendConnection());
        } else {
            componentResource.getProxyConnections().setSendConnection(null);
            connectionPackDao.createOrUpdate(componentResource.getProxyConnections());
        }
    }


    private void updateConnectionAttributes(HttpServletRequest req, String type, Application application, Integer componentResource, ComponentConnection proxyConnection) {
        String parameters = Utils.trimString(req.getParameter(type + ParameterNames.CONNECTION + ParameterNames.PARAMETERS));
        if (proxyConnection != null && (parameters != null && !parameters.isEmpty()) && (application != null)) {
            int headerParamsCount = Integer.parseInt(Utils.trimString(req.getParameter(type + ParameterNames.HEADER_PARAMS_COUNT)));
            int securityParamsCount = Integer.parseInt(Utils.trimString(req.getParameter(type + ParameterNames.SECURITY_PARAMS_COUNT)));
            proxyConnection.setRealProtocol(application.getRemoteProtocol());
            proxyConnection.setRealAddress(application.getRemoteHostname());
            proxyConnection.setRealPort(application.getRemotePort());
            proxyConnection.setRealParameters(parameters);
            proxyConnection.setHeaderParams(getParams(req, type, ParameterNames.HEADER_PARAM, headerParamsCount));
            proxyConnection.setSecurityParams(getParams(req, type, ParameterNames.SECURITY_PARAM, securityParamsCount));
            setProxyUrl(req, application, type, componentResource, proxyConnection);
            connectionDao.createOrUpdate(proxyConnection);
        }
    }

    private Map<String, String> getParams(HttpServletRequest req, String type, String paramType, int paramsCount) {
        Map<String, String> params = null;
        if (paramsCount > 0) {
            params = new HashMap<>();
            for (int i = 1; i <= paramsCount; i++) {
                String key = Utils.trimString(req.getParameter(type + paramType + ParameterNames.KEY + i));
                String value = Utils.trimString(req.getParameter(type + paramType + ParameterNames.VALUE + i));
                params.put(key, value);
            }
        }
        return params;
    }

    public void setProxyUrl(HttpServletRequest req, Application application, String type, Integer componentResourceId, ComponentConnection proxyConnection) {
        //generate proxy url
        proxyConnection.setProtocol(application.getProxyProtocol());
        proxyConnection.setAddress(application.getProxyHostname());
        proxyConnection.setPort(application.getProxyPort());
        proxyConnection.setParameters(req.getContextPath() + "/api/connections/" + type + "/component/" + componentResourceId);
        proxyConnection.setHeaderParams(proxyConnection.getHeaderParams());
        proxyConnection.setSecurityParams(proxyConnection.getSecurityParams());
    }


    private boolean isConnectionActive(HttpServletRequest req, String type) {
        String connectionActiveString = Utils.trimString(req.getParameter(type + ParameterNames.CONNECTION_ACTIVE));
        return connectionActiveString != null && Integer.parseInt(connectionActiveString) == 1;
    }

    private void updateConnectionParameters(Application application, ComponentConnection connection) {
        if (connection != null) {
            connection.setRealProtocol(application.getRemoteProtocol());
            connection.setRealAddress(application.getRemoteHostname());
            connection.setRealPort(application.getRemotePort());
            connection.setProtocol(application.getProxyProtocol());
            connection.setAddress(application.getProxyHostname());
            connection.setPort(application.getProxyPort());
            connectionDao.createOrUpdate(connection);
        }
    }

}
