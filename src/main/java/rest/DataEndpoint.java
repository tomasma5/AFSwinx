package rest;

import model.DeviceStatusWithNearby;
import model.partial.Device;
import rest.exception.NotFoundException;
import service.DataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public DeviceStatusWithNearby getClosestToTime(@PathParam("timestamp") long timestamp) throws NotFoundException {
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
    public DeviceStatusWithNearby getClosestToTime(@PathParam("macAddress") String macAddress, @PathParam("timestamp") long timestamp) throws NotFoundException {
        return dataService.findClosestToGivenTimestamp(macAddress, timestamp);
    }


    @GET
    @Path("/device/{macAddress}/action/{action}/closestToTime/{timestamp}")
    @Produces({MediaType.APPLICATION_JSON})
    public DeviceStatusWithNearby getClosestToTimeWIthGivenAction(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action,
            @PathParam("timestamp") long timestamp) throws NotFoundException {
        return dataService.findClosestToGivenTimestampWithAction(macAddress, action, timestamp);
    }

    @GET
    @Path("/device/{macAddress}/action/{action}/closestToTime/{timestamp}/nearbydevices")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Device> getNearbyDevicesOfRecordWithActionUserAndTime(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action,
            @PathParam("timestamp") long timestamp) throws NotFoundException {
        DeviceStatusWithNearby closest = dataService.findClosestToGivenTimestampWithAction(macAddress, action, timestamp);
        return closest.getNearbyDevices();
    }

    @GET
    @Path("/device/{macAddress}/action/{action}/closestToTime/{timestamp}/user")
    @Produces({MediaType.TEXT_PLAIN})
    public String getUserOfRecordWithActionUserAndTime(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action,
            @PathParam("timestamp") long timestamp) throws NotFoundException {
        DeviceStatusWithNearby closest = dataService.findClosestToGivenTimestampWithAction(macAddress, action, timestamp);
        return closest.getDeviceStatus().getApplicationState().getUser();
    }

    @GET
    @Path("/device/{macAddress}/action/{action}/lastData")
    @Produces({MediaType.APPLICATION_JSON})
    public DeviceStatusWithNearby findLastRecordWithSpecifiedAction(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action) throws NotFoundException {
        return dataService.findLastRecordWithAction(macAddress, action);
    }

    @GET
    @Path("/device/{macAddress}/action/{action}/lastData/nearbydevices")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Device> findNearbyDevicesOfLastRecordWithSpecifiedAction(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action) throws NotFoundException {
        DeviceStatusWithNearby lastRecord = dataService.findLastRecordWithAction(macAddress, action);
        return lastRecord.getNearbyDevices();
    }

    @GET
    @Path("/device/{macAddress}/action/{action}/lastData/user")
    @Produces({MediaType.TEXT_PLAIN})
    public String findUserOfLastRecordWithSpecifiedAction(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action) throws NotFoundException {
        DeviceStatusWithNearby lastRecord = dataService.findLastRecordWithAction(macAddress, action);
        return lastRecord.getDeviceStatus().getApplicationState().getUser();
    }


}
