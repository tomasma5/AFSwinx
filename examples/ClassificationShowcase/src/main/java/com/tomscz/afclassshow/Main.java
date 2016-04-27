package com.tomscz.afclassshow;

import com.tomscz.afclassification.computational.AFClassification;
import com.tomscz.afclassification.model.BCField;
import com.tomscz.afclassification.model.BCPhase;
import com.tomscz.afclassification.model.BusinessCase;
import com.tomscz.afclassification.model.GeneratedField;
import com.tomscz.afclassshow.settings.FlightScoring;

public class Main {

	public static void main(String[] args) {
		System.out.println("Creating business case");
		FlighShowcase flightShowcase = new FlighShowcase();
		System.out.println("Create AFClassification");
		AFClassification classification = new AFClassification(
				new FlightScoring());
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
							null, flightShowcase.getStrictConfiguration());
					System.out.println("The field :"
							+ field.getField().getFieldName()
							+ " has behavior: " + result.getBehavior());
				}
			}
		}
	}

}
