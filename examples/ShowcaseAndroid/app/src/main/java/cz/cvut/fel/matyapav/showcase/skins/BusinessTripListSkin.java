package cz.cvut.fel.matyapav.showcase.skins;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import cz.cvut.fel.matyapav.showcase.R;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class BusinessTripListSkin extends ListSkin{

    public BusinessTripListSkin(Context context) {
        super(context);
    }

    @Override
    public int getListItemsTextSize() {
        return 14;
    }

    @Override
    public int getListItemNameColor() {
        return ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
    }

    @Override
    public int getBorderColor() {
        return Color.WHITE;
    }

    @Override
    public int getListHeight() {
        return convertDpToPixels(350, getContext());
    }

}
