package rest;

import model.DeviceStatusWithNearby;
import service.ConsumerService;
import service.DataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.List;

/**
 * Data endpoint - used for getting data from this app
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@Path("/data")
public class DataEndpoint {

    @Inject
    private DataService dataService;

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<DeviceStatusWithNearby> getAll() {
        return dataService.findAllDeviceStatusesWithNearbyDevices(null);
    }

    @GET
    @Path("/closestToTime/{timestamp}")
    @Produces({MediaType.APPLICATION_JSON})
    public DeviceStatusWithNearby getClosestToTime(@PathParam("timestamp") long timestamp) {
        return dataService.findClosestToGivenTimestamp(null, timestamp);
    }

    @GET
    @Path("/device/{macAddress}/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<DeviceStatusWithNearby> getAllForDevice(@PathParam("macAddress") String macAddress) {
        return dataService.findAllDeviceStatusesWithNearbyDevices(macAddress);
    }

    @GET
    @Path("/device/{macAddress}/closestToTime/{timestamp}")
    @Produces({MediaType.APPLICATION_JSON})
    public DeviceStatusWithNearby getClosestToTime(@PathParam("macAddress") String macAddress, @PathParam("timestamp") long timestamp) {
        return dataService.findClosestToGivenTimestamp(macAddress, timestamp);
    }
}
