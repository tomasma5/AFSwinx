package service.servlet.impl;

import dao.ComponentResourceDao;
import dao.ScreenDao;
import model.*;
import org.bson.types.ObjectId;
import service.servlet.ComponentManagementService;
import servlet.ParameterNames;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Named("componentManagementService")
@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class ComponentManagementServiceImpl implements ComponentManagementService {

    @Inject
    private ComponentResourceDao componentResourceDao;

    @Inject
    private ScreenDao screenDao;

    public ComponentManagementServiceImpl() {
    }

    @Override
    public void addComponent(ComponentResource componentResource) {
        addComponentToReferencedScreens(componentResource);
        componentResourceDao.create(componentResource);
    }

    @Override
    public void removeComponent(ObjectId id) {
        ComponentResource componentResource = componentResourceDao.findById(id);
        removeComponentFromReferencedScreens(componentResource);
        componentResourceDao.deleteByObjectId(id);

    }

    @Override
    public void updateComponent(ComponentResource updated) {
        ComponentResource componentResource = componentResourceDao.findById(updated.getId());
        removeComponentFromReferencedScreens(componentResource);
        addComponentToReferencedScreens(updated);
        componentResourceDao.update(updated);
    }

    @Override
    public ComponentResource findById(ObjectId id) {
        return componentResourceDao.findById(id);
    }

    @Override
    public List<ComponentResource> getAllComponentsByApplication(ObjectId applicationId) {
        return componentResourceDao.findAll().stream()
                .filter(componentResource -> componentResource.getApplicationId().equals(applicationId))
                .collect(Collectors.toList());
    }

    @Override
    public List<ComponentResource> getComponentsNotInScreen(ObjectId screenId, ObjectId applicationId) {
        return getAllComponentsByApplication(applicationId).stream()
                .filter(componentResource -> componentResource.getReferencedScreensIds() == null ||
                        !componentResource.getReferencedScreensIds().contains(screenId))
                .collect(toList());
    }

    @Override
    public void addComponentToScreen(ComponentResource componentResource, Screen screen) {
        screen.addComponentResource(componentResource);
        screenDao.update(screen);
        componentResourceDao.update(componentResource);
    }

    @Override
    public void filterComponentsScreenReferences(ComponentResource componentResource) {
        List<ObjectId> screenIds = screenDao.findAll().stream()
                .filter(screen -> screen.getApplicationId().equals(componentResource.getApplicationId()) &&
                        (screen.getComponents() != null && screen.getComponents().contains(componentResource)))
                .map(Screen::getId)
                .collect(Collectors.toList());
        componentResource.setReferencedScreensIds(screenIds);
        componentResourceDao.update(componentResource);
    }


    private void addComponentToReferencedScreens(ComponentResource componentResource) {
        List<ObjectId> updatedReferencedScreens = componentResource.getReferencedScreensIds();
        if (updatedReferencedScreens != null) {
            for (ObjectId screenId : updatedReferencedScreens) {
                addComponentToScreen(componentResource, screenDao.findById(screenId));
            }
        }
    }

    private void removeComponentFromReferencedScreens(ComponentResource componentResource) {
        List<ObjectId> referencedScreens = componentResource.getReferencedScreensIds();
        if (referencedScreens != null) {
            Screen screen;
            for (ObjectId screenId : referencedScreens) {
                screen = screenDao.findById(screenId);
                if (screen != null) {
                    screen.removeComponentResource(componentResource);
                    screenDao.update(screen);
                }
            }
        }
    }

    @Override
    public void updateComponentConnections(Application application) {
        for (ComponentResource componentResource : getAllComponentsByApplication(application.getId())) {
            ComponentConnectionPack realConnectionsPack = componentResource.getProxyConnections();
            updateConnectionParameters(application, realConnectionsPack.getModelConnection());
            updateConnectionParameters(application, realConnectionsPack.getDataConnection());
            updateConnectionParameters(application, realConnectionsPack.getSendConnection());
            updateComponent(componentResource);
        }
    }

    @Override
    public void updateLinkedComponents(HttpServletRequest req, int linkedComponentsCount, Screen screen) {
        if (screen.getComponents() != null) {
            screen.getComponents().clear();
        }
        for (int i = 0; i < linkedComponentsCount; i++) {
            String componentId = req.getParameter(ParameterNames.LINKED_COMPONENT_ID + (i + 1));
            ComponentResource componentResource = findById(new ObjectId(componentId));
            addComponentToScreen(componentResource, screen);
            filterComponentsScreenReferences(componentResource);
        }
    }

    @Override
    public ComponentResource findOrCreateComponentResource(HttpServletRequest req, String componentId) {
        ComponentResource componentResource;
        if (componentId == null || componentId.isEmpty()) {
            componentResource = new ComponentResource();
            componentResource.setId(new ObjectId());
            componentResource.setProxyConnections(createNewConnectionPack(req));
        } else {
            componentResource = findById(new ObjectId(componentId));
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

    @Override
    public void updateComponentConnections(HttpServletRequest req, Application application, ComponentResource componentResource) {
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
            proxyConnection.setRealProtocol(application.getRemoteProtocol());
            proxyConnection.setRealAddress(application.getRemoteHostname());
            proxyConnection.setRealPort(application.getRemotePort());
            proxyConnection.setRealParameters(parameters);
            proxyConnection.setHeaderParams(getParams(req, type, ParameterNames.HEADER_PARAM, headerParamsCount));
            proxyConnection.setSecurityParams(getParams(req, type, ParameterNames.SECURITY_PARAM, securityParamsCount));
            setProxyUrl(req, application, type, componentResource, proxyConnection);
        }
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

    private void setProxyUrl(HttpServletRequest req, Application application, String type, ObjectId componentResource, ComponentConnection proxyConnection) {
        //generate proxy url
        proxyConnection.setProtocol(application.getProxyProtocol());
        proxyConnection.setAddress(application.getProxyHostname());
        proxyConnection.setPort(application.getProxyPort());
        proxyConnection.setParameters(req.getContextPath() + "/api/connections/" + type + "/component/" + componentResource);
        proxyConnection.setHeaderParams(proxyConnection.getHeaderParams());
        proxyConnection.setSecurityParams(proxyConnection.getSecurityParams());
    }


    private boolean isConnectionActive(HttpServletRequest req, String type) {
        String connectionActiveString = req.getParameter(type + ParameterNames.CONNECTION_ACTIVE);
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
        }
    }


}
