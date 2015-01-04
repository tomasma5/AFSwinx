package com.tomscz.af.showcase.main;

import java.io.FileNotFoundException;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.view.MainFrame;
import com.tomscz.af.showcase.view.WelcomeScreen;
import com.tomscz.af.showcase.view.controller.WelcomeScreenController;

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
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        WelcomeScreenController controller = new WelcomeScreenController(welcomeScreen);
    }

}
