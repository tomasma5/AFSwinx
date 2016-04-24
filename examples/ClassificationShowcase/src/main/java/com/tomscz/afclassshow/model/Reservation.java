package com.tomscz.afclassshow.model;

import java.util.Date;

public class Reservation {

	private Long reservationId;
	
	private User reservationUser;
	
	private Flight flight;
	
	private int numberOfPassangers;
	
	private Date reservationDate;
	
	private String state;

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public User getReservationUser() {
		return reservationUser;
	}

	public void setReservationUser(User reservationUser) {
		this.reservationUser = reservationUser;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public int getNumberOfPassangers() {
		return numberOfPassangers;
	}

	public void setNumberOfPassangers(int numberOfPassangers) {
		this.numberOfPassangers = numberOfPassangers;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
