package com.tomscz.af.showcase.forms;

import java.net.ConnectException;

import javax.swing.JFrame;

import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.form.AFSwinxForm;
import com.tomscz.afswinx.rest.connection.AFSwinxConnection;

public class PersonFormView extends JFrame{
    
    private static final long serialVersionUID = 1L;

    public PersonFormView(){
        AFSwinx swinx = AFSwinx.getInstance();
        AFSwinxConnection connection = new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person");
        AFSwinxConnection dataConnection = new AFSwinxConnection("localhost", 8080, "/AFServer/rest/Person/1");
        try{
            AFSwinxForm form = swinx.buildForm("persoForm",connection, dataConnection, connection);   
            this.add(form);
        }
        catch(ConnectException e){
            e.printStackTrace();
        }
        this.setSize(200, 400);
        this.setVisible(true);
    }
    
}
