package com.tomscz.afswinx.swing.component.converter;

import java.util.ResourceBundle;

import com.tomscz.afrest.rest.dto.AFOptions;
import com.tomscz.afswinx.localization.LocalizationUtils;
import com.tomscz.afswinx.swing.component.AFComponentDataHolder;

/**
 * This class can convert {@link AFOptions} to {@link AFComponentDataHolder}.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class AFOptionToAFSwinxOption implements ModelConverter<AFOptions, AFComponentDataHolder> {
    @Override
    public AFComponentDataHolder convert(AFOptions source, ResourceBundle localization) {
        AFComponentDataHolder result = null;
        if(source != null){
            String displayedValue = source.getValue();
            result = new AFComponentDataHolder(source.getKey(), displayedValue);
            if(localization != null && displayedValue != null && !displayedValue.isEmpty()){
                displayedValue = LocalizationUtils.getTextFromExtendBundle(displayedValue, localization, null);
            }
            result.setValueToDisplay(displayedValue);
        }
        return result;
    }
    
}
