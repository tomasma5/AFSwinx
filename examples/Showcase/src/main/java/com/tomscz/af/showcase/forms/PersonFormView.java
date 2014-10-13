package com.tomscz.af.showcase.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ConnectException;

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
        AFSwinx swinx = AFSwinx.getInstance();
        JPanel panel = new JPanel();
        AFSwinxConnection connection =
                new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person");
        AFSwinxConnection dataConnection =
                new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person/1");
        try {
            AFSwinxForm form = swinx.buildForm(formId, connection, dataConnection, connection);
            panel.add(form);
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



}
