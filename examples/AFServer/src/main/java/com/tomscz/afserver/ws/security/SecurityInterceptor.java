package com.tomscz.afserver.ws.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.jboss.resteasy.util.Base64;

import com.tomscz.afserver.manager.PersonManager;
import com.tomscz.afserver.manager.PersonManagerImpl;
import com.tomscz.afserver.manager.exceptions.BusinessException;
import com.tomscz.afserver.persistence.entity.Person;
import com.tomscz.afserver.persistence.entity.UserRoles;
import com.tomscz.afserver.utils.AFServerConstants;
import com.tomscz.afserver.utils.Utils;

@Provider
@ServerInterceptor
public class SecurityInterceptor implements PreProcessInterceptor {

    private AFSecurityContext securityContext = null;
    private boolean isPermitAll = false;

    public SecurityInterceptor() {}

    @Override
    public ServerResponse preProcess(HttpRequest request, ResourceMethod method) throws Failure,
            WebApplicationException {
        ServerResponse securityResponse = checkPermissions(request, method);
        // Always remove security context
        request.removeAttribute(AFServerConstants.SECURITY_CONTEXT);
        if (securityContext != null) {
            request.setAttribute(AFServerConstants.SECURITY_CONTEXT, securityContext);
        }
        if (securityResponse != null) {
            if (isPermitAll) {
                return null;
            }
            return securityResponse;
        }
        return null;
    }

    private ServerResponse checkPermissions(HttpRequest request, ResourceMethod method) {
        Method methodToVerify = method.getMethod();
        // Check default global annotations
        if (methodToVerify.isAnnotationPresent(DenyAll.class)) {
            return AFServerConstants.ACCESS_FORBIDDEN;
        }
        if (methodToVerify.isAnnotationPresent(PermitAll.class)
                || !methodToVerify.isAnnotationPresent(RolesAllowed.class)) {
            this.isPermitAll = true;
        }
        final MultivaluedMap<String, String> headers = request.getHttpHeaders().getRequestHeaders();

        final List<String> authorization = headers.get(AFServerConstants.AUTHORIZATION_HEADER);
        if (authorization == null || authorization.isEmpty()) {
            return AFServerConstants.ACCESS_DENIED;
        }

        final String encodedUserPassword =
                authorization.get(0)
                        .replaceFirst(AFServerConstants.AUTHENTICATION_SCHEME + " ", "");

        String usernameAndPassword;
        try {
            usernameAndPassword = new String(Base64.decode(encodedUserPassword));
        } catch (IOException e) {
            return AFServerConstants.SERVER_ERROR;
        }
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
}
