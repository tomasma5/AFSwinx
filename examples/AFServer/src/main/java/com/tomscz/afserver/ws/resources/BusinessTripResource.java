package com.tomscz.afserver.ws.resources;

import com.tomscz.afrest.AFRest;
import com.tomscz.afrest.AFRestGenerator;
import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afrest.commons.SupportedValidations;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.layout.Layout;
import com.tomscz.afrest.layout.definitions.LabelPosition;
import com.tomscz.afrest.layout.definitions.LayouDefinitions;
import com.tomscz.afrest.layout.definitions.LayoutOrientation;
import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.AFValidationRule;
import com.tomscz.afserver.manager.BusinessTripManager;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.*;
import com.tomscz.afserver.utils.AFServerConstants;
import com.tomscz.afserver.utils.Utils;
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
            afSwing.setMainLayout("templates/oneColumnLayout.xml");

            AFMetaModelPack data = afSwing.generateSkeleton(BusinessTrip.class.getCanonicalName());
            try {
                //supply vehicles dropdown menu
                AFFieldInfo fieldInfo = Utils.getFieldInfoById(data.getClassInfo(), "vehicle");
                if(fieldInfo != null) {
                    fieldInfo.addRule(new AFValidationRule(SupportedValidations.REQUIRED, "true"));
                    fieldInfo.setWidgetType(SupportedWidgets.DROPDOWNMENU);
                    fieldInfo.setLabel("businessTrip.vehicle");
                    Layout layout = new Layout();
                    layout.setLabelPosstion(LabelPosition.BEFORE);
                    layout.setLayoutDefinition(LayouDefinitions.ONECOLUMNLAYOUT);
                    layout.setLayoutOrientation(LayoutOrientation.AXISY);
                    fieldInfo.setLayout(layout);
                    List<Vehicle> vehicles = getVehicleManager().findAllVehicles();
                    HashMap<String, String> options = new HashMap<String, String>();
                    for (Vehicle vehicle : vehicles) {
                        if (vehicle.isAvailable()) {
                            options.put(String.valueOf(vehicle.getName()), vehicle.getName());
                        }
                    }
                    data.setOptionsToFields(options, "vehicle");
                }
                //set total distance readonly
                fieldInfo = Utils.getFieldInfoById(data.getClassInfo(), "totalDistance");
                if(fieldInfo != null){
                    fieldInfo.setReadOnly(true);
                }

                //supply countries for address country drowdowns
                List<Country> countries = getCountryManager().findAllCountry();
                HashMap<String, String> countryOptions = new HashMap<>();
                for (Country country : countries) {
                    if(country.isActive()) {
                        countryOptions.put(country.getName(), country.getName());
                    }
                }
                data.setOptionsToFields(countryOptions, "startPlace.country");
                data.setOptionsToFields(countryOptions, "endPlace.country");

                //supply statuses for different roles
                AFSecurityContext securityContex =
                        (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
                if (securityContex.isUserInRole(UserRoles.ADMIN)) {
                    HashMap<String, String> stateOptions = AFRestUtils.getDataInEnumClass(BusinessTripState.class
                                    .getCanonicalName());
                    data.setOptionsToFields(stateOptions, "status");
                } else {
                    HashMap<String, String> stateOptions = new HashMap<String, String>();
                    stateOptions.put(BusinessTripState.REQUESTED.toString(), BusinessTripState.REQUESTED.toString());
                    stateOptions.put(BusinessTripState.CANCELLED.toString(), BusinessTripState.CANCELLED.toString());
                    stateOptions.put(BusinessTripState.INPROGRESS.toString(), BusinessTripState.INPROGRESS.toString());
                    stateOptions.put(BusinessTripState.FINISHED.toString(), BusinessTripState.FINISHED.toString());
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
