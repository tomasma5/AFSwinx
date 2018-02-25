package rest;

import model.Screen;
import org.bson.types.ObjectId;
import service.exception.ServiceException;
import service.rest.ScreenRestService;
import service.servlet.ScreenManagementService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Screens endpoint - returns back screen definitions
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@Path("/screens")
public class ScreenEndpoint {

    @Inject
    private ScreenRestService screenRestService;

    @GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Screen> getAllApplicationScreens() {
        return screenRestService.getAllScreens();
    }

    @GET
    @Path("/{screen_id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Screen getScreenDefinition(@PathParam("screen_id") ObjectId screenId) throws ServiceException{
        Screen screen = screenRestService.getScreenById(screenId);
        if(screen == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return screen;
    }


}
