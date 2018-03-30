package rest.context;

import model.afclassification.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static rest.context.JsonContextParserConstants.*;

/**
 * Json parser - parse context information from NSRest API to {@link Client} model
 */
public class JsonContextParser extends JSONParser {

    @Override
    public Client parse(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        Client client = new Client();
        JSONObject json = new JSONObject(jsonString);
        JSONObject deviceStatus = json.getJSONObject(DEVICE_STATUS);
        JSONArray nearbyDevices = json.getJSONArray(NEARBY_DEVICE);
        parseDeviceInfo(client, deviceStatus);
        parseBatteryInfo(client, deviceStatus);
        parseLocationInfo(client, deviceStatus);
        parseNetworkInfo(client, deviceStatus);
        parseNearbyDevices(client, nearbyDevices);
        return client;
    }

    /**
     * Parses nearby devices information
     * @param client client model to parse into
     * @param nearbyDevices json array with nearby devices information
     */
    private void parseNearbyDevices(Client client, JSONArray nearbyDevices) {
        for (int i = 0; i < nearbyDevices.length(); i++) {
            NearbyDevice nearbyDeviceObject = new NearbyDevice();

            JSONObject nearbyDevice = nearbyDevices.getJSONObject(i);
            String name = optString(nearbyDevice, NEARBY_DEVICE_NAME);
            String macAddress = optString(nearbyDevice, NEARBY_DEVICE_MAC_ADDRESS);
            String macVendor = optString(nearbyDevice, NEARBY_DEVICE_MAC_VENDOR);
            String deviceType = optString(nearbyDevice, NEARBY_DEVICE_TYPE);
            JSONObject nearbyDeviceAdditionalInformations = nearbyDevice.optJSONObject(NEARBY_DEVICE_ADDITIONAL_INFO);
            Map<String, String> additionalInfoMap = null;
            if (nearbyDeviceAdditionalInformations != null) {
                additionalInfoMap = new HashMap<>();
                for (String key : nearbyDeviceAdditionalInformations.keySet()) {
                    String value = optString(nearbyDeviceAdditionalInformations, key);
                    additionalInfoMap.put(key, value);
                }
            }
            nearbyDeviceObject.setName(name);
            nearbyDeviceObject.setMacAddress(macAddress);
            nearbyDeviceObject.setMacVendor(macVendor);
            nearbyDeviceObject.setDeviceType(NearbyDeviceType.valueOf(deviceType));
            if (additionalInfoMap != null) {
                nearbyDeviceObject.setAdditionalInformations(additionalInfoMap);
            }
            client.addNearbyDevice(nearbyDeviceObject);
        }
    }

    /**
     * Parses device info into client model
     * @param client client model to parse into
     * @param deviceStatus Json object with device status
     */
    private void parseDeviceInfo(Client client, JSONObject deviceStatus) {
        JSONObject deviceInfo = deviceStatus.optJSONObject(DEVICE_INFO);
        if (deviceInfo != null) {
            String macAddress = optString(deviceInfo, DEVICE_INFO_MAC_ADDRESS);
            client.setDeviceIdentifier(macAddress);

            String deviceName = optString(deviceInfo, DEVICE_INFO_DEVICE);
            String deviceModel = optString(deviceInfo, DEVICE_INFO_MODEL);
            String deviceProduct = optString(deviceInfo, DEVICE_INFO_PRODUCT);
            int deviceApiLevel = deviceInfo.optInt(DEVICE_INFO_API_LEVEL);
            JSONObject deviceResolution = deviceInfo.optJSONObject(DEVICE_INFO_RESOLUTION);


            addPropertyToClient(client, Property.DEVICE_NAME, deviceName);
            addPropertyToClient(client, Property.DEVICE_MODEL, deviceModel);
            addPropertyToClient(client, Property.DEVICE_PRODUCT, deviceProduct);
            addPropertyToClient(client, Property.DEVICE_API_LEVEL, String.valueOf(deviceApiLevel));

            if (deviceResolution != null) {
                int widthInPixels = deviceResolution.optInt(DEVICE_INFO_RESOLUTION_WIDTH);
                int heightInPixels = deviceResolution.optInt(DEVICE_INFO_RESOLUTION_HEIGHT);
                double inches = deviceResolution.optDouble(DEVICE_INFO_RESOLUTION_INCHES);
                addPropertyToClient(client, Property.DEVICE_RESOLUTION_WIDTH, String.valueOf(widthInPixels));
                addPropertyToClient(client, Property.DEVICE_RESOLUTION_HEIGHT, String.valueOf(heightInPixels));
                addPropertyToClient(client, Property.DEVICE_RESOLUTION_INCHES, String.valueOf(inches));
            }
        }
    }

