package com.tomscz.afserver.ws.resources;

import javax.annotation.security.PermitAll;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tomscz.afrest.AFRest;
import com.tomscz.afrest.AFRestGenerator;
import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFFieldInfo;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afserver.manager.*;
import com.tomscz.afserver.persistence.entity.*;
import com.tomscz.afserver.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provide all managers to its children. It also has abstract method called
 * getResourceUrl. All children should implement this method.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class BaseResource {

    @GET
    @Path("/fieldInfo")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response getComponentFields(@javax.ws.rs.core.Context HttpServletRequest request) throws MetamodelException {
        AFRest afSwing = new AFRestGenerator(request.getSession().getServletContext());
        AFMetaModelPack data = afSwing.generateSkeleton(getModelClass().getCanonicalName());
        return Response.status(Response.Status.OK).entity(data.getClassInfo()).build();
    };

    protected abstract Class getModelClass();

    @SuppressWarnings("unchecked")
    protected CountryManager<Country> getCountryManager() throws NamingException {
        Context ctx = new InitialContext();
        CountryManager<Country> cmm =
                (CountryManager<Country>) ctx.lookup(Utils.getJNDIName(CountryManagerImp.name));
        return cmm;
    }

    @SuppressWarnings("unchecked")
    protected PersonManager<Person> getPersonManager() throws NamingException {
        Context ctx = new InitialContext();
        PersonManager<Person> personManager =
                (PersonManager<Person>) ctx.lookup(Utils.getJNDIName(PersonManagerImpl.name));
        return personManager;
    }

    @SuppressWarnings("unchecked")
    protected AbsenceTypeManager<AbsenceType> getAbsenceTypeManager() throws NamingException {
        Context ctx = new InitialContext();
        AbsenceTypeManager<AbsenceType> absenceTypeManager =
                (AbsenceTypeManager<AbsenceType>) ctx.lookup(Utils
                        .getJNDIName(AbsenceTypeManagerImpl.NAME));
        return absenceTypeManager;
    }

    @SuppressWarnings("unchecked")
    protected AbsenceInstanceManager<AbsenceInstance> getAbsenceInstantManager()
            throws NamingException {
        Context ctx = new InitialContext();
        AbsenceInstanceManager<AbsenceInstance> absenceTypeManager =
                (AbsenceInstanceManager<AbsenceInstance>) ctx.lookup(Utils
                        .getJNDIName(AbsenceInstanceManagerImpl.NAME));
        return absenceTypeManager;
    }

    @SuppressWarnings("unchecked")
    protected VehicleManager<Vehicle> getVehicleManager() throws NamingException {
        Context ctx = new InitialContext();
        VehicleManager<Vehicle> vehicleManager =
                (VehicleManager<Vehicle>) ctx.lookup(Utils.getJNDIName(VehicleManagerImpl.name));
        return vehicleManager;
    }

    @SuppressWarnings("unchecked")
    protected BusinessTripManager<BusinessTrip> getBusinessTripManager() throws NamingException {
        Context ctx = new InitialContext();
        BusinessTripManager<BusinessTrip> businessTripManager =
                (BusinessTripManager<BusinessTrip>) ctx.lookup(Utils.getJNDIName(BusinessTripManagerImpl.name));
        return businessTripManager;
    }

    @SuppressWarnings("unchecked")
    protected BusinessTripPartManager<BusinessTripPart> getBusinessTripPartManager() throws NamingException {
        Context ctx = new InitialContext();
        BusinessTripPartManager<BusinessTripPart> businessTripPartManager =
                (BusinessTripPartManager<BusinessTripPart>) ctx.lookup(Utils.getJNDIName(BusinessTripPartManagerImpl.name));
        return businessTripPartManager;
    }

    public abstract String getResourceUrl();

}
