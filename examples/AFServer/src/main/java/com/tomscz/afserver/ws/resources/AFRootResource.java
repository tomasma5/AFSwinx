package com.tomscz.afserver.ws.resources;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;

import com.google.gson.Gson;
import com.tomscz.afi.exceptions.AFRestException;
import com.tomscz.afi.inspector.AFRestSwing;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.ws.resources.mapper.PersonMapper;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.rest.dto.data.AFData;
import com.tomscz.afswinx.rest.dto.data.AFDataPack;

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
            AFMetaModelPack data = afSwing.generateSkeleton(entityClass, null, request.getSession().getServletContext());
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (AFRestException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GET
    @Path("/{param}/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getResources(@PathParam("id") int id) {
        try{
            Person p = new Person(); 
            p.setFirstName("Martin");
            p.setLastName("Tomasek");
            AFRestSwing afSwing = new AFRestSwing(request.getSession().getServletContext());
            AFDataPack data = afSwing.generateDataObject(p.getClass(), p);
            return Response.status(Response.Status.OK).entity(data).build();
        }
        catch(AFRestException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("/{param}")
    @Produces({ MediaType.APPLICATION_JSON})
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response add(PersonMapper personMapper){
        throw new UnsupportedOperationException("Not supported yet");
    }
}
