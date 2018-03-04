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
import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;

public class MyAbsenceInstanceView extends BaseView {

    private static final long serialVersionUID = 1L;
    public static final String MY_INSTANCE_TABLE = "myAbsenceInstancesTable";
    public static final String MY_INSTANCE_TABLE_CONNECTION = "myAbsenceInstancesTableConnection";
    

    public MyAbsenceInstanceView(AFProxyScreenDefinition screenDefinition){
        super(screenDefinition);
        intialize();
    }
    
    @Override
    protected JPanel createContent() {
        JPanel mainPanel = new JPanel();
        Box b1 = Box.createVerticalBox();
        try {
            HashMap<String, String> connectionParmeters =
                    ApplicationContext.getInstance().getSecurityContext().getUserNameAndPasswodr();
            AFSwinxTable table =
                    AFSwinx.getInstance()
                            .getTableBuilder()
                            .initBuilder(MY_INSTANCE_TABLE, null, connectionParmeters)
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
