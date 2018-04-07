package service.afclassification.computational.scm.units;

import model.Application;
import model.afclassification.Client;
import model.afclassification.Purpose;
import model.afclassification.Severity;

import java.util.List;


public class BaseScoringUnit implements Scoring {

	@Override
	public Double scoreField(List<String> possibleValues, String actualValue,
							 Purpose purpose, Severity severity, Client client) {
		throw new UnsupportedOperationException("This operation is not supported yet!");
	}

	@Override
	public Double scoreField(Purpose purpose, Severity severity, Client client, Application application) {
		if(purpose.equals(Purpose.SYSTEM_IDENTIFICATION) && severity.equals(Severity.CRITICAL)){
			return 100D;
		}
		return 80D;
	}

}
