package com.tomscz.af.showcase.view.dialogs;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.tomscz.af.showcase.view.Localization;

/**
 * This is dialog class which can display custom message to user in new dialog windows
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class Dialogs extends JDialog {

    private static final long serialVersionUID = 1L;
    JFrame owner;
    JDialog dialogOwner;

    public Dialogs(JFrame owner) {
        this.owner = owner;
    }

    public Dialogs(JDialog dialog) {
        super(dialog);
        this.dialogOwner = dialog;
    }

    public void failed(String title, String message, String... parameters) {
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JOptionPane.showMessageDialog(dialogOwner,
                Localization.getLocalizationText(message, parameters),
                Localization.getLocalizationText(message), JOptionPane.ERROR_MESSAGE);
    }

    

    
}
