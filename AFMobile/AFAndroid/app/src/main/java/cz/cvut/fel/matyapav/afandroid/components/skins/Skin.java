package cz.cvut.fel.matyapav.afandroid.components.skins;

import android.graphics.Typeface;
import android.view.ViewGroup;

/**
 * Created by Pavel on 24.02.2016.
 */
public interface Skin {

    ViewGroup.LayoutParams getTopLayoutParams();

    //forms
    int getLabelColor();

    int getFieldColor();

    int getValidationColor();

    int getInputWidth();

    Typeface getValidationFont();

    Typeface getFieldFont();

    Typeface getLabelFont();

    int getLabelWidth();

    int getLabelHeight();

    //tables
    int getTableHeight();
    int getHeaderRowHeight();
    int getContentRowHeight();

    int getHeaderRowBackgroundColor();
    int getHeaderRowTextColor();
    int getContentBackgroundColor();
    int getContentTextColor();
    int getBorderColor();

    int getCellPaddingLeft();
    int getCellPaddingRight();
    int getCellPaddingTop();
    int getCellPaddingBottom();

    int getHeaderRowGravity();
    int getContentGravity();

    int getBorderWidth();

    //lists
    int getListWidth();
    int getListHeight();
}
