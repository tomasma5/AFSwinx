package rest;

import org.bson.types.ObjectId;
import service.rest.ComponentResourceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Component resources endpoint - returns back component definitions, data or send it to AFServer
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@Path("/connection")
public class ComponentResourcesEndpoint {

    @Inject
    private ComponentResourceService componentResourceService;

    @GET
    @Path("/model/component/{component_id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getModelDefinition( @PathParam("component_id") ObjectId componentId) {
        //TODO implement me
        return null;
    }

    @GET
    @Path("/data/component/{component_id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getComponentData(@PathParam("component_id") ObjectId componentId) {
        //TODO implement me
        return null;
    }

    @POST
    @Path("/send/component/{component_id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void sendComponentData(@PathParam("component_id") ObjectId componentId) {
        //TODO implement me
    }
}
