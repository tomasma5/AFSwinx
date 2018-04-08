package model;

import javax.persistence.*;

/**
 * Component connection pack holds information model, data and send connection.
 */
@Entity
@Table(name = ComponentConnectionPack.TABLE_NAME)
public class ComponentConnectionPack extends DtoEntity {

    public static final String TABLE_NAME = "component_connection_pack";
    public static final String COMPONENT_PACK_ID = "connection_pack_id";

    @Id
    @Column(name = COMPONENT_PACK_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private ComponentConnection modelConnection;
    @OneToOne
    private ComponentConnection dataConnection;
    @OneToOne
    private ComponentConnection sendConnection;

    public ComponentConnectionPack() {
    }

    public ComponentConnectionPack(ComponentConnection modelConnection, ComponentConnection dataConnection, ComponentConnection sendConnection) {
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.sendConnection = sendConnection;
    }

    public ComponentConnection getModelConnection() {
        return modelConnection;
    }

    public void setModelConnection(ComponentConnection modelConnection) {
        this.modelConnection = modelConnection;
    }

    public ComponentConnection getDataConnection() {
        return dataConnection;
    }

    public void setDataConnection(ComponentConnection dataConnection) {
        this.dataConnection = dataConnection;
    }

    public ComponentConnection getSendConnection() {
        return sendConnection;
    }

    public void setSendConnection(ComponentConnection sendConnection) {
        this.sendConnection = sendConnection;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
