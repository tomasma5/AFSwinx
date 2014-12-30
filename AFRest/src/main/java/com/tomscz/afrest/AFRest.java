package com.tomscz.afrest;

import java.util.HashMap;

import com.tomscz.afrest.exception.MetamodelException;
import com.tomscz.afrest.rest.dto.AFMetaModelPack;

/**
 * This is interface which should be used if you want to generate definition from object. This
 * definition is then used to create components.
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public interface AFRest {

    /**
     * This method generate definition from class which receive in parameter. If exception occur
     * during definition building then {@link MetamodelException} is thrown
     * 
     * @param fullClassName full class (canonical) name of entity in which will be definition
     *        started.
     * @return Definition based on will be created components.
     * @throws MetamodelException if exception during building model is occur, then is exception
     *         will be thrown.
     */
    public AFMetaModelPack generateSkeleton(String fullClassName) throws MetamodelException;

    /**
     * This method generate definition from class which receive in parameter. If exception occur
     * during definition building then {@link MetamodelException} is thrown
     * 
     * @param fullClassName full class (canonical) name of entity in which will be definition
     *        started.
     * @param structureConfig mapping which will be used.
     * @return Definition based on will be created components.
     * @throws MetamodelException if exception during building model is occur, then is exception
     *         will be thrown.
     */
    public AFMetaModelPack generateSkeleton(String fullClassName, String structureConfig)
            throws MetamodelException;

    /**
     * This method generate definition from class which receive in parameter. If exception occur
     * during definition building then {@link MetamodelException} is thrown
     * 
     * @param fullClassName full class (canonical) name of entity in which will be definition
     *        started.
     * @param structureConfig mapping which will be used.
     * @param mainLayout this is layout which will be used. Example: templates/oneColumnLayout.xml.
     * @return Definition based on will be created components.
     * @throws MetamodelException if exception during building model is occur, then is exception
     *         will be thrown.
     */
    public AFMetaModelPack generateSkeleton(String fullClassName, String structureConfig,
            String mainLayout) throws MetamodelException;

    /**
     * This method generate definition from class which receive in parameter. If exception occur
     * during definition building then {@link MetamodelException} is thrown
     * 
     * @param fullClassName full class (canonical) name of entity in which will be definition
     *        started.
     * @param structureConfig mapping which will be used. Based on key will be selected mapping.
     *        Mapping for root is canonical name. Mapping for child nodes is variable in this class.
     * @param mainLayout this is layout which will be used. Example: templates/oneColumnLayout.xml.
     * @return Definition based on will be created components.
     * @throws MetamodelException if exception during building model is occur, then is exception
     *         will be thrown.
     */
    public AFMetaModelPack generateSkeleton(String fullClassName,
            HashMap<String, String> structureConfig, String mainLayout) throws MetamodelException;

    /**
     * This method set mainLayout. Example: templates/oneColumnLayout.xml.
     * 
     * @param layout layout which will be used
     */
    public void setMainLayout(String layout);

    /**
     * This method set default mapping which will be used. example: structure.config.xml
     * 
     * @param mapping which will be used as default mapping
     */
    public void setMapping(String mapping);

    /**
     * This method set variables to AspectFaces context. Key of variable is key from hashMap and
     * value is value in hashMap.
     * 
     * @param variables which will be set to AspectFaces context
     */
    public void setVariablesToContext(HashMap<String, Object> variables);

    /**
     * This method set roles to AspectFaces context. Generated definition could be changed based on
     * roles.
     * 
     * @param roles which will be added to AspectFaces context
     */
    public void setRoles(String[] roles);

    /**
     * This method set profiles to AspectFaces context.
     * 
     * @param profiles array of profiles
     */
    public void setProfile(String[] profiles);

}
