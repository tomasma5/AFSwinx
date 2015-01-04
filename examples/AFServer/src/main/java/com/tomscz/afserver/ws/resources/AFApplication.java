package com.tomscz.afserver.ws.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * This is application class which is used to register resources which are accessible.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFApplication extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public AFApplication() {
        singletons.add(new CountryResource());
        singletons.add(new AFRootResource());
        singletons.add(new UserResource());
        singletons.add(new AbsenceTypeResource());
        singletons.add(new AbsenceInstanceResource());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}
