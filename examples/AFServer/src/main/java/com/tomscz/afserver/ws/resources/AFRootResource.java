package com.tomscz.afserver.ws.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tomscz.afserver.ws.resources.mapping.RootMapping;

@Path("/")
public class AFRootResource extends BaseResource{

    @GET
    @Produces({MediaType.TEXT_HTML})
    @Path("/")
    public Response webPage() {
        URI uri;
        try {
            uri = new URI("http://localhost:8080/AFServer/welcome.xhtml");
            return Response.seeOther(uri).build();
        } catch (URISyntaxException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getResources() {
        List<RootMapping> rootMapping = new ArrayList<RootMapping>();
        AFApplication application = new AFApplication();
        Set<Object> resources = application.getSingletons();
        for (Object resource : resources) {
            try {
                BaseResource concreteResource = (BaseResource) resource;
                RootMapping rm = new RootMapping();
                rm.setResourceName(concreteResource.getClass().getName());
                rm.setResourceUrl(concreteResource.getResourceUrl());
                rootMapping.add(rm);
            } catch (ClassCastException e) {
                // DO NOTHING This resource wont be in root resource
            }
        }
        final GenericEntity<List<RootMapping>> rootGeneric =
                new GenericEntity<List<RootMapping>>(rootMapping) {};
        return Response.status(Response.Status.OK).entity(rootGeneric).build();
    }

    @Override
    public String getResourceUrl() {
        return "/AFServer/rest/";
    }

}
