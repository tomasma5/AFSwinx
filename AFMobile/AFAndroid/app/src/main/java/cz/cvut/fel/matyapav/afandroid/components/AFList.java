package cz.cvut.fel.matyapav.afandroid.components;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ListView;

import cz.cvut.fel.matyapav.afandroid.components.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutDefinitions;
import cz.cvut.fel.matyapav.afandroid.enums.LayoutOrientation;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;
import cz.cvut.fel.matyapav.afandroid.rest.AFSwinxConnectionPack;
import cz.cvut.fel.matyapav.afandroid.rest.holder.AFDataHolder;

/**
 * Created by Pavel on 24.02.2016.
 */
public class AFList extends AFComponent {

    private ListView listView;

    public AFList(Activity activity, AFSwinxConnectionPack connectionPack, Skin skin) {
        super(activity, connectionPack, skin);
    }

    public AFList(String name, ViewGroup view, LayoutDefinitions layoutDefinitions, LayoutOrientation layoutOrientation) {
        super(name, view, layoutDefinitions, layoutOrientation);
    }

    @Override
    SupportedComponents getComponentType() {
        return SupportedComponents.LIST;
    }

    @Override
    boolean validateData() {
        throw new UnsupportedOperationException("List is read only");
    }

    @Override
    AFDataHolder reserialize() {
        throw new UnsupportedOperationException("List is read only");
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    public ListView getListView() {
        return listView;
    }
}
