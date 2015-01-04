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

public class AbsenceInstanceEditView extends BaseView {

    private static final long serialVersionUID = 1L;
    public static final String ABSENCE_INSTANCE_EDIT_TABLE = "absenceInstaceEditTable";
    public static final String ABSENCE_INSTANCE_EDIT_TABLE_CONNECTION =
            "absenceInstaceEditTableConnection";
    public static final String ABSENCE_INSTANCE_EDIT_FORM = "absenceInstaceEditForm";
    public static final String ABSENCE_INSTANCE_EDIT_FORM_CONNECTION =
            "absenceInstaceEditFormConnection";

    private JButton chooseButton;
    private JButton performButton;

    public AbsenceInstanceEditView() {
        intialize();
    }
    

    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        Box b1 = Box.createVerticalBox();
        InputStream connectionResource =
                getClass().getClassLoader().getResourceAsStream("connection.xml");
        try {
            connectionResource = getClass().getClassLoader().getResourceAsStream("connection.xml");
            HashMap<String, String> parameters =
                    ApplicationContext.getInstance().getSecurityContext().getUserNameAndPasswodr();
            AFSwinxTable table =
                    AFSwinx.getInstance()
                            .getTableBuilder()
                            .initBuilder(ABSENCE_INSTANCE_EDIT_TABLE, connectionResource,
                                    ABSENCE_INSTANCE_EDIT_TABLE_CONNECTION, parameters)
                            .setLocalization(ApplicationContext.getInstance().getLocalization())
                            .buildComponent();
            Box centerPanel = Box.createVerticalBox();
            centerPanel.setAlignmentX(CENTER_ALIGNMENT);
            connectionResource = getClass().getClassLoader().getResourceAsStream("connection.xml");
            AFSwinxForm form =
                    AFSwinx.getInstance()
                            .getFormBuilder()
                            .initBuilder(ABSENCE_INSTANCE_EDIT_FORM, connectionResource,
                                    ABSENCE_INSTANCE_EDIT_FORM_CONNECTION, parameters)
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
    
    public void addPerformButtonActionListener(ActionListener a){
        if(performButton != null){
            performButton.addActionListener(a);
        }
    }
    
    public void addChooseButtonActionListener(ActionListener a){
        if(chooseButton != null){
            chooseButton.addActionListener(a);
        }
    }

}
