package com.tomscz.afserver.ws.resources;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tomscz.afrest.AFRestSwing;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.exceptions.AFRestException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afserver.manager.PersonManager;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.view.loginForm.LoginFormDefinitions;

@Path("/users/")
public class UserResource extends BaseResource{    
    
    @GET
    @Path("/{param}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getResources(@javax.ws.rs.core.Context HttpServletRequest request, @PathParam("param") String type) {
        try {
            String fullClassName;
            //add if-else if you want add more form type definition
            if(type.equals(LoginFormDefinitions.LOGIN_FORM)){
                fullClassName = LoginFormDefinitions.class.getCanonicalName();
            }
            else if(type.equals("create")){
                fullClassName = Person.class.getCanonicalName();
            }
            else{
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            AFRestSwing afSwing = new AFRestSwing(request.getSession().getServletContext());
            AFMetaModelPack data = afSwing.generateSkeleton(fullClassName);
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (AFRestException | MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @POST
    @Path("/login")
    @Produces({ MediaType.APPLICATION_JSON})
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response login(LoginFormDefinitions loginForm){
        return Response.status(Response.Status.OK).build();
    }
    
    @POST
    @Path("/update")
    @Produces({ MediaType.APPLICATION_JSON})
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response update(Person personToAdd){
        return Response.status(Response.Status.OK).build();
    }
    
    @POST
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON})
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response create(Person personToAdd){
        try {
            PersonManager<Person> personManager = getPersonManager();
            personManager.createOrupdate(personToAdd);
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }
    
    @GET
    @Path("/allUsers/{countryId}")
    @Produces({ MediaType.APPLICATION_JSON})
    @Consumes({ MediaType.APPLICATION_JSON})
    public Response getAllUsersByCountry(@PathParam("param") int countryId){
        return Response.status(Response.Status.OK).build();
    }
    
}
