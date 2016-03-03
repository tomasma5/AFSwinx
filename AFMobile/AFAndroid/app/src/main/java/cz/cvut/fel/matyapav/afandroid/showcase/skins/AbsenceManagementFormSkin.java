package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;

import cz.cvut.fel.matyapav.afandroid.builders.skins.DefaultSkin;

/**
 * Created by Pavel on 24.02.2016.
 */
public class AbsenceManagementFormSkin extends DefaultSkin {

    public AbsenceManagementFormSkin(Context context) {
        super(context);
    }

    @Override
    public int getInputWidth() {
        return convertDpToPixels(120, getContext());
    }

    @Override
    public int getLabelWidth() {
        return convertDpToPixels(60, getContext());
    }

}
