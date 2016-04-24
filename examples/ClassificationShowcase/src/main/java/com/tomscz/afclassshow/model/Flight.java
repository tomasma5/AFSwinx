package com.tomscz.afclassshow.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Flight {
	
	private Long id;
	
	private String flightCode;
	
	private Double price;
	
	private Airport source;
	
	private Airport destination;

	private int capacity;
	
	private Double distance;
	
	private Date start;
	
	private Date end;
	
	private List<Reservation> reservations;
	
	public synchronized  void addReservation(Reservation reservation){
		if(reservations == null){
			reservations = new ArrayList<Reservation>();
		}
		reservations.add(reservation);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Airport getSource() {
		return source;
	}

	public void setSource(Airport source) {
		this.source = source;
	}

	public Airport getDestination() {
		return destination;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
}
