package rest.context;

import model.afclassification.Client;
import model.afclassification.ClientProperty;
import model.afclassification.Property;
import org.json.JSONObject;

public class JsonContextParser implements JSONParser {

    private static final String DEVICE_STATUS = "deviceStatus";
    private static final String DEVICE_INFO = "deviceInfo";
    private static final String BATTERY_INFO = "batteryStatus";
    private static final String LOCATION_INFO = "locationStatus";
    private static final String NETWORK_INFO = "networkStatus";
    private static final String WIFI_INFO = "wifiStatus";
    private static final String DEVICE_INFO_RESOLUTION = "resolution";
    private static final String DEVICE_INFO_DEVICE = "device";
    private static final String DEVICE_INFO_MAC_ADDRESS = "macAddress";
    private static final String DEVICE_INFO_MODEL = "model";
    private static final String DEVICE_INFO_PRODUCT = "product";
    private static final String DEVICE_INFO_API_LEVEL = "apiLevel";
    private static final String BATTERY_INFO_LEVEL = "batteryLevel";
    private static final String BATTERY_INFO_TEMPERATURE = "temperatureLevel";
    private static final String BATTERY_INFO_CHARGING = "charging";
    private static final String BATTERY_INFO_CHARGE_TYPE = "chargeType";
    private static final String LOCATION_INFO_LATITUDE = "latitude";
    private static final String LOCATION_INFO_LONGITUDE = "longitude";
    private static final String LOCATION_INFO_ALTITUDE = "altitude";
    private static final String LOCATION_INFO_SPEED = "speed";
    private static final String NETWORK_INFO_CONNECTED = "connected";
    private static final String NETWORK_INFO_TYPE_NAME = "networkTypeName";
    private static final String WIFI_INFO_BSSID = "bssid";
    private static final String WIFI_INFO_SSID = "ssid";
    private static final String WIFI_INFO_IP = "ipAddress";

    @Override
    public Client parse(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        Client client = new Client();
        JSONObject json = new JSONObject(jsonString);
        JSONObject deviceStatus = json.getJSONObject(DEVICE_STATUS);
        parseDeviceInfo(client, deviceStatus);
        parseBatteryInfo(client, deviceStatus);
        parseLocationInfo(client, deviceStatus);
        parseNetworkInfo(client, deviceStatus);
        return client;
    }

    private void parseDeviceInfo(Client client, JSONObject deviceStatus) {
        JSONObject deviceInfo = deviceStatus.optJSONObject(DEVICE_INFO);
        if (deviceInfo != null) {
            String macAddress = deviceInfo.optString(DEVICE_INFO_MAC_ADDRESS);
            client.setDeviceIdentifier(macAddress);

            String deviceName = deviceInfo.optString(DEVICE_INFO_DEVICE);
            String deviceModel = deviceInfo.optString(DEVICE_INFO_MODEL);
            String deviceProdcut = deviceInfo.optString(DEVICE_INFO_PRODUCT);
            int deviceApiLevel = deviceInfo.optInt(DEVICE_INFO_API_LEVEL);
            String deviceResolution = deviceInfo.optString(DEVICE_INFO_RESOLUTION);

            addPropertyToClient(client, Property.DEVICE_NAME, deviceName);
            addPropertyToClient(client, Property.DEVICE_MODEL, deviceModel);
            addPropertyToClient(client, Property.DEVICE_PRODUCT, deviceProdcut);
            addPropertyToClient(client, Property.DEVICE_API_LEVEL, String.valueOf(deviceApiLevel));
            addPropertyToClient(client, Property.DEVICE_RESOLUTION, deviceResolution);
        }
    }

    private void parseBatteryInfo(Client client, JSONObject deviceStatus) {
        JSONObject batteryInfo = deviceStatus.optJSONObject(BATTERY_INFO);
        if (batteryInfo != null) {
            int batteryLevel = deviceStatus.optInt(BATTERY_INFO_LEVEL);
            int temperature = deviceStatus.optInt(BATTERY_INFO_TEMPERATURE);
            boolean charging = deviceStatus.optBoolean(BATTERY_INFO_CHARGING);
            String chargeType = deviceStatus.optString(BATTERY_INFO_CHARGE_TYPE);

            addPropertyToClient(client, Property.BATTERY_CAPACITY, String.valueOf(batteryLevel));
            addPropertyToClient(client, Property.BATTERY_TEMPERATURE, String.valueOf(temperature));
            addPropertyToClient(client, Property.BATTERY_CHARGING, String.valueOf(charging));
            addPropertyToClient(client, Property.BATTERY_CHARGING_TYPE, chargeType);
        }
    }

    private void parseLocationInfo(Client client, JSONObject deviceStatus) {
        JSONObject locationInfo = deviceStatus.optJSONObject(LOCATION_INFO);
        if (locationInfo != null) {
            double latitude = deviceStatus.optDouble(LOCATION_INFO_LATITUDE);
            double longitude = deviceStatus.optDouble(LOCATION_INFO_LONGITUDE);
            double altitude = deviceStatus.optDouble(LOCATION_INFO_ALTITUDE);
            double speed = deviceStatus.optDouble(LOCATION_INFO_SPEED);

            addPropertyToClient(client, Property.LOCATION_LATITUDE, String.valueOf(latitude));
            addPropertyToClient(client, Property.LOCATION_LONGITUDE, String.valueOf(longitude));
            addPropertyToClient(client, Property.LOCATION_ALTITUDE, String.valueOf(altitude));
            addPropertyToClient(client, Property.LOCATION_SPEED, String.valueOf(speed));
        }
    }

    private void parseNetworkInfo(Client client, JSONObject deviceStatus) {
        JSONObject networkInfo = deviceStatus.optJSONObject(NETWORK_INFO);
        if (networkInfo != null) {

            boolean connected = networkInfo.optBoolean(NETWORK_INFO_CONNECTED);
            String networkTypeName = networkInfo.optString(NETWORK_INFO_TYPE_NAME);
            addPropertyToClient(client, Property.CONNECTION_CONNECTED, String.valueOf(connected));
            addPropertyToClient(client, Property.CONNECTION_TYPE, networkTypeName);
            parseWifiInfo(client, networkInfo);

        }
    }

    private void parseWifiInfo(Client client, JSONObject networkInfo) {
        JSONObject wifiInfo = networkInfo.optJSONObject(WIFI_INFO);
        if (wifiInfo != null) {
            String bssid = wifiInfo.optString(WIFI_INFO_BSSID);
            String ssid = wifiInfo.optString(WIFI_INFO_SSID);
            String ipAddress = wifiInfo.optString(WIFI_INFO_IP);
            addPropertyToClient(client, Property.CONNECTION_WIFI_BSSID, bssid);
            addPropertyToClient(client, Property.CONNECTION_WIFI_SSID, ssid);
            addPropertyToClient(client, Property.CONNECTION_WIFI_IP_ADDRESS, ipAddress);
        }
    }

    private void addPropertyToClient(Client client, Property property, String value) {
        if (value != null && !value.isEmpty()) {
            client.addProperty(new ClientProperty(property, value));
        }
    }
}
