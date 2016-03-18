package cz.cvut.fel.matyapav.showcase.skins;

import android.content.Context;

import cz.cvut.fel.matyapav.afandroid.builders.skins.DefaultSkin;

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
