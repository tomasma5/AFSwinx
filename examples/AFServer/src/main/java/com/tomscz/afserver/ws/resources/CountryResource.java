package com.tomscz.afserver.ws.resources;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tomscz.afrest.AFRestSwing;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.exceptions.AFRestException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afserver.manager.CountryInterfaceIn;
import com.tomscz.afserver.manager.CountryManager;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Country;

@Path("/country")
public class CountryResource {

    @javax.ws.rs.core.Context
    HttpServletRequest request;

    @GET
    @Path("/definition")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getResources(@javax.ws.rs.core.Context HttpServletRequest requestObject) {
        try {
            AFRestSwing afSwing = new AFRestSwing(requestObject.getSession().getServletContext());
            AFMetaModelPack data = afSwing.generateSkeleton(Country.class.getCanonicalName());
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException | AFRestException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateCountry(Country country) {
           CountryManager cm = new CountryManager();
           try {
            cm.createOrupdate(country);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }

    @DELETE
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteCountry(Country country) {
           CountryManager cm = new CountryManager();
           try {
            cm.delete(country);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }
    
    
    @SuppressWarnings("unchecked")
    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getAllCountries() {       
        try {
            Context ctx = new InitialContext();
            CountryInterfaceIn<Country> cmm = (CountryInterfaceIn<Country>) ctx.lookup("java:global/AFServer/CountryManager");
            List<Country> countries = cmm.findAllCountry();
            final GenericEntity<List<Country>> countryGeneric =
                    new GenericEntity<List<Country>>(countries) {};
                    return Response.status(Response.Status.OK).entity(countryGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }        
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getCountry(@PathParam("param") int id) {
        try {
            CountryManager cm = new CountryManager();
            Country country = cm.findById(id);
            return Response.status(Response.Status.OK).entity(country).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
