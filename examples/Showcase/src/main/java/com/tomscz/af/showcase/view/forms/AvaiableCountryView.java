package com.tomscz.af.showcase.view.forms;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.view.skin.MySkin;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;

public class AvaiableCountryView extends BaseScreen {

    private static final long serialVersionUID = 1L;

    public static final String COUNTRY_TABLE = "countryTable";
    public static final String COUNTRY_FORM = "countryTable";
    public static final String COUNTRY_TABLE_CONNECTION_KEY = "tableCountryPublic";
    public static final String COUNTRY_FORM_CONNECTION_KEY = "countryAdd";
    private JButton addCountryButton;

    public AvaiableCountryView() {
        intialize();
    }

    public void addAddCountryListener(ActionListener a) {
        if (addCountryButton != null) {
            this.addCountryButton.addActionListener(a);
        }
    }

    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        InputStream connectionResource =
                getClass().getClassLoader().getResourceAsStream("connection.xml");
        try {
            AFSwinxTable table =
                    AFSwinx.getInstance()
                            .getTableBuilder()
                            .initBuilder(COUNTRY_TABLE, connectionResource,
                                    COUNTRY_TABLE_CONNECTION_KEY)
                            .setLocalization(ApplicationContext.getInstance().getLocalization())
                            .buildComponent();
            JPanel centerPanel = new JPanel();
            centerPanel.setAlignmentX(CENTER_ALIGNMENT);
            HashMap<String, String> securityConstrains =
                    ApplicationContext.getInstance().getSecurityContext().getUserNameAndPasswodr();
            connectionResource = getClass().getClassLoader().getResourceAsStream("connection.xml");
            AFSwinxForm form =
                    AFSwinx.getInstance()
                            .getFormBuilder()
                            .initBuilder(COUNTRY_FORM, connectionResource,
                                    COUNTRY_FORM_CONNECTION_KEY, securityConstrains)
                            .setLocalization(ApplicationContext.getInstance().getLocalization())
                            .setSkin(new MySkin()).buildComponent();
            centerPanel.add(form);
            addCountryButton =
                    new JButton(Localization.getLocalizationText("avaiableCountryView.buttton.add"));
            addCountryButton.setAlignmentX(CENTER_ALIGNMENT);
            centerPanel.add(addCountryButton);
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            mainPanel.add(table);
            mainPanel.add(centerPanel);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            return mainPanel;
        } catch (AFSwinxBuildException e) {
            getDialogs().failed("afswinx.build.title.failed", "afswinx.build.text.failed",
                    e.getMessage());
        }
        return mainPanel;
    }

}
