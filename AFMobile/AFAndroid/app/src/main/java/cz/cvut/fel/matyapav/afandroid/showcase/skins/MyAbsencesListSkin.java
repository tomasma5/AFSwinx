package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import cz.cvut.fel.matyapav.afandroid.components.skins.DefaultSkin;

/**
 * Created by Pavel on 24.02.2016.
 */
public class MyAbsencesListSkin extends ListSkin {


    public MyAbsencesListSkin(Context context) {
        super(context);
    }

    @Override
    public int getListHeight() {
        return AbsListView.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public int getComponentMarginTop() {
        return convertDpToPixels(20, getContext());
    }
}




