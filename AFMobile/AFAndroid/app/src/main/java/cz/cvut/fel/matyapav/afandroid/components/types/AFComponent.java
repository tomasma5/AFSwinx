package cz.cvut.fel.matyapav.afandroid.components.types;

import android.content.Context;
import android.view.ViewGroup;

import com.tomscz.afswinx.rest.connection.AFSwinxConnectionPack;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.builders.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public abstract class AFComponent implements AbstractComponent {

    private Context context;
    private String name;
    private ViewGroup view;
    private LayoutDefinitions layoutDefinitions;
    private LayoutOrientation layoutOrientation;
    private List<AFField> fields;
    private AFSwinxConnectionPack connectionPack;
    private Skin skin;

    public AFComponent() {
    }

    public AFComponent(Context context, AFSwinxConnectionPack connectionPack, Skin skin) {
        this.context = context;
        this.connectionPack = connectionPack;
        this.skin = skin;
    }

    //this one should be used by users
    public void insertData(Object dataObject) {
        insertData(dataObject.toString(), new StringBuilder());
    }

    public void addField(AFField field) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        field.setParent(this);
        fields.add(field);
    }

    public AFField getFieldById(String id) {
        for (AFField field : getFields()) {
            if (field.getId().equals(id)) {
                return field;
            }
        }
        //not found
        return null;
    }

    public int getVisibleFieldsCount() {
        int res = 0;
        for (AFField field : getFields()) {
            if (field.getFieldInfo().isVisible()) {
                res++;
            }
        }
        return res;
    }

    //GETTERS
    public String getName() {
        return name;
    }

    public Skin getSkin() {
        return skin;
    }

    public List<AFField> getFields() {
        return fields;
    }

    public Context getContext() {
        return context;
    }

    public AFSwinxConnectionPack getConnectionPack() {
        return connectionPack;
    }

    public ViewGroup getView() {
        return view;
    }

    public LayoutOrientation getLayoutOrientation() {
        return layoutOrientation;
    }

    public LayoutDefinitions getLayoutDefinitions() {
        return layoutDefinitions;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setConnectionPack(AFSwinxConnectionPack connectionPack) {
        this.connectionPack = connectionPack;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public void setFields(List<AFField> fields) {
        this.fields = fields;
    }

    public void setView(ViewGroup view) {
        this.view = view;
    }

    public void setLayoutDefinitions(LayoutDefinitions layoutDefinitions) {
        this.layoutDefinitions = layoutDefinitions;
    }

    public void setLayoutOrientation(LayoutOrientation layoutOrientation) {
        this.layoutOrientation = layoutOrientation;
    }


}
