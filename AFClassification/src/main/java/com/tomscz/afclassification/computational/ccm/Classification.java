package com.tomscz.afclassification.computational.ccm;

import java.util.List;

import com.tomscz.afclassification.model.Behavior;
import com.tomscz.afclassification.model.Configuration;

public interface Classification {

	public Behavior classify(Double score, List<Configuration> configurations);
}
