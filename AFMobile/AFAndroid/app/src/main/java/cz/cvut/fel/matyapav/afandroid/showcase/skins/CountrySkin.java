package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;
import android.graphics.Color;

import cz.cvut.fel.matyapav.afandroid.components.skins.DefaultSkin;

/**
 * Created by Pavel on 24.02.2016.
 */
public class CountrySkin extends DefaultSkin {

    public CountrySkin(Context context) {
        super(context);
    }

    @Override
    public int getLabelWidth() {
        return convertDpToPixels(80, getContext());
    }

    @Override
    public int getInputWidth() {
        return convertDpToPixels(200, getContext());
    }


}
