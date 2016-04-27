package com.tomscz.afclassification.computational.scm;

import java.util.List;

import com.tomscz.afclassification.model.Client;
import com.tomscz.afclassification.model.Purpose;
import com.tomscz.afclassification.model.Severity;

public interface Scoring {

	public Double scoreField(List<String> possibleValues, String actualValue,
			Purpose purpose, Severity severity, Client client);

	public Double scoreField(Purpose purpose, Severity severity, Client client);

}
