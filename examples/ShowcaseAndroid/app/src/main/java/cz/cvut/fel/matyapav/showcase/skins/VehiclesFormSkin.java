package cz.cvut.fel.matyapav.showcase.skins;

import android.content.Context;

import cz.cvut.fel.matyapav.afandroid.builders.skins.DefaultSkin;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class VehiclesFormSkin extends DefaultSkin {

    public VehiclesFormSkin(Context context) {
        super(context);
    }

    @Override
    public int getInputWidth() {
        return convertDpToPixels(200, getContext());
    }

    @Override
    public int getLabelWidth() {
        return convertDpToPixels(120, getContext());
    }
}
