package com.tomscz.afserver.ws.resources;

import java.util.HashMap;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.AbsenceInstance;
import com.tomscz.afserver.persistence.entity.AbsenceType;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.utils.AFServerConstants;
import com.tomscz.afserver.ws.security.AFSecurityContext;

@Path("/absenceInstance")
public class AbsenceInstanceResource extends BaseResource {


    @GET
    @Path("/definitionAdd/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getResources(@javax.ws.rs.core.Context HttpServletRequest request,
            @PathParam("userName") String userName) {
        try {
            AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
            String mainlayout = "templates/oneColumnLayout.xml";
            HashMap<String, String> customStructureMapping = new HashMap<String, String>();
            customStructureMapping.put("absenceType",
                    "absenceInstanceType.xml");
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
    @Path("/user/{userName}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getUsersInstances(@javax.ws.rs.core.Context HttpServletRequest request,
            @PathParam("userName") String userName) {
        try {
            AFSecurityContext securityContex =
                    (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
            List<AbsenceInstance> usersInstances =
                    getAbsenceInstantManager().findInstanceByUser(userName, securityContex);
            final GenericEntity<List<AbsenceInstance>> absenceInstanceGeneric =
                    new GenericEntity<List<AbsenceInstance>>(usersInstances) {};
            return Response.status(Response.Status.OK).entity(absenceInstanceGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }


}
