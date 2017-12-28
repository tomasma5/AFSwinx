package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.miner;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.DeviceStatus;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.BatteryChargeType;
import cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial.BatteryStatus;

import static cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.util.DeviceStatusConstants.BATTERY_PROPERTY_UNKNOWN;

/**
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */

public class BatteryStatusMiner extends AbstractStatusMiner {


    @Override
    public void mineAndFillStatus(DeviceStatus deviceStatus){
        BatteryStatus batteryStatus = new BatteryStatus();
        Intent batteryStatusIntent = getActivity().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        //get battery levels
        batteryStatus.setBatteryLevel(extractBatteryLevel(batteryStatusIntent));
        batteryStatus.setTemperatureLevel(extractTemperature(batteryStatusIntent));
        batteryStatus.setVoltageLevel(extractBatteryVoltage(batteryStatusIntent));
        //are we charging? and if yes, how are we charging?
        boolean charging = isBatteryCharging(batteryStatusIntent);
        batteryStatus.setCharging(charging);
        if(charging) {
            batteryStatus.setChargeType(extractChargeType(batteryStatusIntent));
        }
        deviceStatus.setBatteryStatus(batteryStatus);
    }

    private boolean isBatteryCharging (Intent i) {
        int status = i.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
    }


    private int extractBatteryLevel(Intent i){
        return i.getIntExtra(BatteryManager.EXTRA_LEVEL, BATTERY_PROPERTY_UNKNOWN);
    }

    private int extractBatteryVoltage(Intent i){
        return i.getIntExtra(BatteryManager.EXTRA_VOLTAGE, BATTERY_PROPERTY_UNKNOWN);
    }

    private int extractTemperature(Intent i){
        return i.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, BATTERY_PROPERTY_UNKNOWN);
    }

    private BatteryChargeType extractChargeType(Intent i) {
        int chargePlug = i.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        switch (chargePlug) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                return BatteryChargeType.AC;
            case BatteryManager.BATTERY_PLUGGED_USB:
                return BatteryChargeType.USB;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                return BatteryChargeType.WIRELESS;
            default:
                return BatteryChargeType.UNKNOWN;
        }
    }
}
