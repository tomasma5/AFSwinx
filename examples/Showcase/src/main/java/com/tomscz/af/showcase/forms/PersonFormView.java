package com.tomscz.af.showcase.forms;

import javax.swing.JFrame;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.form.AFSwinxForm;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;

public class PersonFormView extends JFrame{
    
    private static final long serialVersionUID = 1L;

    public PersonFormView(){
        AFSwinx swinx = AFSwinx.getInstance();
        AFSwinxConnection connection = new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person");
        AFSwinxForm form = swinx.buildForm("persoForm",connection, connection, connection);
        this.add(form);
        this.setVisible(true);
    }
    
}
