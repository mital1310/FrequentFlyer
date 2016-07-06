package edu.ncsu.csc216.airline.airplane;

/**
 * Seat Class represents seat for the airplane.
 * 
 * @author mital
 * 
 */
public class Seat {

	private String location; // Location of Seat
	private boolean occupied; // to check if seat is occupied
	private boolean windowSeat; // to check if seat is window
	private boolean aisleSeat; // to check if seat is aisle

	/**
	 * Seat constructor
	 * 
	 * @param location
	 * @param windowSeat
	 * @param aisleSeat
	 */

	public Seat(String location, boolean windowSeat, boolean aisleSeat) {
		this.location = location;
		this.windowSeat = windowSeat;
		this.aisleSeat = aisleSeat;
	}

	/**
	 * checks if seat is occupied
	 * 
	 * @return true if seat is occupied
	 */
	public boolean isOccupied() {
		if (occupied == true) {
			return true;
		}
		return false;
	}

	/**
	 * checks if seat is window
	 * 
	 * @return true if seat is window
	 */
	public boolean isWindowSeat() {
		if (windowSeat == true) {
			return true;
		}
		return false;
	}

	/**
	 * checks if seat is aisle
	 * 
	 * @return true if seat is aisle
	 */
	public boolean isAisleSeat() {
		if (aisleSeat == true) {
			return true;
		}
		return false;
	}

	/**
	 * Makes seat occupied
	 */
	public void occupy() {
		this.occupied = true;
	}

	/**
	 * Makes seat unoccupied
	 */
	public void clear() {
		this.occupied = false;
	}

	/**
	 * location of the Seat
	 * 
	 * @return seat location
	 */
	public String getLocation() {
		return location;
	}

}
