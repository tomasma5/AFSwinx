package com.tomscz.af.showcase.view.controller;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.SecurityContext;
import com.tomscz.af.showcase.application.ShowcaseSecurity;
import com.tomscz.af.showcase.view.WelcomeScreen;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreenController extends BaseController {

    public final static String loginFormName = "loginForm";

    public WelcomeScreenController(WelcomeScreen screen) {
        super(screen);
        registerListeners();
    }
    
    @Override
    protected void registerListeners(){
        super.registerListeners();
        WelcomeScreen ws = (WelcomeScreen) view;
        ws.addSwinxLoginButtonListner(onLoginButtonExec);
    }
    
    private ActionListener onLoginButtonExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(ApplicationContext.getInstance().getSecurityContext() != null && ApplicationContext.getInstance().getSecurityContext().isUserLogged()){
                    ApplicationContext.getInstance().setSecurityContext(null);
                    view.getMainFrame().getContentPane().removeAll();
                    view.intialize();
                    registerListeners();
                    view.getMainFrame().getContentPane().repaint();
                }
                else if(AFSwinx.getInstance().getExistedComponent(loginFormName).validateData()){
                    AFSwinx.getInstance().getExistedComponent(loginFormName).sendData();
                    AFDataHolder data =
                            AFSwinx.getInstance().getExistedComponent(loginFormName)
                                    .resealize();
                    // Parse send userName and password. This part is depend on server source.
                    String userName = null;
                    String password = null;
                    for (String key : data.getPropertiesAndValues().keySet()) {
                        String value = data.getPropertiesAndValues().get(key);
                        if (key.toLowerCase().equals("username")) {
                            userName = value;
                        } else if (key.toLowerCase().equals("password")) {
                            password = value;
                        }
                    }
                    SecurityContext securityContext = new ShowcaseSecurity(userName, password, true);
                    ApplicationContext.getInstance().setSecurityContext(securityContext);
                    view.getMainFrame().getContentPane().removeAll();
                    view.intialize();
                    registerListeners();
                    view.getMainFrame().getContentPane().repaint();
                }
            } catch (AFSwinxConnectionException e1) {
                view.getDialogs().failed("login.failed", "login.failed", e1.getMessage());
            }
        }
    };

}
