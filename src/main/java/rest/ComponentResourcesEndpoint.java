package rest;

import org.bson.types.ObjectId;
import service.exception.ComponentRequestException;
import service.exception.ServiceException;
import service.rest.ComponentResourceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * Component resources endpoint - returns back component definitions, data or send it to AFServer
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@Path("/connections")
public class ComponentResourcesEndpoint {

    @Inject
    private ComponentResourceService componentResourceService;

    @GET
    @Path("/model/component/{component_id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getModelDefinition(@Context HttpHeaders headers, @PathParam("component_id") ObjectId componentId) throws ComponentRequestException, ServiceException {
        return componentResourceService.getComponentModel(componentId, headers);
    }

    @GET
    @Path("/data/component/{component_id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getComponentData(@Context HttpHeaders headers, @PathParam("component_id") ObjectId componentId) throws ComponentRequestException {
        return componentResourceService.getComponentData(componentId, headers);
    }

    @POST
    @Path("/send/component/{component_id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String sendComponentData(String data, @Context HttpHeaders headers, @PathParam("component_id") ObjectId componentId) throws ComponentRequestException {
        return componentResourceService.sendComponentData(componentId, headers, data);
    }
}
