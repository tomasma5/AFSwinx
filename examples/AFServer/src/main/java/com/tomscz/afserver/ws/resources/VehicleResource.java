package com.tomscz.afserver.ws.resources;

import com.tomscz.afrest.AFRest;
import com.tomscz.afrest.AFRestGenerator;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afserver.manager.CountryManager;
import com.tomscz.afserver.manager.VehicleManager;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.UserRoles;
import com.tomscz.afserver.persistence.entity.Vehicle;
import com.tomscz.afserver.utils.AFServerConstants;
import com.tomscz.afserver.ws.security.AFSecurityContext;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

public class VehicleResource extends BaseResource {

    @Override
    public String getResourceUrl() {
        return  "/AFServer/rest/vehicle/";
    }

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
                AFSecurityContext securityContext = (AFSecurityContext) request
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
            AFMetaModelPack data = afSwing.generateSkeleton(Vehicle.class.getCanonicalName());

            HashMap<String, String> availableFlag = new HashMap<String, String>();
            // Define possibilities about active
            availableFlag.put("true", "true");
            availableFlag.put("false", "false");
            data.setOptionsToFields(availableFlag, "available");

            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * This method update or create vehicle. Manager will decide what to do.
     *
     * @param vehicle which will be created or updated.
     * @return Response with status code.
     */
    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public Response updateOrCreateVehicle(Vehicle vehicle) {
        try {
            VehicleManager<Vehicle> vehicleManager = getVehicleManager();
            vehicleManager.createOrupdate(vehicle);
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
    public Response deleteVehicle(@PathParam("id") int id) {
        try {
            VehicleManager<Vehicle> vehicleManager = getVehicleManager();
            Vehicle vehicleToDelete = vehicleManager.findById(id);
            if (vehicleToDelete == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            vehicleManager.delete(vehicleToDelete);
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
    public Response getAllVehicles() {
        try {
            VehicleManager<Vehicle> vehicleManager = getVehicleManager();
            List<Vehicle> vehicles = vehicleManager.findAllVehicles();
            final GenericEntity<List<Vehicle>> vehicleGeneric =
                    new GenericEntity<List<Vehicle>>(vehicles) {};
            return Response.status(Response.Status.OK).entity(vehicleGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getVehicle(@PathParam("id") int id) {
        try {
            VehicleManager<Vehicle> vehicleManager = getVehicleManager();
            Vehicle vehicle = vehicleManager.findById(id);
            return Response.status(Response.Status.OK).entity(vehicle).build();
        } catch (BusinessException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
