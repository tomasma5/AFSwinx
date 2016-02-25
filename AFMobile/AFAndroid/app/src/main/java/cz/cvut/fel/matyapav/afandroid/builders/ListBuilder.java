package cz.cvut.fel.matyapav.afandroid.builders;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.AFList;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;

/**
 * Created by Pavel on 24.02.2016.
 */
public class ListBuilder extends AFComponentBuilder<ListBuilder> {

    @Override
    public AFList createComponent() throws Exception {
        initializeConnections();
        String modelResponse = getModelResponse();
        //create form from response
        AFList list = (AFList) buildComponent(modelResponse, SupportedComponents.LIST);
        //fill it with data (if there are some)
        String data = getDataResponse();
        if(data != null) {
            list.insertData(data);
        }
        AFAndroid.getInstance().addCreatedComponent(getComponentKeyName(), list);
        return list;
    }

    @Override
    protected View buildComponentView(AFComponent component) {
        ListView listView = new ListView(getActivity());
        listView.setLayoutParams(new AbsListView.LayoutParams(getSkin().getListWidth(), getSkin().getListHeight()));
        listView.setAdapter(null);
        ((AFList)component).setListView(listView);
        return listView;
    }




}
