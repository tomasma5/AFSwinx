package com.tomscz.af.showcase.view;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.SecurityContext;
import com.tomscz.af.showcase.utils.Localization;
import com.tomscz.af.showcase.view.skin.LoginSkin;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WelcomeScreen extends BaseView {

    private static final long serialVersionUID = 1L;
    public final static String LOGIN_FORM_NAME = "loginForm";

    private JButton afSwinxLoginButton;

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
            afSwinxLoginButton = new JButton(Localization.getLocalizationText("logout.button"));
            mainPanel.add(afSwinxLoginButton);
        } else {
            try {
                AFSwinxForm form = getScreenDefinition().getFormBuilderByKey(LOGIN_FORM_NAME)
                        .setLocalization(ApplicationContext.getInstance().getLocalization())
                        .setSkin(new LoginSkin())
                        .buildComponent();
                afSwinxLoginButton = new JButton(Localization.getLocalizationText("login.button"));
                JPanel componentPanel = new JPanel();
                componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));
                componentPanel.add(form);
                componentPanel.add(Box.createVerticalStrut(20));
                afSwinxLoginButton.setAlignmentX(CENTER_ALIGNMENT);
                componentPanel.add(afSwinxLoginButton);
                mainPanel.add(componentPanel, BorderLayout.NORTH);
                return mainPanel;
            } catch (AFSwinxBuildException e) {
                getDialogs().failed("afswinx.build.title.failed", "afswinx.build.text.failed", e.getMessage());
            }
        }
        return mainPanel;
    }

    public void addSwinxLoginButtonListner(ActionListener a) {
        if (afSwinxLoginButton != null) {
            this.afSwinxLoginButton.addActionListener(a);
        }
    }

}
