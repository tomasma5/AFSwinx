package rest;

import dao.DeviceDao;
import dao.impl.DeviceDaoImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

//TODO remove - just experimenting
@Path("/helloworld")
public class HelloWorldEndpoint {

    @GET
    @Path("/")
    public String helloWorld() {
        DeviceDao deviceDao = new DeviceDaoImpl();
        deviceDao.findAll();
        return "Hello world";
    }

}