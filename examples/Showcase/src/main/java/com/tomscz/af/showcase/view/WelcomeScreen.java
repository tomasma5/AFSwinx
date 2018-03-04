package com.tomscz.af.showcase.view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.InputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.SecurityContext;
import com.tomscz.af.showcase.utils.Localization;
import com.tomscz.af.showcase.view.skin.LoginSkin;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxMenuButton;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;

public class WelcomeScreen extends BaseView {

    private static final long serialVersionUID = 1L;

    public WelcomeScreen(AFProxyScreenDefinition screenDefinition) {
        super(screenDefinition);
        intialize();
    }

    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        SecurityContext sc = ApplicationContext.getInstance().getSecurityContext();
        if (sc != null && sc.isUserLogged()) {
            mainPanel.add(new JLabel("Welcome user:" + sc.getUserLogin()));
        } else {
            mainPanel.add(new JLabel("Welcome to our application. Please click on Login in menu to proceed."));
        }
        return mainPanel;
    }


}
