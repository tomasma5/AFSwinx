package service.rest.impl;

import com.google.gson.Gson;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import dao.ComponentResourceDao;
import model.Application;
import model.ComponentResource;
import model.Screen;
import model.afclassification.*;
import org.bson.types.ObjectId;
import rest.context.JsonContextParser;
import rest.security.RequestContext;
import service.afclassification.computational.AFClassification;
import service.exception.ComponentRequestException;
import service.exception.ServiceException;
import service.rest.ComponentResourceService;
import service.servlet.BusinessCaseManagementService;
import service.servlet.ScreenManagementService;
import utils.Constants;
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
    BusinessCaseManagementService businessCaseManagementService;

    @Inject
    ScreenManagementService screenManagementService;

    @Inject
    RequestContext requestContext;

    @Override
    public String getComponentModel(ObjectId id, HttpHeaders headers) throws ComponentRequestException {
        Application application = requestContext.getCurrentApplication();
        ComponentResource componentResource = componentResourceDao.findById(id);
        if (componentResource != null && application != null) {
            if (!componentResource.getApplicationId().equals(application.getId())) {
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
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public String getComponentData(ObjectId id, HttpHeaders headers) throws ComponentRequestException {
        String realEndpoint = checkRealEndpointUrlPresence(headers);

        ComponentResource componentResource = componentResourceDao.findById(id);
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
    public String sendComponentData(ObjectId id, HttpHeaders headers, String data) throws ComponentRequestException {
        String realEndpoint = checkRealEndpointUrlPresence(headers);
        ComponentResource componentResource = componentResourceDao.findById(id);
        String response = null;
        if (componentResource != null) {
            try {
                response = HttpUtils.postRequest(realEndpoint, headers.getRequestHeaders(), data);
            } catch (IOException e) {
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

    private AFMetaModelPack getFilteredComponentModel(HttpHeaders headers, String modelStr, Gson gson) throws ServiceException, IOException {
        BCPhase phase = getBusinessPhaseFromRequest(headers);
        Client client = getClientFromRequest(headers);
        AFMetaModelPack metaModel = gson.fromJson(modelStr, AFMetaModelPack.class);
        AFClassification classification = new AFClassification();
        classification.classifyMetaModel(metaModel, client, phase);
        return metaModel;
    }

    private BCPhase getBusinessPhaseFromRequest(HttpHeaders headers) throws ServiceException {
        String screenKey = headers.getRequestHeaders().getFirst(Constants.SCREEN_HEADER);
        Screen screen = screenManagementService.findScreenByKey(screenKey);
        return businessCaseManagementService.findPhaseById(screen.getBusinessCaseId(), screen.getPhaseId());
    }

    private Client getClientFromRequest(HttpHeaders headers) throws IOException {
        Client client;
        String deviceType = headers.getRequestHeaders().getFirst(Constants.DEVICE_TYPE_HEADER);
        String deviceIdentifier = headers.getRequestHeaders().getFirst(Constants.DEVICE_IDENTIFIER_HEADER);
        if (Device.valueOf(deviceType).equals(Device.PHONE) || Device.valueOf(deviceType).equals(Device.TABLET)) {
            String contextData = HttpUtils.getRequest(HttpUtils.buildUrl(
                    "http",
                    "localhost",
                    8082,
                    "/NSRest",
                    "/api/data/device/" + deviceIdentifier + "/closestToTime/" + System.currentTimeMillis()
            ), null);
            client = new JsonContextParser().parse(contextData);
        } else {
            //pc or other
            client = new Client();
        }
        if (client != null) {
            client.setDevice(Device.valueOf(deviceType));
        }
        return client;
    }


}
