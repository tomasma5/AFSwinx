package com.tomscz.afserver.ws.resources;

import com.tomscz.afrest.AFRest;
import com.tomscz.afrest.AFRestGenerator;
import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afserver.manager.BusinessTripManager;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.*;
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

@Path("/businessTrip")
public class BusinessTripResource extends BaseResource {

    @Override
    public String getResourceUrl() {
        return  "/AFServer/rest/businessTrip/";
    }

    @GET
    @Path("/definition")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getDefinition(@javax.ws.rs.core.Context HttpServletRequest request) {
        try {
            AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
            AFMetaModelPack data =
                    afSwing.generateSkeleton(BusinessTrip.class.getCanonicalName());
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
            afSwing.setMainLayout("templates/structure.xml");
            AFMetaModelPack data = afSwing.generateSkeleton(BusinessTrip.class.getCanonicalName());
            try {
                List<Vehicle> vehicles = getVehicleManager().findAllVehicles();
                HashMap<String, String> options = new HashMap<>();
                for (Vehicle vehicle : vehicles) {
                    if(vehicle.isAvailable()) {
                        options.put(String.valueOf(vehicle.getId()), vehicle.toString());
                    }
                }
                data.setOptionsToFields(options, "vehicle");

                List<Country> countries = getCountryManager().findAllCountry();
                HashMap<String, String> countryOptions = new HashMap<>();
                for (Country country : countries) {
                    if(country.isActive()) {
                        countryOptions.put(String.valueOf(country.getId()), country.getName());
                    }
                }
                data.setOptionsToFields(countryOptions, "startPlace.country");
                data.setOptionsToFields(countryOptions, "endPlace.country");

                AFSecurityContext securityContex =
                        (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
                if (securityContex.isUserInRole(UserRoles.ADMIN)) {
                    HashMap<String, String> stateOptions = AFRestUtils.getDataInEnumClass(BusinessTripState.class
                                    .getCanonicalName());
                    data.setOptionsToFields(stateOptions, "status");
                } else {
                    HashMap<String, String> stateOptions = new HashMap<String, String>();
                    stateOptions.put(BusinessTripState.CANCELLED.name(), BusinessTripState.CANCELLED.name());
                    stateOptions.put(BusinessTripState.REQUESTED.name(), BusinessTripState.REQUESTED.name());
                    stateOptions.put(BusinessTripState.INPROGRESS.name(), BusinessTripState.REQUESTED.name());
                    stateOptions.put(BusinessTripState.FINISHED.name(), BusinessTripState.FINISHED.name());
                    data.setOptionsToFields(stateOptions, "status");
                }
            } catch (NamingException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public Response getUsersAllBusinessTrips() {
        try {
            List<BusinessTrip> btpInstances =
                    getBusinessTripManager().getAllBusinessTrips();
            final GenericEntity<List<BusinessTrip>> businessTripGeneric =
                    new GenericEntity<List<BusinessTrip>>(btpInstances) {};
            return Response.status(Response.Status.OK).entity(businessTripGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/user/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getUsersAllBusinessTrips(@javax.ws.rs.core.Context HttpServletRequest request,
                                         @PathParam("username") String username) {
        try {
            AFSecurityContext securityContex =
                    (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
            List<BusinessTrip> businessTrips =
                    getBusinessTripManager().getBusinessTripsForPerson(username, securityContex);
            final GenericEntity<List<BusinessTrip>> businessTripGeneric =
                    new GenericEntity<List<BusinessTrip>>(businessTrips) {};
            return Response.status(Response.Status.OK).entity(businessTripGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }


    @POST
    @Path("/add/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response createOrUpdateBusinessTrip(
            @javax.ws.rs.core.Context HttpServletRequest request,
            @PathParam("username") String username, BusinessTrip businessTrip) {
        try {
            AFSecurityContext securityContext =
                    (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
            getBusinessTripManager().createOrUpdate(businessTrip, username, securityContext);
            return Response.status(Response.Status.OK).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }

    @DELETE
    @Path("/remove/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public Response deleteBusinessTrip(@PathParam("id") int id) {
        try {
            BusinessTripManager<BusinessTrip> businessTripManager = getBusinessTripManager();
            BusinessTrip businessTripToDelete = businessTripManager.findById(id);
            if (businessTripToDelete == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            businessTripManager.delete(businessTripToDelete);
            return Response.status(Response.Status.OK).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }




}
