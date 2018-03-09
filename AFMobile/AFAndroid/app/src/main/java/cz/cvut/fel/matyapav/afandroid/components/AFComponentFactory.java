package cz.cvut.fel.matyapav.afandroid.components;

import cz.cvut.fel.matyapav.afandroid.components.types.AFComponent;
import cz.cvut.fel.matyapav.afandroid.components.types.AFForm;
import cz.cvut.fel.matyapav.afandroid.components.types.AFList;
import cz.cvut.fel.matyapav.afandroid.components.types.AFTable;
import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public class AFComponentFactory {

    private static AFComponentFactory instance = null;

    public static synchronized AFComponentFactory getInstance() {
        if(instance == null){
            instance = new AFComponentFactory();
        }
        return instance;
    }

    public AFComponent getComponentByType(SupportedComponents type){
        if(type.equals(SupportedComponents.FORM)) {
            return new AFForm();
        }else if(type.equals(SupportedComponents.TABLE)){
            return new AFTable();
        }else if(type.equals(SupportedComponents.LIST)){
            return new AFList();
        }else{
            System.err.println("Component type not supported.");
            return null;
        }

    }
}
