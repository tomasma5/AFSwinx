package com.tomscz.af.showcase.view.forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.view.dialogs.Dialogs;

public abstract class BaseScreen extends JFrame {

    protected abstract void intialize();
    private JButton loginButton;
    private JButton avaiableCountryPublicButton;

    private Dialogs dialogs = new Dialogs(this);
    private static final long serialVersionUID = 1L;

    protected JPanel createHeader(){
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
    
    protected JPanel createLeftMenu(){
        JPanel menu = new JPanel();
        loginButton = new JButton(Localization.getLocalizationText("link.login"));
        menu.add(loginButton);
        avaiableCountryPublicButton = new JButton(Localization.getLocalizationText("link.supportedCountries"));
        menu.setBackground(Color.WHITE);
        menu.setPreferredSize(new Dimension(200, 500));
        return menu;
    }
    
    public void addLoginButtonListener(ActionListener a){
        loginButton.addActionListener(a);
    }
    
    public void addAvaiableCountryListener(ActionListener a){
        avaiableCountryPublicButton.addActionListener(a);
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
