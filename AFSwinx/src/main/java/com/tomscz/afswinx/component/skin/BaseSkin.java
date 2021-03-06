package com.tomscz.afswinx.component.skin;

import java.awt.Color;
import java.awt.Font;

/**
 * This is default implementation of {@link Skin} interface. It should be also used as default skin
 * if no skin is created and set by user. Extends from this class if you want to make you skin or
 * implement {@link Skin} interface.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class BaseSkin implements Skin {

    private Color labelColor = Color.WHITE;
    private Color validationColor = Color.RED;
    private Color fieldColor = Color.BLACK;
    private Font validationFont = new Font("Verdana", Font.BOLD, 12);
    private Font labelFont = new Font("Verdana", Font.PLAIN, 10);
    private Font fieldFont = new Font("Verdana", Font.PLAIN, 15);
    private int inputWidth = 100;
    private int textAreaColums;
    private int textAreaRows;
    private int labelWidht = 100;
    private int labelHeight = 30;

    @Override
    public Color getLabelColor() {
        return labelColor;
    }

    @Override
    public Color getValidationColor() {
        return validationColor;
    }

    public void setValidationColor(Color validationColor) {
        this.validationColor = validationColor;
    }

    @Override
    public int getInputWidth() {
        return inputWidth;
    }

    @Override
    public int getTextAreaColums() {
        return textAreaColums;
    }

    public void setTextAreaColums(int textAreaColums) {
        this.textAreaColums = textAreaColums;
    }

    @Override
    public int getTextAreaRows() {
        return textAreaRows;
    }

    public void setTextAreaRows(int textAreaRows) {
        this.textAreaRows = textAreaRows;
    }

    @Override
    public Font getValidationFont() {
        return validationFont;
    }

    @Override
    public Font getFieldFont() {
        return fieldFont;
    }

    @Override
    public Font getLabelFont() {
        return labelFont;
    }

    @Override
    public Color getFieldColor() {
        return fieldColor;
    }

    @Override
    public int getLabelWidht() {
        return labelWidht;
    }

    @Override
    public int getLabelHeight() {
        return labelHeight;
    }

}
