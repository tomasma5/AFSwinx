package service.afclassification.computational.scm.units;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Application;
import model.afclassification.*;
import utils.HttpUtils;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;


public class NearbyDevicesScoringUnit implements Scoring {

    private HashMap<Severity, Double> rankedSeverity = new HashMap<>();

    private HashMap<Purpose, Double> rankedPurpose = new HashMap<>();

    private HashMap<NearbyDeviceSetupSimilarity, Double> rankedNearbyDeviceSetup = new HashMap<>();

    private List<NearbyDevice> pastNearbyDevices;

    private String pastUser;

    public NearbyDevicesScoringUnit(Client client, Application application) {
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
        //Rank nearby device setup
        rankedNearbyDeviceSetup.put(NearbyDeviceSetupSimilarity.SAME, 35D);
        rankedNearbyDeviceSetup.put(NearbyDeviceSetupSimilarity.MOSTLY_SIMILAR, 25D);
        rankedNearbyDeviceSetup.put(NearbyDeviceSetupSimilarity.MORE_SIMILAR, 15D);
        rankedNearbyDeviceSetup.put(NearbyDeviceSetupSimilarity.MORE_DIFFERENT, 5D);
        rankedNearbyDeviceSetup.put(NearbyDeviceSetupSimilarity.MOSTLY_DIFFERENT, -5D);
        rankedNearbyDeviceSetup.put(NearbyDeviceSetupSimilarity.DIFFERENT, -15D);

        try {
            String endpoint = getEndpointUrlForLastRecord(client);
            if (pastNearbyDevices == null) {
                String lastNearbyDevicesString = HttpUtils.getRequest(getUrlForLastNearbyDevices(application, endpoint), null);
                Gson gson = new Gson();
                pastNearbyDevices = gson.fromJson(lastNearbyDevicesString, new TypeToken<List<NearbyDevice>>() {
                }.getType());
            }
            if (pastUser == null) {
                pastUser = HttpUtils.getRequest(getUrlForLastUser(application, endpoint), null);
            }
        } catch (IOException e) {
            System.err.println("[Classification]Cannot get data from server ");
            e.printStackTrace();
        }
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
            Double score = (severityValue * 0.8) + (purposeValue * 0.6);
            System.out.println("[Classification][NearbyDevicesScoringUnit] START score for purpose " + purpose + " and severity " + severity + " is " + score);
            if ((purpose == Purpose.INFORMATION_MINING || purpose == Purpose.SYSTEM_INFORMATION) && pastNearbyDevices != null && !pastUser.isEmpty()) {
                double similarity = getNearbyDeviceSetupSimilarity(client);
                NearbyDeviceSetupSimilarity nearbyDeviceSetupSimilarity = NearbyDeviceSetupSimilarity.getEnumBySimilarityValue(similarity);
                if (nearbyDeviceSetupSimilarity != null) {
                    System.out.println("[Classification][NearbyDevicesScoringUnit] It is considered to be " + nearbyDeviceSetupSimilarity +
                            " setup. Similarity was from " + nearbyDeviceSetupSimilarity.getThresholdFrom() + " to "
                            + nearbyDeviceSetupSimilarity.getThresholdTo());
                    score -= rankedNearbyDeviceSetup.get(nearbyDeviceSetupSimilarity);
                }
            }
            if (score > 100D) {
                score = 100D;
            }
            if (score < 0D) {
                score = 0D;
            }
            System.out.println("[Classification][NearbyDevicesScoringUnit] FINAL score for purpose " + purpose + " and severity " + severity + " is " + score);
            return score;

        }
        return null;
    }


    private double getNearbyDeviceSetupSimilarity(Client client) {
        if (pastUser == null || !pastUser.equals(client.getUsername()) ) {
            //different user - check information again
            System.err.println("[Classification][NearbyDevicesScoringUnit] Cannot compare context data for different user");
            return 0D;
        }
        if(client.getNearbyDevices() == null) {
            //no nearby devices found .. setup is different or we cannot decide
            return 0D;
        }

        double devicesFound = 0;
        System.out.println(client.toString());
        for (NearbyDevice currentDevice : client.getNearbyDevices()) {
            for (NearbyDevice pastDevice : pastNearbyDevices) {
                if (currentDevice.getMacAddress().equals(pastDevice.getMacAddress())) {
                    devicesFound++;
                    break;
                }
            }
        }
        double size = (client.getNearbyDevices().size() > pastNearbyDevices.size() ? client.getNearbyDevices().size() : pastNearbyDevices.size());
        double result = (devicesFound / size) * 100D;
        System.out.println("[Classification][NearbyDevicesScoringUnit] New context data has " + result + " % of last context data nearby devices ");
        return result;
    }

    private String getEndpointUrlForLastRecord(Client client) {
        return "/api/data/device/" + client.getDeviceIdentifier() +
                "/action/" + client.getAction()
                + "/lastData";
    }

    private String getUrlForLastNearbyDevices(Application application, String endpoint) {
        return HttpUtils.buildConsumerEndpointUrl(application, endpoint + "/nearbydevices");
    }

    private String getUrlForLastUser(Application application, String endpoint) {
        return HttpUtils.buildConsumerEndpointUrl(application, endpoint + "/user");
    }

}
