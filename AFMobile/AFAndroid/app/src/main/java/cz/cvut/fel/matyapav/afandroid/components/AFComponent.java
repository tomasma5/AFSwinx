package cz.cvut.fel.matyapav.afandroid.components;

import android.view.View;

import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;

/**
 * Created by Pavel on 13.02.2016.
 */
public abstract class AFComponent {

    private String name;
    private View view;
    private LayoutDefinitions layoutDefinitions;
    private LayoutOrientation layoutOrientation;

    public AFComponent() {
    }

    public AFComponent(String name, View view, LayoutDefinitions layoutDefinitions, LayoutOrientation layoutOrientation) {
        this.name = name;
        this.view = view;
        this.layoutDefinitions = layoutDefinitions;
        this.layoutOrientation = layoutOrientation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public LayoutDefinitions getLayoutDefinitions() {
        return layoutDefinitions;
    }

    public void setLayoutDefinitions(LayoutDefinitions layoutDefinitions) {
        this.layoutDefinitions = layoutDefinitions;
    }

    public LayoutOrientation getLayoutOrientation() {
        return layoutOrientation;
    }

    public void setLayoutOrientation(LayoutOrientation layoutOrientation) {
        this.layoutOrientation = layoutOrientation;
    }

    abstract SupportedComponents getComponentType();
}
