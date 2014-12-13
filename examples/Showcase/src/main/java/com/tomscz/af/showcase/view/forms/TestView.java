package com.tomscz.af.showcase.view.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxForm;
import com.tomscz.afswinx.component.abstraction.AFSwinxTopLevelComponent;
import com.tomscz.afswinx.rest.connection.AFSwinxConnectionException;

public class TestView extends JFrame {

    private static final long serialVersionUID = 1L;
    JButton button;

    public TestView() {
        JPanel panel = new JPanel();
        button = new JButton("Action");
        button.addActionListener(buttonClick);
        panel.add(createCountryFormView());
        panel.add(button);
        this.add(panel);
        this.setSize(600, 600);
        this.setVisible(true);
    }

    private AFSwinxTopLevelComponent createCountryFormView() {
        File f = new File(getClass().getClassLoader().getResource("testConnection.xml").getFile());
        AFSwinxTopLevelComponent component = null;
        try {
//            component =
//                    AFSwinx.getInstance().getFormBuilder()
//                            .initBuilder("form", f, "createCountryForm").buildComponent();
//            component =
//                    AFSwinx.getInstance().getTableBuilder()
//                            .initBuilder("form", f, "tableCountryPublic").buildComponent();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("id", "2");
            component =
                    AFSwinx.getInstance().getFormBuilder()
                            .initBuilder("form", f, "updateCountryForm",params).buildComponent();
        } catch (AFSwinxBuildException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return component;
    }

    private ActionListener buttonClick = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                AFSwinx.getInstance().getExistedComponent("form").sendData();
            } catch (AFSwinxConnectionException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    };

}
