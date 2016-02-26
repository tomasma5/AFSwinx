package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.AbsListView;

import cz.cvut.fel.matyapav.afandroid.components.skins.DefaultSkin;

/**
 * Created by Pavel on 24.02.2016.
 */
public class MyAbsencesListSkin extends DefaultSkin {


    public MyAbsencesListSkin(Context context) {
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
    public int getListHeight() {
        return AbsListView.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int getListContentWidth() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }
}




