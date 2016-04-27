package com.tomscz.afclassshow;

import java.util.ArrayList;
import java.util.List;

import com.tomscz.afclassification.model.BCField;
import com.tomscz.afclassification.model.BCFieldSeverity;
import com.tomscz.afclassification.model.BCPhase;
import com.tomscz.afclassification.model.Behavior;
import com.tomscz.afclassification.model.BusinessCase;
import com.tomscz.afclassification.model.Configuration;
import com.tomscz.afclassification.model.Field;
import com.tomscz.afclassification.model.Purpose;
import com.tomscz.afclassification.model.Severity;

public class FlighShowcase {

	private List<BusinessCase> businessCases;

	private static final String FLIGHT_CLASS = "Flight";

	private static final String RESERVATION_CLASS = "Reservation";

	private static final String USER_CLASS = "User";

	private static final String AIRPORT_CLASS = "Airport";

	public FlighShowcase() {
		businessCases = new ArrayList<>();
		buildInstance();
	}

	private void buildInstance() {
		// Create business case
		BusinessCase orderTicket = new BusinessCase("Ticket order",
				"Users wants to find and book flight");
		BCPhase searchPhase = new BCPhase();
		BCPhase reserveTicketPhase = new BCPhase();
		reserveTicketPhase.setBusinessCase(orderTicket, "Reserve ticket");
		searchPhase.setBusinessCase(orderTicket, "Search ticket");
		orderTicket.addPhase(searchPhase);
		orderTicket.addPhase(reserveTicketPhase);
		// Create fields for search
		BCField source = createField(createFlightField("source", "airport"),
				searchPhase, "flight.airport", Severity.CRITICAL,
				Purpose.SYSTEM_IDENTIFICATION);
		BCField destination = createField(
				createFlightField("destination", "airport"), searchPhase,
				"flight.airport", Severity.CRITICAL,
				Purpose.SYSTEM_IDENTIFICATION);
		BCField flightStart = createField(createFlightField("start", "date"),
				searchPhase, "flight", Severity.REQUIRED,
				Purpose.SYSTEM_IDENTIFICATION);
		BCField flightEnd = createField(createFlightField("end", "date"),
				searchPhase, "flight", Severity.NEEDED,
				Purpose.SYSTEM_INFORMATION);
		BCField flightPrice = createField(createFlightField("price", "double"),
				searchPhase, "flight", Severity.NEEDED,
				Purpose.SYSTEM_INFORMATION);
		BCField flightRank = createField(createFlightField("rank", "double"),
				searchPhase, "flight", Severity.NICE_TO_HAVE,
				Purpose.FUTURE_INTERACTION);
		// Add them to phase
		searchPhase.addBCField(source);
		searchPhase.addBCField(destination);
		searchPhase.addBCField(flightStart);
		searchPhase.addBCField(flightEnd);
		searchPhase.addBCField(flightPrice);
		searchPhase.addBCField(flightRank);
		//Create reserve phase
		BCField clientName = createField(createUserField("name", "string"),
				reserveTicketPhase, "user", Severity.NEEDED,
				Purpose.SYSTEM_INFORMATION);
		BCField clientSurname = createField(
				createUserField("surname", "string"), reserveTicketPhase,
				"usert", Severity.NEEDED,
				Purpose.SYSTEM_INFORMATION);
		BCField clientPhone = createField(
				createUserField("phoneNumber", "string"), reserveTicketPhase,
				"usert", Severity.CRITICAL,
				Purpose.SYSTEM_IDENTIFICATION);
		BCField clientEmail = createField(
				createUserField("email", "string"), reserveTicketPhase,
				"usert", Severity.CRITICAL,
				Purpose.SYSTEM_IDENTIFICATION);
		BCField clientStreet = createField(
				createUserField("street", "string"), reserveTicketPhase,
				"usert", Severity.NICE_TO_HAVE,
				Purpose.FUTURE_INTERACTION);
		BCField clientCity = createField(
				createUserField("city", "string"), reserveTicketPhase,
				"usert", Severity.NICE_TO_HAVE,
				Purpose.SYSTEM_INFORMATION);
		BCField numberOfPassangers = createField(
				createReservationField("numberOfPassangers", "int"), reserveTicketPhase,
				"user.reservation", Severity.CRITICAL,
				Purpose.SYSTEM_IDENTIFICATION);
		//add them to phase
		reserveTicketPhase.addBCField(clientName);
		reserveTicketPhase.addBCField(clientSurname);
		reserveTicketPhase.addBCField(clientPhone);
		reserveTicketPhase.addBCField(clientEmail);
		reserveTicketPhase.addBCField(clientStreet);
		reserveTicketPhase.addBCField(clientCity);
		reserveTicketPhase.addBCField(numberOfPassangers);
		// Add business case to set
		businessCases.add(orderTicket);

	}

