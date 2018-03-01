package com.tomscz.afserver.ws.resources;

import com.tomscz.afrest.AFRest;
import com.tomscz.afrest.AFRestGenerator;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afserver.manager.BusinessTripPartManager;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.BusinessTrip;
import com.tomscz.afserver.persistence.entity.BusinessTripPart;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.Vehicle;
import com.tomscz.afserver.utils.AFServerConstants;
import com.tomscz.afserver.ws.security.AFSecurityContext;

import javax.annotation.security.RolesAllowed;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

@Path("/businessTripPart")
public class BusinessTripPartResource extends BaseResource {

    @Override
    public String getResourceUrl() {
        return  "/AFServer/rest/businessTripPart/";
    }

    @GET
    @Path("/definition")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getDefinition(@javax.ws.rs.core.Context HttpServletRequest request) {
        try {
            AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
            AFMetaModelPack data =
                    afSwing.generateSkeleton(BusinessTripPart.class.getCanonicalName());
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/definitionAdd")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getAddDefinition(@javax.ws.rs.core.Context HttpServletRequest request) {
        try {
            AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
            String mainlayout = "templates/structure.xml";
            HashMap<String, String> customStructureMapping = new HashMap<>();
            AFMetaModelPack data = afSwing.generateSkeleton(BusinessTripPart.class.getCanonicalName(),
                    customStructureMapping, mainlayout);
            List<Country> countries = getCountryManager().findAllCountry();
            HashMap<String, String> countryOptions = new HashMap<>();
            for (Country country : countries) {
                if(country.isActive()) {
                    countryOptions.put(country.getName(), country.getName());
                }
            }
            data.assignOptionsToFields(countryOptions, "startPlace.country");
            data.assignOptionsToFields(countryOptions, "endPlace.country");
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException | NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/list/{businessTripId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public Response getUsersAllBusinessTrips(@PathParam("businessTripId") int businessTripId) {
        try {
            List<BusinessTripPart> btpInstances =
                    getBusinessTripPartManager().getAllBusinessTripParts(businessTripId);
            final GenericEntity<List<BusinessTripPart>> businessTripGeneric =
                    new GenericEntity<List<BusinessTripPart>>(btpInstances) {};
            return Response.status(Response.Status.OK).entity(businessTripGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }

    @GET
    @Path("/user/{username}/list/{businessTripId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getUsersAllBusinessTrips(@javax.ws.rs.core.Context HttpServletRequest request,
                                         @PathParam("username") String username, @PathParam("businessTripId") int businessTripId) {
        try {
            AFSecurityContext securityContex =
                    (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
            List<BusinessTripPart> businessTripParts =
                    getBusinessTripPartManager().getBusinessTripsPartsForPerson(username, businessTripId, securityContex);
            final GenericEntity<List<BusinessTripPart>> businessTripPartGeneric =
                    new GenericEntity<List<BusinessTripPart>>(businessTripParts) {};
            return Response.status(Response.Status.OK).entity(businessTripPartGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }


    @POST
    @Path("/user/{username}/add/{businessTripId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response createOrUpdateBusinessTripPart(
            @javax.ws.rs.core.Context HttpServletRequest request,
            @PathParam("username") String username, @PathParam("businessTripId") int businessTripId, BusinessTripPart businessTripPart) {
        try {
            AFSecurityContext securityContext =
                    (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
            getBusinessTripPartManager().createOrUpdate(businessTripPart, businessTripId, username, securityContext);
            return Response.status(Response.Status.OK).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }

    @DELETE
    @Path("/user/{username}/remove/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response deleteBusinessTrip(@javax.ws.rs.core.Context HttpServletRequest request,
                                       @PathParam("username") String username,
                                       @PathParam("id") int id) {
        try {
            AFSecurityContext securityContex =
                    (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
            BusinessTripPartManager<BusinessTripPart> businessTripPartManager = getBusinessTripPartManager();
            businessTripPartManager.remove(id, username, securityContex);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
