package com.tomscz.afclassshow.settings;

import java.util.HashMap;
import java.util.List;

import com.tomscz.afclassification.computational.scm.Scoring;
import com.tomscz.afclassification.model.Client;
import com.tomscz.afclassification.model.Device;
import com.tomscz.afclassification.model.Purpose;
import com.tomscz.afclassification.model.Severity;

public class FlightScoring implements Scoring {

	private HashMap<Severity, Double> rankedSeverity = new HashMap<Severity, Double>();

	private HashMap<Purpose, Double> rankedPurpose = new HashMap<Purpose, Double>();
	
	private HashMap<Device, Double> rankedDevice = new HashMap<Device, Double>();

	public FlightScoring() {
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
		// Rank device
		rankedDevice.put(Device.TABLET, 0D);
		rankedDevice.put(Device.PHONE, 20D);
		rankedDevice.put(Device.TABLET, 10D);
		rankedDevice.put(Device.OTHER, 5D);
	}

	@Override
	public Double scoreField(List<String> possibleValues, String actualValue,
			Purpose purpose, Severity severity, Client client) {
		throw new UnsupportedOperationException(
				"This opertaion is not supported");
	}

	@Override
	public Double scoreField(Purpose purpose, Severity severity, Client client) {
		Double severityValue = rankedSeverity.get(severity);
		Double purposeValue = rankedPurpose.get(purpose);
		if (severityValue == 100D && purposeValue == 100D) {
			return severityValue;
		}
		Double score = (severityValue * 0.8) + (purposeValue * 0.5);
		if(client != null && client.getDevice() != null){
			score = score - rankedDevice.get(client.getDevice());
		}
		return score;
	}

}
