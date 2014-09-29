package com.tomscz.afserver.ws.resources;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;
import com.tomscz.afi.exceptions.SkeletonException;
import com.tomscz.afi.inspector.AFRestSwing;

@Path("/")
public class AFRootResource {

    @javax.ws.rs.core.Context HttpServletRequest request;
    
    @Resource
    private WebServiceContext wsContext;
    
    @GET
    @Path("/{param}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getResources(@PathParam("param") String entityClass) {
        
        try {
            AFRestSwing afSwing = new AFRestSwing(request.getSession().getServletContext());
            afSwing.generateSkeleton(entityClass, null, request.getSession().getServletContext());
            //TODO Return object
        } catch (SkeletonException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }
}
