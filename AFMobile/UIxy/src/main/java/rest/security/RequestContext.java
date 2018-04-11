package rest.security;

import model.Application;

import javax.enterprise.context.RequestScoped;
import java.io.Serializable;

/**
 * Request context which contains application from which the request is
 *
 * @Author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
@RequestScoped
public class RequestContext implements Serializable{

    private static final long serialVersionUID = -5039880007004887337L;

    private Application currentApplication;

    public Application getCurrentApplication() {
        return currentApplication;
    }

    public void setCurrentApplication(Application currentApplication) {
        this.currentApplication = currentApplication;
    }
}
