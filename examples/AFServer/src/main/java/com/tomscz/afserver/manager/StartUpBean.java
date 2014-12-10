package com.tomscz.afserver.manager;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class StartUpBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void initApp() {
        //There should be initialize database
        System.out.print("StartUp");
    }
}
