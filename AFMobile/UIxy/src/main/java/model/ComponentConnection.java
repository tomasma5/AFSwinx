package model;

import javax.persistence.*;
import javax.ws.rs.POST;
import java.util.Map;

/**
 * Holds information about component real server (where component is defined) and proxy connections (where user can get it)
 */
@Entity
@Table(name = ComponentConnection.TABLE_NAME)
public class ComponentConnection extends DtoEntity {

    public static final String TABLE_NAME = "component_connection";
    public static final String COMPONENT_CONNECTION_ID = "connection_id";
    public static final String ADDRESS = "address";
    public static final String PORT = "port";
    public static final String PARAMETERS = "parameters";
    public static final String PROTOCOL = "protocol";
    public static final String REAL_ADDRESS = "real_address";
    public static final String REAL_PORT = "real_port";
    public static final String REAL_PARAMETERS = "real_parameters";
    public static final String REAL_PROTOCOL = "real_protocol";

    @Id
    @Column(name = COMPONENT_CONNECTION_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = ADDRESS)
    private String address;
    @Column(name = PORT)
    private int port;
    @Column(name = PARAMETERS)
    private String parameters;
    @Column(name = PROTOCOL)
    private String protocol;
    @Column(name = REAL_ADDRESS)
    private String realAddress;
    @Column(name = REAL_PORT)
    private int realPort;
    @Column(name = REAL_PARAMETERS)
    private String realParameters;
    @Column(name = REAL_PROTOCOL)
    private String realProtocol;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "connection_headerParams",
            joinColumns = @JoinColumn(name = COMPONENT_CONNECTION_ID))
    @MapKeyColumn(name = "param_name")
    @Column(name = "param_value")
    private Map<String, String> headerParams;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "connection_securityParams",
            joinColumns = @JoinColumn(name = COMPONENT_CONNECTION_ID))
    @MapKeyColumn(name = "param_name")
    @Column(name = "param_value")
    private Map<String, String> securityParams;

    public ComponentConnection() {
    }

    public ComponentConnection(String address, int port, String parameters, String protocol,
                               String realAddress, int realPort, String realParameters, String realProtocol,
                               Map<String, String> headerParams, Map<String, String> securityParams) {
        this.address = address;
        this.port = port;
        this.parameters = parameters;
        this.protocol = protocol;
        this.realAddress = realAddress;
        this.realPort = realPort;
        this.realParameters = realParameters;
        this.realProtocol = realProtocol;
        this.headerParams = headerParams;
        this.securityParams = securityParams;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        if (!parameters.startsWith("/")) {
            parameters = "/" + parameters;
        }
        this.parameters = parameters;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
    }

    public Map<String, String> getSecurityParams() {
        return securityParams;
    }

    public void setSecurityParams(Map<String, String> securityParams) {
        this.securityParams = securityParams;
    }

    public String getRealAddress() {
        return realAddress;
    }

    public void setRealAddress(String realAddress) {
        this.realAddress = realAddress;
    }

    public int getRealPort() {
        return realPort;
    }

    public void setRealPort(int realPort) {
        this.realPort = realPort;
    }

    public String getRealParameters() {
        return realParameters;
    }

    public void setRealParameters(String realParameters) {
        this.realParameters = realParameters;
    }

    public String getRealProtocol() {
        return realProtocol;
    }

    public void setRealProtocol(String realProtocol) {
        this.realProtocol = realProtocol;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
