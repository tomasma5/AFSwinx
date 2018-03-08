package com.tomscz.af.showcase.view;

import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.utils.Localization;
import com.tomscz.af.showcase.view.skin.MySkin;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;

public class AbsenceTypManagementView extends BaseView {

    private static final long serialVersionUID = 1L;

    public static final String ABSENCE_TYPE_TABLE = "absenceTypeTable";
    public static final String ABSENCY_TYPE_FORM = "absenceTypeForm";
    public static final String COUNTRY_FORM = "absenceCountryForm";

    private JButton chooseButton;
    private JButton chooseCountryButton;
    private JButton performButton;
    private JButton formResetButton;
    private boolean displayAdditionalsField = false;
    private int selectedCountry = 0;

    public AbsenceTypManagementView(AFProxyScreenDefinition screenDefinition) {
        super(screenDefinition);
        intialize();
    }

    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        Box b1 = Box.createVerticalBox();
        try {
            Box horizontalTopBox = Box.createHorizontalBox();
            horizontalTopBox.setAlignmentX(CENTER_ALIGNMENT);
            AFSwinxForm chooseCountry = getScreenDefinition().getFormBuilderByKey(COUNTRY_FORM)
                    .setLocalization(ApplicationContext.getInstance().getLocalization())
                    .setSkin(new MySkin()).buildComponent();
            chooseCountryButton = new JButton(Localization.getLocalizationText("button.choose"));
            horizontalTopBox.add(chooseCountry);
            horizontalTopBox.add(Box.createHorizontalStrut(10));
            horizontalTopBox.add(chooseCountryButton);
            horizontalTopBox.add(Box.createHorizontalStrut(130));
            b1.add(horizontalTopBox);
        } catch (AFSwinxBuildException e) {
            getDialogs().failed("afswinx.build.title.failed", "afswinx.build.text.failed",
                    e.getMessage());
        }
        if (isDisplayAdditionalsField()) {
            try {
                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("id", String.valueOf(selectedCountry));
                AFSwinxTable table = getScreenDefinition()
                        .getTableBuilderByKey(ABSENCE_TYPE_TABLE)
                        .setConnectionParameters(parameters)
                        .setLocalization(ApplicationContext.getInstance().getLocalization())
                        .buildComponent();
                Box centerPanel = Box.createVerticalBox();
                centerPanel.setAlignmentX(CENTER_ALIGNMENT);
                HashMap<String, String> securityConstrains =
                        ApplicationContext.getInstance().getSecurityContext()
                                .getUserNameAndPasswodr();
                securityConstrains.put("id", String.valueOf(selectedCountry));
                AFSwinxForm form =
                        getScreenDefinition().getFormBuilderByKey(ABSENCY_TYPE_FORM)
                                .setConnectionParameters(securityConstrains)
                                .setLocalization(ApplicationContext.getInstance().getLocalization())
                                .setSkin(new MySkin()).buildComponent();
                centerPanel.add(form);
                performButton =
                        new JButton(
                                Localization.getLocalizationText("avaiableCountryView.buttton.add"));
                performButton.setAlignmentX(CENTER_ALIGNMENT);
                formResetButton =
                        new JButton(
                                Localization.getLocalizationText("button.reset"));
                formResetButton.setAlignmentX(CENTER_ALIGNMENT);
                Box buttonBox = Box.createHorizontalBox();
                buttonBox.add(performButton);
                buttonBox.add(Box.createHorizontalStrut(60));
                buttonBox.add(formResetButton);
                centerPanel.add(Box.createVerticalStrut(20));
                centerPanel.add(buttonBox);
                chooseButton =
                        new JButton(
                                Localization
                                        .getLocalizationText("avaiableCountryView.buttton.choose"));
                chooseButton.setAlignmentX(RIGHT_ALIGNMENT);
                Box centerBox = Box.createHorizontalBox();
                centerBox.add(Box.createHorizontalStrut(100));
                centerBox.add(centerPanel);
                centerBox.add(Box.createHorizontalStrut(100));
                b1.add(table);
                b1.add(chooseButton);
                b1.add(Box.createVerticalStrut(40));
                b1.add(centerBox);
                b1.add(Box.createVerticalStrut(40));
            } catch (AFSwinxBuildException e) {
                getDialogs().failed("afswinx.build.title.failed", "afswinx.build.text.failed",
                        e.getMessage());
                b1.add(Box.createVerticalStrut(400));
            }
        } else {
            b1.add(Box.createVerticalStrut(400));
        }
        mainPanel.add(b1);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        return mainPanel;
    }

    public void addChooseButtonActionListener(ActionListener a) {
        if (chooseButton != null) {
            chooseButton.addActionListener(a);
        }
    }

    public void addResetFormActionListener(ActionListener a) {
        if (formResetButton != null) {
            formResetButton.addActionListener(a);
        }
    }

    public void addPerformButtonActionListener(ActionListener a) {
        if (performButton != null) {
            performButton.addActionListener(a);
        }
    }

    public void addChooseCountryButtonActionListener(ActionListener a) {
        if (chooseCountryButton != null) {
            chooseCountryButton.addActionListener(a);
        }
    }

    public boolean isDisplayAdditionalsField() {
        return displayAdditionalsField;
    }

    public void setDisplayAdditionalsField(boolean displayAdditionalsField) {
        this.displayAdditionalsField = displayAdditionalsField;
    }

    public int getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(int selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

}
