package com.tomscz.afswinx.component;

import com.tomscz.afswinx.component.uiproxy.AFProxyScreenDefinition;
import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AFSwinxMenuButton extends JButton{

    private static final long serialVersionUID = 1L;

    private String title;
    private String url;
    private int menuOrder;
    private ActionListener onClickListener;
    private ScreenPreparedListener screenPreparedListener;

    public AFSwinxMenuButton() {
    }

    public AFSwinxMenuButton(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ActionListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(ActionListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ScreenPreparedListener getScreenPreparedListener() {
        return screenPreparedListener;
    }

    public void setScreenPreparedListener(ScreenPreparedListener screenPreparedListener) {
        this.screenPreparedListener = screenPreparedListener;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }
}
