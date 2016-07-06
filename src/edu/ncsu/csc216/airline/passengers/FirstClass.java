package edu.ncsu.csc216.airline.passengers;

import edu.ncsu.csc216.airline.airplane.Flight;
/**
 * FirstClass, Child class of flight Reservation.
 * 
 * @author mital
 *
 */
public class FirstClass extends FlightReservation {

	public FirstClass(String name, Flight myAirplane, boolean prefersWindow) {
		super(name, myAirplane, prefersWindow);

	}

	@Override
	public void findSeat() {

		mySeat = getVehicle().reserveFirstClassSeat(wantsWindowSeat());
		if (mySeat == null) {
			mySeat = getVehicle().reserveBusinessSeat(wantsWindowSeat());
			if (mySeat == null) {
				mySeat = getVehicle().reserveCoachSeat(wantsWindowSeat());
				if (mySeat == null) {
					mySeat = "none";
				}
			}
		}
	}

	public String stringForPrint() {

		String b = String.format("%-13s", "First Class");
		String a = b + super.stringForPrint();
		return a;
	}

}
