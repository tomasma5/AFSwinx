package com.tomscz.af.showcase.view.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;

public class WelcomeScreen extends BaseScreen {

    private static final long serialVersionUID = 1L;
    public final static String loginFormName = "loginForm";
    
    private JButton afSwinxLoginButton;

    public WelcomeScreen() {
        intialize();
    }
    
    @Override
    protected void intialize(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(createHeader());
        JPanel contentPanel = new JPanel();
        Box b1 = Box.createHorizontalBox();
        b1.add(createLeftMenu());
        b1.add(Box.createHorizontalGlue());
        b1.add(createAFSwinxLoginForm());
        b1.setBackground(Color.RED);
        contentPanel.add(b1);
        contentPanel.setBackground(Color.GREEN);
        mainPanel.add(contentPanel);
        this.add(mainPanel);
        this.setTitle("Showcase for "
                + Localization.getLocalizationText("login.header.mainText",
                        ShowcaseConstants.APPLICATION_VERSION));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
    }

    private JPanel createAFSwinxLoginForm() {
        JPanel mainPanel = new JPanel();
        File connectionFile =
                new File(getClass().getClassLoader().getResource("connection.xml").getFile());
        try {
            AFSwinxForm form =
                    AFSwinx.getInstance().getFormBuilder()
                            .initBuilder(loginFormName, connectionFile, "loginForm")
                            .setLocalization(ApplicationContext.getInstance().getLocalization())
                            .buildComponent();
            afSwinxLoginButton = new JButton(Localization.getLocalizationText("login.button"));
            JPanel componentPanel = new JPanel();
            componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));
            componentPanel.add(form);
            componentPanel.add(Box.createVerticalStrut(20));
            afSwinxLoginButton.setAlignmentX(CENTER_ALIGNMENT);
            componentPanel.add(afSwinxLoginButton);
            mainPanel.add(componentPanel,BorderLayout.NORTH);
            mainPanel.setPreferredSize(new Dimension(500, 500));
            return mainPanel;
        } catch (AFSwinxBuildException e) {
            getDialogs().failed("afswinx.build.title.failed", "afswinx.build.text.failed", e.getMessage());
        }
        return mainPanel;
    }
    
    public void addSwinxLoginButtonListner(ActionListener a){
        if(afSwinxLoginButton != null){
            this.afSwinxLoginButton.addActionListener(a);
        }
    }

}
