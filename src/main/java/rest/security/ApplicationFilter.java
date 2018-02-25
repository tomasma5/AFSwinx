package rest.security;

import model.Application;
import service.servlet.ApplicationsManagementService;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;

/**
 * Filter for application authorization via Basic auth method.
 *
 * Author: Pavel Matyáš (matyapav@fel.cvut.cz)
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class ApplicationFilter implements ContainerRequestFilter {

    @Inject
    private ApplicationsManagementService applicationsManagementService;

    @Inject
    private RequestContext requestContext;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
            abortOnUnauthorized(requestContext, "Authorization header must be provided");
            return;
        }

        String base64hash = authorizationHeader.substring("Basic".length()).trim();
        String applicationName = new String(Base64.getDecoder().decode(base64hash));

        Application application = applicationsManagementService.findByUuid(applicationName);
        // password validation
        if (application == null) {
            abortOnUnauthorized(requestContext, "Invalid application.");
            return;
        }
        this.requestContext.setCurrentApplication(application);
    }

    private void abortOnUnauthorized(ContainerRequestContext context, String reason, Object... reasonArgs) {
        String text = String.format(reason, reasonArgs);
        context.abortWith(Response.status(
                Response.Status.UNAUTHORIZED)
                .type(MediaType.TEXT_HTML)
                .entity(text)
                .build());
    }
}
