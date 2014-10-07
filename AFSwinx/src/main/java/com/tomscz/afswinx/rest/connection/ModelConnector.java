package com.tomscz.afswinx.rest.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;

import org.apache.http.HttpHost;

import com.google.gson.Gson;
import com.tomscz.afswinx.rest.dto.AFMetaModelPack;

public class ModelConnector extends BaseConnector {

    private HttpHost host = null;
    private HttpGetBuilder httpGetBuilder;
    private String parameter;

    public ModelConnector(String hostName, String parameter, int port, HeaderType accept,
            HeaderType contentType) {
        this.host = new HttpHost(hostName, port, BaseConnector.HTTP_PROTOCOL);
        this.parameter = parameter;
    }

    public ModelConnector(String hostName, String parameter, int port) {
        this.host = new HttpHost(hostName, port, BaseConnector.HTTP_PROTOCOL);
        this.parameter = parameter;
    }

    public ModelConnector(String hostName, String parameter, int port, String protocol) {
        this.host = new HttpHost(hostName, port, protocol);
        this.parameter = parameter;
    }


    @Override
    public HttpHost getHost() {
        return host;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AFMetaModelPack getContent() throws ConnectException {
        try {
            this.httpGetBuilder = new HttpGetBuilder(this.accept, this.contentType);
            InputStream inputStream = getResponse(this.httpGetBuilder.getGET(parameter));
            //Check if any data was received, if no throw exception
            if (inputStream != null && this.getStatusCode() == 200) {
                BufferedReader streamReader =
                        new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();
                String line;
                //Read data
                while ((line = streamReader.readLine()) != null) {
                    responseStrBuilder.append(line);
                }
                String data = responseStrBuilder.toString();
                AFMetaModelPack result = null;
                //Construct metamodel holder 
                if (this.accept.equals(HeaderType.JSON)) {
                    Gson gson = new Gson();
                    result = gson.fromJson(data, AFMetaModelPack.class);
                } else if (this.accept.equals(HeaderType.XML)) {
                    // TODO add support for xml
                }
                return result;
            } else {
                throw new ConnectException("Request to adress "
                        + buildEndpoint(parameter) + " was unsuccessfull status code is "
                        + this.getStatusCode());
            }
        } catch (IOException e) {
            throw new ConnectException("Request to adress " + buildEndpoint(parameter)
                    + " was unsuccessfull status code is " + this.getStatusCode());
        } finally {
            this.close();
        }
    }

}
