package com.tomscz.afserver.daomanager;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataSourceDefinition(name = "java:app/jdbc/AFServerDS", className = "org.apache.derby.jdbc.EmbeddedDriver", url = "jdbc:derby:memory:AFServerDB;create=true;user=app;password=app")
public class DatabaseProducer {

    @javax.enterprise.inject.Produces
    @PersistenceContext(unitName = "AFServerPU")
    private EntityManager em;

}
