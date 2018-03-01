package utils;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.POST;
import javax.ws.rs.core.MultivaluedMap;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    private static final String HTTP_GET = "GET";
    private static final String HTTP_POST = "POST";

    public static String getRequest(String url, MultivaluedMap<String, String> requestHeaders) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod(HTTP_GET);

        //add request header
        for (Map.Entry<String, String> requestHeader : prepareParameters(requestHeaders).entrySet()) {
            con.setRequestProperty(requestHeader.getKey(), requestHeader.getValue());
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

    public static void postRequest(String url,  MultivaluedMap<String, String> requestHeaders, String data) throws IOException {

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod(HTTP_POST);
        //add request header
        for (Map.Entry<String, String> requestHeader : prepareParameters(requestHeaders).entrySet()) {
            con.setRequestProperty(requestHeader.getKey(), requestHeader.getValue());
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
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }


    private static Map<String,String> prepareParameters(MultivaluedMap<String, String> queryParameters) {

        Map<String,String> parameters = new HashMap<String,String>();

        for(String str : queryParameters.keySet()){
            parameters.put(str, queryParameters.getFirst(str));
        }
        return parameters;

    }
}
