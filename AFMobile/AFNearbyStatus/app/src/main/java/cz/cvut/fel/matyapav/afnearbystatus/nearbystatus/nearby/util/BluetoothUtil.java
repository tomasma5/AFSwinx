package cz.cvut.fel.matyapav.afnearbystatus.nearbystatus.nearby.util;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;

/**
 * Util class for bluetooth devices
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class BluetoothUtil {

    //hides constructor - this class should never be instantiated
    private BluetoothUtil() {}

    /**
     * Gets major device class as a string - the implementation must be done this way because {@link BluetoothClass.Device.Major} is not an enum
     * @param bluetoothDevice bluetooth device
     * @return major device class as a string
     */
    public static String getBluetoothMajorDeviceClass(BluetoothDevice bluetoothDevice) {
        int bluetoothDeviceTypeInteger = bluetoothDevice.getBluetoothClass().getMajorDeviceClass();
        switch (bluetoothDeviceTypeInteger) {
            case BluetoothClass.Device.Major.AUDIO_VIDEO:
                return "AUDIO-VIDEO";
            case BluetoothClass.Device.Major.COMPUTER:
                return "COMPUTER";
            case BluetoothClass.Device.Major.HEALTH:
                return "HEALTH";
            case BluetoothClass.Device.Major.IMAGING:
                return "IMAGING";
            case BluetoothClass.Device.Major.MISC:
                return "MISC";
            case BluetoothClass.Device.Major.NETWORKING:
                return "NETWORKING";
            case BluetoothClass.Device.Major.PERIPHERAL:
                return "PERIPHERAL";
            case BluetoothClass.Device.Major.PHONE:
                return "PHONE";
            case BluetoothClass.Device.Major.TOY:
                return "TOY";
            case BluetoothClass.Device.Major.UNCATEGORIZED:
                return "UNCATEGORIZED";
            case BluetoothClass.Device.Major.WEARABLE:
                return "WEARABLE";
            default:
                return null;
        }
    }

    /**
     * Gets device class as a string - the implementation must be done this way because {@link BluetoothClass.Device} is not an enum
     * @param bluetoothDevice bluetooth device
     * @return device class as a string
     */
    public static String getBluetoothDeviceClass(BluetoothDevice bluetoothDevice) {
        int bluetoothDeviceTypeInteger = bluetoothDevice.getBluetoothClass().getDeviceClass();
        switch (bluetoothDeviceTypeInteger) {
            case BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER:
                return "AUDIO_VIDEO_CAMCORDER";
            case BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO:
                return "AUDIO_VIDEO_CAR_AUDIO";
            case BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE:
                return "AUDIO_VIDEO_HANDSFREE";
            case BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES:
                return "AUDIO_VIDEO_HEADPHONES";
            case BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO:
                return "AUDIO_VIDEO_HIFI_AUDIO";
            case BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER:
                return "AUDIO_VIDEO_LOUDSPEAKER";
            case BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE:
                return "AUDIO_VIDEO_MICROPHONE";
            case BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO:
                return "AUDIO_VIDEO_PORTABLE_AUDIO";
            case BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX:
                return "AUDIO_VIDEO_SET_TOP_BOX";
            case BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED:
                return "AUDIO_VIDEO_UNCATEGORIZED";
            case BluetoothClass.Device.AUDIO_VIDEO_VCR:
                return "AUDIO_VIDEO_VCR";
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA:
                return "AUDIO_VIDEO_VIDEO_CAMERA";
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING:
                return "AUDIO_VIDEO_VIDEO_CONFERENCING";
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER:
                return "AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER";
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY:
                return "AUDIO_VIDEO_VIDEO_GAMING_TOY";
            case BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR:
                return "AUDIO_VIDEO_VIDEO_MONITOR";
            case BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET:
                return "AUDIO_VIDEO_WEARABLE_HEADSET";
            case BluetoothClass.Device.COMPUTER_DESKTOP:
                return "COMPUTER_DESKTOP";
            case BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA:
                return "COMPUTER_HANDHELD_PC_PDA";
            case BluetoothClass.Device.COMPUTER_LAPTOP:
                return "COMPUTER_LAPTOP";
            case BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA:
                return "COMPUTER_PALM_SIZE_PC_PDA";
            case BluetoothClass.Device.COMPUTER_SERVER:
                return "COMPUTER_SERVER";
            case BluetoothClass.Device.COMPUTER_UNCATEGORIZED:
                return "COMPUTER_UNCATEGORIZED";
            case BluetoothClass.Device.COMPUTER_WEARABLE:
                return "COMPUTER_WEARABLE";
            case BluetoothClass.Device.HEALTH_BLOOD_PRESSURE:
                return "HEALTH_BLOOD_PRESSURE";
            case BluetoothClass.Device.HEALTH_DATA_DISPLAY:
                return "HEALTH_DATA_DISPLAY";
            case BluetoothClass.Device.HEALTH_GLUCOSE:
                return "HEALTH_GLUCOSE";
            case BluetoothClass.Device.HEALTH_PULSE_OXIMETER:
                return "HEALTH_PULSE_OXIMETER";
            case BluetoothClass.Device.HEALTH_PULSE_RATE:
                return "HEALTH_PULSE_RATE";
            case BluetoothClass.Device.HEALTH_THERMOMETER:
                return "HEALTH_THERMOMETER";
            case BluetoothClass.Device.HEALTH_UNCATEGORIZED:
                return "HEALTH_UNCATEGORIZED";
            case BluetoothClass.Device.HEALTH_WEIGHING:
                return "HEALTH_WEIGHING";
            case BluetoothClass.Device.PHONE_CELLULAR:
                return "PHONE_CELLULAR";
            case BluetoothClass.Device.PHONE_CORDLESS:
                return "PHONE_CORDLESS";
            case BluetoothClass.Device.PHONE_ISDN:
                return "PHONE_ISDN";
            case BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY:
                return "PHONE_MODEM_OR_GATEWAY";
            case BluetoothClass.Device.PHONE_SMART:
                return "PHONE_SMART";
            case BluetoothClass.Device.PHONE_UNCATEGORIZED:
                return "PHONE_UNCATEGORIZED";
            case BluetoothClass.Device.TOY_CONTROLLER:
                return "TOY_CONTROLLER";
            case BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE:
                return "TOY_DOLL_ACTION_FIGURE";
            case BluetoothClass.Device.TOY_GAME:
                return "TOY_GAME";
            case BluetoothClass.Device.TOY_ROBOT:
                return "TOY_ROBOT";
            case BluetoothClass.Device.TOY_UNCATEGORIZED:
                return "TOY_UNCATEGORIZED";
            case BluetoothClass.Device.TOY_VEHICLE:
                return "TOY_VEHICLE";
            case BluetoothClass.Device.WEARABLE_GLASSES:
                return "WEARABLE_GLASSES";
            case BluetoothClass.Device.WEARABLE_HELMET:
                return "WEARABLE_HELMET";
            case BluetoothClass.Device.WEARABLE_JACKET:
                return "WEARABLE_JACKET";
            case BluetoothClass.Device.WEARABLE_PAGER:
                return "WEARABLE_PAGER";
            case BluetoothClass.Device.WEARABLE_UNCATEGORIZED:
                return "WEARABLE_UNCATEGORIZED";
            case BluetoothClass.Device.WEARABLE_WRIST_WATCH:
                return "WEARABLE_WRIST_WATCH";
            default:
                return null;
        }
    }

}
