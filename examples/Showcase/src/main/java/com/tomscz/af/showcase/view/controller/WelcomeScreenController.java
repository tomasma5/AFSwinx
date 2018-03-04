package com.tomscz.af.showcase.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.SecurityContext;
import com.tomscz.af.showcase.application.ShowcaseSecurity;
import com.tomscz.af.showcase.view.WelcomeScreen;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;
import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

public class WelcomeScreenController extends BaseController {

    public WelcomeScreenController(WelcomeScreen screen) {
        super(screen);
        registerListeners();
    }
    
    @Override
    protected void registerListeners(){
        super.registerListeners();
    }

}
