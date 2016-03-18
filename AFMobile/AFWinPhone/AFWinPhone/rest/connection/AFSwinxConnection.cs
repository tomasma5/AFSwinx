using System;
using System.Collections.Generic;
using Windows.Web.Http;

namespace AFWinPhone.rest.connection
{
    public class AFSwinxConnection
    {
        // Default protocol to perform request on resource
        public const String PROTOCOL_HTTP = "http";

        // Default protocol to perform request on resource
        public const String PROTOCOL_HTTPS = "https";

        // Address ex: localhost or toms-cz.com without http or https
        private String address;
        private int port;
        // Additional parameters it should exclude port, which is specify in port variable
        private String parameters;
        // Protocol ex: http or https
        private String protocol;
        // Type of expected returned value
        private HeaderType acceptedType;
        // Type of header request
        private HeaderType contentType;
        // Type of send method
        private HttpMethod httpMethod = null;

        private ConnectionSecurity security;

        private Dictionary<String, String> headerParams = new Dictionary<String, String>();

        /**
         * Constructor to create connection.
         * 
         * @param address end point without protocol ex: localhost, toms-cz.com, google.com
         * @param port port in which service available.
         * @param parameters url parameters if service is available on localhost:8080/rest/personService
         *        then parameter are /rest/personService include slash, or
         *        localhost:8080/AFServer/rest/personService then parameter is
         *        /AFserver/rest/personService
         */
        public AFSwinxConnection(String address, int port, String parameters)
        {
            this.address = address;
            this.port = port;
            this.parameters = parameters;
            this.acceptedType = HeaderType.JSON;
            this.contentType = HeaderType.JSON;
            this.protocol = PROTOCOL_HTTP;
            this.httpMethod = HttpMethod.Get;
        }

        /**
         * Constructor to create connection.
         * 
         * @param address end point without protocol ex: localhost, toms-cz.com, google.com
         * @param port port in which service available.
         * @param parameters url parameters if service is available on localhost:8080/rest/personService
         *        then parameter are /rest/personService include slash, or
         *        localhost:8080/AFServer/rest/personService then parameter is
         *        /AFserver/rest/personService
         * @param protocol ex: http, https, etc.
         */
        public AFSwinxConnection(String address, int port, String parameters, String protocol)
        {
            this.address = address;
            this.port = port;
            this.parameters = parameters;
            this.acceptedType = HeaderType.JSON;
            this.contentType = HeaderType.JSON;
            this.protocol = protocol;
            this.httpMethod = HttpMethod.Get;
        }

        /**
         * 
         * Constructor to create connection.
         * 
         * @param address end point without protocol ex: localhost, toms-cz.com, google.com
         * @param port port in which service available.
         * @param parameters url parameters if service is available on localhost:8080/rest/personService
         *        then parameter are /rest/personService include slash, or
         *        localhost:8080/AFServer/rest/personService then parameter is
         *        /AFserver/rest/personService
         * @param acceptedType type of accepted response see {@link HeaderType} for more information
         * @param contentType type of request on end point see {@link HeaderType} for more information
         */
        public AFSwinxConnection(String address, int port, String parameters, HeaderType acceptedType,
                HeaderType contentType)
        {
            this.address = address;
            this.port = port;
            this.parameters = parameters;
            this.acceptedType = acceptedType;
            this.contentType = contentType;
            this.protocol = PROTOCOL_HTTP;
            this.httpMethod = HttpMethod.Get;
        }

        /**
         * 
         * Constructor to create connection.
         * 
         * @param address end point without protocol ex: localhost, toms-cz.com, google.com
         * @param port port in which service available.
         * @param parameters url parameters if service is available on localhost:8080/rest/personService
         *        then parameter are /rest/personService include slash, or
         *        localhost:8080/AFServer/rest/personService then parameter is
         *        /AFserver/rest/personService
         * @param acceptedType type of accepted response see {@link HeaderType} for more information
         * @param contentType type of request on end point see {@link HeaderType} for more information
         * @param httpMethod method which will be used see {@link HttpMethod}
         */
        public AFSwinxConnection(String address, int port, String parameters, HeaderType acceptedType,
                HeaderType contentType, HttpMethod httpMethod)
        {
            this.address = address;
            this.port = port;
            this.parameters = parameters;
            this.acceptedType = acceptedType;
            this.contentType = contentType;
            this.protocol = PROTOCOL_HTTP;
            this.httpMethod = httpMethod;
        }

