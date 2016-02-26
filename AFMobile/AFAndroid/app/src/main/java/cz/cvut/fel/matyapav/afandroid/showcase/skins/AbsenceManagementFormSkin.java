package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.AbsListView;

import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.components.skins.DefaultSkin;

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
