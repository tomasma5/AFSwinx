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
import com.tomscz.af.showcase.view.skin.AbsenceInstanceEditSkin;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;

public class AbsenceInstanceEditView extends BaseView {

    private static final long serialVersionUID = 1L;
    public static final String ABSENCE_INSTANCE_EDIT_TABLE = "absenceInstanceEditTable";
    public static final String ABSENCE_INSTANCE_EDIT_FORM = "absenceInstanceEditForm";

    private JButton chooseButton;
    private JButton performButton;

    public AbsenceInstanceEditView(AFProxyScreenDefinition screenDefinition) {
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
                    .getTableBuilderByKey(ABSENCE_INSTANCE_EDIT_TABLE)
                    .setConnectionParameters(parameters)
                    .setLocalization(ApplicationContext.getInstance().getLocalization())
                    .buildComponent();
            Box centerPanel = Box.createVerticalBox();
            centerPanel.setAlignmentX(CENTER_ALIGNMENT);
            AFSwinxForm form = getScreenDefinition()
                    .getFormBuilderByKey(ABSENCE_INSTANCE_EDIT_FORM)
                    .setConnectionParameters(parameters)
                    .setLocalization(ApplicationContext.getInstance().getLocalization())
                    .setSkin(new AbsenceInstanceEditSkin()).buildComponent();
            centerPanel.add(form);
            performButton =
                    new JButton(Localization.getLocalizationText("avaiableCountryView.buttton.add"));
            performButton.setAlignmentX(CENTER_ALIGNMENT);
            Box buttonBox = Box.createHorizontalBox();
            buttonBox.add(performButton);
            centerPanel.add(Box.createVerticalStrut(20));
            centerPanel.add(buttonBox);
            chooseButton =
                    new JButton(
                            Localization.getLocalizationText("avaiableCountryView.buttton.choose"));
            chooseButton.setAlignmentX(RIGHT_ALIGNMENT);
            Box centerBox = Box.createHorizontalBox();
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

}
