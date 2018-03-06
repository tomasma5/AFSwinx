package com.tomscz.af.showcase.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.SecurityContext;
import com.tomscz.af.showcase.utils.Localization;
import com.tomscz.af.showcase.view.dialogs.Dialogs;
import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxMenu;
import com.tomscz.afswinx.component.AFSwinxMenuButton;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.uiproxy.AFProxyComponentDefinition;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;

/**
 * This class is base view which extends all view. This view hold left menu, localization bar and
 * has abstract method to fill content. In this method place your content.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public abstract class BaseView extends JPanel {

    private JButton czech;
    private JButton english;
    private AFSwinxMenu swinxMenu;
    private AFProxyScreenDefinition screenDefinition;
    private MainFrame mainFrame = MainFrame.getInstance();

    private static final long serialVersionUID = 1L;

    public BaseView(AFProxyScreenDefinition screenDefinition) {
        this.screenDefinition = screenDefinition;
    }

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
        JPanel content;
        if(getScreenDefinition() != null){
            getScreenDefinition().reload();
        }
        content = createContent();

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
        menu.setPreferredSize(new Dimension(250, 500));
        try {
            swinxMenu = AFSwinx.getInstance().getMenuBuilder().setUrl("http://localhost:8081/UIxy/api/screens/").buildComponent();
            if(swinxMenu.getMenuButtons() != null) {
                if(ApplicationContext.getInstance().getSecurityContext() != null &&
                        ApplicationContext.getInstance().getSecurityContext().isUserLogged()) {
                    for (AFSwinxMenuButton button : swinxMenu.getMenuButtons().values()) {
                        if(button.getTitle().equals("Login")){
                            continue;
                        }
                        button.setPreferredSize(buttonSize);
                        menu.add(button);
                    }
                } else {
                    AFSwinxMenuButton loginButton = swinxMenu.getMenuButtons().get("Login");
                    loginButton.setPreferredSize(buttonSize);
                    menu.add(loginButton);
                }
            }
        } catch (AFSwinxBuildException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return menu;
    }

    public void addOnScreenPreparedListener(AFSwinxMenuButton button, ScreenPreparedListener spl) {
        button.setScreenPreparedListener(spl);
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

    public AFSwinxMenu getSwinxMenu() {
        return swinxMenu;
    }

    public AFProxyScreenDefinition getScreenDefinition() {
        return screenDefinition;
    }
}
