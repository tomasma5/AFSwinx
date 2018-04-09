package com.tomscz.afswinx.component.abstraction;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import org.apache.http.HttpResponse;

import com.google.gson.JsonElement;
import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;
import com.tomscz.afswinx.component.builders.ComponentDataPacker;
import com.tomscz.afswinx.rest.connection.AFConnector;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.connection.HeaderType;
import com.tomscz.afswinx.rest.rebuild.BaseRestBuilder;
import com.tomscz.afswinx.rest.rebuild.RestBuilderFactory;

/**
 * This is abstract top level component. This components are used to hold another concrete
 * components. It component can be added to {@link JPanel} as well.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class AFSwinxTopLevelComponent extends JPanel
        implements
            AFSwinxInteraction,
            ComponentReserialization {

    private HashMap<String, ComponentDataPacker> panels =
            new HashMap<String, ComponentDataPacker>();

    // Connection which are used to retrieve data
    protected AFSwinxConnection modelConnection;
    protected AFSwinxConnection sendConnection;
    protected AFSwinxConnection dataConnection;
    // Last response from server.
    private HttpResponse lastResponse;

    private static final long serialVersionUID = 1L;

    public abstract SupportedComponents getComponentType();

    public ResourceBundle localization;

    @Override
    public AFMetaModelPack getModel() throws AFSwinxConnectionException {
        try {
            AFConnector<AFMetaModelPack> modelConnector =
                    new AFConnector<AFMetaModelPack>(getModelConnection(), AFMetaModelPack.class);
            this.lastResponse = modelConnector.getResponse();
            return modelConnector.doRequest(null);
        } catch (ConnectException e) {
            throw new AFSwinxConnectionException(e.getLocalizedMessage());
        }
    }

    @Override
    public Object getData() throws AFSwinxConnectionException {
        if (getDataConnection() == null) {
            return null;
        }
        try {
            AFConnector<?> dataConnector;
            if (getDataConnection().getAcceptedType().equals(HeaderType.XML)) {
                // TODO finish XML, It wont be support in first version
                throw new UnsupportedOperationException("XML File is not supperted yet");
            } else {
                dataConnector =
                        new AFConnector<>(getDataConnection(), JsonElement.class);
            }
            // Set response for future use
            this.lastResponse = dataConnector.getResponse();
            return dataConnector.doRequest(null);
        } catch (ConnectException e) {
            throw new AFSwinxConnectionException(e.getLocalizedMessage());
        }
    }

    @Override
    public Object generateSendData() {
        // before building data and sending, validate actual data
        boolean isValid = validateData();
        if (!isValid) {
            return null;
        }
        AFSwinxConnection sendConnection = getSendConnection();
        // Generate send connection based on which will be retrieve data. The send connection is
        // used to generate data in this case it will be generated JSON
        if (sendConnection == null) {
            sendConnection = new AFSwinxConnection("", 0, "");
        }
        BaseRestBuilder dataBuilder = RestBuilderFactory.getInstance().getBuilder(sendConnection);
        Object data = dataBuilder.reselialize(this.resealize());
        return data;
    }

    @Override
    public void sendData() throws AFSwinxConnectionException {
        if (getSendConnection() == null) {
            throw new IllegalStateException(
                    "The post connection was not specify. Check your XML configuration or Connection which was used to build this form");
        }
        Object data = generateSendData();
        if (data == null) {
            return;
        }
        AFConnector<Object> dataConnector =
                new AFConnector<Object>(getSendConnection(), Object.class);
        try {
            dataConnector.doRequest(data.toString());
            // Set response for future use
            this.lastResponse = dataConnector.getResponse();
        } catch (ConnectException e) {
            throw new AFSwinxConnectionException(e.getMessage());
        }
    }

    public HashMap<String, ComponentDataPacker> getPanels() {
        return panels;
    }

    public AFSwinxConnection getModelConnection() {
        return modelConnection;
    }

    public AFSwinxConnection getDataConnection() {
        return dataConnection;
    }

    public HttpResponse getLastResponse() {
        return lastResponse;
    }

    public AFSwinxConnection getSendConnection() {
        return sendConnection;
    }

    public void setSendConnection(AFSwinxConnection sendConnection) {
        this.sendConnection = sendConnection;
    }

}
