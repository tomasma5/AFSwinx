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

import com.google.gson.Gson;
import com.tomscz.afi.exceptions.SkeletonException;
import com.tomscz.afi.inspector.AFRestSwing;
import com.tomscz.afswinx.rest.dto.AFRestDataPackage;

@Path("/")
public class AFRootResource {

    @javax.ws.rs.core.Context HttpServletRequest request;
    
    @Resource
    private WebServiceContext wsContext;
    
    @GET
    @Path("/{param}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getResources(@PathParam("param") String entityClass) {
        
        try {
            AFRestSwing afSwing = new AFRestSwing(request.getSession().getServletContext());
            AFRestDataPackage data = afSwing.generateSkeleton(entityClass, null, request.getSession().getServletContext());
            Gson gson = new Gson();
            String responseData = gson.toJson(data);
            return Response.status(Response.Status.OK).entity(responseData).build();
        } catch (SkeletonException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
