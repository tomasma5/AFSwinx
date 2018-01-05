import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/helloworld")
public class HelloWorldEndpoint {

    @GET
    @Path("/")
    public String helloWorld() {
        return "Hello world";
    }

}