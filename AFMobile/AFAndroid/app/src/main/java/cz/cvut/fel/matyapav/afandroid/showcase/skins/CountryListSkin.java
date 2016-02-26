package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import cz.cvut.fel.matyapav.afandroid.components.skins.DefaultSkin;

/**
 * Created by Pavel on 24.02.2016.
 */
public class CountryListSkin extends DefaultSkin {


    public CountryListSkin(Context context) {
        super(context);
    }

    @Override
    public ViewGroup.LayoutParams getTopLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getListBackgroundColor() {
        return Color.WHITE;
    }
}




