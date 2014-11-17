package com.tomscz.af.showcase.view.forms;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.utils.FileUtils;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.form.AFSwinxForm;

public class WelcomeScreen extends BaseScreen {

    private static final long serialVersionUID = 1L;

    private JButton afSwinxLoginButton;

    private final static String loginFormName = "loginForm";


    public WelcomeScreen() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
        //Generate content
        JPanel contentPanel = new JPanel();
        JButton companyCenters = new JButton(Localization.getLocalizationText("login.company.center.show.button"));
        companyCenters.addActionListener(onPrivateButtonExec);
        JPanel publicPanel = new JPanel();
        publicPanel.add(companyCenters);
        publicPanel.setBorder(BorderFactory.createTitledBorder(Localization.getLocalizationText("login.content.public")));
        JPanel privatePanel = createAFSwinxLoginForm();
        privatePanel.setBorder(BorderFactory.createTitledBorder(Localization.getLocalizationText("login.content.private")));
        contentPanel.setLayout(new GridLayout(0,2));
        contentPanel.add(publicPanel);
        contentPanel.add(privatePanel);
        //add semi-generated parts
        mainPanel.add(createHeader());
        mainPanel.add(contentPanel);
        mainPanel.add(createFooter());
        this.add(mainPanel);
        this.setTitle("Showcase for "
                + Localization.getLocalizationText("login.header.mainText",
                        ShowcaseConstants.APPLICATION_VERSION));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
    }

    private JPanel createHeader() {
        JPanel mainPanel = new JPanel();
        JLabel label = new JLabel();
        mainPanel.setLayout(new GridBagLayout());
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("<html>");
        headerBuilder.append(Localization.getLocalizationText("login.header.mainText",
                ShowcaseConstants.APPLICATION_VERSION));
        headerBuilder.append("<br />");
        headerBuilder.append(Localization.getLocalizationText("login.header.information"));
        headerBuilder.append("</html>");
        label.setText(headerBuilder.toString());
        JPanel translationPanel = createLocalizationToolbar();
        mainPanel.add(label);
        mainPanel.add(translationPanel);
        return mainPanel;
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
            mainPanel.add(form);
            mainPanel.add(Box.createVerticalStrut(20));
            afSwinxLoginButton = new JButton(Localization.getLocalizationText("login.button"));
            afSwinxLoginButton.addActionListener(onLoginButtonExec);
            afSwinxLoginButton.setPreferredSize(new Dimension(60, 20));
            afSwinxLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(afSwinxLoginButton);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            return mainPanel;
        } catch (AFSwinxBuildException e) {
            dialogs.failed("afswinx.build.title.failed", "afswinx.build.text.failed", e.getMessage());
        }
        return mainPanel;
    }

    private JPanel createFooter() {
        JPanel mainPanel = new JPanel();
        JLabel label = new JLabel(Localization.getLocalizationText("source.view"));
        mainPanel.add(label);
        JTextArea textArea = new JTextArea(10, 70);
        textArea.setText(new FileUtils().readClass("login.txt"));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 60, 780, 500);
        mainPanel.add(scrollPane);
        return mainPanel;
    }

    private ActionListener onLoginButtonExec = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object body =
                    AFSwinx.getInstance().getExistedComponent(loginFormName).generatePostData();
            if (body != null) {
                // SEND
            }
        }
    };
    
    private ActionListener onPrivateButtonExec=new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            
        }
    };

}
