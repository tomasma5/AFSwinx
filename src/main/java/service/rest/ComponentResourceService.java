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

    public String getComponentModel(ObjectId componentId, HttpHeaders headers) throws ComponentRequestException, ServiceException;

    public String getComponentData(ObjectId componentId, HttpHeaders headers) throws ComponentRequestException;

    public String sendComponentData(ObjectId componentId, HttpHeaders headers, String data) throws ComponentRequestException;

}
