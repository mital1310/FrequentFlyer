package edu.ncsu.csc216.airline.passengers;

import edu.ncsu.csc216.airline.airplane.Flight;

public class Business extends FlightReservation {
	/**
	 * Business, Child class of flight Reservation.
	 * 
	 * @author mital
	 *
	 */

	public Business(String name, Flight myAirplane, boolean prefersWindow) {
		super(name, myAirplane, prefersWindow);
	}

	@Override
	public void findSeat() {
		mySeat = (getVehicle().reserveBusinessSeat(wantsWindowSeat()));
		if (mySeat == null) {
			mySeat = getVehicle().reserveCoachSeat(wantsWindowSeat());
			if (mySeat == null) {
				mySeat = "none";
			}
		}
	}

	public String stringForPrint() {

		String b = String.format("%-13s", "Business");
		String a = b + super.stringForPrint();
		return a;
	}

}
