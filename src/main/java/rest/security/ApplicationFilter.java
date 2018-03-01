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
        String applicationUuid = requestContext.getHeaderString("Application");
        if (applicationUuid == null) {
            abortOnBadRequest(requestContext, "Application header must be provided");
            return;
        }

        Application application = applicationsManagementService.findByUuid(applicationUuid);
        if (application == null) {
            abortOnBadRequest(requestContext, "Invalid application.");
            return;
        }
        this.requestContext.setCurrentApplication(application);
    }

    private void abortOnBadRequest(ContainerRequestContext context, String reason, Object... reasonArgs) {
        String text = String.format(reason, reasonArgs);
        context.abortWith(Response.status(
                Response.Status.BAD_REQUEST)
                .type(MediaType.TEXT_HTML)
                .entity(text)
                .build());
    }
}
