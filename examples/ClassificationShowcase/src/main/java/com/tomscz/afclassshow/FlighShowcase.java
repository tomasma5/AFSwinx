package com.tomscz.afclassshow;

import java.util.ArrayList;
import java.util.List;

import com.tomscz.afclassification.model.BCPhase;
import com.tomscz.afclassification.model.BusinessCase;

public class FlighShowcase {

	private List<BusinessCase> businessCases;

	public FlighShowcase() {
		businessCases = new ArrayList<>();
	}

	private void buildInstance() {
		BusinessCase orderTicket = new BusinessCase("Ticket order",
				"Users wants to find and book flight");
		BCPhase searchPhase = new BCPhase();
		searchPhase.setBusinessCase(orderTicket);
		orderTicket.addPhase(searchPhase);
	}

}
