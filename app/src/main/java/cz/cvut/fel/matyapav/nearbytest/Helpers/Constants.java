package cz.cvut.fel.matyapav.nearbytest.Helpers;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class Constants {

    private Constants(){}

    public static final String APPLICATION_TAG = "NearbyTestService";
    //permissions
    public static final int ACCESS_COARSE_LOCATION_PERMISSION_REQUEST = 1;

    //others
    public static final String EMPTY_MAC_ADDRESS = "00:00:00:00:00:00";
    static final String MAC_REGULAR_EXPRESSION = "^%s\\s+0x1\\s+0x2\\s+([:0-9a-fA-F]+)\\s+\\*\\s+\\w+$";
}
