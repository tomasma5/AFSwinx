package com.tomscz.af.showcase.view;

import javax.swing.JFrame;

import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.view.dialogs.Dialogs;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private Dialogs dialogs = new Dialogs(this);
    
    private static MainFrame instance;
    
    public static synchronized MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame();
        }
        return instance;
    }
    
    
    private MainFrame(){
        this.setTitle("Showcase for "
                + Localization.getLocalizationText("login.header.mainText",
                        ShowcaseConstants.APPLICATION_VERSION));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(940, 640);
        this.setVisible(true);
    }
    
    public Dialogs getDialogs() {
        return dialogs;
    }
    
    public void setDialogs(Dialogs dialogs) {
        this.dialogs = dialogs;
    }
}
