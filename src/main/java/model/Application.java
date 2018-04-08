package model;

import javax.persistence.*;
import java.util.List;

/**
 * Application model. Holds information about applications registered in proxy
 */
@Entity
@Table(name = Application.TABLE_NAME)
public class Application extends DtoEntity {

    public static final String TABLE_NAME = "Application";
    public static final String APPLICATION_ID = "application_id";
    public static final String APPLICATION_UUID = "application_uuid";
    public static final String APPLICATION_NAME = "application_name";
    public static final String REMOTE_PROTOCOL = "remote_protocol";
    public static final String REMOTE_HOSTNAME = "remote_hostname";
    public static final String REMOTE_PORT = "remote_port";
    public static final String PROXY_PROTOCOL = "proxy_protocol";
    public static final String PROXY_HOSTNAME = "proxy_hostname";
    public static final String PROXY_PORT = "proxy_port";
    public static final String CONSUMER_PROTOCOL = "consumer_protocol";
    public static final String CONSUMER_HOSTNAME = "consumer_hostname";
    public static final String CONSUMER_PORT = "consumer_port";
    public static final String CONSUMER_CONTEXT_PATH = "consumer_context_path";

    @Id
    @Column(name = APPLICATION_ID)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(name = APPLICATION_UUID)
    private String uuid;
    @Column(name = APPLICATION_NAME)
    private String applicationName;
    @Column(name = REMOTE_PROTOCOL)
    private String remoteProtocol;
    @Column(name = REMOTE_HOSTNAME)
    private String remoteHostname;
    @Column(name = REMOTE_PORT)
    private int remotePort;
    @Column(name = PROXY_PROTOCOL)
    private String proxyProtocol;
    @Column(name = PROXY_HOSTNAME)
    private String proxyHostname;
    @Column(name = PROXY_PORT)
    private int proxyPort;
    @Column(name = CONSUMER_PROTOCOL)
    private String consumerProtocol;
    @Column(name = CONSUMER_HOSTNAME)
    private String consumerHostname;
    @Column(name = CONSUMER_PORT)
    private int consumerPort;
    @Column(name = CONSUMER_CONTEXT_PATH)
    private String consumerContextPath;
    @OneToMany(mappedBy = "application")
    private List<Screen> screenList;

    public Application() {
    }

    public Application(String uuid, String applicationName,
                       String remoteProtocol, String remoteHostname, int remotePort,
                       String proxyProtocol, String proxyHostname, int proxyPort) {
        this.uuid = uuid;
        this.applicationName = applicationName;
        this.remoteHostname = remoteHostname;
        this.remoteProtocol = remoteProtocol;
        this.remotePort = remotePort;
        this.proxyProtocol = proxyProtocol;
        this.proxyHostname = proxyHostname;
        this.proxyPort = proxyPort;
    }

    public List<Screen> getScreenList() {
        return screenList;
    }

    public void setScreenList(List<Screen> screenList) {
        this.screenList = screenList;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getRemoteProtocol() {
        return remoteProtocol;
    }

    public void setRemoteProtocol(String remoteProtocol) {
        this.remoteProtocol = remoteProtocol;
    }

    public String getRemoteHostname() {
        return remoteHostname;
    }

    public void setRemoteHostname(String remoteHostname) {
        this.remoteHostname = remoteHostname;
    }

    public String getProxyProtocol() {
        return proxyProtocol;
    }

    public void setProxyProtocol(String proxyProtocol) {
        this.proxyProtocol = proxyProtocol;
    }

    public String getProxyHostname() {
        return proxyHostname;
    }

    public void setProxyHostname(String proxyHostname) {
        this.proxyHostname = proxyHostname;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getConsumerProtocol() {
        return consumerProtocol;
    }

    public void setConsumerProtocol(String consumerProtocol) {
        this.consumerProtocol = consumerProtocol;
    }

    public String getConsumerHostname() {
        return consumerHostname;
    }

    public void setConsumerHostname(String consumerHostname) {
        this.consumerHostname = consumerHostname;
    }

    public int getConsumerPort() {
        return consumerPort;
    }

    public void setConsumerPort(int consumerPort) {
        this.consumerPort = consumerPort;
    }

    public String getConsumerContextPath() {
        return consumerContextPath;
    }

    public void setConsumerContextPath(String consumerContextPath) {
        this.consumerContextPath = consumerContextPath;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
