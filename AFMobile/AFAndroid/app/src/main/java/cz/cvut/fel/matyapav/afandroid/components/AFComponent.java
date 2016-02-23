package cz.cvut.fel.matyapav.afandroid.components;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnection;
import cz.cvut.fel.matyapav.afandroid.rest.BaseRestBuilder;
import cz.cvut.fel.matyapav.afandroid.rest.RequestTask;
import cz.cvut.fel.matyapav.afandroid.rest.RestBuilderFactory;
import cz.cvut.fel.matyapav.afandroid.rest.holder.AFDataHolder;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 13.02.2016.
 */
public abstract class AFComponent {

    private Activity activity;
    private String name;
    private ViewGroup view;
    private LayoutDefinitions layoutDefinitions;
    private LayoutOrientation layoutOrientation;
    private List<AFField> fields;

    private AFSwinxConnection modelConnection;
    private AFSwinxConnection dataConnection;
    private AFSwinxConnection sendConnection;

    public AFComponent(Activity activity, AFSwinxConnection modelConnection, AFSwinxConnection dataConnection, AFSwinxConnection sendConnection) {
        this.activity = activity;
        this.modelConnection = modelConnection;
        this.dataConnection = dataConnection;
        this.sendConnection = sendConnection;
    }

    public AFComponent(String name, ViewGroup view, LayoutDefinitions layoutDefinitions, LayoutOrientation layoutOrientation) {
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

    public ViewGroup getView() {
        return view;
    }

    public void setView(ViewGroup view) {
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

    abstract boolean validateData();

    abstract AFDataHolder reserialize();

    public AFSwinxConnection getDataConnection() {
        return dataConnection;
    }

    public AFSwinxConnection getModelConnection() {
        return modelConnection;
    }

    public AFSwinxConnection getSendConnection() {
        return sendConnection;
    }

    public void setDataConnection(AFSwinxConnection dataConnection) {
        this.dataConnection = dataConnection;
    }

    public void setModelConnection(AFSwinxConnection modelConnection) {
        this.modelConnection = modelConnection;
    }

    public void setSendConnection(AFSwinxConnection sendConnection) {
        this.sendConnection = sendConnection;
    }

    public void addField(AFField field){
        if(fields == null){
            fields = new ArrayList<AFField>();
        }
        fields.add(field);
    }

    public List<AFField> getFields() {
        return fields;
    }

    public void setFields(List<AFField> fields) {
        this.fields = fields;
    }

    public Activity getActivity() {
        return activity;
    }
}
