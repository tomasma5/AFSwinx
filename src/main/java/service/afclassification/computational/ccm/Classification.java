package service.afclassification.computational.ccm;


import model.afclassification.Behavior;
import model.afclassification.ConfigurationPack;

public interface Classification {

	public Behavior classify(Double score, ConfigurationPack configurations);
}
