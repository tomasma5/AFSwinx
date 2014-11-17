package com.tomscz.af.showcase.main;

import java.io.FileNotFoundException;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.view.forms.PersonFormView;
import com.tomscz.af.showcase.view.forms.WelcomeScreen;

public class Main {

    public static void main(String[] args) {

        try {
            ApplicationContext.getInstance().changeLocalization(ShowcaseConstants.ENGLISH_BUNDLE);
        } catch (FileNotFoundException e) {
            //TODO handle it with
        }
//        WelcomeScreen welcomeScreen = new WelcomeScreen();
      PersonFormView personView = new PersonFormView();
    }

}
