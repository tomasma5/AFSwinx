package com.tomscz.afclassification.computational;

import java.util.List;

import com.tomscz.afclassification.computational.ccm.Classification;
import com.tomscz.afclassification.computational.ccm.BaseClassificationUnit;
import com.tomscz.afclassification.computational.scm.BaseScoringUnit;
import com.tomscz.afclassification.computational.scm.Scoring;
import com.tomscz.afclassification.model.BCField;
import com.tomscz.afclassification.model.Behavior;
import com.tomscz.afclassification.model.Client;
import com.tomscz.afclassification.model.Configuration;
import com.tomscz.afclassification.model.GeneratedField;

public class AFClassification {

	private Scoring scoringModule;

	private Classification classificationModule;

	public AFClassification(Scoring scoringModule,
			Classification classificationModule) {
		this.scoringModule = scoringModule;
		this.classificationModule = classificationModule;
	}

	public AFClassification(Scoring scoringModule) {
		this.scoringModule = scoringModule;
		this.classificationModule = new BaseClassificationUnit();
	}
	
	public AFClassification(Classification classificationModule) {
		this.scoringModule = new BaseScoringUnit();
		this.classificationModule = classificationModule;
	}
	
	public AFClassification() {
		this.scoringModule = new BaseScoringUnit();
		this.classificationModule = new BaseClassificationUnit();
	}

	public GeneratedField classifyField(BCField field, Client client, List<Configuration> configuration) {
		Double score = scoringModule.scoreField(field.getFieldSpecification()
				.getPurpose(), field.getFieldSpecification().getSeverity(),
				client);
		Behavior behavior = classificationModule.classify(score, configuration);
		GeneratedField generatedField = new GeneratedField();
		generatedField.setBehavior(behavior);
		return generatedField;
	}

}
