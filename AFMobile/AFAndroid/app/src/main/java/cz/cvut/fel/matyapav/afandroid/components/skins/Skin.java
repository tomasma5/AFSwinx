package cz.cvut.fel.matyapav.afandroid.components.skins;

import android.graphics.Typeface;

/**
 * Created by Pavel on 24.02.2016.
 */
public interface Skin {

    //forms
    public int getLabelColor();

    public int getFieldColor();

    public int getValidationColor();

    public int getInputWidth();

    public Typeface getValidationFont();

    public Typeface getFieldFont();

    public Typeface getLabelFont();

    public int getLabelWidth();

    public int getLabelHeight();

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
}
