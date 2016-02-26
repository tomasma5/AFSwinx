package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import cz.cvut.fel.matyapav.afandroid.R;
import cz.cvut.fel.matyapav.afandroid.components.skins.DefaultSkin;

/**
 * Created by Pavel on 26.02.2016.
 */
public class AbsenceManagementListSkin extends DefaultSkin{

    public AbsenceManagementListSkin(Context context) {
        super(context);
    }

    @Override
    public ViewGroup.LayoutParams getTopLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean isListItemNameLabelVisible() {
        return true;
    }

    @Override
    public int getListContentWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int getListItemsTextSize() {
        return 14;
    }

    @Override
    public int getListItemNameColor() {
        return ContextCompat.getColor(getContext(), R.color.colorAccent2);
    }
}
