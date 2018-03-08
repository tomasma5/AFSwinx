package com.tomscz.af.showcase.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.view.WelcomeScreen;
import com.tomscz.af.showcase.view.controller.WelcomeScreenController;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;

public class Main {

    public static void main(String[] args) {

        try {
            ApplicationContext.getInstance().changeLocalization(ShowcaseConstants.ENGLISH_BUNDLE);
            //setup ui proxy
            AFSwinx.getInstance().setProxyApplicationContext(ApplicationContext.getInstance().getUiProxyApplicationUuid());
            ApplicationContext.getInstance().loadUIProxyUrl();
        } catch (FileNotFoundException e) {
            // Try czech bundle
            try {
                ApplicationContext.getInstance().changeLocalization(ShowcaseConstants.CZECH_BUNDLE);
            } catch (FileNotFoundException fileNotFoundException) {
                // Do nothing localization wont be used
            }
        } catch (IOException e) {
            System.err.println("Cannot get application uuid from properties");
            e.printStackTrace();
        }

        try {
            AFProxyScreenDefinition screenDefinition = AFSwinx.getInstance()
                    .getScreenDefinitionBuilder(ApplicationContext.getInstance().getUiProxyUrl()+"/api/screens/5a9955636402eb092c3b56c7")
                    .getScreenDefinition();
            WelcomeScreen welcomeScreen = new WelcomeScreen(screenDefinition);
            WelcomeScreenController controller = new WelcomeScreenController(welcomeScreen);
        } catch (Exception e) {
            //TODO something went wrong
            e.printStackTrace();
        }
    }

}
