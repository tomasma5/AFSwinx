package cz.cvut.fel.matyapav.afandroid.rest;


/**
 * This class create builder which is able to build data, which will be send back to server.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class RestBuilderFactory {

    private static RestBuilderFactory instance;

    private RestBuilderFactory() {

    }

    public static synchronized RestBuilderFactory getInstance() {
        if (instance == null) {
            instance = new RestBuilderFactory();
        }
        return instance;
    }

    /**
     * This method return builder based on content type of connection.
     * 
     * @param connection based on which will be returned builder.
     * @return Builder which can build data for request on server
     */
    public BaseRestBuilder getBuilder(AFSwinxConnection connection) {
        if (connection == null) {
            return new JSONBuilder();
        }
        if (connection.getContentType().equals(HeaderType.JSON)) {
            return new JSONBuilder();
        } else if (connection.getContentType().equals(HeaderType.XML)) {
            return new XMLBuilder();
        } else {
            return new JSONBuilder();
        }
    }

}
