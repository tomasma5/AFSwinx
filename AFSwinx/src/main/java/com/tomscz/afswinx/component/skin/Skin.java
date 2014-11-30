package com.tomscz.afswinx.component.skin;

import java.awt.Color;
import java.awt.Font;

public interface Skin {

    public Color getLabelColor();
    public Color getFieldColor();
    public Color getValidationColor();
    public int getInputColum();
    public int getTextAreaRows();
    public int getTextAreaColums();
    public Font getValidationFont();
    public Font getFieldFont();
    public Font getLabelFont();
}
