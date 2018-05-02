package service.afclassification.computational.scm.units;

import model.Application;
import model.afclassification.*;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


public class BatteryConnectionScoringUnit implements Scoring {

    private HashMap<Severity, Double> rankedSeverity = new HashMap<>();

    private HashMap<Purpose, Double> rankedPurpose = new HashMap<>();

    private HashMap<String, Double> rankedConnectionType = new HashMap<>();

    public BatteryConnectionScoringUnit() {
        // Rank severity
        rankedSeverity.put(Severity.CRITICAL, 100D);
        rankedSeverity.put(Severity.REQUIRED, 80D);
        rankedSeverity.put(Severity.NEEDED, 60D);
        rankedSeverity.put(Severity.NICE_TO_HAVE, 50D);
        // Rank purpose
        rankedPurpose.put(Purpose.SYSTEM_IDENTIFICATION, 100D);
        rankedPurpose.put(Purpose.SYSTEM_INFORMATION, 40D);
        rankedPurpose.put(Purpose.FUTURE_INTERACTION, 30D);
        rankedPurpose.put(Purpose.INFORMATION_MINING, 10D);
        // Rank connection type
        rankedConnectionType.put("WIFI", 5D);
        rankedConnectionType.put("MOBILE", -5D);
    }

    @Override
    public Double scoreField(List<String> possibleValues, String actualValue,
                             Purpose purpose, Severity severity, Client client) {
        throw new UnsupportedOperationException("This operation is not supported yet!");
    }

    @Override
    public Double scoreField(Purpose purpose, Severity severity, Client client, Application application) {
        Double severityValue = rankedSeverity.get(severity);
        Double purposeValue = rankedPurpose.get(purpose);
        if (severityValue == 100D && purposeValue == 100D) {
            return severityValue;
        }
        if (client != null && (client.getDevice().equals(Device.PHONE) || client.getDevice().equals(Device.TABLET))) {
            Double score = (severityValue * 0.7) + (purposeValue * 0.5);
            System.out.println("[BatteryConnectionScoringUnit] START score for purpose " + purpose + " and severity " + severity + " is " + score);
            if (purpose == Purpose.INFORMATION_MINING && (severity == Severity.NICE_TO_HAVE || severity == Severity.NEEDED)) {
                score -= getScoreChangeByBatteryLevel(client);
                score += getScoreChangeByConnectionType(client);
            }
            if (score > 100D) {
                score = 100D;
            }
            if (score < 0D) {
                score = 0D;
            }
            System.out.println("[BatteryConnectionScoringUnit] FINAL score for purpose " + purpose + " and severity " + severity + " is " + score);
            return score;
        }
        return null;
    }

    private double getScoreChangeByConnectionType(Client client) {
        double result = 0D;
        for (ClientProperty property : client.getClientProperties()) {
            if (property.getProperty() == Property.CONNECTION_TYPE) {
                result = rankedConnectionType.get(property.getValue());
                break;
            }
        }
        System.out.println("[BatteryConnectionScoringUnit] Score changed because of connection type by " + result);
        return result;
    }

    private double getScoreChangeByBatteryLevel(Client client) {
        double result = 0;
        if (client.getClientProperties() != null) {
            String batteryCapacityStr = null;
            String chargingStr = null;
            for (ClientProperty property : client.getClientProperties()) {
                if (property.getProperty() == Property.BATTERY_CAPACITY) {
                    batteryCapacityStr = property.getValue();
                }
                if (property.getProperty() == Property.BATTERY_CHARGING) {
                    chargingStr = property.getValue();
                }
            }
            if (batteryCapacityStr != null && chargingStr != null) {
                double batteryCapacity = Integer.parseInt(batteryCapacityStr);
                boolean charging = Boolean.valueOf(chargingStr);
                System.out.println("[BatteryConnectionScoringUnit] Battery has capacity of " + batteryCapacity + " and " + (charging? "is charging" : "is not charging"));
                if (!charging) {
                    result = (100D - batteryCapacity) * 0.4;
                }
            }
        }
        System.out.println("[BatteryConnectionScoringUnit] Score changed because of battery level by " + result);
        return result;
    }

}
