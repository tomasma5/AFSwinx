package com.tomscz.afserver.ws.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class AFApplication extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public AFApplication() {
        singletons.add(new CountryResource());
        singletons.add(new AFRootResource());
        singletons.add(new UserResource());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}
