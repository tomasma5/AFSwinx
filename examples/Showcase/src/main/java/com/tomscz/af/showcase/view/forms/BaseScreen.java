package com.tomscz.af.showcase.view.forms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.view.dialogs.Dialogs;

public abstract class BaseScreen extends JFrame {

    private JButton loginButton;
    private JButton avaiableCountryButton;
    private JButton myProfileButton;
    private JButton myAbsencesButton;
    private JButton editAbsenceButton;
    private JButton addAbsenceButton;
    private JButton addAbsenceTypeButton;

    private Dialogs dialogs = new Dialogs(this);
    private static final long serialVersionUID = 1L;

    protected abstract JPanel createContent();

    public void intialize() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(createHeader());
        JPanel contentPanel = new JPanel();
        Box b1 = Box.createHorizontalBox();
        b1.add(createLeftMenu());
        b1.add(Box.createHorizontalGlue());
        JPanel content = createContent();
        b1.add(content);
        contentPanel.add(b1);
        mainPanel.add(contentPanel);
        content.setPreferredSize(new Dimension(540, 500));
        JScrollPane panel2 =
                new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(panel2);
        this.setTitle("Showcase for "
                + Localization.getLocalizationText("login.header.mainText",
                        ShowcaseConstants.APPLICATION_VERSION));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(840, 640);
        this.setVisible(true);
    }

    protected JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.add(createLocalizationToolbar());
        return headerPanel;
    }

    protected JPanel createLocalizationToolbar() {
        JPanel localiztionToolbar = new JPanel();
        JButton czech =
                new JButton(new ImageIcon(this.getClass().getResource("/images/czech.png")));
        czech.setBorder(BorderFactory.createEmptyBorder());
        czech.setContentAreaFilled(false);
        czech.addActionListener(onCzechButtonExec);
        JButton english =
                new JButton(new ImageIcon(this.getClass().getResource("/images/english.png")));
        english.setBorder(BorderFactory.createEmptyBorder());
        english.setContentAreaFilled(false);
        english.addActionListener(onEnglishButtonExec);
        localiztionToolbar.add(czech);
        localiztionToolbar.add(english);
        localiztionToolbar.setAlignmentX(LEFT_ALIGNMENT);
        return localiztionToolbar;
    }

    protected JPanel createLeftMenu() {
        JPanel menu = new JPanel();
        Dimension buttonSize = new Dimension(200, 30);
        avaiableCountryButton =
                new JButton(Localization.getLocalizationText("link.supportedCountries"));
        avaiableCountryButton.setPreferredSize(new Dimension(150, 30));
        avaiableCountryButton.setPreferredSize(buttonSize);
        myProfileButton = new JButton(Localization.getLocalizationText("link.myProfile"));
        myProfileButton.setPreferredSize(buttonSize);
        myAbsencesButton = new JButton(Localization.getLocalizationText("link.myAbsence"));
        myAbsencesButton.setPreferredSize(buttonSize);
        editAbsenceButton = new JButton(Localization.getLocalizationText("link.editMyAbsences"));
        editAbsenceButton.setPreferredSize(buttonSize);
        addAbsenceButton = new JButton(Localization.getLocalizationText("link.createAbsence"));
        addAbsenceButton.setPreferredSize(buttonSize);
        addAbsenceTypeButton = new JButton(Localization.getLocalizationText("link.manageAbsenceType"));
        addAbsenceTypeButton.setPreferredSize(buttonSize);
        menu.setPreferredSize(new Dimension(250, 500));
        if (ApplicationContext.getInstance().getSecurityContext() != null
                && ApplicationContext.getInstance().getSecurityContext().isUserLogged()) {
            loginButton = new JButton(Localization.getLocalizationText("link.home"));
            loginButton.setPreferredSize(buttonSize);
            menu.add(loginButton);
            menu.add(avaiableCountryButton);
            menu.add(myProfileButton);
            menu.add(myAbsencesButton);
            menu.add(editAbsenceButton);
            menu.add(addAbsenceButton);
            menu.add(addAbsenceTypeButton);
        }
        else{
            loginButton = new JButton(Localization.getLocalizationText("link.login"));
            loginButton.setPreferredSize(buttonSize);
            menu.add(loginButton); 
        }
        return menu;
    }

    public void addLoginButtonListener(ActionListener a) {
        loginButton.addActionListener(a);
    }

    public void addAvaiableCountryListener(ActionListener a) {
        if (avaiableCountryButton != null) {
            avaiableCountryButton.addActionListener(a);
        }
    }
    
    public void addMyProfileListener(ActionListener a) {
        if (myProfileButton != null) {
            myProfileButton.addActionListener(a);
        }
    }

    public Dialogs getDialogs() {
        return dialogs;
    }

    private ActionListener onEnglishButtonExec = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ApplicationContext.getInstance().changeLocalization(
                        ShowcaseConstants.ENGLISH_BUNDLE);
                getContentPane().removeAll();
                intialize();
                getContentPane().repaint();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
    };



    private ActionListener onCzechButtonExec = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ApplicationContext.getInstance().changeLocalization(ShowcaseConstants.CZECH_BUNDLE);
                getContentPane().removeAll();
                intialize();
                getContentPane().repaint();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    };

}
