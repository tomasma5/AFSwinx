package service.afclassification.computational.ccm;

import model.afclassification.Behavior;
import model.afclassification.ConfigurationPack;

/**
 * Classification interface. Classification is used for give fields Behaviour based on its score and configurations.
 */
public interface Classification {

	/**
	 * Returns Behavior for given score and configurations
	 * @param score the score
	 * @param configurations the configurations
	 * @return selected behaviour
	 */
	public Behavior classify(Double score, ConfigurationPack configurations);
}
