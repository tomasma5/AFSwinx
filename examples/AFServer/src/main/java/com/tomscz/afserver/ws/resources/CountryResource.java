package com.tomscz.afserver.ws.resources;

import java.util.HashMap;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.UserRoles;
import com.tomscz.afserver.utils.AFServerConstants;
import com.tomscz.afserver.ws.security.AFSecurityContext;

/**
 * This is country resource. This resource provide definition and end points with data and method to
 * work with country.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
@Path("/country")
public class CountryResource extends BaseResource {

    /**
     * This method create definition based on is build form and table with countries. Based on user
     * role is form editable or not.
     * 
     * @param request current request
     * @return Response which contain definition. It is used to build form and table.
     */
    @GET
    @Path("/definition")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getResources(@javax.ws.rs.core.Context HttpServletRequest request) {
        try {
            HashMap<String, Object> readOnlyVariables = new HashMap<String, Object>();
            // If user was authenticate and is admin then
            if (request.getAttribute(AFServerConstants.SECURITY_CONTEXT) != null) {
                AFSecurityContext securityContext =
                        (AFSecurityContext) request
                                .getAttribute(AFServerConstants.SECURITY_CONTEXT);
                if (!securityContext.isUserInRole(UserRoles.ADMIN)) {
                    readOnlyVariables.put("readonly", "true");
                }
            } else {
                // otherwise all fields will be readonly
                readOnlyVariables.put("readonly", "true");
            }
            AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
            afSwing.setVariablesToContext(readOnlyVariables);
            afSwing.setMainLayout("templates/oneColumnLayout.xml");
            AFMetaModelPack data = afSwing.generateSkeleton(Country.class.getCanonicalName());
            HashMap<String, String> activeFlag = new HashMap<String, String>();
            // Define possibilities about active
            activeFlag.put("true", "true");
            activeFlag.put("false", "false");
            data.setOptionsToFields(activeFlag, "active");
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method update or create country. Manager will decide what to do.
     * 
     * @param country which will be created or updated.
     * @return Response with status code.
     */
    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public Response updateOrCreateCountry(Country country) {
        try {
            CountryManager<Country> countryManager = getCountryManager();
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
    @RolesAllowed({"admin"})
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
    @PermitAll
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

    @Override
    public String getResourceUrl() {
        return "/AFServer/rest/country/";
    }

}
