package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import android.content.Context;

import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.builders.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class WidgetBuilderFactory {

    private static WidgetBuilderFactory instance = null;

    public static synchronized WidgetBuilderFactory getInstance() {
        if(instance == null){
            instance = new WidgetBuilderFactory();
        }
        return instance;
    }


    public AbstractWidgetBuilder getFieldBuilder(Context context, FieldInfo properties, Skin skin){
        if(Utils.isFieldWritable(properties.getWidgetType())){
            return new TextWidgetBuilder(context, skin, properties);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.CALENDAR)) {
            return new DateWidgetBuilder(context, skin, properties);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.OPTION)){
            return new OptionWidgetBuilder(context, skin, properties);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.DROPDOWNMENU)){
            return new DropDownWidgetBuilder(context, skin, properties);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.CHECKBOX)){
            return new CheckboxWidgetBuilder(context, skin, properties);
        }
        System.err.println("BUILDER FOR "+properties.getWidgetType()+" NOT FOUND");
        return null;
    }
}
