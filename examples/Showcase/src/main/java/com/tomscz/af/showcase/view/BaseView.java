package com.tomscz.af.showcase.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.utils.Localization;
import com.tomscz.af.showcase.view.dialogs.Dialogs;

/**
 * This class is base view which extends all view. This view hold left menu, localization bar and
 * has abstract method to fill content. In this method place your content.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class BaseView extends JPanel {

    private JButton loginButton;
    private JButton avaiableCountryButton;
    private JButton myProfileButton;
    private JButton myAbsencesButton;
    private JButton editAbsenceButton;
    private JButton addAbsenceButton;
    private JButton addAbsenceTypeButton;
    private JButton logoutButton;
    private JButton businessTripsButton;
    private JButton vehiclesButton;
    private JButton czech;
    private JButton english;
    private MainFrame mainFrame = MainFrame.getInstance();

    private static final long serialVersionUID = 1L;

    protected abstract JPanel createContent();

    /**
     * This method will build whole new screen.
     */
    public void intialize() {
        mainFrame.getContentPane().removeAll();
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
        content.setPreferredSize(new Dimension(640, 600));
        JScrollPane panel2 =
                new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainFrame.add(panel2);
        mainFrame.setVisible(true);
        mainFrame.getContentPane().repaint();
    }

    protected JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.add(createLocalizationToolbar());
        return headerPanel;
    }

    /**
     * This method will create localization bar.
     * @return localization bar which could be added to frame.
     */
    protected JPanel createLocalizationToolbar() {
        JPanel localiztionToolbar = new JPanel();
        czech = new JButton(new ImageIcon(this.getClass().getResource("/images/czech.png")));
        czech.setBorder(BorderFactory.createEmptyBorder());
        czech.setContentAreaFilled(false);
        english = new JButton(new ImageIcon(this.getClass().getResource("/images/english.png")));
        english.setBorder(BorderFactory.createEmptyBorder());
        english.setContentAreaFilled(false);
        localiztionToolbar.add(czech);
        localiztionToolbar.add(english);
        localiztionToolbar.setAlignmentX(LEFT_ALIGNMENT);
        return localiztionToolbar;
    }

    /**
     * This method will create left menu.
     * @return left menu panel which could be added to frame.
     */
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
        businessTripsButton = new JButton(Localization.getLocalizationText("link.businessTrips"));
        businessTripsButton.setPreferredSize(buttonSize);
        vehiclesButton = new JButton(Localization.getLocalizationText("link.vehicles"));
        vehiclesButton.setPreferredSize(buttonSize);
        logoutButton = new JButton(Localization.getLocalizationText("logout.button"));
        logoutButton.setPreferredSize(buttonSize);
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
            menu.add(businessTripsButton);
            menu.add(vehiclesButton);
            menu.add(logoutButton);
        } else {
            loginButton = new JButton(Localization.getLocalizationText("link.login"));
            loginButton.setPreferredSize(buttonSize);
            menu.add(loginButton);
        }
        return menu;
    }

    //These methods add action to current button in menu.
    
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

    public void addAbsenceTypeListener(ActionListener a) {
        if (addAbsenceTypeButton != null) {
            addAbsenceTypeButton.addActionListener(a);
        }
    }

    public void addAbsenceAddListener(ActionListener a) {
        if (addAbsenceButton != null) {
            addAbsenceButton.addActionListener(a);
        }
    }

    public void addMyAbsencesListener(ActionListener a) {
        if (myAbsencesButton != null) {
            myAbsencesButton.addActionListener(a);
        }
    }

    public void addAbsencesInstanceEditListener(ActionListener a) {
        if (editAbsenceButton != null) {
            editAbsenceButton.addActionListener(a);
        }
    }

    public void addBusinessTripsListener(ActionListener a){
        if(businessTripsButton != null){
            businessTripsButton.addActionListener(a);
        }
    }

    public void addVehiclesButtonListener(ActionListener a) {
        if(vehiclesButton != null){
            vehiclesButton.addActionListener(a);
        }
    }

    public void addLogoutButtonMenuListener(ActionListener a) {
        if (logoutButton != null) {
            logoutButton.addActionListener(a);
        }
    }

    public void addEnglishButtonListener(ActionListener a) {
        if (english != null) {
            english.addActionListener(a);
        }
    }

    public void addCzechButtonListener(ActionListener a) {
        if (czech != null) {
            czech.addActionListener(a);
        }
    }

    public Dialogs getDialogs() {
        return getMainFrame().getDialogs();
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

}
