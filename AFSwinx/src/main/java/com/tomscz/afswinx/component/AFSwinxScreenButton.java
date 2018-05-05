package com.tomscz.afswinx.component;

import com.tomscz.afswinx.component.uiproxy.ScreenPreparedListener;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Model for button which should serve screen definition on its click
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz)
 */
public class AFSwinxScreenButton extends JButton{

    private static final long serialVersionUID = 1L;

    private String key;
    private String url;
    private String displayText;
    private int menuOrder;
    private ActionListener onClickListener;
    private ScreenPreparedListener screenPreparedListener;

    public AFSwinxScreenButton() {
    }

    public AFSwinxScreenButton(String key, String displayText, String url) {
        this.key = key;
        this.displayText = displayText;
        this.url = url;
    }

    public AFSwinxScreenButton(String key, String url) {
        this.key = key;
        this.displayText = key;
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
}
