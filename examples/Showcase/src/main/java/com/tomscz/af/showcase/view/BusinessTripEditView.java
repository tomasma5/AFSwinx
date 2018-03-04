package com.tomscz.af.showcase.view;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.utils.Localization;
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

public class BusinessTripEditView extends BaseView {

    private static final long serialVersionUID = 1L;
    public static final String BUSINESS_TRIP_EDIT_TABLE = "businessTripEditTable";
    public static final String BUSINESS_TRIP_EDIT_FORM = "businessTripEditForm";

    private JButton chooseButton;
    private JButton detailButton;
    private JButton performButton;

    public BusinessTripEditView(AFProxyScreenDefinition screenDefinition) {
        super(screenDefinition);
        intialize();
    }

    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        Box b1 = Box.createVerticalBox();
        InputStream connectionResource;
        try {
            connectionResource = ApplicationContext.getInstance().getConnectionFile();
            HashMap<String, String> parameters =
                    ApplicationContext.getInstance().getSecurityContext().getUserNameAndPasswodr();
            AFSwinxTable table =
                    AFSwinx.getInstance()
                            .getTableBuilder()
                            .initBuilder(BUSINESS_TRIP_EDIT_TABLE, null, parameters)
                            .setLocalization(ApplicationContext.getInstance().getLocalization())
                            .setSkin(new MySkin())
                            .buildComponent();
            Box centerPanel = Box.createVerticalBox();
            centerPanel.setAlignmentX(CENTER_ALIGNMENT);
            connectionResource = ApplicationContext.getInstance().getConnectionFile();
            AFSwinxForm form =
                    AFSwinx.getInstance()
                            .getFormBuilder()
                            .initBuilder(BUSINESS_TRIP_EDIT_FORM, null, parameters)
                            .setLocalization(ApplicationContext.getInstance().getLocalization())
                            .setSkin(new MySkin())
                            .buildComponent();
            centerPanel.add(form);
            performButton = new JButton(Localization.getLocalizationText("businessTrip.buttton.add"));
            performButton.setAlignmentX(CENTER_ALIGNMENT);
            Box buttonBox = Box.createHorizontalBox();
            buttonBox.add(performButton);
            centerPanel.add(Box.createVerticalStrut(20));
            centerPanel.add(buttonBox);
            Box buttonBox2 = Box.createHorizontalBox();
            chooseButton = new JButton(Localization.getLocalizationText("businessTrip.buttton.choose"));
            chooseButton.setAlignmentX(LEFT_ALIGNMENT);
            detailButton = new JButton(Localization.getLocalizationText("businessTrip.button.detail"));
            detailButton.setAlignmentX(RIGHT_ALIGNMENT);
            buttonBox2.add(chooseButton);
            buttonBox2.add(detailButton);

            Box centerBox = Box.createHorizontalBox();
            centerBox.add(centerPanel);
            centerBox.add(Box.createHorizontalStrut(100));

            b1.add(table);
            b1.add(buttonBox2);
            b1.add(Box.createVerticalStrut(40));
            b1.add(centerBox);
            b1.add(Box.createVerticalStrut(40));
        } catch (AFSwinxBuildException e) {
            getDialogs().failed("afswinx.build.title.failed", "afswinx.build.text.failed",
                    e.getMessage());
            b1.add(Box.createVerticalStrut(400));
        }
        mainPanel.add(b1);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        return mainPanel;
    }

    public void addPerformButtonActionListener(ActionListener a) {
        if (performButton != null) {
            performButton.addActionListener(a);
        }
    }

    public void addChooseButtonActionListener(ActionListener a) {
        if (chooseButton != null) {
            chooseButton.addActionListener(a);
        }
    }

    public void addDetailButtonActionListener(ActionListener a) {
        if (detailButton != null) {
            detailButton.addActionListener(a);
        }
    }

}
