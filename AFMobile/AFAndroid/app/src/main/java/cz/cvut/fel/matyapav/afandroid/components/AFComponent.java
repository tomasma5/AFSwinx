package cz.cvut.fel.matyapav.afandroid.components;

import android.view.View;

import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;

/**
 * Created by Pavel on 13.02.2016.
 */
public abstract class AFComponent {

    private String name;
    private View view;

    public AFComponent() {
    }

    public AFComponent(String name, View view) {
        this.name = name;
        this.view = view;
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

    abstract SupportedComponents getComponentType();
}
