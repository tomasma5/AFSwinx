package com.tomscz.afclassification.model;

public class Configuration {
	
	private Behavior behavior;
	
	private Double thresholdStart;
	
	private Double thresholdEnd;

	public Configuration(){
		
	}
	
	public Configuration(Behavior behavior, Double thresholdStart, Double thresholdEnd){
		this.behavior = behavior;
		this.thresholdStart = thresholdStart;
		this.thresholdEnd = thresholdEnd;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	public Double getThresholdStart() {
		return thresholdStart;
	}

	public void setThresholdStart(Double thresholdStart) {
		this.thresholdStart = thresholdStart;
	}

	public Double getThresholdEnd() {
		return thresholdEnd;
	}

	public void setThresholdEnd(Double thresholdEnd) {
		this.thresholdEnd = thresholdEnd;
	}

}
