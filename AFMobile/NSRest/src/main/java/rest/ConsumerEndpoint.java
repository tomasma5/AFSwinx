package rest;

import model.DeviceStatusWithNearby;
import service.ConsumerService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Consumer endpoint - used for storing data into this app
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
@Path("/consumer")
public class ConsumerEndpoint {

    @Inject
    private ConsumerService consumerService;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void receiveNewDeviceStatus(DeviceStatusWithNearby deviceStatusWithNearby) {
        consumerService.addNewDeviceNearbyStatusRecord(deviceStatusWithNearby);
    }

}