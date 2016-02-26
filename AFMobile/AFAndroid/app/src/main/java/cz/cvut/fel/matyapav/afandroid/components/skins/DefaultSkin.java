package cz.cvut.fel.matyapav.afandroid.components.skins;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AbsListView;

/**
 * Created by Pavel on 24.02.2016.
 */
public class DefaultSkin implements Skin {

    private Context context;

    public DefaultSkin(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public int convertDpToPixels(int dps, Context context){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    /*******************COMMON*******************/
    @Override
    public ViewGroup.LayoutParams getTopLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /*******************FORM*******************/
    @Override
    public int getLabelColor() {
        return Color.BLACK;
    }

    @Override
    public int getFieldColor() {
        return Color.BLACK;
    }

    @Override
    public int getValidationColor() {
        return Color.RED;
    }

    @Override
    public int getInputWidth() {
        return convertDpToPixels(200, getContext());
    }

    @Override
    public Typeface getValidationFont() {
        return Typeface.DEFAULT;
    }

    @Override
    public Typeface getFieldFont() {
        return Typeface.DEFAULT;
    }

    @Override
    public Typeface getLabelFont() {
        return Typeface.DEFAULT_BOLD;
    }

    @Override
    public int getLabelWidth() {
        return convertDpToPixels(125, getContext());
    }

    @Override
    public int getLabelHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }


    /*******************TABLE*******************/
    @Override
    public int getTableHeight() {
        return convertDpToPixels(200, getContext());
    }

    @Override
    public int getHeaderRowHeight() {
        return convertDpToPixels(60, getContext()); //with padding
    }

    @Override
    public int getContentRowHeight() {
        return convertDpToPixels(50, getContext());
    }

    @Override
    public int getHeaderRowBackgroundColor() {
        return Color.GREEN;
    }

    @Override
    public int getHeaderRowTextColor() {
        return Color.BLACK;
    }

    @Override
    public int getContentBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public int getContentTextColor() {
        return Color.GRAY;
    }

    @Override
    public int getBorderColor() {
        return Color.LTGRAY;
    }

    @Override
    public int getCellPaddingLeft() {
        return convertDpToPixels(5, getContext());
    }

    @Override
    public int getCellPaddingRight() {
        return convertDpToPixels(5, getContext());
    }

    @Override
    public int getCellPaddingTop() {
        return convertDpToPixels(5, getContext());
    }

    @Override
    public int getCellPaddingBottom() {
        return convertDpToPixels(5, getContext());
    }

    @Override
    public int getHeaderRowGravity() {
        return Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
    }

    @Override
    public int getContentGravity() {
        return Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
    }

    @Override
    public int getBorderWidth() {
        return convertDpToPixels(1, getContext());
    }

    /********************************lists****************************/
    @Override
    public int getListWidth() {
        return AbsListView.LayoutParams.MATCH_PARENT;
    }

    @Override
    public int getListHeight() {
        return convertDpToPixels(200, getContext());
    }

    @Override
    public int getListBackgroundColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public int getListItemBackgroundColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public int getListItemNameColor() {
        return Color.BLACK;
    }

    @Override
    public int getListItemTextColor() {
        return Color.GRAY;
    }

    @Override
    public Typeface getListItemNameFont() {
        return Typeface.DEFAULT_BOLD;
    }

    @Override
    public Typeface getListItemTextFont() {
        return Typeface.DEFAULT;
    }

    @Override
    public int getListItemNameSize() {
        return 16;
    }

    @Override
    public int getListItemsTextSize() {
        return 10;
    }

    @Override
    public boolean isListItemNameLabelVisible() {
        return false;
    }

    @Override
    public boolean isListItemTextLabelsVisible() {
        return true;
    }

    @Override
    public boolean isListScrollBarAlwaysVisible() {
        return true;
    }

    @Override
    public int getListItemTextPaddingLeft() {
        return convertDpToPixels(10,getContext());
    }

    @Override
    public int getListItemTextPaddingRight() {
        return convertDpToPixels(5,getContext());
    }

    @Override
    public int getListItemTextPaddingTop() {
        return 0;
    }

    @Override
    public int getListItemTextPaddingBottom() {
        return 0;
    }

    @Override
    public int getListItemNamePaddingLeft() {
        return convertDpToPixels(10,getContext());
    }

    @Override
    public int getListItemNamePaddingRight() {
        return convertDpToPixels(5,getContext());
    }

    @Override
    public int getListItemNamePaddingTop() {
        return 0;
    }

    @Override
    public int getListItemNamePaddingBottom() {
        return 0;
    }

    @Override
    public int getListContentWidth() {
        return convertDpToPixels(200, getContext());
    }
}
