package com.tomscz.afswinx.rest.connection;

/**
 * This class holds all connection which are used in AFSwinx. It is holder object.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class AFSwinxConnectionPack {

    private AFSwinxConnection metamodelConnection;
    private AFSwinxConnection dataConnection;
    private AFSwinxConnection postConnection;

    public AFSwinxConnectionPack() {

    }

    public AFSwinxConnectionPack(AFSwinxConnection metamodelConnection,
            AFSwinxConnection dataConnection, AFSwinxConnection postConnection) {
        super();
        this.metamodelConnection = metamodelConnection;
        this.dataConnection = dataConnection;
        this.postConnection = postConnection;
    }

    public AFSwinxConnection getPostConnection() {
        return postConnection;
    }

    public void setPostConnection(AFSwinxConnection postConnection) {
        this.postConnection = postConnection;
    }

    public AFSwinxConnection getDataConnection() {
        return dataConnection;
    }

    public void setDataConnection(AFSwinxConnection dataConnection) {
        this.dataConnection = dataConnection;
    }

    public AFSwinxConnection getMetamodelConnection() {
        return metamodelConnection;
    }

    public void setMetamodelConnection(AFSwinxConnection metamodelConnection) {
        this.metamodelConnection = metamodelConnection;
    }

}
