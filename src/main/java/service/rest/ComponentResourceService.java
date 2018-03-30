package service.rest;

import org.bson.types.ObjectId;
import service.exception.ComponentRequestException;
import service.exception.ServiceException;

import javax.ws.rs.core.HttpHeaders;

/**
 * Service for getting component resources
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ComponentResourceService {

    /**
     * Gets component model from "real" server where the actual models are.
     *
     * @param componentId the component id
     * @param headers     the headers
     * @return the component model
     * @throws ComponentRequestException the component request exception
     * @throws ServiceException          the service exception
     */
    public String getComponentModel(ObjectId componentId, HttpHeaders headers) throws ComponentRequestException, ServiceException;

    /**
     * Gets component data from "real" server where the actual models are.
     *
     * @param componentId the component id
     * @param headers     the headers
     * @return the component data
     * @throws ComponentRequestException the component request exception
     */
    public String getComponentData(ObjectId componentId, HttpHeaders headers) throws ComponentRequestException;

    /**
     * Send component data string from "real" server where the actual models are.
     *
     * @param componentId the component id
     * @param headers     the headers
     * @param data        the data
     * @return the string
     * @throws ComponentRequestException the component request exception
     */
    public String sendComponentData(ObjectId componentId, HttpHeaders headers, String data) throws ComponentRequestException;

}
