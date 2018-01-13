package rest;

import model.DeviceStatusWithNearby;
import service.ConsumerService;
import service.DataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/data")
public class DataEndpoint {

    @Inject
    private DataService dataService;

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<DeviceStatusWithNearby> getAll() {
        return dataService.findAllDeviceStatusesWithNearbyDevices();
    }
}
