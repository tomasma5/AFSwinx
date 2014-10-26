package com.tomscz.af.showcase.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.ConnectException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.form.AFSwinxForm;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;

public class PersonFormView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JButton button;
    private static final String formId = "personForm";

    public PersonFormView() {
        JPanel panel = new JPanel();
        try {
            panel.add(buildFormBasedOnXMLConnection("personWithEL"));
        } catch (ConnectException e) {
            e.printStackTrace();
        }
        button = new JButton("Validate");
        button.addActionListener(sendData);
        panel.add(button);
        this.add(panel);
        this.setSize(200, 400);
        this.setVisible(true);
    }

    private ActionListener sendData = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            try {
                AFSwinx.getInstance().getExistedComponent(formId).postData();
            } catch (ConnectException e1) {
                e1.printStackTrace();
            };
        }
    };

    private AFSwinxForm buildFormBasedOnXMLConnection(String connectionKey) throws ConnectException {
        AFSwinx swinx = AFSwinx.getInstance();
        File f = new File(getClass().getClassLoader().getResource("connection.xml").getFile());
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("id", "2");
        AFSwinxForm form = swinx.buildForm(formId, f, connectionKey, parameters);
        return form;
    }

    private AFSwinxForm buildFormBasedOnMyConnection() throws ConnectException {
        AFSwinx swinx = AFSwinx.getInstance();
        AFSwinxConnection connection =
                new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person");
        AFSwinxConnection dataConnection =
                new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person/1");
        AFSwinxForm form = swinx.buildForm(formId, connection, dataConnection, connection);
        return form;
    }



}
