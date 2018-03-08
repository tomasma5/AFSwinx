package com.tomscz.af.showcase.view;

import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.SecurityContext;
import com.tomscz.af.showcase.utils.Localization;
import com.tomscz.af.showcase.view.controller.AvaiableCountryController;
import com.tomscz.af.showcase.view.skin.MySkin;
import com.tomscz.afrest.commons.SupportedComponents;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.component.uiproxy.AFProxyComponentDefinition;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;

/**
 * This is view which display country table and country form. Logic is that if you choose some
 * country from country table then you can edit it in form. If no then you can create the new one.
 * See {@link AvaiableCountryController} for more information about logic.
 *
 * @author Martin Tomasek (martin@toms-cz.com)
 * @since 1.0.0.
 */
public class AvaiableCountryView extends BaseView {

    private static final long serialVersionUID = 1L;

    public static final String COUNTRY_TABLE = "countryTable";
    public static final String COUNTRY_FORM = "countryForm";
    private JButton addCountryButton;
    private JButton chooseCountry;
    private JButton resetForm;

    public AvaiableCountryView(AFProxyScreenDefinition screenDefinition) {
        super(screenDefinition);
        intialize();
    }

    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        Box b1 = Box.createVerticalBox();
        try {
            AFSwinxTable table = getScreenDefinition()
                    .getTableBuilderByKey(COUNTRY_TABLE)
                    .setLocalization(ApplicationContext.getInstance().getLocalization())
                    .buildComponent();
            Box centerPanel = Box.createVerticalBox();
            centerPanel.setAlignmentX(CENTER_ALIGNMENT);
            // Because of security policy, get logged user

            SecurityContext securityContext = ApplicationContext.getInstance().getSecurityContext();

            AFSwinxForm form = getScreenDefinition()
                    .getFormBuilderByKey(COUNTRY_FORM)
                    .setLocalization(ApplicationContext.getInstance().getLocalization())
                    .setConnectionParameters(securityContext != null ? securityContext.getUserNameAndPasswodr() : null)
                    .setSkin(new MySkin())
                    .buildComponent();
            centerPanel.add(form);
            //Add buttons
            addCountryButton =
                    new JButton(Localization.getLocalizationText("avaiableCountryView.buttton.add"));
            addCountryButton.setAlignmentX(CENTER_ALIGNMENT);
            resetForm = new JButton(Localization.getLocalizationText("button.reset"));
            resetForm.setAlignmentX(CENTER_ALIGNMENT);
            Box buttonBox = Box.createHorizontalBox();
            buttonBox.add(addCountryButton);
            buttonBox.add(Box.createHorizontalStrut(60));
            buttonBox.add(resetForm);
            centerPanel.add(Box.createVerticalStrut(20));
            centerPanel.add(buttonBox);
            chooseCountry =
                    new JButton(
                            Localization.getLocalizationText("avaiableCountryView.buttton.choose"));
            chooseCountry.setAlignmentX(RIGHT_ALIGNMENT);
            Box centerBox = Box.createHorizontalBox();
            centerBox.add(Box.createHorizontalStrut(100));
            centerBox.add(centerPanel);
            centerBox.add(Box.createHorizontalStrut(100));
            b1.add(table);
            b1.add(chooseCountry);
            b1.add(Box.createVerticalStrut(40));
            b1.add(centerBox);
            b1.add(Box.createVerticalStrut(40));
            mainPanel.add(b1);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            return mainPanel;
        } catch (AFSwinxBuildException e) {
            getDialogs().failed("afswinx.build.title.failed", "afswinx.build.text.failed",
                    e.getMessage());
        }
        return mainPanel;
    }

    // Section which provide add action listeners to buttons.
    public void addAddCountryListener(ActionListener a) {
        if (addCountryButton != null) {
            this.addCountryButton.addActionListener(a);
        }
    }

    public void addChooseCountryListener(ActionListener a) {
        if (chooseCountry != null) {
            this.chooseCountry.addActionListener(a);
        }
    }

    public void addResetForm(ActionListener a) {
        if (resetForm != null) {
            this.resetForm.addActionListener(a);
        }
    }

}
