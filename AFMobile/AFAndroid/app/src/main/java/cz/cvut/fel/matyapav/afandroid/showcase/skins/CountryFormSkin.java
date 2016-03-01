package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;

import cz.cvut.fel.matyapav.afandroid.builders.skins.DefaultSkin;

/**
 * Created by Pavel on 28.02.2016.
 */
public class CountryFormSkin extends DefaultSkin {

    public CountryFormSkin(Context context) {
        super(context);
    }

    @Override
    public int getInputWidth() {
        return convertDpToPixels(150, getContext());
    }

    @Override
    public int getLabelWidth() {
        return convertDpToPixels(80, getContext());
    }
}
