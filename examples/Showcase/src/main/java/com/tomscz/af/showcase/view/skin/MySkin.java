package com.tomscz.af.showcase.view.skin;

import java.awt.Color;

import com.tomscz.afswinx.component.skin.BaseSkin;

public class MySkin extends BaseSkin {

    @Override
    public Color getLabelColor() {
        return Color.BLACK;
    }

    @Override
    public int getInputWidth() {
        return 150;
    }
 
}
