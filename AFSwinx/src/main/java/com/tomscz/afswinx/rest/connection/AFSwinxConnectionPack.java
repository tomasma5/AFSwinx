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
    private AFSwinxConnection sendConnection;

    public AFSwinxConnectionPack() {

    }

    public AFSwinxConnectionPack(AFSwinxConnection metamodelConnection,
            AFSwinxConnection dataConnection, AFSwinxConnection sendConnection) {
        super();
        this.metamodelConnection = metamodelConnection;
        this.dataConnection = dataConnection;
        this.sendConnection = sendConnection;
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

    public AFSwinxConnection getSendConnection() {
        return sendConnection;
    }

    public void setSendConnection(AFSwinxConnection sendConnection) {
        this.sendConnection = sendConnection;
    }

}
