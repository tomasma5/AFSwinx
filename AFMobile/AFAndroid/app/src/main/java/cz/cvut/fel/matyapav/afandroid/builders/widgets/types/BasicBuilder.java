package cz.cvut.fel.matyapav.afandroid.builders.widgets.types;

import android.app.Activity;
import android.view.View;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.components.skins.Skin;

/**
 * Created by Pavel on 24.02.2016.
 */
abstract class BasicBuilder implements AbstractBuilder {

    private Skin skin;

    public BasicBuilder(Skin skin) {
        this.skin = skin;
    }

    public Skin getSkin() {
        return skin;
    }
}
