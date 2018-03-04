package service.rest.impl;

import dao.ComponentResourceDao;
import model.Application;
import model.ComponentConnection;
import model.ComponentResource;
import org.bson.types.ObjectId;
import rest.security.RequestContext;
import service.exception.ComponentRequestException;
import service.rest.ComponentResourceService;
import utils.HttpUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of service for getting data from application
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ComponentResourceServiceImpl implements ComponentResourceService {

    private static final Logger LOGGER = Logger.getLogger(ComponentResourceServiceImpl.class.getName());

    @Inject
    ComponentResourceDao componentResourceDao;

    @Inject
    RequestContext requestContext;

    @Override
    public String getComponentModel(ObjectId id, HttpHeaders headers) throws ComponentRequestException {
        Application application = requestContext.getCurrentApplication();
        String modelStr = null;
        ComponentResource componentResource = componentResourceDao.findById(id);
        if (componentResource != null && application != null) {
            if (!componentResource.getApplicationId().equals(application.getId())) {
                String errorMsg = "Cannot get component model, this component belongs to another application";
                LOGGER.log(Level.SEVERE, errorMsg);
                throw new ComponentRequestException(errorMsg);
            }
            ComponentConnection realModelConnection = componentResource.getRealConnections().getModelConnection();
            //TODO add context filtering
            try {
                modelStr = HttpUtils.getRequest(buildUrl(realModelConnection), headers.getRequestHeaders());
            } catch (IOException e) {
                throw new ComponentRequestException(e.getMessage(), e);
            }
        }
        return modelStr;
    }

    @Override
    public String getComponentData(ObjectId id, HttpHeaders headers) throws ComponentRequestException {
        ComponentResource componentResource = componentResourceDao.findById(id);
        String dataStr = null;
        if (componentResource != null) {
            ComponentConnection realDataConnection = componentResource.getRealConnections().getDataConnection();

            //TODO add context filtering
            try {
                dataStr = HttpUtils.getRequest(buildUrl(realDataConnection), headers.getRequestHeaders());
            } catch (IOException e) {
                throw new ComponentRequestException(e.getMessage(), e);
            }
        }
        return dataStr;
    }

    @Override
    public String sendComponentData(ObjectId id, HttpHeaders headers, String data) throws ComponentRequestException {
        ComponentResource componentResource = componentResourceDao.findById(id);
        String response = null;
        if (componentResource != null) {
            ComponentConnection realDataConnection = componentResource.getRealConnections().getSendConnection();

            //TODO add context filtering
            try {
                response = HttpUtils.postRequest(buildUrl(realDataConnection), headers.getRequestHeaders(), data);
            } catch (IOException e) {
                throw new ComponentRequestException(e.getMessage(), e);
            }
        }
        return response;
    }

    private String buildUrl(ComponentConnection componentConnection) {
        StringBuilder url = new StringBuilder();
        url.append(componentConnection.getProtocol());
        url.append("://");
        url.append(componentConnection.getAddress());
        url.append(":");
        url.append(componentConnection.getPort());
        url.append(componentConnection.getParameters());
        return url.toString();
    }
}
