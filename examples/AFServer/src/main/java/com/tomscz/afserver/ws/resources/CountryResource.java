package com.tomscz.afserver.ws.resources;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
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

import com.tomscz.afrest.AFRest;
import com.tomscz.afrest.AFRestGenerator;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afserver.manager.CountryManager;
import com.tomscz.afserver.manager.StartUpBean;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.IdGenerator;
import com.tomscz.afserver.persistence.entity.Country;

@Path("/country")
public class CountryResource extends BaseResource {

    @javax.ws.rs.core.Context
    HttpServletRequest request;

    @Inject
    StartUpBean startUp;

    @GET
    @Path("/definition")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getResources() {
        try {
            AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
            AFMetaModelPack data = afSwing.generateSkeleton(Country.class.getCanonicalName());
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateCountry(Country country) {
        try {
            CountryManager<Country> countryManager = getCountryManager();
            if (country.getId() == 0) {
                country.setId(IdGenerator.getNextCountryId());
            }
            countryManager.createOrupdate(country);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/remove/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteCountry(@PathParam("id") int id) {
        try {
            CountryManager<Country> countryManager = getCountryManager();
            Country coutryToDelete = countryManager.findById(id);
            if (coutryToDelete == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            countryManager.delete(coutryToDelete);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getAllCountries() {
        try {
            CountryManager<Country> countryManager = getCountryManager();
            List<Country> countries = countryManager.findAllCountry();
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
    public Response getCountry(@PathParam("id") int id) {
        try {
            CountryManager<Country> countryManager = getCountryManager();
            Country country = countryManager.findById(id);
            return Response.status(Response.Status.OK).entity(country).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
