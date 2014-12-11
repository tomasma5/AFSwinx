package com.tomscz.afserver.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.dom4j.Branch;

@Singleton
@Startup
public class StartUpBean implements Serializable {

    @Inject
    EntityManager em;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void initApp() {
        try  {
            URL url = this.getClass().getResource("/import.sql");
            if(url == null){
                return;
            }
            InputStream importStream = this.getClass().getResource("/import.sql").openStream();
            BufferedReader inbf = new BufferedReader(new InputStreamReader(importStream));
            String line = null;
            while ((line = inbf.readLine()) != null) {
                int aafectedNumber=em.createNativeQuery(line).executeUpdate();
                System.out.println(aafectedNumber);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
