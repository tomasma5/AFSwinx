package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.SecurityContext;
import com.tomscz.af.showcase.application.ShowcaseSecurity;
import com.tomscz.af.showcase.view.forms.WelcomeScreen;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

public class WelcomeScreenController extends BaseController {

    public WelcomeScreenController(WelcomeScreen screen) {
        super(screen);
        WelcomeScreen ws = (WelcomeScreen) view;
        ws.addSwinxLoginButtonListner(onLoginButtonExec);
    }

    private ActionListener onLoginButtonExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AFSwinx.getInstance().getExistedComponent(WelcomeScreen.loginFormName).sendData();
                AFDataHolder data =
                        AFSwinx.getInstance().getExistedComponent(WelcomeScreen.loginFormName)
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
            } catch (AFSwinxConnectionException e1) {
                view.getDialogs().failed("login.failed", "login.failed", e1.getMessage());
            }
        }
    };

}