        /**
         * 
         * Constructor to create connection.
         * 
         * @param address end point without protocol ex: localhost, toms-cz.com, google.com
         * @param port port in which service available.
         * @param parameters url parameters if service is available on localhost:8080/rest/personService
         *        then parameter are /rest/personService include slash, or
         *        localhost:8080/AFServer/rest/personService then parameter is
         *        /AFserver/rest/personServicer more information
         * @param contentType type of request on end point see {@link HeaderType} for more information
         * @param protocol ex: http, https, etc.
         */
        public AFSwinxConnection(String address, int port, String parameters, HeaderType acceptedType,
                HeaderType contentType, String protocol)
        {
            this.address = address;
            this.port = port;
            this.parameters = parameters;
            this.acceptedType = acceptedType;
            this.contentType = contentType;
            this.protocol = protocol;
            this.httpMethod = HttpMethod.Get;
        }

        /**
         * 
         * Constructor to create connection.
         * 
         * @param address end point without protocol ex: localhost, toms-cz.com, google.com
         * @param port port in which service available.
         * @param parameters url parameters if service is available on localhost:8080/rest/personService
         *        then parameter are /rest/personService include slash, or
         *        localhost:8080/AFServer/rest/personService then parameter is
         *        /AFserver/rest/personServicer more information
         * @param contentType type of request on end point see {@link HeaderType} for more information
         * @param protocol ex: http, https, etc.
         * @param httpMethod method which will be used see {@link HttpMethod}
         */
        public AFSwinxConnection(String address, int port, String parameters, HeaderType acceptedType,
                HeaderType contentType, String protocol, HttpMethod httpMethod)
        {
            this.address = address;
            this.port = port;
            this.parameters = parameters;
            this.acceptedType = acceptedType;
            this.contentType = contentType;
            this.protocol = protocol;
            this.httpMethod = httpMethod;
        }

        /**
         * This constructor is protected because its used in {@link ConnectionParser}. It's easily use
         * set method then hold all variables in memory. Default content and header type is
         * application.json from {@link HeaderType}.
         */
        public AFSwinxConnection()
        {
            this.contentType = HeaderType.JSON;
            this.acceptedType = HeaderType.JSON;
        }

        public String getAddress()
        {
            return address;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }

        public int getPort()
        {
            return port;
        }

        public void setPort(int port)
        {
            this.port = port;
        }

        public String getParameters()
        {
            return parameters;
        }

        public void setParameters(String parameters)
        {
            this.parameters = parameters;
        }

        public HeaderType getAcceptedType()
        {
            return acceptedType;
        }

        public void setAcceptedType(HeaderType acceptedType)
        {
            this.acceptedType = acceptedType;
        }

        public HeaderType getContentType()
        {
            return contentType;
        }

        public void setContentType(HeaderType contentType)
        {
            this.contentType = contentType;
        }

        public String getProtocol()
        {
            return protocol;
        }

        public void setProtocol(String protocol)
        {
            this.protocol = protocol;
        }

        public HttpMethod getHttpMethod()
        {
            return httpMethod;
        }

        public void setHttpMethod(HttpMethod httpMethod)
        {
            this.httpMethod = httpMethod;
        }

        public Dictionary<String, String> getHeaderParams()
        {
            return headerParams;
        }

        public void setHeaderParams(Dictionary<String, String> headerParams)
        {
            this.headerParams = headerParams;
        }

        public void addHeaderParam(String key, String value)
        {
            this.headerParams.Add(key, value);
        }

        public ConnectionSecurity getSecurity()
        {
            return security;
        }

        public void setSecurity(ConnectionSecurity security)
        {
            this.security = security;
        }
    }
}
