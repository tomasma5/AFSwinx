package com.tomscz.af.showcase.view.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.AFSwinxTable;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.component.builders.AFSwinxTableBuilder;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

public class PersonFormView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JButton button;
    private static final String formId = "personForm";
    private static final String formId2 = "personForm2";
    private static final String tableId_resource = "tablePerson";
    private static final String person_resource_key = "personWithEL";
    private static final String COUNTRY_TABLE_CONNECTION_KEY = "tableCountryPublic";

    public PersonFormView() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        JPanel panel = new JPanel();
        ResourceBundle czechLocalizationBundle = ResourceBundle.getBundle("cs_CZ");
        AFSwinx swinx = AFSwinx.getInstance();
        swinx.enableLocalization(czechLocalizationBundle);
        try {
            panel.add(buildFormBasedOnXMLConnection(person_resource_key));
//            panel.add(buildFormBasedOnMyConnection());
        } catch (AFSwinxBuildException e) {
            e.printStackTrace();
        }
        button = new JButton("Validate");
        button.addActionListener(sendData);
        panel.add(button);
        this.add(panel);
        this.setSize(600, 600);
        this.setVisible(true);
    }

    private ActionListener sendData = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            try {
                AFSwinx.getInstance().getExistedComponent(formId).postData();
//                AFSwinx.getInstance().getExistedComponent(formId2).postData();              
            } catch (AFSwinxConnectionException e1) {
                e1.printStackTrace();
            };
        }
    };

    private AFSwinxTopLevelComponent buildFormBasedOnXMLConnection(String connectionKey) throws AFSwinxBuildException {
        AFSwinx swinx = AFSwinx.getInstance();
        File f = new File(getClass().getClassLoader().getResource("connection.xml").getFile());
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id", "2");
//        AFSwinxTable component = swinx.getTableBuilder().initBuilder(formId, f, person_resource_key, parameters).buildComponent();
        AFSwinxForm component  = swinx.getFormBuilder().initBuilder(formId, f, connectionKey, parameters).buildComponent();
        return component;
    }

    private AFSwinxForm buildFormBasedOnMyConnection() throws AFSwinxBuildException {
        AFSwinx swinx = AFSwinx.getInstance();
        AFSwinxConnection connection =
                new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person");
        AFSwinxConnection dataConnection =
                new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person/1");
        ResourceBundle englishLocalization = ResourceBundle.getBundle("en_EN");
        AFSwinxForm form = swinx.getFormBuilder().initBuilder(formId2, connection, dataConnection, connection).setLocalization(englishLocalization).buildComponent();
        return form;
    }
    
}
