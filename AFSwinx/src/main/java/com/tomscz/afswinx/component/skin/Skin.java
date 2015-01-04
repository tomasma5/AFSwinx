package com.tomscz.afswinx.component.skin;

import java.awt.Color;
import java.awt.Font;

/**
 * This is skin interfaces. This interface is used to skin component based on user setting.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public interface Skin {

    public Color getLabelColor();

    public Color getFieldColor();

    public Color getValidationColor();

    public int getInputWidth();

    public int getTextAreaRows();

    public int getTextAreaColums();

    public Font getValidationFont();

    public Font getFieldFont();

    public Font getLabelFont();

    public int getLabelWidht();

    public int getLabelHeight();

}
