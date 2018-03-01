package com.tomscz.afserver.utils;

import javax.ws.rs.core.Response;

/**
 * This class holds all constants which will be used in application.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFServerConstants {

    // ///////////////////////////////
    // ///SECURITY///////////////////
    // //////////////////////////////

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String SECURITY_CONTEXT = "securityContext";

    public static final Response ACCESS_FORBIDDEN = Response.status(403).entity("Nobody can access this resource").build();
    public static final Response ACCESS_DENIED = Response.status(401).entity("Access denied for this resource").build();
    public static final String AUTHENTICATION_SCHEME = "Basic";
    public static final Response SERVER_ERROR = Response.status(500).entity("INTERNAL SERVER ERROR").build();

}
