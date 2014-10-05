package com.tomscz.afswinx.common;

import java.util.EnumSet;

import com.tomscz.afswinx.exception.MetamodelException;

public class Utils {
    
    public static <E extends Enum<E>> Enum<E> getEnumFromString(Class<E> clazz, String s)throws MetamodelException{
        if(s == null || s.isEmpty()){
            throw new MetamodelException();
        }
        for(E en : EnumSet.allOf(clazz)){
            if(en.name().equalsIgnoreCase(s)){
              return en;
            }
          }
          throw new MetamodelException();
    }

}
