package service.rest.impl;

import dao.ComponentResourceDao;
import service.rest.ComponentResourceService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Implementation of service for getting data from application
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@ApplicationScoped
public class ComponentResourceServiceImpl implements ComponentResourceService {

    private static final Logger LOGGER = Logger.getLogger( ComponentResourceServiceImpl.class.getName() );

    @Inject
    ComponentResourceDao componentResourceDao;

    @Override
    public String getComponentModel() {
        return null;
    }

    @Override
    public String getComponentData() {
        return null;
    }

    @Override
    public String sendComponentData() {
        return null;
    }
}
