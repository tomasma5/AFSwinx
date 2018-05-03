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
import com.tomscz.af.showcase.utils.Localization;
import com.tomscz.af.showcase.view.skin.LongInputSkin;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;

public class PersonView extends BaseView {

    private static final long serialVersionUID = 1L;
    public static final String PERSON_FORM = "personProfileForm";

    private JButton updateButton;

    public PersonView(AFProxyScreenDefinition screenDefinition) {
        super(screenDefinition);
        intialize();
    }

    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        Box b1 = Box.createVerticalBox();
        try {
            Box centerPanel = Box.createVerticalBox();
            centerPanel.setAlignmentX(LEFT_ALIGNMENT);
            HashMap<String, String> securityConstrains =
                    ApplicationContext.getInstance().getSecurityContext().getUserNameAndPasswodr();
            long start = System.currentTimeMillis();
            AFSwinxForm form = getScreenDefinition().getFormBuilderByKey(PERSON_FORM)
                            .setConnectionParameters(securityConstrains)
                            .setLocalization(ApplicationContext.getInstance().getLocalization())
                            .setSkin(new LongInputSkin()).buildComponent();
            centerPanel.add(form);
            System.out.println("Creating took " + (System.currentTimeMillis() - start) + " ms");
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
            centerBox.add(Box.createHorizontalStrut(40));
            centerBox.add(centerPanel);
            centerBox.add(Box.createHorizontalStrut(80));
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
    
    public void addUpdateButtonListener(ActionListener a) {
        if (updateButton != null) {
            this.updateButton.addActionListener(a);
        }
    }

}
