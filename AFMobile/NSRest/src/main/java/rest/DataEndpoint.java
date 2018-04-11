package rest;

import model.DeviceStatusWithNearby;
import model.partial.Device;
import rest.exception.NotFoundException;
import service.DataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response getClosestToTime(@PathParam("timestamp") long timestamp) {
        try {
            DeviceStatusWithNearby closest = dataService.findClosestToGivenTimestamp(null, timestamp);
            return Response.status(Response.Status.OK).entity(closest).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
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
    public Response getClosestToTime(@PathParam("macAddress") String macAddress, @PathParam("timestamp") long timestamp) {
        try {
            DeviceStatusWithNearby closest = dataService.findClosestToGivenTimestamp(macAddress, timestamp);
            return Response.status(Response.Status.OK).entity(closest).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("/device/{macAddress}/action/{action}/closestToTime/{timestamp}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getClosestToTimeWIthGivenAction(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action,
            @PathParam("timestamp") long timestamp) {
        try {
            DeviceStatusWithNearby closest = dataService.findClosestToGivenTimestampWithAction(macAddress, action, timestamp);
            return Response.status(Response.Status.OK).entity(closest).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/device/{macAddress}/action/{action}/closestToTime/{timestamp}/nearbydevices")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getNearbyDevicesOfRecordWithActionUserAndTime(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action,
            @PathParam("timestamp") long timestamp) {
        try {
            DeviceStatusWithNearby closest = dataService.findClosestToGivenTimestampWithAction(macAddress, action, timestamp);
            return Response.status(Response.Status.OK).entity(closest.getNearbyDevices()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/device/{macAddress}/action/{action}/closestToTime/{timestamp}/user")
    @Produces({MediaType.TEXT_PLAIN})
    public Response getUserOfRecordWithActionUserAndTime(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action,
            @PathParam("timestamp") long timestamp) {
        try {
            DeviceStatusWithNearby closest = dataService.findClosestToGivenTimestampWithAction(macAddress, action, timestamp);
            return Response.status(Response.Status.OK).entity(closest.getDeviceStatus().getApplicationState().getUser()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/device/{macAddress}/action/{action}/lastData")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findLastRecordWithSpecifiedAction(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action) {
        try {
            DeviceStatusWithNearby record = dataService.findLastRecordWithAction(macAddress, action);
            return Response.status(Response.Status.OK).entity(record).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/device/{macAddress}/action/{action}/lastData/nearbydevices")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findNearbyDevicesOfLastRecordWithSpecifiedAction(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action) {
        DeviceStatusWithNearby lastRecord = null;
        try {
            lastRecord = dataService.findLastRecordWithAction(macAddress, action);
            return Response.status(Response.Status.OK).entity(lastRecord.getNearbyDevices()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/device/{macAddress}/action/{action}/lastData/user")
    @Produces({MediaType.TEXT_PLAIN})
    public Response findUserOfLastRecordWithSpecifiedAction(
            @PathParam("macAddress") String macAddress,
            @PathParam("action") String action) {
        DeviceStatusWithNearby lastRecord = null;
        try {
            lastRecord = dataService.findLastRecordWithAction(macAddress, action);
            return Response.status(Response.Status.OK).entity(lastRecord.getDeviceStatus().getApplicationState().getUser()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}
