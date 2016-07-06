package edu.ncsu.csc216.airline.airplane;

/**
 * Behaviors required for an entity that manages seats (such as an airplane or </br>
 *    theater for example). Seats can be classified as First Class, Business, or Coach.
 *    Each seat has a unique label.
 * @author Jo Perry
 * @version 1.0
 */
public interface SeatManager {
	
	/**
	 * Reserve a seat in the Business Class section, applying the seat</br>
	 *    preference if possible. The chosen seat is marked occupied.
	 * @param preference indicates seating preference
	 * @return the reserved seat or null if no such seat is available
	 */
	public String reserveBusinessSeat(boolean prefersWindow);
	
	/**
	 * Reserve a seat in the First Class section, applying the seat</br>
	 *    preference if possible. The chosen seat is marked occupied.
	 * @param preference indicates seating preference
	 * @return the reserved seat or null if no such seat is available
	 */
	public String reserveFirstClassSeat(boolean prefersWindow);
	
	/**
	 * Reserve a seat in the Coach class, starting at the most recently </br>
	 *   assigned row in Coach and wrapping around to the start of Coach class if </br>
	 *   none are available. Apply the seat preference if possible. The chosen seat is </br>
	 *   marked occupied.
	 * @param preference indicates seating preference
	 * @return the reserved seat or null if no such seat is available
	 */
	public String reserveCoachSeat(boolean prefersWindow);
	
	/**
	 * Get a seat map, where each seat is represented by its location </br>
	 *    (row and seat label) or by null if there is no seat at the location. </br>
	 *    The chosen seat is marked occupied.
	 * @return the seat map.
	 */
	public String[][] getSeatMap();
	
	/**
	 * Get a map showing which seats are occupied. </br>
	 *   A map element is true if its row,col correspond to an occupied seat </br>
	 *   or false if the seat is not occupied or if there is no seat.
	 * @return the seat map
	 */
	public boolean[][] getSeatOccupationMap(); 


}