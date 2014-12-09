package com.tomscz.afswinx.rest.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.gson.Gson;
import com.tomscz.afswinx.common.Utils;

public abstract class BaseConnector implements Connector {

    protected static final String HTTP_PROTOCOL = "http";

    protected CloseableHttpClient httpClient;
    protected HttpContext context = null;
    protected HeaderType accept = HeaderType.JSON;
    protected HeaderType contentType = HeaderType.JSON;

    protected int statusCode = -1;

    protected HttpResponse response = null;

    public abstract HttpHost getHost();

    public abstract String getParameter();

    public String buildEndpoint(String parameters) {
        return getHost().getHostName() + ":" + getHost().getPort() + parameters;
    }

    private InputStream getResponse(HttpRequest httpMethod) throws ConnectException {
        response = null;
        try {
            this.statusCode = -1;
            httpMethod.addHeader("Accept", accept.toString());
            response = getClient().execute(getHost(), httpMethod, getContext());
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
            // Check if any data was received, if no throw exception
            if (inputStream != null && this.getStatusCode() >= 200 && this.getStatusCode() < 300) {
                String data = Utils.readInputSteam(inputStream).toString();
                T result = null;
                // Construct metamodel holder
                if (this.accept.equals(HeaderType.JSON)) {
                    Gson gson = new Gson();
                    result = gson.fromJson(data, clazz);
                } else if (this.accept.equals(HeaderType.XML)) {
                    // TODO add support for xml
                }
                return result;
            } else {
                throw new ConnectException("Request to adress " + buildEndpoint(getParameter())
                        + " was unsuccessfull status code is " + this.getStatusCode());
            }
        } catch (IOException e) {
            throw new ConnectException("Request to adress " + buildEndpoint(getParameter())
                    + " was unsuccessfull status code is " + this.getStatusCode());
        } finally {
            this.close();
        }
    }

    @Override
    public void doPost(String body) throws ConnectException {
        try {
            HttpPostBuilder postBuilder = new HttpPostBuilder(this.accept, this.contentType);
            HttpPost post = postBuilder.getPost(getParameter());
            post.setEntity(new StringEntity(body));
            InputStream inputStream = getResponse(post);
            if (inputStream != null && this.getStatusCode() >= 200 && this.getStatusCode() < 300) {
                // Do nothing post was successful
            } else {
                // Throws exception
                throw new ConnectException("Request to adress " + buildEndpoint(getParameter())
                        + " was unsuccessfull status code is " + this.getStatusCode()
                        + "\r\n Response is: " + getResponse().toString());
            }
        } catch (UnsupportedEncodingException e) {
            // Do nothing yet
            // TODO handle it more nicely
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

    public HttpResponse getResponse() {
        return response;
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

    public static class HttpPostBuilder {

        protected HttpPost httpPost = null;
        private HeaderType accept;
        private HeaderType contentType;

        public HttpPostBuilder(HeaderType accept, HeaderType contentType) {
            this.accept = accept;
            this.contentType = contentType;
        }

        protected HttpPost getPost(String endPoint) {
            close();
            HttpPost httPost = new HttpPost(endPoint);
            httPost.addHeader("Content-Type", contentType.toString());
            httPost.addHeader("Accept", accept.toString());
            return httPost;
        }

        protected void close() {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
        }
    }

    public static class HttpDeleteBuilder {
        protected HttpDelete httpDelete = null;
        private HeaderType accept;
        private HeaderType contentType;

        public HttpDeleteBuilder(HeaderType accept, HeaderType contentType) {
            this.accept = accept;
            this.contentType = contentType;
        }

        protected HttpDelete getDelete(String endPoint) {
            close();
            HttpDelete httpDelete = new HttpDelete(endPoint);
            httpDelete.addHeader("Content-Type", contentType.toString());
            httpDelete.addHeader("Accept", accept.toString());
            return httpDelete;
        }

        protected void close() {
            if (httpDelete != null) {
                httpDelete.releaseConnection();
            }
        }
    }

    public static class HttpPutBuilder {
        protected HttpPut httpPut = null;
        private HeaderType accept;
        private HeaderType contentType;

        public HttpPutBuilder(HeaderType accept, HeaderType contentType) {
            this.accept = accept;
            this.contentType = contentType;
        }

        protected HttpPut getDelete(String endPoint) {
            close();
            HttpPut httpPut = new HttpPut(endPoint);
            httpPut.addHeader("Content-Type", contentType.toString());
            httpPut.addHeader("Accept", accept.toString());
            return httpPut;
        }

        protected void close() {
            if (httpPut != null) {
                httpPut.releaseConnection();
            }
        }
    }

    /**
     * This class specify type of supported request and response types.
     * 
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
