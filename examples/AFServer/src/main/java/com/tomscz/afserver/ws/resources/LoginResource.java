package com.tomscz.afserver.ws.resources;

import com.tomscz.afrest.AFRestGenerator;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afserver.manager.PersonManager;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Country;
import com.tomscz.afserver.persistence.entity.Gender;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.utils.AFServerConstants;
import com.tomscz.afserver.view.loginForm.LoginFormDefinitions;
import com.tomscz.afserver.ws.security.AFSecurityContext;

import javax.annotation.security.RolesAllowed;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

@Path("/login")
public class LoginResource extends BaseResource {

    @GET
    @Path("/definition")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getLoginFormResource(@Context HttpServletRequest request) {
        try {
            AFRestGenerator afRest = new AFRestGenerator(request.getSession().getServletContext());
            String fullClassName = LoginFormDefinitions.class.getCanonicalName();
            afRest.setMainLayout("templates/oneColumnLayout.xml");
            AFMetaModelPack data = afRest.generateSkeleton(fullClassName);
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response login(LoginFormDefinitions loginForm) {
        try {
            Person loggedPerson =
                    getPersonManager().findUser(loginForm.getUsername(), loginForm.getPassword());
            if (loggedPerson != null) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).build();
            }
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public String getResourceUrl() {
        return "/AFServer/rest/login/";
    }

    @Override
    protected Class getModelClass() {
        return LoginFormDefinitions.class;
    }
}
