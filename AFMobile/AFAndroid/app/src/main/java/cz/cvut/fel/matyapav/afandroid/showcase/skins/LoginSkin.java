package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import cz.cvut.fel.matyapav.afandroid.components.skins.DefaultSkin;
import cz.cvut.fel.matyapav.afandroid.rest.ConnectionSecurity;
import cz.cvut.fel.matyapav.afandroid.showcase.ShowCaseUtils;

/**
 * Created by Pavel on 24.02.2016.
 */
public class LoginSkin extends DefaultSkin {

    public LoginSkin(Context context) {
        super(context);
    }

    @Override
    public int getLabelWidth() {
        return convertDpToPixels(200, getContext());
    }

    @Override
    public int getInputWidth() {
        return convertDpToPixels(200, getContext());
    }
}
