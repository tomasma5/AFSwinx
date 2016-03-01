package cz.cvut.fel.matyapav.afandroid.showcase.skins;

import android.content.Context;
import android.view.ViewGroup;

import cz.cvut.fel.matyapav.afandroid.builders.skins.DefaultSkin;

/**
 * Created by Pavel on 28.02.2016.
 */
public class ListSkin extends DefaultSkin {

    public ListSkin(Context context) {
        super(context);
    }

    @Override
    public ViewGroup.LayoutParams getTopLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
