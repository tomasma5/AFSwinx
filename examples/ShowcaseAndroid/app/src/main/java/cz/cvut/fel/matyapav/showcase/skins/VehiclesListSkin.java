package cz.cvut.fel.matyapav.showcase.skins;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import cz.cvut.fel.matyapav.showcase.R;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class VehiclesListSkin extends ListSkin{

    public VehiclesListSkin(Context context) {
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
        return ContextCompat.getColor(getContext(), R.color.clear);
    }

    @Override
    public int getComponentMarginBottom() {
        return convertDpToPixels(30, getContext());
    }
}
