package com.tomscz.af.showcase.view;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.utils.Localization;
import com.tomscz.af.showcase.view.controller.AvaiableCountryController;
import com.tomscz.af.showcase.view.skin.MySkin;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.HashMap;

/**
 * This is view which display country table and country form. Logic is that if you choose some
 * country from country table then you can edit it in form. If no then you can create the new one.
 * See {@link AvaiableCountryController} for more information about logic.
 *
 * @author Martin Tomasek (martin@toms-cz.com)
 * @since 1.0.0.
 */
public class AvailableVehiclesView extends BaseView {

    private static final long serialVersionUID = 1L;

    public static final String VEHICLES_TABLE = "vehiclesTable";
    public static final String VEHICLES_FORM = "vehiclesForm";

    private JButton addVehicleButton;
    private JButton chooseVehicleButton;
    private JButton resetForm;

    public AvailableVehiclesView(AFProxyScreenDefinition screenDefinition) {
        super(screenDefinition);
        intialize();
    }

    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        Box b1 = Box.createVerticalBox();
        InputStream connectionResource;
        try {
            AFSwinxTable table = AFSwinx.getInstance()
                    .getTableBuilder()
                    .initBuilder(VEHICLES_TABLE, null)
                    .setLocalization(ApplicationContext.getInstance().getLocalization())
                    .buildComponent();
            Box centerPanel = Box.createVerticalBox();
            centerPanel.setAlignmentX(CENTER_ALIGNMENT);
            HashMap<String, String> securityConstrains =
                    ApplicationContext.getInstance().getSecurityContext().getUserNameAndPasswodr();
            connectionResource = ApplicationContext.getInstance().getConnectionFile();
            AFSwinxForm form = AFSwinx.getInstance()
                    .getFormBuilder()
                    .initBuilder(VEHICLES_FORM, null, securityConstrains)
                    .setLocalization(ApplicationContext.getInstance().getLocalization())
                    .setSkin(new MySkin()).buildComponent();
            centerPanel.add(form);
            //Add buttons
            addVehicleButton = new JButton(Localization.getLocalizationText("vehicles.buttton.add"));
            addVehicleButton.setAlignmentX(CENTER_ALIGNMENT);
            resetForm = new JButton(Localization.getLocalizationText("button.reset"));
            resetForm.setAlignmentX(CENTER_ALIGNMENT);
            Box buttonBox = Box.createHorizontalBox();
            buttonBox.add(addVehicleButton);
            buttonBox.add(Box.createHorizontalStrut(60));
            buttonBox.add(resetForm);
            centerPanel.add(Box.createVerticalStrut(20));
            centerPanel.add(buttonBox);
            chooseVehicleButton = new JButton(Localization.getLocalizationText("vehicles.buttton.choose"));
            chooseVehicleButton.setAlignmentX(RIGHT_ALIGNMENT);
            Box centerBox = Box.createHorizontalBox();
            centerBox.add(centerPanel);
            b1.add(table);
            b1.add(chooseVehicleButton);
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
    public void addAddVehicleListener(ActionListener a) {
        if (addVehicleButton != null) {
            this.addVehicleButton.addActionListener(a);
        }
    }

    public void addChooseVehicleListener(ActionListener a) {
        if (chooseVehicleButton != null) {
            this.chooseVehicleButton.addActionListener(a);
        }
    }

    public void addResetForm(ActionListener a) {
        if (resetForm != null) {
            this.resetForm.addActionListener(a);
        }
    }

}
