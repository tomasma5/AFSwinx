package cz.cvut.fel.matyapav.afandroid.components.types;

import com.tomscz.afswinx.rest.rebuild.holder.AFDataHolder;

import cz.cvut.fel.matyapav.afandroid.enums.SupportedComponents;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 *
 *@since 1.0.0..
 */
public interface AbstractComponent {

    void insertData(String dataResponse, StringBuilder road);

    SupportedComponents getComponentType();

    boolean validateData();

    AFDataHolder reserialize();

}
