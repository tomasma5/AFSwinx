package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.util;

/**
 * Global constants used by both processes - status mining and neraby devices finding
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class GlobalConstants {

    //hides constructor - this class should never be instantiated
    private GlobalConstants(){}

    public static final String APPLICATION_TAG = "NearbyStatusService";

    public static final String GOOGLE_FIT_TAG  = "Google_FIT_API_Service";

    public static final String VOLLEY_DSWN_REQUEST_TAG = "DeviceStatusWithNearbyJsonRequest";

}
