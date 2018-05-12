package service.afclassification.computational.ccm.units;

import model.afclassification.Behavior;
import model.afclassification.ConfigurationPack;

public class RequiredClassificationUnit implements Classification {

	@Override
	public Behavior classify(Double score, ConfigurationPack configurations) {
		return Behavior.REQUIRED;
	}

}
