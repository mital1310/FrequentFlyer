package edu.ncsu.csc216.airline.passengers;

import edu.ncsu.csc216.airline.airplane.Flight;

/**
 * Abstract class Flight Reservation serves as the reservation system for
 * passengers.
 * 
 * @author mital
 * 
 */
public abstract class FlightReservation implements
		Comparable<FlightReservation> {

	private String name; // name of the passenger
	protected String mySeat; // Seat location
	private boolean prefersWindow; // Passenger preference
	private Flight myAirplane; // Instance of Flight

	/**
	 * FlightReservation constructor
	 * 
	 * @param name
	 * @param myAirplane
	 * @param prefersWindow
	 */

	public FlightReservation(String name, Flight myAirplane,
			boolean prefersWindow) {

		if ((name == null) || (name.trim() == "") || (name.trim().isEmpty())) {
			throw new IllegalArgumentException();
		}

		if (myAirplane == null) {
			throw new IllegalArgumentException();
		}

		this.name = name.trim();
		this.myAirplane = myAirplane;
		this.prefersWindow = prefersWindow;

	}

	/**
	 * 
	 * @return name of the passenger
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return Seat location
	 */
	public String getSeat() {
		return mySeat;
	}

	/**
	 * 
	 * @return Flight used to create reservations
	 */
	public Flight getVehicle() {
		return myAirplane;
	}

	/**
	 * 
	 * @return true if passenger prefers Window Seat
	 */
	public boolean wantsWindowSeat() {
		if (prefersWindow == true) {
			return true;
		}
		return false;
	}

	/**
	 * Abstract method implemented by child class to find the seat at specific
	 * class section and according to preference
	 */

	public abstract void findSeat();

	/**
	 * 
	 * @return String to be displayed on reservation list
	 */

	public String stringForPrint() {
		String s = String.format("%4s" + "  " + name, mySeat);
		return s;
	}

	/**
	 * Compares 2 names for reservation(Existing and to be added) ignoring case
	 * difference
	 */
	public int compareTo(FlightReservation r) {
		int i = this.name.compareToIgnoreCase(r.getName());
		return i;
	}

	/**
	 * deletes the reservation
	 */
	public void cancelReservation() {
		myAirplane.freeSeat(mySeat);
	}

}
