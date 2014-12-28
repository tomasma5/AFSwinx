package com.tomscz.af.showcase.view;

import java.io.InputStream;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.tomscz.af.showcase.application.ApplicationContext;
import com.tomscz.afswinx.component.AFSwinx;
import com.tomscz.afswinx.component.AFSwinxBuildException;
import com.tomscz.afswinx.component.AFSwinxTable;

public class MyAbsenceInstanceView extends BaseScreen {

    private static final long serialVersionUID = 1L;
    public static final String MY_INSTANCE_TABLE = "myAbsenceInstancesTable";
    public static final String MY_INSTANCE_TABLE_CONNECTION = "myAbsenceInstancesTableConnection";
    

    public MyAbsenceInstanceView(){
        intialize();
    }
    
    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        Box b1 = Box.createVerticalBox();
        InputStream connectionResource =
                getClass().getClassLoader().getResourceAsStream("connection.xml");
        try {
            HashMap<String, String> connectionParmeters =
                    ApplicationContext.getInstance().getSecurityContext().getUserNameAndPasswodr();
            connectionResource = getClass().getClassLoader().getResourceAsStream("connection.xml");
            AFSwinxTable table =
                    AFSwinx.getInstance()
                            .getTableBuilder()
                            .initBuilder(MY_INSTANCE_TABLE, connectionResource,
                                    MY_INSTANCE_TABLE_CONNECTION, connectionParmeters)
                            .setLocalization(ApplicationContext.getInstance().getLocalization()).setFitSize(true).setDynamicSize(true)
                            .buildComponent();
            b1.add(table);
            b1.add(Box.createVerticalStrut(40));
            mainPanel.add(b1);
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        } catch (AFSwinxBuildException e) {
            getDialogs().failed("afswinx.build.title.failed", "afswinx.build.text.failed",
                    e.getMessage());
        }
        return mainPanel;
    }

}
