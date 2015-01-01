package com.tomscz.afserver.ws.resources;

import java.util.HashMap;
import java.util.List;

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
import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.AbsenceInstance;
import com.tomscz.afserver.persistence.entity.AbsenceInstanceState;
import com.tomscz.afserver.persistence.entity.AbsenceType;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.persistence.entity.UserRoles;
import com.tomscz.afserver.utils.AFServerConstants;
import com.tomscz.afserver.ws.security.AFSecurityContext;

@Path("/absenceInstance")
public class AbsenceInstanceResource extends BaseResource {

    @GET
    @Path("/definitionAdd/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getDefinitionToCreateAI(@javax.ws.rs.core.Context HttpServletRequest request,
            @PathParam("userName") String userName) {
        try {
            AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
            String mainlayout = "templates/oneColumnLayout.xml";
            afSwing.setMapping("absenceInstanceAdd.xml");
            HashMap<String, String> customStructureMapping = new HashMap<String, String>();
            customStructureMapping.put("absenceType", "absenceInstanceType.xml");
            customStructureMapping.put(AbsenceInstance.class.getCanonicalName(),
                    "absenceInstanceAdd.xml");
            AFMetaModelPack data =
                    afSwing.generateSkeleton(AbsenceInstance.class.getCanonicalName(),
                            customStructureMapping, mainlayout);
            try {
                Person person = getPersonManager().findUser(userName);
                List<AbsenceType> absenceTypesInCountry =
                        getAbsenceTypeManager().findAbsenceTypeInCountry(
                                person.getCountry().getId());
                HashMap<String, String> options = new HashMap<String, String>();
                for (AbsenceType absenceType : absenceTypesInCountry) {
                    options.put(String.valueOf(absenceType.getId()), absenceType.getName());
                }
                data.setOptionsToFields(options, "absenceType.id");
            } catch (BusinessException e) {
                return Response.status(e.getStatus()).build();
            } catch (NamingException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/definition/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getDefinitionToShowAbsenceInstanceByUser(
            @javax.ws.rs.core.Context HttpServletRequest request,
            @PathParam("userName") String userName) {
        try {
            AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
            AFMetaModelPack data =
                    afSwing.generateSkeleton(AbsenceInstance.class.getCanonicalName());
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/definitionManaged/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getDefinitionToManageAbsenceInstance(
            @javax.ws.rs.core.Context HttpServletRequest request,
            @PathParam("userName") String userName) {
        try {
            AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
            HashMap<String, String> customMapping = new HashMap<String, String>();
            customMapping.put(AbsenceInstance.class.getCanonicalName(),
                    "absenceInstanceManagement.config.xml");
            customMapping.put("affectedPerson", "absence.instance.inner.simple.xml");
            customMapping.put("absenceType", "absence.instance.inner.simple.xml");
            customMapping.put("country", "absence.instance.inner.simple.xml");
            AFMetaModelPack data =
                    afSwing.generateSkeleton(AbsenceInstance.class.getCanonicalName(),
                            customMapping, "");
            AFSecurityContext securityContex =
                    (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
            if (securityContex.isUserInRole(UserRoles.ADMIN)) {
                HashMap<String, String> stateOptions;
                stateOptions =
                        AFRestUtils.getDataInEnumClass(AbsenceInstanceState.class
                                .getCanonicalName());
                data.setOptionsToFields(stateOptions, "status");
            } else {
                HashMap<String, String> stateOptions = new HashMap<String, String>();
                stateOptions.put(AbsenceInstanceState.CANCELLED.name(), AbsenceInstanceState.CANCELLED.name());
                stateOptions.put(AbsenceInstanceState.REQUESTED.name(), AbsenceInstanceState.REQUESTED.name());
                data.setOptionsToFields(stateOptions, "status");
            }
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/user/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getUsersAllInstances(@javax.ws.rs.core.Context HttpServletRequest request,
            @PathParam("username") String username) {
        try {
            AFSecurityContext securityContex =
                    (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
            List<AbsenceInstance> usersInstances =
                    getAbsenceInstantManager().findInstanceByUser(username, securityContex);
            final GenericEntity<List<AbsenceInstance>> absenceInstanceGeneric =
                    new GenericEntity<List<AbsenceInstance>>(usersInstances) {};
            return Response.status(Response.Status.OK).entity(absenceInstanceGeneric).build();
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
    public Response createOrUpdateAbsenceInstance(
            @javax.ws.rs.core.Context HttpServletRequest request,
            @PathParam("username") String username, AbsenceInstance absenceInstance) {
        try {
            AFSecurityContext securityContex =
                    (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
            getAbsenceInstantManager().createOrUpdate(absenceInstance, username, securityContex);
            return Response.status(Response.Status.OK).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }

    @GET
    @Path("/editable/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getUsersInstancesManaged(@javax.ws.rs.core.Context HttpServletRequest request,
            @PathParam("username") String username) {
        try {
            AFSecurityContext securityContex =
                    (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
            List<AbsenceInstance> usersInstances =
                    getAbsenceInstantManager().findEditableInstanceByUser(username, securityContex);
            final GenericEntity<List<AbsenceInstance>> absenceInstanceGeneric =
                    new GenericEntity<List<AbsenceInstance>>(usersInstances) {};
            return Response.status(Response.Status.OK).entity(absenceInstanceGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }

    @Override
    public String getResourceUrl() {
        return "/AFServer/rest/absenceInstance/";
    }

}
