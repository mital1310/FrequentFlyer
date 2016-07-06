package edu.ncsu.csc216.airline.passengers;

import edu.ncsu.csc216.airline.airplane.Flight;

public class Coach extends FlightReservation {
	/**
	 * Coach, Child class of flight Reservation.
	 * 
	 * @author mital
	 *
	 */
	public Coach(String name, Flight myAirplane, boolean prefersWindow) {
		super(name, myAirplane, prefersWindow);
	}
	
	/**
	 * Finds seat in Coach Class if the capacity hasn't reached
	 */
	@Override
	public void findSeat() {
		if (getVehicle().coachAtCap() == false) {
			mySeat = getVehicle().reserveCoachSeat(wantsWindowSeat());
			if (mySeat == null) {
				mySeat = "none";
			}
		} else
			mySeat = "none";
	}
	
	/**
	 * String to display on reservation list.
	 */
	public String stringForPrint() {
		String b = String.format("%-13s", "Coach");
		String a = b + super.stringForPrint();
		return a;
	}

}
