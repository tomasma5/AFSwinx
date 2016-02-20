package cz.cvut.fel.matyapav.afandroid.components;

import android.app.Activity;
import android.view.View;

import java.util.List;

import cz.cvut.fel.matyapav.afandroid.components.parts.AFField;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnection;
import cz.cvut.fel.matyapav.afandroid.rest.holder.AFDataHolder;

/**
 * Created by Pavel on 20.02.2016.
 */
public class AFTable extends AFComponent{

    private List<AFField> rows;

    public AFTable(Activity activity, AFSwinxConnection modelConnection, AFSwinxConnection dataConnection, AFSwinxConnection sendConnection) {
        super(activity, modelConnection, dataConnection, sendConnection);
    }

    public AFTable(String name, View view, LayoutDefinitions layoutDefinitions, LayoutOrientation layoutOrientation) {
        super(name, view, layoutDefinitions, layoutOrientation);
    }

    @Override
    SupportedComponents getComponentType() {
        return SupportedComponents.TABLE;
    }

    @Override
    boolean validateData() {
        throw new UnsupportedOperationException("Table is read only");
    }

    @Override
    AFDataHolder reserialize() {
        throw new UnsupportedOperationException("Table is read only");
    }
}
