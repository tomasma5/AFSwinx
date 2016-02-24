package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;
import android.view.ViewGroup;

import cz.cvut.fel.matyapav.afandroid.components.skins.DefaultSkin;

/**
 * Created by Pavel on 24.02.2016.
 */
public class AbsenceManagementSkin extends DefaultSkin{

    public AbsenceManagementSkin(Context context) {
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
