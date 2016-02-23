package cz.cvut.fel.matyapav.afandroid.builders.widgets.types;

import android.app.Activity;
import android.view.View;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;

/**
 * Created by Pavel on 14.02.2016.
 */
public interface BasicBuilder {

    public View buildFieldView(Activity activity);

    public void setData(AFField field, Object value);

    public Object getData(AFField field);
}
