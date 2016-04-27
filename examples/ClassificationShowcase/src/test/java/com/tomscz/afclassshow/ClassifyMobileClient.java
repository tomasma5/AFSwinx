package com.tomscz.afclassshow;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.tomscz.afclassification.computational.AFClassification;
import com.tomscz.afclassification.model.BCField;
import com.tomscz.afclassification.model.BCPhase;
import com.tomscz.afclassification.model.BusinessCase;
import com.tomscz.afclassification.model.Client;
import com.tomscz.afclassification.model.ClientProperty;
import com.tomscz.afclassification.model.Configuration;
import com.tomscz.afclassification.model.Device;
import com.tomscz.afclassification.model.GeneratedField;
import com.tomscz.afclassification.model.Property;
import com.tomscz.afclassshow.settings.FlightScoring;

public class ClassifyMobileClient {

	private FlighShowcase flightShowcase;
	private AFClassification classification;
	private Client client;
	private List<Configuration> configurations;

	@Before
	public void prepareTest(){
		flightShowcase = new FlighShowcase();
		System.out.println("Setting up ClassifyMobileClient");
		classification = new AFClassification(new FlightScoring());
		configurations = flightShowcase.getBeneConfiguration();
		client = new Client();
		client.setDevice(Device.PHONE);
		ClientProperty connectionProperty = new ClientProperty();
		connectionProperty.setProperty(Property.CONNECTION_TYPE);
		connectionProperty.setValue(FlightScoring.CONNECTION_DATA);
		client.addProperty(connectionProperty);
	}
	
	@Test
	public void test() {
		for (BusinessCase businessCase : flightShowcase.getBusinessCases()) {
			System.out.println("Going to classify business case: "
					+ businessCase.getName());
			for (BCPhase phase : businessCase.getPhases()) {
				System.out.println("Classifing business phase: "
						+ phase.getName());
				for (BCField field : phase.getFields()) {
					System.out.println("Classifing field: "
							+ field.getField().getFieldName());
					GeneratedField result = classification.classifyField(field,
							client, configurations);
					System.out.println("The field :"
							+ field.getField().getFieldName()
							+ " has behavior: " + result.getBehavior());
				}
				System.out.println();
				System.out.println();
			}
		}
	}
}
