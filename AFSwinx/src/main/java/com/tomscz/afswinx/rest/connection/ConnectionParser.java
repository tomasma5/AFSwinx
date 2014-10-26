package com.tomscz.afswinx.rest.connection;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tomscz.afswinx.common.Utils;
import com.tomscz.afswinx.common.XMLParser;
import com.tomscz.afswinx.rest.connection.BaseConnector.HeaderType;
import com.tomscz.afrest.commons.AFRestUtils;

/**
 * This class parse connection from input file (xml) and build connection from them.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class ConnectionParser implements XMLParser {

    // Tags which separate connections
    private static final String CONNECTION_TAG = "connection";
    private static final String CONNECTION_ID_ATTRIBUTE = "id";

    // Tags which separate connection type
    private static final String METAMODEL_CONNECTION = "metaModel";
    private static final String DATA_CONNECTION = "data";
    private static final String POST_CONNECTION = "post";

    // Concrete value of connection
    private static final String END_POINT = "endPoint";
    private static final String END_POINT_PARAMETERS = "endPointParameters";
    private static final String PROTOCOL = "protocol";
    private static final String PORT = "port";
    private static final String CONTENT_TYPE = "content-type";
    private static final String ACCEPT_TYPE = "accept-type";

    private HashMap<String, String> elConnectionData;

    // Flag if EL evaluation will be done
    private boolean doElEvaluation = false;
    // Id of connection to parse
    private String connectionId;

    /**
     * Constructor. 
     * @param id of connection which will be parsed
     * @param elConnectionData data which will be used to replace EL variables.
     */
    public ConnectionParser(String id, HashMap<String, String> elConnectionData) {
        this.connectionId = id;
        if (elConnectionData != null && !elConnectionData.isEmpty()) {
            this.elConnectionData = elConnectionData;
            doElEvaluation = true;
        }
    }

    /**
     * Constructor
     * @param id of connection will will be parsed
     */
    public ConnectionParser(String id) {
        this.connectionId = id;
    }

    /**
     * This method parse document in which are defined connection.
     * 
     * @return {@link AFSwinxConnectionPack} which holds all connection which are in documents and
     *         which belongs to concrete connection key.
     */
    @SuppressWarnings("unchecked")
    @Override
    public AFSwinxConnectionPack parseDocument(Document documentToParse) {
        //Prepare connection pack
        AFSwinxConnectionPack connectionPack = new AFSwinxConnectionPack();
        //Find root of document
        NodeList childs = documentToParse.getElementsByTagName(CONNECTION_TAG);
        for (int i = 0; i < childs.getLength(); i++) {
            Node connectionRoot = childs.item(i);
            if (connectionRoot.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            //For each connection check if it's id is id which must be parsed
            Element nodeElement = (Element) connectionRoot;
            if (nodeElement.getAttribute(CONNECTION_ID_ATTRIBUTE).equals(connectionId)) {
                NodeList connectionsTypes = connectionRoot.getChildNodes();
                for (int j = 0; j < connectionsTypes.getLength(); j++) {
                    Node concreteConnection = connectionsTypes.item(j);
                    if (concreteConnection.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    String connectionName = concreteConnection.getNodeName();
                    NodeList nodeProperties = concreteConnection.getChildNodes();
                    //Create new connection
                    AFSwinxConnection connection = new AFSwinxConnection();
                    for (int k = 0; k < nodeProperties.getLength(); k++) {
                        //Set connection properties
                        Node property = nodeProperties.item(k);
                        String nodeName = property.getNodeName();
                        String nodeValue = property.getTextContent();
                        if (nodeName.equals(END_POINT)) {
                            connection.setAddress(evaluateEL(nodeValue));
                        } else if (nodeName.equals(END_POINT_PARAMETERS)) {
                            connection.setParameters(evaluateEL(nodeValue));
                        } else if (nodeName.equals(PROTOCOL)) {
                            connection.setProtocol(evaluateEL(nodeValue));
                        } else if (nodeName.equals(PORT)) {
                            connection.setPort(Utils.convertStringToInteger(evaluateEL(nodeValue)));
                        } else if (nodeName.equals(CONTENT_TYPE)) {
                            String contentType = evaluateEL(nodeValue);
                            connection.setContentType((HeaderType) AFRestUtils.getEnumFromString(
                                    HeaderType.class, contentType, true));
                        } else if (nodeName.equals(ACCEPT_TYPE)) {
                            String acceptType = evaluateEL(nodeValue);
                            connection.setAcceptedType((HeaderType) AFRestUtils.getEnumFromString(
                                    HeaderType.class, acceptType, true));
                        }
                    }
                    //Set created connection to connection holder based on connection type
                    if (connectionName.equals(METAMODEL_CONNECTION)) {
                        connectionPack.setMetamodelConnection(connection);
                    } else if (connectionName.equals(DATA_CONNECTION)) {
                        connectionPack.setDataConnection(connection);
                    } else if (connectionName.equals(POST_CONNECTION)) {
                        connectionPack.setPostConnection(connection);
                    }
                }
            }
        }
        return connectionPack;
    }

    /**
     * This method determine if EL parse will be done or not.
     * 
     * @param nodeValue value of node in which will be used EL parser
     * @return value of node after execute EL parser on it. If parser should not be execute then
     *         return received value without modification
     */
    private String evaluateEL(String nodeValue) {
        if (doElEvaluation && nodeValue != null && !nodeValue.isEmpty()) {
            return Utils.evaluateElExpression(nodeValue, elConnectionData);
        }
        return nodeValue;
    }

}
