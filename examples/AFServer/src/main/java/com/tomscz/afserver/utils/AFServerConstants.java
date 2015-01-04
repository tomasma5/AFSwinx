package com.tomscz.afserver.utils;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;

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

    public static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse(
            "Nobody can access this resource", 403, new Headers<Object>());;
    public static final ServerResponse ACCESS_DENIED = new ServerResponse(
            "Access denied for this resource", 401, new Headers<Object>());;
    public static final String AUTHENTICATION_SCHEME = "Basic";
    public static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR",
            500, new Headers<Object>());;

}
