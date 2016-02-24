package cz.cvut.fel.matyapav.afandroid.builders.widgets;

import cz.cvut.fel.matyapav.afandroid.builders.widgets.types.AbstractBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.types.CheckboxFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.types.DateFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.types.DropDownFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.types.OptionFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.widgets.types.TextFieldBuilder;
import cz.cvut.fel.matyapav.afandroid.components.parts.FieldInfo;
import cz.cvut.fel.matyapav.afandroid.components.skins.Skin;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedWidgets;
import cz.cvut.fel.matyapav.afandroid.utils.Utils;

/**
 * Created by Pavel on 19.02.2016.
 */
public class FieldBuilderFactory {

    private static FieldBuilderFactory instance = null;

    public static FieldBuilderFactory getInstance() {
        if(instance == null){
            instance = new FieldBuilderFactory();
        }
        return instance;
    }


    public AbstractBuilder getFieldBuilder(FieldInfo properties, Skin skin){
        if(Utils.isFieldWritable(properties.getWidgetType())){
            return new TextFieldBuilder(skin, properties);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.CALENDAR)) {
            return new DateFieldBuilder(skin);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.OPTION)){
            return new OptionFieldBuilder(skin, properties);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.DROPDOWNMENU)){
            return new DropDownFieldBuilder(skin, properties);
        }
        if(properties.getWidgetType().equals(SupportedWidgets.CHECKBOX)){
            return new CheckboxFieldBuilder(skin);
        }
        System.err.println("BUILDER FOR "+properties.getWidgetType()+" NOT FOUND");
        return null;
    }
}
