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
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;

public class PersonView extends BaseScreen {

    private static final long serialVersionUID = 1L;
    public static final String PERSON_FORM = "personForm";
    public static final String PERSON_CONNECTION_KEY = "personProfile";

    private JButton updateButton;

    public PersonView() {
        intialize();
    }
    
    public void addUpdateButtonListener(ActionListener a) {
        if (updateButton != null) {
            this.updateButton.addActionListener(a);
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
            connectionResource = getClass().getClassLoader().getResourceAsStream("connection.xml");
            AFSwinxForm form =
                    AFSwinx.getInstance()
                            .getFormBuilder()
                            .initBuilder(PERSON_FORM, connectionResource,
                                    PERSON_CONNECTION_KEY, securityConstrains)
                            .setLocalization(ApplicationContext.getInstance().getLocalization())
                            .setSkin(new LongInputSkin()).buildComponent();
            centerPanel.add(form);
            Box buttonBox = Box.createHorizontalBox();
            buttonBox.add(Box.createHorizontalStrut(60));
            updateButton =
                    new JButton(
                            Localization.getLocalizationText("personView.buttton.update"));
            updateButton.setAlignmentX(RIGHT_ALIGNMENT);
            buttonBox.add(updateButton);
            centerPanel.add(buttonBox);
            //Content
            Box centerBox = Box.createHorizontalBox();
            centerBox.add(centerPanel);
            centerBox.add(Box.createHorizontalStrut(10));
            //Vertical box
            b1.add(Box.createVerticalStrut(20));
            b1.add(centerBox);
            b1.add(Box.createVerticalStrut(20));
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
