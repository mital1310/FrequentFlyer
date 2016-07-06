package edu.ncsu.csc216.airline.passengers;

import java.util.ArrayList;

import edu.ncsu.csc216.airline.airplane.Flight;

public class Test {

	private static Manifest manifest = new Manifest();
	private static Flight flight = new Flight("tiny-plane.txt");
	private static FlightReservation r = new FirstClass("Bruce Lee", flight,
			true);

	private static ArrayList<FlightReservation> passengerList = new ArrayList<FlightReservation>();

	public static void main(String[] args) {

		r.findSeat();
		System.out.println(r.mySeat);
		manifest.add(r);

		System.out.println(manifest.report());
	}

}
