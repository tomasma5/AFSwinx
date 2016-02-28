package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;

import cz.cvut.fel.matyapav.afandroid.components.skins.DefaultSkin;

/**
 * Created by Pavel on 28.02.2016.
 */
public class CreateAbsenceFormSkin extends DefaultSkin {

    public CreateAbsenceFormSkin(Context context) {
        super(context);
    }

    @Override
    public int getLabelWidth() {
        return convertDpToPixels(100, getContext());
    }

    @Override
    public int getInputWidth() {
        return convertDpToPixels(200, getContext());
    }

}
