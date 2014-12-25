package com.tomscz.af.showcase.view.forms;

import java.awt.Color;
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

    public void intialize() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(createHeader());
        JPanel contentPanel = new JPanel();
        Box b1 = Box.createHorizontalBox();
        b1.add(createLeftMenu());
        b1.add(Box.createHorizontalGlue());
        JPanel content = createContent();
        content.setPreferredSize(new Dimension(500, 500));
        JScrollPane panel =
                new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        panel.setViewportView(content);
        b1.add(panel);
        b1.setBackground(Color.RED);
        contentPanel.add(b1);
        contentPanel.setBackground(Color.GREEN);
        mainPanel.add(contentPanel);
        content.setPreferredSize(new Dimension(500, 500));
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

    protected abstract JPanel createContent();

    private JButton loginButton;
    private JButton avaiableCountryButton;

    private Dialogs dialogs = new Dialogs(this);
    private static final long serialVersionUID = 1L;

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
        localiztionToolbar.setBackground(Color.BLUE);
        localiztionToolbar.setAlignmentX(LEFT_ALIGNMENT);
        return localiztionToolbar;
    }

    protected JPanel createLeftMenu() {
        JPanel menu = new JPanel();
        loginButton = new JButton(Localization.getLocalizationText("link.login"));
        loginButton.setPreferredSize(new Dimension(150, 30));
        avaiableCountryButton =
                new JButton(Localization.getLocalizationText("link.supportedCountries"));
        avaiableCountryButton.setPreferredSize(new Dimension(150, 30));
        menu.add(loginButton);
        menu.setBackground(Color.WHITE);
        menu.setPreferredSize(new Dimension(200, 500));
        if (ApplicationContext.getInstance().getSecurityContext() != null
                && ApplicationContext.getInstance().getSecurityContext().isUserLogged()) {
            menu.add(avaiableCountryButton);
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
