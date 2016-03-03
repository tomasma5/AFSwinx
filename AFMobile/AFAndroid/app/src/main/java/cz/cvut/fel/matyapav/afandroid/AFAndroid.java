package cz.cvut.fel.matyapav.afandroid;

import java.util.HashMap;

import cz.cvut.fel.matyapav.afandroid.builders.FormBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.ListBuilder;
import cz.cvut.fel.matyapav.afandroid.builders.TableBuilder;
import cz.cvut.fel.matyapav.afandroid.components.types.AFComponent;

/**
 * Created by Pavel on 13.02.2016.
 */
public class AFAndroid {

    private static AFAndroid instance = null;
    private HashMap<String, AFComponent> createdComponents;

    public AFAndroid() {
        createdComponents = new HashMap<>();
    }

    public static synchronized AFAndroid getInstance(){
        if(instance == null){
            instance = new AFAndroid();
        }
        return instance;
    }

    public HashMap<String, AFComponent> getCreatedComponents() {
        return createdComponents;
    }

    public void addCreatedComponent(String name, AFComponent component){
        createdComponents.put(name,component);
    }

    public FormBuilder getFormBuilder(){
        return new FormBuilder();
    }


    public TableBuilder getTableBuilder() {
        return new TableBuilder();
    }

    public ListBuilder getListBuilder(){
        return new ListBuilder();
    }
}
