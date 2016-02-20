package cz.cvut.fel.matyapav.afandroid.rest;

import java.util.List;

import cz.cvut.fel.matyapav.afandroid.rest.holder.AFDataHolder;
import cz.cvut.fel.matyapav.afandroid.rest.holder.AFDataPack;


/**
 * This class is XMLBuilder it will build XML which will be used to send to server.
 * @author Martin Tomasek (martin@toms-cz.com)
 *
 * @since 1.0.0.
 */
public class XMLBuilder extends BaseRestBuilder{

    @Override
    public Object reselialize(AFDataHolder componentData) {
        throw new UnsupportedOperationException("Not supported in this version!");
    }

    @Override
    public List<AFDataPack> serialize(Object componentData) {
        throw new UnsupportedOperationException("Not supported in this version!");
    }

}
