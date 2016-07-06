package edu.ncsu.csc216.airline.passengers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Manifest {

	private ArrayList<FlightReservation> passengerList;
	private FlightReservation reservation;

	public Manifest() {
		passengerList = new ArrayList<FlightReservation>();
	}

	public void add(FlightReservation reservation) {

		passengerList.add(reservation);
		Collections.sort(passengerList);

	}

	public void removePassenger(int index) {
		if (passengerList.isEmpty() || index < 0
				|| index > passengerList.size()) {
			throw new IllegalArgumentException();
		}
		passengerList.get(index).cancelReservation();
		passengerList.remove(index);

	}

	public String report() {
		String s = new String();
		for (FlightReservation p : passengerList) {
			s += p.stringForPrint() + "\n";
		}

		return s;
	}
}
