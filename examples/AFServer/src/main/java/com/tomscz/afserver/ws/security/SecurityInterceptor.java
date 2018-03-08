package com.tomscz.afserver.ws.security;

import com.tomscz.afserver.manager.PersonManager;
import com.tomscz.afserver.manager.PersonManagerImpl;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.persistence.entity.UserRoles;
import com.tomscz.afserver.utils.AFServerConstants;
import com.tomscz.afserver.utils.Utils;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * This class is security interceptor. Some of logic was transfered from my previous project:
 * https://gitlab.fit.cvut.cz/listivit/letadla/blob/master/flightAirlines/src/main/java/cz/cvut/fel/aos
 * /flightAirlines/security/MySecurityInterceptor.java
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

    private AFSecurityContext securityContext = null;
    private boolean isPermitAll = false;

    @javax.ws.rs.core.Context
    private ResourceInfo resourceInfo;

    @javax.ws.rs.core.Context
    private HttpServletRequest httpRequest;

    public SecurityInterceptor() {}

    private Response checkPermissions(ContainerRequestContext requestContext) {
        Method methodToVerify = resourceInfo.getResourceMethod();
        // Check default global annotations
        if (methodToVerify.isAnnotationPresent(DenyAll.class)) {
            return AFServerConstants.ACCESS_FORBIDDEN;
        }
        // If annotation is permit all then set it to true
        if (methodToVerify.isAnnotationPresent(PermitAll.class)
                || !methodToVerify.isAnnotationPresent(RolesAllowed.class)) {
            this.isPermitAll = true;
        }
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();
        // Get authorization header
        final List<String> authorization = headers.get(AFServerConstants.AUTHORIZATION_HEADER);
        if (authorization == null || authorization.isEmpty()) {
            return AFServerConstants.ACCESS_DENIED;
        }
        // Get username and password
        final String encodedUserPassword =
                authorization.get(0)
                        .replaceFirst(AFServerConstants.AUTHENTICATION_SCHEME + " ", "");
        String usernameAndPassword;
        usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String nickname = tokenizer.nextToken();
        final String password = tokenizer.nextToken();
        PersonManager<Person> personManager = getPersonManager();
        if (personManager == null) {
            return AFServerConstants.ACCESS_DENIED;
        }
        Person authenticatedUser;
        try {
            authenticatedUser = personManager.findUser(nickname, password);
            if (authenticatedUser == null) {
                return AFServerConstants.ACCESS_DENIED;
            } else {
                securityContext = new AFSecurityContext(nickname);
                securityContext.userRoles = authenticatedUser.getUserRole();
                if (methodToVerify.isAnnotationPresent(RolesAllowed.class)) {
                    RolesAllowed rolesAnnotation = methodToVerify.getAnnotation(RolesAllowed.class);
                    if (!isUserInRoles(new HashSet<String>(Arrays.asList(rolesAnnotation.value())),
                            authenticatedUser)) {
                        return AFServerConstants.ACCESS_DENIED;
                    }
                }
            }
        } catch (BusinessException e) {
            return AFServerConstants.SERVER_ERROR;
        }
        return null;
    }

    private boolean isUserInRoles(Set<String> roleSet, Person authenticatedUser) {
        List<UserRoles> userRole = authenticatedUser.getUserRole();
        if (userRole == null) {
            return false;
        }
        for (UserRoles currentRole : userRole) {
            for (String role : roleSet) {
                String curentRoleInString = currentRole.name().toLowerCase();
                if (role.equals(curentRoleInString)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private PersonManager<Person> getPersonManager() {
        try {
            Context ctx = new InitialContext();
            PersonManager<Person> personManager =
                    (PersonManager<Person>) ctx.lookup(Utils.getJNDIName(PersonManagerImpl.name));
            return personManager;
        } catch (NamingException e) {
            // Do nothing, null will be returned
        }
        return null;
    }


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Response securityResponse = checkPermissions(requestContext);
        // Always remove security context
        httpRequest.removeAttribute(AFServerConstants.SECURITY_CONTEXT);
        if (securityContext != null) {
            httpRequest.setAttribute(AFServerConstants.SECURITY_CONTEXT, securityContext);
        }
        if (securityResponse != null) {
            if (!isPermitAll) {
                requestContext.abortWith(securityResponse);
            }
        }
    }
}
