package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;

/**
 * Created by Pavel on 14.02.2016.
 */
public interface AbstractWidgetBuilder {

    View buildFieldView(Context context);

    void setData(AFField field, Object value);

    Object getData(AFField field);
}
