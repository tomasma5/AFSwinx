package com.tomscz.afswinx.rest.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.gson.Gson;

public abstract class BaseConnector implements Connector {

    protected static final String HTTP_PROTOCOL = "http";

    protected CloseableHttpClient httpClient;
    protected HttpContext context = null;
    protected HeaderType accept = HeaderType.JSON;
    protected HeaderType contentType = HeaderType.JSON;

    protected int statusCode = -1;

    public abstract HttpHost getHost();
    public abstract String getParameter();

    public String buildEndpoint(String parameters) {
        return getHost().getHostName() + ":" + getHost().getPort() + parameters;
    }

    protected InputStream getResponse(HttpGet httpGet) throws ConnectException {
        HttpResponse response = null;
        try {
            this.statusCode = -1;
            httpGet.addHeader("Accept", accept.toString());
            response = getClient().execute(getHost(), httpGet, getContext());
            statusCode = response.getStatusLine().getStatusCode();
        } catch (ClientProtocolException e) {
            throw new ConnectException(e.getMessage());
        } catch (IOException e) {
            throw new ConnectException(e.getMessage());
        }
        HttpEntity entity = response.getEntity();
        try {
            return entity.getContent();
        } catch (IllegalStateException e) {
            throw new ConnectException(e.getMessage());
        } catch (IOException e) {
            throw new ConnectException(e.getMessage());
        }
    }
    
    protected <T> T getContent(Class<T> clazz) throws ConnectException {
        try {
             HttpGetBuilder httpGetBuilder = new HttpGetBuilder(this.accept, this.contentType);
            InputStream inputStream = getResponse(httpGetBuilder.getGET(getParameter()));
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
                T result = null;
                //Construct metamodel holder 
                if (this.accept.equals(HeaderType.JSON)) {
                    Gson gson = new Gson();
                    result = gson.fromJson(data, clazz);
                } else if (this.accept.equals(HeaderType.XML)) {
                    // TODO add support for xml
                }
                return result;
            } else {
                throw new ConnectException("Request to adress "
                        + buildEndpoint(getParameter()) + " was unsuccessfull status code is "
                        + this.getStatusCode());
            }
        } catch (IOException e) {
            throw new ConnectException("Request to adress " + buildEndpoint(getParameter())
                    + " was unsuccessfull status code is " + this.getStatusCode());
        } finally {
            this.close();
        }
    }

    protected HttpContext getContext() {
        if (context == null) {
            context = new BasicHttpContext();
        }
        return context;
    }

    protected CloseableHttpClient getClient() {
        if (httpClient == null) {
            httpClient = HttpClientBuilder.create().build();
        }
        return httpClient;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void close() throws ConnectException {
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new ConnectException("Could not close http client session");
            }
        }
    }

    public static class HttpGetBuilder {

        protected HttpGet httpGet = null;
        private HeaderType accept;
        private HeaderType contentType;

        public HttpGetBuilder(HeaderType accept, HeaderType contentType) {
            this.accept = accept;
            this.contentType = contentType;
        }

        public HttpGet getGET(String endPoint) {
            close();
            HttpGet httpGet = new HttpGet(endPoint);
            httpGet.addHeader("Accept", accept.toString());
            httpGet.addHeader("Content-Type", contentType.toString());
            return httpGet;
        }

        protected void close() {
            if (httpGet != null) {
                httpGet.releaseConnection();
            }
        }
    }

    /**
     * This class specify type of supported request and response types.
     * @author Martin Tomasek (martin@toms-cz.com)
     *
     * @since 1.0.0.
     */
    public enum HeaderType {
        XML("application/xml"), JSON("application/json");

        private final String name;

        private HeaderType(String name) {
            this.name = name;
        }

        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        public String toString() {
            return name;
        }
    }

}
