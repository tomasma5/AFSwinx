package com.tomscz.afserver.ws.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.tomscz.afrest.AFRestSwing;
import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.exceptions.AFRestException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afserver.persistence.entity.AbsenceInstance;
import com.tomscz.afserver.persistence.entity.AbsenceType;
import com.tomscz.afserver.persistence.entity.Address;
import com.tomscz.afserver.persistence.entity.Gender;
import com.tomscz.afserver.persistence.entity.Person;

@Path("/person")
public class AFRootResource {

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getResources(@javax.ws.rs.core.Context HttpServletRequest request) {
        try {
            AFRestSwing afSwing = new AFRestSwing(request.getSession().getServletContext());
            AFMetaModelPack data =
                    afSwing.generateSkeleton("com.tomscz.afserver.persistence.entity.Person");
            data.setOptionsToFields(AFRestUtils.getEnumDataInClass(
                    "com.tomscz.afserver.persistence.entity.Person", "gender"), "gender");
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException | AFRestException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getResources(@PathParam("id") int id) {
        Person p = new Person();
        p.setFirstName("Martin");
        p.setLastName("Tomasek");
        p.setGender(Gender.MALE);
        p.setConfidentialAgreement(true);
        Address address = new Address();
        address.setStreet("5th evenue");
        p.setMyAdress(address);
        AbsenceInstance firstInstance = new AbsenceInstance();
        firstInstance.setDuration(320);
        Date date = new Date();
        firstInstance.setStartDate(date);
        AbsenceType type = new AbsenceType();
        type.setName("Dovolena");
        type.setId(23);
        firstInstance.setAbsenceType(type);
        p.setAbsence(firstInstance);
        return Response.status(Response.Status.OK).entity(p).build();
    }

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getAllPersons() {
        Person p = new Person();
        p.setFirstName("Martin");
        p.setLastName("Tomasek");
        p.setGender(Gender.MALE);
        List<Person> person = new ArrayList<Person>();
        person.add(p);
        p = new Person();
        p.setFirstName("Jan");
        p.setLastName("Novák");
        p.setGender(Gender.MALE);
        person.add(p);
        final GenericEntity<List<Person>> personGeneric =
                new GenericEntity<List<Person>>(person) {};
        return Response.status(Response.Status.OK).entity(personGeneric).build();
    }

    @GET
    @Path("/list/definition")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getListDefinition(@javax.ws.rs.core.Context HttpServletRequest request ) {
        try {
            AFRestSwing afSwing = new AFRestSwing(request.getSession().getServletContext());
            AFMetaModelPack data =
                    afSwing.generateSkeleton("com.tomscz.afserver.persistence.entity.Person",
                            "simple.config.xml");
            data.setOptionsToFields(AFRestUtils.getEnumDataInClass(
                    "com.tomscz.afserver.persistence.entity.Person", "gender"), "gender");
            return Response.status(Response.Status.OK).entity(data).build();
        } catch (MetamodelException | AFRestException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response add(Person personMapper) {
        return Response.status(Response.Status.OK).build();
    }
}
