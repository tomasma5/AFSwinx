package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import android.content.Context;

import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.builders.skins.Skin;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
abstract class BasicBuilder implements AbstractWidgetBuilder {

    private Skin skin;
    private FieldInfo properties;
    private Context context;

    BasicBuilder(Context context, Skin skin, FieldInfo properties) {
        this.skin = skin;
        this.properties = properties;
        this.context = context;
    }

    public Skin getSkin() {
        return skin;
    }

    public FieldInfo getProperties() {
        return properties;
    }

    public Context getContext() {
        return context;
    }
}
