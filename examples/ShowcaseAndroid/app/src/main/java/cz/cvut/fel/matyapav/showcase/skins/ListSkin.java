package cz.cvut.fel.matyapav.showcase.skins;

import android.content.Context;
import android.view.ViewGroup;

import cz.cvut.fel.matyapav.afandroid.builders.skins.DefaultSkin;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
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
