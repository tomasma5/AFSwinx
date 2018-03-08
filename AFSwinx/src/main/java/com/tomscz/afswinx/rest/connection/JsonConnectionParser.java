package com.tomscz.afswinx.rest.connection;

import com.tomscz.afrest.commons.AFRestUtils;
import com.tomscz.afswinx.common.Utils;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * This class parse connection from input file (xml) and build connection from them.
 *
 * @author Martin Tomasek (martin@toms-cz.com)
 * @since 1.0.0.
 */
public class JsonConnectionParser implements JSONParser {

    // Tags which separate connection type
    private static final String METAMODEL_CONNECTION = "modelConnection";
    private static final String DATA_CONNECTION = "dataConnection";
    private static final String SEND_CONNECTION = "sendConnection";

    // Tags which will specify security
    private static final String SECURITY_METHOD = "securityMethod";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    // Concrete value of connection
    private static final String ADDRESS = "address";
    private static final String END_POINT_PARAMETERS = "parameters";
    private static final String PROTOCOL = "protocol";
    private static final String PORT = "port";

    private static final String REAL_ADDRESS = "realAddress";
    private static final String REAL_END_POINT_PARAMETERS = "realParameters";
    private static final String REAL_PROTOCOL = "realProtocol";
    private static final String REAL_PORT = "realPort";

    private static final String HEADER_PARAM = "headerParams";
    private static final String SECURITY_PARAMS = "securityParams";
    private static final String CONTENT_TYPE = "content-type";
    private static final String ACCEPT_TYPE = "accept-type";

    private static final String REAL_ENDPOINT = "real-endpoint";

    private HashMap<String, String> elConnectionData;

    // Flag if EL evaluation will be done
    private boolean doElEvaluation = false;

    /**
     * Constructor.
     *
     * @param elConnectionData data which will be used to replace EL variables.
     */
    public JsonConnectionParser(HashMap<String, String> elConnectionData) {
        if (elConnectionData != null && !elConnectionData.isEmpty()) {
            this.elConnectionData = elConnectionData;
            doElEvaluation = true;
        }
    }

    /**
     * This method parse document in which are defined connection.
     *
     * @return {@link AFSwinxConnectionPack} which holds all connection which are in documents and
     * which belongs to concrete connection key.
     */
    @SuppressWarnings("unchecked")
    @Override
    public AFSwinxConnectionPack parse(JSONObject connections) {
        // Prepare connection pack
        AFSwinxConnectionPack connectionPack = new AFSwinxConnectionPack();
        // Find root of document

        JSONObject modelConnection = connections.optJSONObject(METAMODEL_CONNECTION);
        JSONObject dataConnection = connections.optJSONObject(DATA_CONNECTION);
        JSONObject sendConnection = connections.optJSONObject(SEND_CONNECTION);
        if (modelConnection != null) {
            parseConnection(connectionPack, METAMODEL_CONNECTION, modelConnection);
        }
        if (dataConnection != null) {
            parseConnection(connectionPack, DATA_CONNECTION, dataConnection);
        }
        if (sendConnection != null) {
            parseConnection(connectionPack, SEND_CONNECTION, sendConnection);
        }

        return connectionPack;
    }

    private void parseConnection(AFSwinxConnectionPack connectionPack, String connectionName, JSONObject connectionObj) {
        AFSwinxConnection connection = new AFSwinxConnection();
        connection.setAddress(evaluateEL(connectionObj.getString(ADDRESS)));
        connection.setParameters(evaluateEL(connectionObj.getString(END_POINT_PARAMETERS)));
        connection.setPort(connectionObj.getInt(PORT));
        connection.setProtocol(evaluateEL(connectionObj.getString(PROTOCOL)));
        String realAddress = evaluateEL(connectionObj.getString(REAL_ADDRESS));
        String realParameters = evaluateEL(connectionObj.getString(REAL_END_POINT_PARAMETERS));
        int realPort = connectionObj.getInt(REAL_PORT);
        String realProtocol = evaluateEL(connectionObj.getString(REAL_PROTOCOL));
        String realEndpoint = realProtocol + "://" + realAddress + (realPort != 0 ? ":" + realPort : "") + realParameters;
        connection.addHeaderParam(REAL_ENDPOINT, realEndpoint);
        parseHeaderParam(connection, connectionObj.optJSONObject(HEADER_PARAM));
        parseSecurityParams(connection, connectionObj.optJSONObject(SECURITY_PARAMS));
        // Set created connection to connection holder based on connection type
        switch (connectionName) {
            case METAMODEL_CONNECTION:
                connection.setHttpMethod(HttpMethod.GET);
                connectionPack.setMetamodelConnection(connection);
                break;
            case DATA_CONNECTION:
                connection.setHttpMethod(HttpMethod.GET);
                connectionPack.setDataConnection(connection);
                break;
            case SEND_CONNECTION:
                if (connection.getHttpMethod() == null) {
                    connection.setHttpMethod(HttpMethod.POST);
                }
                connectionPack.setSendConnection(connection);
                break;
            default:
                break;
        }
    }

    /**
     * This method parse security and set it to connection.
     *
     * @param connection     current connection
     * @param securityParams node with security options
     */
    private void parseSecurityParams(AFSwinxConnection connection, JSONObject securityParams) {
        if (securityParams == null) {
            return;
        }
        ConnectionSecurity security = new ConnectionSecurity();
        String key, value;
        for (String nextKey : securityParams.keySet()) {
            key = evaluateEL(nextKey);
            value = evaluateEL(securityParams.getString(nextKey));
            if (value != null) {

                if (key.equals(SECURITY_METHOD)) {
                    security.setMethod((SecurityMethod) AFRestUtils.getEnumFromString(SecurityMethod.class, value, true));
                }
                if (key.equals(USER_NAME)) {
                    security.setUserName(value);
                }
                if (key.equals(PASSWORD)) {
                    security.setPassword(value);
                }
            }
        }
        connection.setSecurity(security);
    }

    private void parseHeaderParam(AFSwinxConnection connection, JSONObject headerParams) {
        if (headerParams == null) {
            return;
        }
        String key, value;
        for (String nextKey : headerParams.keySet()) {
            key = evaluateEL(nextKey);
            value = evaluateEL(headerParams.getString(nextKey));
            // Parse content type and accept type separately
            if (value != null) {
                if (key.toLowerCase().equals(CONTENT_TYPE)) {
                    connection.setContentType((HeaderType) AFRestUtils.getEnumFromString(HeaderType.class, value, true));
                } else if (key.toLowerCase().equals(ACCEPT_TYPE)) {
                    connection.setAcceptedType((HeaderType) AFRestUtils.getEnumFromString(HeaderType.class, value, true));
                } else {
                    connection.addHeaderParam(key, value);
                }
            }
        }
    }

    /**
     * This method determine if EL parse will be done or not.
     *
     * @param nodeValue value of node in which will be used EL parser
     * @return value of node after execute EL parser on it. If parser should not be execute then
     * return received value without modification
     */
    private String evaluateEL(String nodeValue) {
        if (doElEvaluation && nodeValue != null && !nodeValue.isEmpty()) {
            return Utils.evaluateElExpression(nodeValue, elConnectionData);
        }
        return nodeValue;
    }

}
