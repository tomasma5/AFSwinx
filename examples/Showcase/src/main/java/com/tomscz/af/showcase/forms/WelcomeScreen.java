package com.tomscz.af.showcase.forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;

public class WelcomeScreen extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JTextField userName;
    private JPasswordField password;
    
    private JButton afSwinxLoginButton;
    private JButton swinxOrdinaryLoginButton;
    
    private final static String loginFormName = "loginForm";


    public WelcomeScreen() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,1));
        JPanel contentPanel = new JPanel();
        contentPanel.add(createAFSwinxLoginForm());
        contentPanel.add(createOrdinalLoginForm());
        mainPanel.add(createHeader());
        mainPanel.add(contentPanel);
        mainPanel.add(createFooter());
        this.add(mainPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }

    private JPanel createHeader() {
        JPanel mainPanel = new JPanel();
        return mainPanel;
    }

    private JPanel createOrdinalLoginForm() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,1));
        userName = new JTextField();
        password = new JPasswordField();
        swinxOrdinaryLoginButton = new JButton(Localization.getLocalizationText("login.button"));
        mainPanel.add(userName);
        mainPanel.add(password);
        mainPanel.add(swinxOrdinaryLoginButton);
        return mainPanel;
    }

    private JPanel createAFSwinxLoginForm() {
        JPanel mainPanel = new JPanel();
        File connectionFile = new File(getClass().getClassLoader().getResource("connection.xml").getFile());
        try {
            mainPanel = AFSwinx.getInstance().getFormBuilder().initBuilder(loginFormName, connectionFile, "loginForm").setLocalization(ApplicationContext.getInstance().getLocalization()).buildComponent();
            return mainPanel;
        } catch (AFSwinxBuildException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mainPanel;
    }

    private JPanel createFooter() {
        JPanel mainPanel = new JPanel();
        return mainPanel;
    }

}
