package com.tomscz.af.showcase.view.forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.af.showcase.application.ShowcaseConstants;
import com.tomscz.af.showcase.utils.FileUtils;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;

public class AvaiableCountryView extends BaseScreen {

    private static final long serialVersionUID = 1L;
    
    private static final String COUNTRY_TABLE = "countryTable";
    private static final String COUNTRY_TABLE_CONNECTION_KEY = "tableCountryPublic";

    public AvaiableCountryView(){
        intialize();
    }
    
    @Override
    protected void intialize() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
        //Generate content
        JPanel contentPanel = new JPanel();
        JPanel privatePanel = createSupportedCountry();
        contentPanel.setLayout(new GridLayout(0,1));
        contentPanel.add(privatePanel);
        //add semi-generated parts
        mainPanel.add(createHeader());
        mainPanel.add(contentPanel);
        mainPanel.add(createFooter());
        this.add(mainPanel);
        this.setTitle("Showcase for "
                + Localization.getLocalizationText("login.header.mainText",
                        ShowcaseConstants.APPLICATION_VERSION));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(800, 600);
        this.setVisible(true);
        
    }
    
    private JPanel createHeader() {
        JPanel mainPanel = new JPanel();
        JLabel label = new JLabel();
        mainPanel.setLayout(new GridBagLayout());
        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("<html>");
        headerBuilder.append(Localization.getLocalizationText("avaiableCountryView.header.information"));
        headerBuilder.append("</html>");
        label.setText(headerBuilder.toString());
        JPanel translationPanel = createLocalizationToolbar();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(translationPanel, c);    
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        mainPanel.add(label,c);
        return mainPanel;
    }

    private JPanel createSupportedCountry() {
        JPanel mainPanel = new JPanel();
        File connectionFile =
                new File(getClass().getClassLoader().getResource("connection.xml").getFile());
        try {
            AFSwinxTable table =
                    AFSwinx.getInstance().getTableBuilder()
                            .initBuilder(COUNTRY_TABLE, connectionFile, COUNTRY_TABLE_CONNECTION_KEY)
                            .setLocalization(ApplicationContext.getInstance().getLocalization())
                            .buildComponent();
            mainPanel.add(table);
            mainPanel.add(Box.createVerticalStrut(20));
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            return mainPanel;
        } catch (AFSwinxBuildException e) {
            dialogs.failed("afswinx.build.title.failed", "afswinx.build.text.failed", e.getMessage());
        }
        return mainPanel;
    }

    private JPanel createFooter() {
        JPanel mainPanel = new JPanel();
        JLabel label = new JLabel(Localization.getLocalizationText("source.view"));
        mainPanel.add(label);
        JTextArea textArea = new JTextArea(10, 70);
        textArea.setText(new FileUtils().readClass("countryTable.txt"));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 60, 780, 500);
        mainPanel.add(scrollPane);
        return mainPanel;
    }
}
