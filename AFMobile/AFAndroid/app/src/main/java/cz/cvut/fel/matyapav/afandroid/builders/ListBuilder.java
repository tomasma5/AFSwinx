package cz.cvut.fel.matyapav.afandroid.builders;

import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tomscz.afswinx.common.ParameterMissingException;

import org.json.JSONException;

import cz.cvut.fel.matyapav.afandroid.AFAndroid;
import cz.cvut.fel.matyapav.afandroid.components.types.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;

/**
 * Builds list component from class definition
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class ListBuilder extends AFComponentBuilder<ListBuilder> {

    @Override
    public AFList createComponent() throws Exception {
        try {
            initializeConnections();
        } catch (ParameterMissingException | JSONException e) {
            e.printStackTrace();
            return null;
        }
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
        ListView listView = new ListView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getSkin().getListWidth(), getSkin().getListHeight());
        params.setMargins(getSkin().getComponentMarginLeft(), getSkin().getComponentMarginTop(),
                getSkin().getComponentMarginRight(), getSkin().getComponentMarginBottom());
        listView.setLayoutParams(params);
        listView.setAdapter(null);
        //create border
        if(getSkin().getListBorderWidth() > 0) {
            RectShape rect = new RectShape();
            ShapeDrawable rectShapeDrawable = new ShapeDrawable(rect);
            Paint paint = rectShapeDrawable.getPaint();
            paint.setColor(getSkin().getListBorderColor());
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(getSkin().getListBorderWidth());
            listView.setBackground(rectShapeDrawable);
        }

        listView.setScrollbarFadingEnabled(!getSkin().isListScrollBarAlwaysVisible());
        //listView.setBackgroundColor(getSkin().getListBackgroundColor());
        ((AFList)component).setListView(listView);
        return listView;
    }




}
