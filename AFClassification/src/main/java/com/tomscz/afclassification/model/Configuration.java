package com.tomscz.afclassification.model;

public class Configuration {
	
	private Behavior behavior;
	
	private Double threshold;

	public Configuration(){
		
	}
	
	public Configuration(Behavior behavior, Double threshold){
		this.behavior = behavior;
		this.threshold = threshold;
	}
	
	public Double getThreshold() {
		return threshold;
	}

	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

}
