package rest;

import model.Screen;
import model.rest.MenuItem;
import service.exception.ServiceException;
import service.rest.ScreenRestService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
    public List<MenuItem> getAllApplicationScreens() {
        List<MenuItem> menuItems = new ArrayList<>();
        List<Screen> screens = screenRestService.getAllScreens();
        for (Screen screen : screens) {
            menuItems.add(new MenuItem(screen.getKey(), screen.getName(), screen.getScreenUrl(), screen.getMenuOrder()));
        }
        return menuItems;
    }

    @GET
    @Path("/{screen_id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Screen getScreenDefinition(@PathParam("screen_id") int screenId) throws ServiceException {
        Screen screen = screenRestService.getScreenById(screenId);
        if (screen == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return screen;
    }

    @GET
    @Path("/key/{screen_key}")
    @Produces({MediaType.APPLICATION_JSON})
    public Screen getScreenDefinition(@PathParam("screen_key") String screenKey) throws ServiceException {
        Screen screen = screenRestService.getScreenByKey(screenKey);
        if (screen == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return screen;
    }

}
