package rest.context;

/**
 * Contants used in {@link JsonContextParser}
 */
public class JsonContextParserConstants {

    JsonContextParserConstants() {
        //disable instantiation
    }

    static final String DEVICE_STATUS = "deviceStatus";
    static final String DEVICE_INFO = "deviceInfo";
    static final String BATTERY_INFO = "batteryStatus";
    static final String LOCATION_INFO = "locationStatus";
    static final String NETWORK_INFO = "networkStatus";
    static final String WIFI_INFO = "wifiStatus";
    static final String DEVICE_INFO_RESOLUTION = "resolution";
    static final String DEVICE_INFO_DEVICE = "device";
    static final String DEVICE_INFO_MAC_ADDRESS = "macAddress";
    static final String DEVICE_INFO_MODEL = "model";
    static final String DEVICE_INFO_PRODUCT = "product";
    static final String DEVICE_INFO_API_LEVEL = "apiLevel";
    static final String BATTERY_INFO_LEVEL = "batteryLevel";
    static final String BATTERY_INFO_TEMPERATURE = "temperatureLevel";
    static final String BATTERY_INFO_CHARGING = "charging";
    static final String BATTERY_INFO_CHARGE_TYPE = "chargeType";
    static final String LOCATION_INFO_LATITUDE = "latitude";
    static final String LOCATION_INFO_LONGITUDE = "longitude";
    static final String LOCATION_INFO_ALTITUDE = "altitude";
    static final String LOCATION_INFO_SPEED = "speed";
    static final String NETWORK_INFO_CONNECTED = "connected";
    static final String NETWORK_INFO_TYPE_NAME = "networkTypeName";
    static final String WIFI_INFO_BSSID = "bssid";
    static final String WIFI_INFO_SSID = "ssid";
    static final String WIFI_INFO_IP = "ipAddress";
    static final String DEVICE_INFO_RESOLUTION_WIDTH = "widthInPixels";
    static final String DEVICE_INFO_RESOLUTION_HEIGHT = "heightInPixels";
    static final String DEVICE_INFO_RESOLUTION_INCHES = "inches";

    static final String NEARBY_DEVICE = "nearbyDevices";
    static final String NEARBY_DEVICE_NAME = "name";
    static final String NEARBY_DEVICE_MAC_ADDRESS = "macAddress";
    static final String NEARBY_DEVICE_MAC_VENDOR = "macVendor";
    static final String NEARBY_DEVICE_TYPE = "deviceType";
    static final String NEARBY_DEVICE_ADDITIONAL_INFO = "additionalInformations";
}
