package com.tomscz.af.showcase.view.forms;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.SecurityContext;
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
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        SecurityContext sc = ApplicationContext.getInstance().getSecurityContext();
        if(sc != null && sc.isUserLogged()){
            mainPanel.add(new JLabel("Welcome user:"+sc.getUserLogin()));
        }
        else{
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
                return mainPanel;
            } catch (AFSwinxBuildException e) {
                getDialogs().failed("afswinx.build.title.failed", "afswinx.build.text.failed", e.getMessage());
            }
        }
        return mainPanel;
    }
    
    public void addSwinxLoginButtonListner(ActionListener a){
        if(afSwinxLoginButton != null){
            this.afSwinxLoginButton.addActionListener(a);
        }
    }
    
}
