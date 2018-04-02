package com.tomscz.af.showcase.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.view.WelcomeScreen;
import com.tomscz.af.showcase.view.controller.WelcomeScreenController;
import com.tomscz.af.showcase.view.dialogs.Dialogs;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.rest.connection.Device;

public class Main {

    public static void main(String[] args) {

        try {
            ApplicationContext.getInstance().changeLocalization(ShowcaseConstants.ENGLISH_BUNDLE);
        } catch (FileNotFoundException e) {
            // Try czech bundle
            try {
                ApplicationContext.getInstance().changeLocalization(ShowcaseConstants.CZECH_BUNDLE);
            } catch (FileNotFoundException fileNotFoundException) {
                // Do nothing localization wont be used
            }
        }

        //setup ui proxy
        AFSwinx.getInstance().setProxySetup(ApplicationContext.getInstance());

        try {
            AFProxyScreenDefinition screenDefinition = AFSwinx.getInstance()
                    .getScreenDefinitionBuilder(ApplicationContext.getInstance().getUiProxyUrl() + "/api/screens/5a9955636402eb092c3b56c7", "Login")
                    .getScreenDefinition();
            WelcomeScreen welcomeScreen = new WelcomeScreen(screenDefinition);
            WelcomeScreenController controller = new WelcomeScreenController(welcomeScreen);
        } catch (Exception e) {
            System.err.println("Cannot get welcome screen from server. Please check stack trace");
            e.printStackTrace();
        }
    }

}
