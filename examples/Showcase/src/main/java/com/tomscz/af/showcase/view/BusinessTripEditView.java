package com.tomscz.af.showcase.view;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.utils.Localization;
import com.tomscz.af.showcase.utils.ProxyConstants;
import com.tomscz.af.showcase.view.skin.MySkin;
import com.tomscz.afswinx.component.*;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.HashMap;

public class BusinessTripEditView extends BaseView {

    private static final long serialVersionUID = 1L;
    public static final String BUSINESS_TRIP_EDIT_TABLE = "businessTripTable";
    public static final String BUSINESS_TRIP_EDIT_FORM = "businessTripForm";

    private JButton chooseButton;
    private AFSwinxScreenButton detailButton;
    private JButton performButton;

    public BusinessTripEditView(AFProxyScreenDefinition screenDefinition) {
        super(screenDefinition);
        intialize();
    }

    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        Box b1 = Box.createVerticalBox();
        try {
            HashMap<String, String> parameters =
                    ApplicationContext.getInstance().getSecurityContext().getUserNameAndPasswodr();
            AFSwinxTable table = getScreenDefinition()
                    .getTableBuilderByKey(BUSINESS_TRIP_EDIT_TABLE)
                    .setConnectionParameters(parameters)
                    .setLocalization(ApplicationContext.getInstance().getLocalization())
                    .setSkin(new MySkin())
                    .buildComponent();
            Box centerPanel = Box.createVerticalBox();
            centerPanel.setAlignmentX(CENTER_ALIGNMENT);
            AFSwinxForm form = getScreenDefinition()
                    .getFormBuilderByKey(BUSINESS_TRIP_EDIT_FORM)
                    .setConnectionParameters(parameters)
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
            detailButton = AFSwinx.getInstance().getScreenButtonBuilder().buildComponent(
                    ProxyConstants.BTN_CUSTOM_BUSINESS_TRIP_DETAIL,
                    Localization.getLocalizationText("businessTrip.button.detail"),
                    getScreenDefinition().getScreenUrl()
            );
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

    public void addDetailButtonActionListener(ScreenPreparedListener a) {
        if (detailButton != null) {
            detailButton.setScreenPreparedListener(a);
        }
    }

}
