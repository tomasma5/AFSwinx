package com.tomscz.af.showcase.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.view.skin.LongInputSkin;
import com.tomscz.af.showcase.view.skin.MySkin;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;

public class AbsenceInstanceCreateView extends BaseScreen {

    private static final long serialVersionUID = 1L;
    public static final String ABSENCE_INSTANCE_ADD_FORM = "absenceInstanceAddForm";
    public static final String ABSENCE_INSTANCE_ADD_FORM_CONNECTION =
            "absenceInstanceAddConnection";

    private JButton createButton;
    
    public AbsenceInstanceCreateView    () {
        intialize();
    }


    public void addCreateButtonActionListener(ActionListener e) {
        if (createButton != null) {
            createButton.addActionListener(e);
        }
    }

    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        Box b1 = Box.createVerticalBox();
        InputStream connectionResource =
                getClass().getClassLoader().getResourceAsStream("connection.xml");
        try {
            Box centerPanel = Box.createVerticalBox();
            centerPanel.setAlignmentX(LEFT_ALIGNMENT);
            HashMap<String, String> securityConstrains =
                    ApplicationContext.getInstance().getSecurityContext().getUserNameAndPasswodr();
            securityConstrains.put("user", ApplicationContext.getInstance().getSecurityContext()
                    .getUserLogin());
            connectionResource = getClass().getClassLoader().getResourceAsStream("connection.xml");
            AFSwinxForm form =
                    AFSwinx.getInstance()
                            .getFormBuilder()
                            .initBuilder(ABSENCE_INSTANCE_ADD_FORM, connectionResource,
                                    ABSENCE_INSTANCE_ADD_FORM_CONNECTION, securityConstrains)
                            .setLocalization(ApplicationContext.getInstance().getLocalization())
                            .setSkin(new MySkin()).buildComponent();
            centerPanel.add(form);
            Box buttonBox = Box.createHorizontalBox();
            buttonBox.add(Box.createHorizontalStrut(60));
            createButton =
                    new JButton(Localization.getLocalizationText("absenceInstance.action.add"));
            createButton.setAlignmentX(RIGHT_ALIGNMENT);
            buttonBox.add(createButton);
            centerPanel.add(buttonBox);
            // Content
            Box centerBox = Box.createHorizontalBox();
            centerBox.add(Box.createHorizontalStrut(200));
            centerBox.add(centerPanel);
            centerBox.add(Box.createHorizontalStrut(180));
            // Vertical box
            b1.add(Box.createVerticalStrut(10));
            b1.add(centerBox);
            b1.add(Box.createVerticalStrut(240));
            JScrollPane panel =
                    new JScrollPane(b1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            panel.setBackground(Color.RED);
            mainPanel.add(panel);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            return mainPanel;
        } catch (AFSwinxBuildException e) {
            getDialogs().failed("afswinx.build.title.failed", "afswinx.build.text.failed",
                    e.getMessage());
        }
        return mainPanel;
    }

}
