package cz.cvut.fel.matyapav.nearbytest.nearbystatus.devicestatus.model.partial;

/**
 * Battery Status model - keeps information about battery
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0..
 */
public class BatteryStatus {

    private int batteryLevel;
    private int voltageLevel;
    private int temperatureLevel;
    private boolean charging;
    private BatteryChargeType chargeType;

    public BatteryStatus() {
    }

    public int getVoltageLevel() {
        return voltageLevel;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public void setVoltageLevel(int voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public int getTemperatureLevel() {
        return temperatureLevel;
    }

    public void setTemperatureLevel(int temperatureLevel) {
        this.temperatureLevel = temperatureLevel;
    }

    public boolean isCharging() {
        return charging;
    }

    public void setCharging(boolean charging) {
        this.charging = charging;
    }

    public BatteryChargeType getChargeType() {
        return chargeType;
    }

    public void setChargeType(BatteryChargeType chargeType) {
        this.chargeType = chargeType;
    }
}
