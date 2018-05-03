package service.afclassification.computational.ccm.units;

import model.afclassification.Behavior;
import model.afclassification.Configuration;
import model.afclassification.ConfigurationPack;

import java.util.logging.Logger;

public class BaseClassificationUnit implements Classification {

	private final static Logger LOGGER = Logger.getLogger(BaseClassificationUnit.class.getName());

	@Override
	public Behavior classify(Double score, ConfigurationPack configurations) {
		return findBestConfiguration(score, configurations).getBehavior();
	}

	private Configuration findBestConfiguration(Double score, ConfigurationPack configurations) {
		Configuration selectedConfiguration = configurations.getConfigurations().get(0);
		for (Configuration config : configurations.getConfigurations()) {
			if (score >= config.getThresholdStart() && score <= config.getThresholdEnd()) {
				selectedConfiguration = config;
				System.out.println("[Classification][BaseClassificationUnit] Behavior " + selectedConfiguration.getBehavior() +
						" was chosen! Score was "+ score + " and config thresholds were from " + config.getThresholdStart() + "" +
						" to " + config.getThresholdEnd());
				break;
			}
		}
		return selectedConfiguration;
	}

}
