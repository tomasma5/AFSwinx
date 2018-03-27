package service.afclassification.computational.ccm;

import model.afclassification.Behavior;
import model.afclassification.Configuration;
import model.afclassification.ConfigurationPack;

public class BaseClassificationUnit implements Classification {

	@Override
	public Behavior classify(Double score, ConfigurationPack configurations) {
		return findBestConfiguration(score, configurations).getBehavior();
	}

	private Configuration findBestConfiguration(Double score, ConfigurationPack configurations) {
		Configuration selectedConfiguration = configurations.getConfigurations().get(0);
		for (Configuration config : configurations.getConfigurations()) {
			if (score >= config.getThresholdStart() && score <= config.getThresholdEnd()) {
				selectedConfiguration = config;
				break;
			}
		}
		return selectedConfiguration;
	}

}