	public List<Configuration> getStrictConfiguration() {
		List<Configuration> configurations = new ArrayList<Configuration>();
		Configuration requiredConfiguration = new Configuration(
				Behavior.REQUIRED, 70D, 100D);
		Configuration validationConfiguration = new Configuration(
				Behavior.VALIDATION, 40D, 70D);
		Configuration onlyDisplayConfiguration = new Configuration(Behavior.ONLY_DISPLAY,
				0D, 40D);
		Configuration hiddenConfiguration = new Configuration(Behavior.HIDDEN,
				0D, 0D);
		Configuration notPresentConfiguration = new Configuration(
				Behavior.NOT_PRESENT, -10D, -10D);

		configurations.add(requiredConfiguration);
		configurations.add(validationConfiguration);
		configurations.add(hiddenConfiguration);
		configurations.add(notPresentConfiguration);
		configurations.add(onlyDisplayConfiguration);
		return configurations;
	}

	public List<Configuration> getBeneConfiguration() {
		List<Configuration> configurations = new ArrayList<Configuration>();
		Configuration requiredConfiguration = new Configuration(
				Behavior.REQUIRED, 90D, 100D);
		Configuration validationConfiguration = new Configuration(
				Behavior.VALIDATION, 60D, 90D);
		Configuration onlyDisplayConfiguration = new Configuration(Behavior.ONLY_DISPLAY,
				40D, 60D);
		Configuration hiddenConfiguration = new Configuration(Behavior.HIDDEN,
				10D, 40D);
		Configuration notPresentConfiguration = new Configuration(
				Behavior.NOT_PRESENT, 0D, 10D);

		configurations.add(requiredConfiguration);
		configurations.add(validationConfiguration);
		configurations.add(hiddenConfiguration);
		configurations.add(notPresentConfiguration);
		configurations.add(onlyDisplayConfiguration);
		return configurations;
	}

	public List<BusinessCase> getBusinessCases() {
		return businessCases;
	}

	private BCField createField(Field field, BCPhase phase, String classUri,
			Severity severity, Purpose purpose) {
		BCField bcField = new BCField(field, phase, classUri);
		BCFieldSeverity fieldSeverity = new BCFieldSeverity();
		fieldSeverity.setField(bcField);
		fieldSeverity.setPurpose(purpose);
		fieldSeverity.setSeverity(severity);
		bcField.setFieldSpecification(fieldSeverity);
		return bcField;
	}

	private Field createFlightField(String fieldName, String type) {
		Field flight = new Field();
		flight.setClassName(FLIGHT_CLASS);
		flight.setFieldName(fieldName);
		flight.setType(type);
		return flight;
	}
	
	private Field createUserField(String fieldName, String type) {
		Field flight = new Field();
		flight.setClassName(USER_CLASS);
		flight.setFieldName(fieldName);
		flight.setType(type);
		return flight;
	}
	
	private Field createReservationField(String fieldName, String type) {
		Field flight = new Field();
		flight.setClassName(RESERVATION_CLASS);
		flight.setFieldName(fieldName);
		flight.setType(type);
		return flight;
	}

}