    /**
     * Parses information about battery into client model
     * @param client client model to parse into
     * @param deviceStatus json object with device status
     */
    private void parseBatteryInfo(Client client, JSONObject deviceStatus) {
        JSONObject batteryInfo = deviceStatus.optJSONObject(BATTERY_INFO);
        if (batteryInfo != null) {
            int batteryLevel = batteryInfo.optInt(BATTERY_INFO_LEVEL);
            int temperature = batteryInfo.optInt(BATTERY_INFO_TEMPERATURE);
            boolean charging = batteryInfo.optBoolean(BATTERY_INFO_CHARGING);
            String chargeType = optString(batteryInfo, BATTERY_INFO_CHARGE_TYPE);

            addPropertyToClient(client, Property.BATTERY_CAPACITY, String.valueOf(batteryLevel));
            addPropertyToClient(client, Property.BATTERY_TEMPERATURE, String.valueOf(temperature));
            addPropertyToClient(client, Property.BATTERY_CHARGING, String.valueOf(charging));
            addPropertyToClient(client, Property.BATTERY_CHARGING_TYPE, chargeType);
        }
    }

    /**
     * Parses information about device location into client model
     * @param client client model to parse into
     * @param deviceStatus json object wih device status
     */
    private void parseLocationInfo(Client client, JSONObject deviceStatus) {
        JSONObject locationInfo = deviceStatus.optJSONObject(LOCATION_INFO);
        if (locationInfo != null) {
            double latitude = locationInfo.optDouble(LOCATION_INFO_LATITUDE);
            double longitude = locationInfo.optDouble(LOCATION_INFO_LONGITUDE);
            double altitude = locationInfo.optDouble(LOCATION_INFO_ALTITUDE);
            double speed = locationInfo.optDouble(LOCATION_INFO_SPEED);

            addPropertyToClient(client, Property.LOCATION_LATITUDE, String.valueOf(latitude));
            addPropertyToClient(client, Property.LOCATION_LONGITUDE, String.valueOf(longitude));
            addPropertyToClient(client, Property.LOCATION_ALTITUDE, String.valueOf(altitude));
            addPropertyToClient(client, Property.LOCATION_SPEED, String.valueOf(speed));
        }
    }

    /**
     * Parses information about device network into client model
     * @param client client model to parse into
     * @param deviceStatus json object with device status
     */
    private void parseNetworkInfo(Client client, JSONObject deviceStatus) {
        JSONObject networkInfo = deviceStatus.optJSONObject(NETWORK_INFO);
        if (networkInfo != null) {
            boolean connected = networkInfo.optBoolean(NETWORK_INFO_CONNECTED);
            String networkTypeName = optString(networkInfo, NETWORK_INFO_TYPE_NAME);
            addPropertyToClient(client, Property.CONNECTION_CONNECTED, String.valueOf(connected));
            addPropertyToClient(client, Property.CONNECTION_TYPE, networkTypeName);
            parseWifiInfo(client, networkInfo);
        }
    }

    /**
     * Parses information about wifi into client model
     * @param client client model to parse inforamtion into
     * @param networkInfo json object with information about network
     */
    private void parseWifiInfo(Client client, JSONObject networkInfo) {
        JSONObject wifiInfo = networkInfo.optJSONObject(WIFI_INFO);
        if (wifiInfo != null) {
            String bssid = optString(wifiInfo, WIFI_INFO_BSSID);
            String ssid = optString(wifiInfo, WIFI_INFO_SSID);
            String ipAddress = optString(wifiInfo, WIFI_INFO_IP);
            addPropertyToClient(client, Property.CONNECTION_WIFI_BSSID, bssid);
            addPropertyToClient(client, Property.CONNECTION_WIFI_SSID, ssid);
            addPropertyToClient(client, Property.CONNECTION_WIFI_IP_ADDRESS, ipAddress);
        }
    }

    /**
     * Adds property into client. Checks value for null or empty value.
     * @param client client model to parse inforamtion into
     * @param property property key
     * @param value value to be added
     */
    private void addPropertyToClient(Client client, Property property, String value) {
        if (value != null && !value.isEmpty()) {
            client.addProperty(new ClientProperty(property, value));
        }
    }
}
