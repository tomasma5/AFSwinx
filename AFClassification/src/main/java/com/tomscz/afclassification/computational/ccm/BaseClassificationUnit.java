package com.tomscz.afclassification.computational.ccm;

import java.util.List;

import com.tomscz.afclassification.model.Behavior;
import com.tomscz.afclassification.model.Configuration;

public class BaseClassificationUnit implements Classification {

	@Override
	public Behavior classify(Double score, List<Configuration> configurations) {
		return findBestConfiguration(score, configurations).getBehavior();
	}

	private Configuration findBestConfiguration(Double score,
			List<Configuration> configurations) {
		if (score > 100D) {
			score = 100D;
		}
		Configuration selectedConfiguration = configurations.get(0);
		for (Configuration config : configurations) {
			if (score >= config.getThresholdStart()
					&& score <= config.getThresholdEnd()) {
				selectedConfiguration = config;
				break;
			}
		}
		return selectedConfiguration;
	}

}
