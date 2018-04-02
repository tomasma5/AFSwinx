package utils;

import model.ComponentConnection;

import javax.ws.rs.core.MultivaluedMap;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains some helper methods for working with HTTP requests
 */
public class HttpUtils {

    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";
    private static final String DEFAULT_PROTOCOL = "http";

    /**
     * Sens GET request to given url.
     *
     * @param url            the url
     * @param requestHeaders the request headers
     * @return response
     * @throws IOException the io exception
     */
    public static String getRequest(String url, MultivaluedMap<String, String> requestHeaders) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod(HTTP_GET);

        //add request header
        if (requestHeaders != null) {
            for (Map.Entry<String, String> requestHeader : prepareParameters(requestHeaders).entrySet()) {
                con.setRequestProperty(requestHeader.getKey(), requestHeader.getValue());
            }
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    /**
     * Sends POST request to given url.
     *
     * @param url            the url
     * @param requestHeaders the request headers
     * @param data           the data
     * @return the string
     * @throws IOException the io exception
     */
    public static String postRequest(String url, MultivaluedMap<String, String> requestHeaders, String data) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod(HTTP_POST);
        //add request header
        if (requestHeaders != null) {
            for (Map.Entry<String, String> requestHeader : prepareParameters(requestHeaders).entrySet()) {
                con.setRequestProperty(requestHeader.getKey(), requestHeader.getValue());
            }
        }

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(data);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }


    private static Map<String, String> prepareParameters(MultivaluedMap<String, String> queryParameters) {

        Map<String, String> parameters = new HashMap<>();

        for (String str : queryParameters.keySet()) {
            parameters.put(str, queryParameters.getFirst(str));
        }
        return parameters;

    }

    /**
     * Builds url from parts. Port is defined as string.
     *
     * @param protocol    the protocol - e.g. http, https
     * @param hostname    the hostname - e.g. example.com
     * @param port        the port - e.g. 8080
     * @param contextPath the context path - e.g. /MyApplication
     * @param parameters  the parameters - e.g. /api/cars/id/45
     * @return the string
     */
    public static String buildUrl(String protocol, String hostname, String port, String contextPath, String parameters) {
        return buildUrl(protocol, hostname, (port != null && !port.isEmpty() ? Integer.parseInt(port) : 0), contextPath, parameters);
    }

    /**
     * Build url from parts. Port is defined as integer.
     *
     * @param protocol    the protocol - e.g. http, https
     * @param hostname    the hostname - e.g. example.com
     * @param port        the port - e.g. 8080
     * @param contextPath the context path - e.g. /MyApplication
     * @param parameters  the parameters - e.g. /api/cars/id/45
     * @return the string
     */
    public static String buildUrl(String protocol, String hostname, int port, String contextPath, String parameters) {
        if (protocol == null || protocol.isEmpty()) {
            protocol = DEFAULT_PROTOCOL;
        }
        if (hostname == null || hostname.isEmpty()) {
            hostname = "localhost";
        }
        if (contextPath != null && !contextPath.startsWith("/")) {
            contextPath = "/" + contextPath;
        }
        return protocol +
                "://" +
                hostname +
                (port != 0 ? ":" + port : "") +
                (contextPath != null ? contextPath : "") +
                (parameters != null ? parameters : "");
    }

    /**
     * Build url from parts. Information about protocol, hostname, port and parameters are got from component connection model.
     *
     * @param componentConnection the component connection
     * @param contextPath         the context path
     * @param proxy               the proxy
     * @return the string
     */
    public static String buildUrl(ComponentConnection componentConnection, String contextPath, boolean proxy) {
        if (proxy) {
            return buildUrl(componentConnection.getProtocol(), componentConnection.getAddress(),
                    componentConnection.getPort(), contextPath, componentConnection.getParameters());
        } else {
            return buildUrl(componentConnection.getRealProtocol(), componentConnection.getRealAddress(),
                    componentConnection.getRealPort(), contextPath, componentConnection.getRealParameters());
        }

    }
}
