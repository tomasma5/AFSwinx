package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import cz.cvut.fel.matyapav.afandroid.R;

/**
 * Created by Pavel on 26.02.2016.
 */
public class AbsenceManagementListSkin extends ListSkin{

    public AbsenceManagementListSkin(Context context) {
        super(context);
    }


    @Override
    public boolean isListItemNameLabelVisible() {
        return false;
    }

    @Override
    public int getListItemsTextSize() {
        return 14;
    }

    @Override
    public int getListItemNameColor() {
        return ContextCompat.getColor(getContext(), R.color.colorAccent2);
    }

    @Override
    public int getComponentMarginBottom() {
        return convertDpToPixels(30, getContext());
    }
}
