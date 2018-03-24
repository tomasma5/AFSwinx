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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

@Path("/users")
public class UserResource extends BaseResource {

    private static final String PROFILE = "profile";

    @GET
    @Path("/" + PROFILE)
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getProfileResource(@javax.ws.rs.core.Context HttpServletRequest request) {
        try {
            AFRestGenerator afRest = new AFRestGenerator(request.getSession().getServletContext());
            String fullClassName = Person.class.getCanonicalName();
            afRest.setMainLayout("templates/oneColumnLayout.xml");

            AFMetaModelPack data = afRest.generateSkeleton(fullClassName);
            try {
                List<Country> countries = getCountryManager().findAllCountry();
                HashMap<String, String> countriesToChoose = new HashMap<String, String>();
                for (Country country : countries) {
                    countriesToChoose.put(country.getName(), country.getName());
                }
                data.assignOptionsToFields(countriesToChoose, "myAddress.country");
                HashMap<String, String> genderOptions = new HashMap<String, String>();
                genderOptions.put(Gender.MALE.name(), Gender.MALE.name());
                genderOptions.put(Gender.FEMALE.name(), Gender.FEMALE.name());
                data.assignOptionsToFields(genderOptions, "gender");
            } catch (NamingException e) {
                // Do nothing.
            }
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/update")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response update(@javax.ws.rs.core.Context HttpServletRequest request, Person personToAdd) {
        AFSecurityContext securityContext =
                (AFSecurityContext) request.getAttribute(AFServerConstants.SECURITY_CONTEXT);
        try {
            getPersonManager().updateExistedUser(personToAdd, securityContext);
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public Response create(Person personToAdd) {
        try {
            PersonManager<Person> personManager = getPersonManager();
            personManager.createOrupdate(personToAdd);
        } catch (NamingException | BusinessException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/allUsers/{countryId}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public Response getAllUsersByCountry(@PathParam("countryId") int countryId) {
        try {
            List<Person> persons = getPersonManager().findUsersByCountry(countryId);
            final GenericEntity<List<Person>> personGeneric =
                    new GenericEntity<List<Person>>(persons) {
                    };
            return Response.status(Response.Status.OK).entity(personGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }

    @GET
    @Path("/allUsers")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin"})
    public Response getAllUsers(@javax.ws.rs.core.Context HttpServletRequest request) {
        try {
            List<Person> persons = getPersonManager().findAllUsers();
            final GenericEntity<List<Person>> personGeneric =
                    new GenericEntity<List<Person>>(persons) {
                    };
            return Response.status(Response.Status.OK).entity(personGeneric).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/user/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"admin", "user"})
    public Response getUser(@PathParam("username") String username) {
        try {
            Person person = getPersonManager().findUser(username);
            return Response.status(Response.Status.OK).entity(person).build();
        } catch (NamingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (BusinessException e) {
            return Response.status(e.getStatus()).build();
        }
    }

    @Override
    public String getResourceUrl() {
        return "/AFServer/rest/users/";
    }

    @Override
    protected Class getModelClass() {
        return Person.class;
    }
}
