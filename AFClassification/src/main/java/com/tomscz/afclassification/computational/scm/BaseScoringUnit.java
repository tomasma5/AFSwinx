package com.tomscz.afclassification.computational.scm;

import java.util.List;

import com.tomscz.afclassification.model.Client;
import com.tomscz.afclassification.model.Purpose;
import com.tomscz.afclassification.model.Severity;

public class BaseScoringUnit implements Scoring{

	@Override
	public Double scoreField(List<String> possibleValues, String actualValue,
			Purpose purpose, Severity severity, Client client) {
		throw new UnsupportedOperationException("This operation is not supported yet!");
	}

	@Override
	public Double scoreField(Purpose purpose, Severity severity, Client client) {
		if(purpose.equals(Purpose.SYSTEM_IDENTIFICATION) && severity.equals(Severity.CRITICAL)){
			return 100D;
		}
		return 80D;
	}

}
