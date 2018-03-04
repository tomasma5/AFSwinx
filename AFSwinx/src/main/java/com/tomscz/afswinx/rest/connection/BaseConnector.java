package com.tomscz.afswinx.rest.connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.gson.Gson;
import com.tomscz.afswinx.common.Utils;

/**
 * This abstract class is responsible for perform requests to server.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class BaseConnector implements Connector {

    protected static final String HTTP_PROTOCOL = "http";

    protected CloseableHttpClient httpClient;
    protected HttpContext context = null;
    protected HeaderType accept = HeaderType.JSON;
    protected HeaderType contentType = HeaderType.JSON;
    protected HttpMethod httpMethod = HttpMethod.GET;
    protected HashMap<String, String> headersParams;
    protected ConnectionSecurity security;
    protected int statusCode = -1;
    protected HttpResponse response = null;

    public abstract HttpHost getHost();

    public abstract String getParameter();

    public String buildEndpoint(String parameters) {
        return getHost().getHostName() + ":" + getHost().getPort() + parameters;
    }

    /**
     * This method perform request on server and return response from server. It also set status
     * code. If during performing request is exception occur then {@link AFSwinxConnectionException}
     * is thrown.
     * 
     * @param httpMethod method which will be used.
     * @return InputStream of response from server.
     * @throws ConnectException if during request is exception occur.
     */
    private InputStream getResponse(HttpRequest httpMethod) throws ConnectException {
        response = null;
        try {
            this.statusCode = -1;
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

    /**
     * This method do request on server.
     * 
     * @param clazz returned type.
     * @param body of request.
     * @return object of T which will contains response body from server.
     * @throws ConnectException
     */
    protected <T> T doRequest(Class<T> clazz, String body) throws ConnectException {
        try {
            HttpRequestBuilder requestBuilder =
                    new HttpRequestBuilder(this.accept, this.contentType, this.httpMethod,
                            this.headersParams, getContext(), this.security);
            HttpRequest request = requestBuilder.getRequest(getParameter());
            request.setHeader("Application", "4f1eea54-f08b-4f55-bb93-e6d8642abefa"); //TODO nekam s tim do properties
            InputStream inputStream;
            boolean transformResponseData = true;
            if (body != null
                    && (requestBuilder.httpMethod.equals(HttpMethod.POST) || requestBuilder.httpMethod
                            .equals(HttpMethod.PUT))) {
                HttpEntityEnclosingRequest requestWithBody = (HttpEntityEnclosingRequest) request;
                requestWithBody.setEntity(new StringEntity(body));
                inputStream = getResponse(requestWithBody);
                transformResponseData = false;
            } else {
                inputStream = getResponse(request);
            }
            // Check if any data was received, if no throw exception
            if (transformResponseData && inputStream != null && this.getStatusCode() >= 200
                    && this.getStatusCode() < 300) {
                String data = Utils.readInputSteam(inputStream).toString();
                T result = null;
                // Construct metamodel holder
                if (this.accept.equals(HeaderType.JSON)) {
                    Gson gson = new Gson();
                    result = gson.fromJson(data, clazz);
                } else if (this.accept.equals(HeaderType.XML)) {
                    throw new UnsupportedOperationException("Not supported in this version!");
                }
                return result;
            } else if (inputStream == null || this.getStatusCode() < 200
                    || this.getStatusCode() >= 300) {
                throw new ConnectException("Request to adress " + buildEndpoint(getParameter())
                        + " was unsuccessfull status code is " + this.getStatusCode());
            }
            return null;
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
        httpClient = null;
    }

    /**
     * This class is request builder. It build http or https request which will be used.
     * 
     * @author Martin Tomasek (martin@toms-cz.com)
     * 
     * @since 1.0.0.
     */
    public static class HttpRequestBuilder {

        protected HttpRequestBase request = null;
        private HeaderType accept;
        private HeaderType contentType;
        private HttpMethod httpMethod;
        private HashMap<String, String> headersParam;
        private HttpContext context;
        private ConnectionSecurity security;

        public HttpRequestBuilder(HeaderType accept, HeaderType contentType, HttpMethod httpMethod,
                HashMap<String, String> headersParams, HttpContext context,
                ConnectionSecurity security) {
            this.accept = accept;
            this.contentType = contentType;
            this.httpMethod = httpMethod;
            this.headersParam = headersParams;
            this.context = context;
            this.security = security;
        }

        protected HttpRequestBase getRequest(String endPoint) {
            close();
            if (httpMethod.equals(HttpMethod.GET)) {
                request = new HttpGet(endPoint);
            } else if (httpMethod.equals(HttpMethod.POST)) {
                request = new HttpPost(endPoint);
            } else if (httpMethod.equals(HttpMethod.PUT)) {
                request = new HttpPut(endPoint);
            } else if (httpMethod.equals(HttpMethod.DELETE)) {
                request = new HttpDelete(endPoint);
            }
            request.addHeader("Content-Type", contentType.toString() + ";charset=UTF-8");
            request.addHeader("Accept", accept.toString() + ";charset=UTF-8");
            if (headersParam != null) {
                for (String key : headersParam.keySet()) {
                    String value = headersParam.get(key);
                    if (value != null && !value.isEmpty()) {
                        request.addHeader(key, value);
                    }
                }
            }
            // If there is security setting then set it
            if (security != null) {
                UsernamePasswordCredentials s =
                        new UsernamePasswordCredentials(security.getUserName(),
                                security.getPassword());
                try {
                    // So far only basic method is supported
                    request.addHeader(new BasicScheme().authenticate(s, request, context));
                } catch (AuthenticationException e) {
                    // Do nothing source wont be secured, if it failed then upper layer inform user
                    // about it
                }
            }
            return request;
        }

        protected void close() {
            if (request != null) {
                request.releaseConnection();
            }
        }
    }

}
