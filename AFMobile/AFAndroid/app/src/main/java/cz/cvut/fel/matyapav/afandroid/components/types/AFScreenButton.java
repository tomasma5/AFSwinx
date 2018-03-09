package cz.cvut.fel.matyapav.afandroid.components.types;

import android.content.Context;
import cz.cvut.fel.matyapav.afandroid.components.uiproxy.AFAndroidScreenPreparedListener;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class AFScreenButton extends android.support.v7.widget.AppCompatButton {

    private String key;
    private String url;
    private String displayText;
    private int menuOrder;
    private OnClickListener onClickListener;
    private AFAndroidScreenPreparedListener screenPreparedListener;

    public AFScreenButton(Context context) {
        super(context);
    }

    public AFScreenButton(Context context, String key, String displayText, String url) {
        super(context);
        this.key = key;
        this.displayText = displayText;
        this.url = url;
    }

    public AFScreenButton(Context context, String key, String url) {
        super(context);
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

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public AFAndroidScreenPreparedListener getScreenPreparedListener() {
        return screenPreparedListener;
    }

    public void setScreenPreparedListener(AFAndroidScreenPreparedListener screenPreparedListener) {
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
