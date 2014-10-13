package com.tomscz.afswinx.component.panel;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.tomscz.afswinx.common.SupportedWidgets;

public class AFSwinxPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JComponent dataHolder;
    private JComponent labelHolder;
    private SupportedWidgets widgetType;
    private String panelId;

    public AFSwinxPanel(String panelId, SupportedWidgets widgetType, JComponent dataHolder) {
        this.panelId = panelId;
        this.widgetType = widgetType;
        this.dataHolder = dataHolder;
    }

    public AFSwinxPanel(String panelId, SupportedWidgets widgetType, JComponent dataHolder,
            JComponent labelHolder) {
        this.panelId = panelId;
        this.widgetType = widgetType;
        this.dataHolder = dataHolder;
        this.labelHolder = labelHolder;
    }

    public AFSwinxPanel(String panelId, SupportedWidgets widgetType, JComponent dataHolder,
            JComponent labelHolder, JPanel content) {
        this.panelId = panelId;
        this.widgetType = widgetType;
        this.dataHolder = dataHolder;
        this.labelHolder = labelHolder;
        this.add(content);
    }

    public JComponent getDataHolder() {
        return dataHolder;
    }

    public void setDataHolder(JComponent dataHolder) {
        this.dataHolder = dataHolder;
    }

    public SupportedWidgets getWidgetType() {
        return widgetType;
    }

    public void setWidgetType(SupportedWidgets widgetType) {
        this.widgetType = widgetType;
    }

    public String getPanelId() {
        return panelId;
    }

}
