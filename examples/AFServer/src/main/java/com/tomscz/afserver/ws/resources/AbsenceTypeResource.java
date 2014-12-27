package com.tomscz.afserver.ws.resources;

import java.util.HashMap;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import com.tomscz.afrest.commons.SupportedValidations;
import com.tomscz.afrest.commons.SupportedWidgets;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFClassInfo;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afrest.rest.dto.AFValidationRule;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.AbsenceType;
import com.tomscz.afserver.persistence.entity.Country;

@Path("/absencetype")
public class AbsenceTypeResource extends BaseResource {

    @GET
    @Path("/definition")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @PermitAll
    public Response getResources(@javax.ws.rs.core.Context HttpServletRequest request) {
        try {
            AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
            afSwing.setMainLayout("templates/oneColumnLayout.xml");
            AFMetaModelPack data = afSwing.generateSkeleton(AbsenceType.class.getCanonicalName());
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/definition/supportedCountries")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @PermitAll
    public Response getCountriesToAbsenceType(@javax.ws.rs.core.Context HttpServletRequest request) {
        try {
            AFMetaModelPack data = new AFMetaModelPack();
            AFClassInfo classInfo = new AFClassInfo();
            classInfo.setName("");
            AFFieldInfo fieldInfo = new AFFieldInfo();
            fieldInfo.setId("country");
            fieldInfo.addRule(new AFValidationRule(SupportedValidations.REQUIRED, "true"));
            fieldInfo.setWidgetType(SupportedWidgets.DROPDOWNMENU);
            List<Country> supportedCountries = getCountryManager().findAllCountry();
            classInfo.addFieldInfo(fieldInfo);
            data.setClassInfo(classInfo);
            HashMap<String, String> options = new HashMap<String, String>();
            for (Country country : supportedCountries) {
                options.put(String.valueOf(country.getId()), country.getName());
            }
            data.setOptionsToFields(options, "country");
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/country/{countryId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @PermitAll
    public Response getAbsenceTypesBasedOnCountry(@PathParam("countryId") int countryId) {
        try {
            List<AbsenceType> absenceTypes =
                    getAbsenceTypeManager().findAbsenceTypeInCountry(countryId);
            final GenericEntity<List<AbsenceType>> absenceTypeGeneric =
                    new GenericEntity<List<AbsenceType>>(absenceTypes) {};
            return Response.status(Response.Status.OK).entity(absenceTypeGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }
    
    @POST
    @Path("/country/{countryId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public Response createOrUpdateAbsenceType(@PathParam("countryId") int countryId, AbsenceType absenceType) {
        try {
            getAbsenceTypeManager().updateAbsenceTypeBasedOnCountry(absenceType, countryId);
            return Response.status(Response.Status.OK).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }


}
