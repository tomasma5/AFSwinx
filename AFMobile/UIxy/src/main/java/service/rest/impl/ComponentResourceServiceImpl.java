package service.rest.impl;

import com.google.gson.Gson;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import dao.ComponentResourceDao;
import dao.ConfigurationPackDao;
import model.Application;
import model.ComponentResource;
import model.Screen;
import model.afclassification.*;
import rest.context.JsonContextParser;
import rest.security.RequestContext;
import service.afclassification.computational.AFClassification;
import service.afclassification.computational.AFClassificationFactory;
import service.exception.ComponentRequestException;
import service.exception.ServiceException;
import service.rest.ComponentResourceService;
import service.servlet.*;
import utils.Constants;
import utils.HttpUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.List;
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
    ConfigurationPackDao configurationPackDao;
    @Inject
    ComponentResourceDao componentResourceDao;
    @Inject
    BusinessFieldsManagementService businessFieldsManagementService;
    @Inject
    ScreenManagementService screenManagementService;
    @Inject
    ApplicationsManagementService applicationsManagementService;
    @Inject
    RequestContext requestContext;

    @Override
    public String getComponentModel(int id, HttpHeaders headers) throws ComponentRequestException, ServiceException {
        Application application = requestContext.getCurrentApplication();
        ComponentResource componentResource = componentResourceDao.getById(id);
        if (componentResource != null && application != null) {
            if (!(componentResource.getApplication().getId().equals(application.getId()))) {
                String errorMsg = "Cannot get component model, this component belongs to another application";
                LOGGER.log(Level.SEVERE, errorMsg);
                throw new ComponentRequestException(errorMsg);
            }
            String realEndpoint = checkRealEndpointUrlPresence(headers);
            try {
                String modelStr = HttpUtils.getRequest(realEndpoint, headers.getRequestHeaders());
                Gson gson = new Gson();
                //classify fields and filter model with context
                AFMetaModelPack metaModel = getFilteredComponentModel(headers, modelStr, gson);
                return gson.toJson(metaModel);
            } catch (IOException e) {
                throw new ComponentRequestException(e.getMessage(), e);
            }
        }
        return null;
    }


    @Override
    public String getComponentData(int id, HttpHeaders headers) throws ComponentRequestException {
        String realEndpoint = checkRealEndpointUrlPresence(headers);

        ComponentResource componentResource = componentResourceDao.getById(id);
        String dataStr = null;
        if (componentResource != null) {
            try {
                dataStr = HttpUtils.getRequest(realEndpoint, headers.getRequestHeaders());
            } catch (IOException e) {
                throw new ComponentRequestException(e.getMessage(), e);
            }
        }
        return dataStr;
    }

    @Override
    public String sendComponentData(int id, HttpHeaders headers, String data) throws ComponentRequestException {
        String realEndpoint = checkRealEndpointUrlPresence(headers);
        ComponentResource componentResource = componentResourceDao.getById(id);
        String response = null;
        if (componentResource != null) {
            try {
                response = HttpUtils.postRequest(realEndpoint, headers.getRequestHeaders(), data);
            } catch (IOException e) {
                //TODO lepe zpracovat response kody
                throw new ComponentRequestException(e.getMessage(), e);
            }
        }
        return response;
    }


    private String checkRealEndpointUrlPresence(HttpHeaders headers) throws ComponentRequestException {
        String realEndpoint = headers.getHeaderString(Constants.REAL_ENDPOINT_HEADER);
        if (realEndpoint == null) {
            String errorMsg = "Cannot get component model. Real endpoint was not correctly evaluated at client side.";
            LOGGER.log(Level.SEVERE, errorMsg);
            throw new ComponentRequestException(errorMsg);
        }
        return realEndpoint;
    }

    private AFMetaModelPack getFilteredComponentModel(HttpHeaders headers, String modelStr, Gson gson) throws IOException {
        AFMetaModelPack metaModel = gson.fromJson(modelStr, AFMetaModelPack.class);
        BCPhase phase = null;
        List<BCField> phaseFields = null;
        try {
            phase = getBusinessPhaseFromRequest(headers);
            phaseFields = businessFieldsManagementService.findAllByPhase(phase.getId());
        } catch (ServiceException e) {
            LOGGER.log(Level.SEVERE, "Cannot find business phase for screen. Classification cannot be done. Returning basic metamodel.");
        }
        if (phase != null && phaseFields != null && phase.getConfiguration() != null) {
            ConfigurationPack configurationPack = configurationPackDao.getByIdWithLoadedConfigurations(phase.getConfiguration().getId());
            Application application = getApplicationFromRequest(headers);
            Client client = getClientFromRequest(headers);
            AFClassification classification = AFClassificationFactory.getInstance().getClassificationModule(phase, client, application);
            long start = System.currentTimeMillis();
            classification.classifyMetaModel(metaModel, client, configurationPack, phaseFields, application);
            LOGGER.log(Level.INFO, "Classification took " + (System.currentTimeMillis() - start) + " ms");
        }
        return metaModel;
    }

    private Application getApplicationFromRequest(HttpHeaders headers) {
        String appUuid = headers.getRequestHeaders().getFirst(Constants.APPLICATION_HEADER);
        return applicationsManagementService.findByUuid(appUuid);
    }

    private BCPhase getBusinessPhaseFromRequest(HttpHeaders headers) throws ServiceException {
        String screenKey = headers.getRequestHeaders().getFirst(Constants.SCREEN_HEADER);
        Screen screen = screenManagementService.findScreenByKey(screenKey);
        if (screen == null) {
            throw new ServiceException("Cannot get business phase. Screen specified in headers was not found.");
        }
        if (screen.getPhase() == null) {
            throw new ServiceException("Screen does not have phase.");
        }
        return screen.getPhase();
    }

    private Client getClientFromRequest(HttpHeaders headers) throws IOException {
        Client client;
        String deviceType = headers.getRequestHeaders().getFirst(Constants.DEVICE_TYPE_HEADER);
        Application application = applicationsManagementService.findByUuid(headers.getRequestHeaders().getFirst(Constants.APPLICATION_HEADER));
        if (Device.valueOf(deviceType).equals(Device.PHONE) || Device.valueOf(deviceType).equals(Device.TABLET)) {
            String deviceIdentifier = headers.getRequestHeaders().getFirst(Constants.DEVICE_IDENTIFIER_HEADER);
            String contextData = HttpUtils.getRequest(HttpUtils.buildUrl(
                    application.getConsumerProtocol(),
                    application.getConsumerHostname(),
                    application.getConsumerPort(),
                    application.getConsumerContextPath(),
                    "/api/data/device/" + deviceIdentifier + "/closestToTime/" + System.currentTimeMillis()
            ), null);
            client = new JsonContextParser().parse(contextData);
        } else {
            //pc or other
            client = new Client();
        }
        if (client != null) {
            client.setDevice(Device.valueOf(deviceType));
            client.setAction(headers.getRequestHeaders().getFirst(Constants.SCREEN_HEADER));
            client.setUsername(headers.getRequestHeaders().getFirst(Constants.USER_HEADER));
        }
        return client;
    }


}
