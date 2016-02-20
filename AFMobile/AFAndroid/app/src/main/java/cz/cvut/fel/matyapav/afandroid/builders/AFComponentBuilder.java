package cz.cvut.fel.matyapav.afandroid.builders;

import android.app.Activity;

import java.io.InputStream;
import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.components.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.AFForm;
import cz.cvut.fel.matyapav.afandroid.parsers.ConnectionParser;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnection;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnectionPack;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 19.02.2016.
 */
public abstract class AFComponentBuilder<T> {

    private Activity activity;

    AFSwinxConnection modelConnection;
    AFSwinxConnection dataConnection;
    AFSwinxConnection sendConnection;

    String connectionKey;
    String componentKeyName;
    InputStream connectionResource;
    HashMap<String, String> connectionParameters;

    public T initBuilder(Activity activity, String componentKeyName, InputStream connectionResource, String connectionKey){
        this.activity = activity;
        this.componentKeyName = componentKeyName;
        this.connectionResource = connectionResource;
        this.connectionKey = connectionKey;
        return (T) this;
    }

    public T initBuilder(Activity activity, String componentKeyName, InputStream connectionResource,
                         String connectionKey, HashMap<String, String> connectionParameters) {
        this.activity = activity;
        this.componentKeyName = componentKeyName;
        this.connectionResource = connectionResource;
        this.connectionKey = connectionKey;
        this.connectionParameters = connectionParameters;
        return (T) this;
    }

    public void initializeConnections() throws Exception {
        if (modelConnection == null && connectionKey != null && connectionResource != null) {
            ConnectionParser connectionParser =
                    new ConnectionParser(connectionKey, connectionParameters);
            AFSwinxConnectionPack connections =
                    connectionParser.parseDocument(Utils
                            .buildDocumentFromFile(connectionResource));
            modelConnection = connections.getMetamodelConnection();
            dataConnection = connections.getDataConnection();
            sendConnection = connections.getSendConnection();
        } else {
            // Model connection is important if it could be found then throw exception
            throw new Exception(
                    "There is error during building AFSwinxForm. Connection was not specified. Did you used initBuilder method before build?");
        }
    }

    public abstract AFComponent createComponent() throws Exception;

    protected abstract void insertData(String dataResponse, AFComponent component, StringBuilder road);

    public Activity getActivity() {
        return activity;
    }
}
