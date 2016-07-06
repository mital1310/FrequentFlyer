package edu.ncsu.csc216.airline.airplane;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Flight Class implements seat Manager It represents the collection of seats
 * and handles the reservations.
 * 
 * @author mital
 * 
 */
public class Flight implements SeatManager {

	private static final int COACH_CAP = 80; // capacity of coach
	private String seatLabels; // Alphabets for seats
	private int numRows; // number of Rows
	private int numColumns; // number of Columns
	private int startFirstClass; // First row for First Class
	private int startBusiness; // First row for Business Class
	private int startCoach; // First row for Coach Class
	private int mostRecentCoachRow; // Last row of added seat
	private int coachSeatsReserved; // number of coach seats reserved
	private int coachCapacity; // Capacity of coach
	private Seat[][] map; // Array of seats
	private Scanner readFile; // Scanner to read File
	private String[][] stringMap; // Array of String representing labels
	private boolean[][] seatOccupationMap; // Array returning true if seat's
											// occupied
	private int firstRowBegins; // Row number for first class
	private int businessRowBegins; // Row number for business class
	private int coachRowBegins; // Row number for coach class

	/**
	 * Creates a seating Map
	 * 
	 * @param fileName
	 *            Throws IllegalArgumentException if file is missing or not
	 *            named properly
	 */
	public Flight(String fileName) {
		try {
			readFile = new Scanner(new File("aa-777.txt"));

			readFromFile(readFile);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Finds a business seat according to preference. returns Seat label for the
	 * assigned seat.
	 */
	@Override
	public String reserveBusinessSeat(boolean prefersWindow) {

		String s = null;
		String a = Integer.toString(startCoach);
		String b = Integer.toString(startBusiness);

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].getLocation()
							.substring(0, map[i][j].getLocation().length() - 1)
							.equals(a)) {
						coachRowBegins = i;
					}
				}
				if (map[i][j] != null) {
					if (map[i][j].getLocation()
							.substring(0, map[i][j].getLocation().length() - 1)
							.equals(b)) {
						businessRowBegins = i;
					}
				}
			}
		}
		int windowCounter = 0;
		for (int i = businessRowBegins; i < coachRowBegins; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].isWindowSeat() == true) {
						windowCounter++;
					}
				}

			}
		}

		int aisleCounter = 0;
		for (int i = businessRowBegins; i < coachRowBegins; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].isAisleSeat() == true) {
						aisleCounter++;
					}
				}

			}
		}

		if (prefersWindow == true) {

			for (int i = businessRowBegins; i < coachRowBegins; i++) {
				for (int j = 0; j < numColumns; j++) {
					if (map[i][j] != null) {
						if (map[i][j].isWindowSeat() == true) {
							if (map[i][j].isOccupied() == false) {
								map[i][j].occupy();
								s = map[i][j].getLocation();
								return s;
							}
						}
					}
				}
			}
			int windowSeatOccupiedCounter = 0;
			for (int i = businessRowBegins; i < coachRowBegins; i++) {
				for (int j = 0; j < numColumns; j++) {
					if (map[i][j] != null) {
						if (map[i][j].isWindowSeat() == true
								&& map[i][j].isOccupied() == true) {
							windowSeatOccupiedCounter++;
						}
					}
				}
			}
			if (windowSeatOccupiedCounter == windowCounter) {
				for (int i = businessRowBegins; i < coachRowBegins; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isOccupied() == false) {
								map[i][j].occupy();
								s = map[i][j].getLocation();
								return s;
							}

						}
					}
				}
			}
		}

		else if (prefersWindow == false) {
			for (int i = businessRowBegins; i < coachRowBegins; i++) {
				for (int j = 0; j < numColumns; j++) {
					if (map[i][j] != null) {
						if (map[i][j].isAisleSeat() == true) {
							if (map[i][j].isOccupied() == false) {
								map[i][j].occupy();
								s = map[i][j].getLocation();
								return s;
							}

						}

					}
				}
			}
			int aisleSeatOccupiedCounter = 0;
			for (int i = businessRowBegins; i < coachRowBegins; i++) {
				for (int j = 0; j < numColumns; j++) {
					if (map[i][j] != null) {
						if (map[i][j].isAisleSeat() == true
								&& map[i][j].isOccupied() == true) {
							aisleSeatOccupiedCounter++;
						}
					}
				}
			}
			if (aisleSeatOccupiedCounter == aisleCounter) {
				for (int i = businessRowBegins; i < coachRowBegins; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isOccupied() == false) {
								map[i][j].occupy();
								s = map[i][j].getLocation();
								return s;
							}

						}
					}
				}
			}
		}
		return s;

	}

	/**
	 * Finds a First Class seat according to preference. returns Seat label for
	 * the assigned seat.
	 */
	@Override
	public String reserveFirstClassSeat(boolean prefersWindow) {

		String s = null;
		String a = Integer.toString(startFirstClass);
		String b = Integer.toString(startBusiness);

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].getLocation()
							.substring(0, map[i][j].getLocation().length() - 1)
							.equals(a)) {
						firstRowBegins = i;
					}
					if (map[i][j].getLocation().startsWith(b)) {
						businessRowBegins = i;
					}
				}
			}
		}
		int windowCounter = 0;
		for (int i = firstRowBegins; i < businessRowBegins; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].isWindowSeat() == true) {
						windowCounter++;
					}
				}

			}
		}

		int aisleCounter = 0;
		for (int i = firstRowBegins; i < businessRowBegins; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].isAisleSeat() == true) {
						aisleCounter++;
					}
				}

			}
		}

		if (prefersWindow == true) {
			for (int i = firstRowBegins; i < businessRowBegins; i++) {
				for (int j = 0; j < numColumns; j++) {
					if (map[i][j] != null) {
						if (map[i][j].isWindowSeat() == true) {
							if (map[i][j].isOccupied() == false) {
								map[i][j].occupy();
								s = map[i][j].getLocation();
								return s;
							}
						}
					}
				}
			}
			int windowSeatOccupiedCounter = 0;
			for (int i = firstRowBegins; i < businessRowBegins; i++) {
				for (int j = 0; j < numColumns; j++) {
					if (map[i][j] != null) {
						if (map[i][j].isWindowSeat() == true
								&& map[i][j].isOccupied() == true) {
							windowSeatOccupiedCounter++;
						}
					}
				}
			}
			if (windowSeatOccupiedCounter == windowCounter) {
				for (int i = firstRowBegins; i < businessRowBegins; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isOccupied() == false) {
								map[i][j].occupy();
								s = map[i][j].getLocation();
								return s;
							}

						}
					}
				}
			}
		}

		else if (prefersWindow == false) {
			for (int i = firstRowBegins; i < businessRowBegins; i++) {
				for (int j = 0; j < numColumns; j++) {
					if (map[i][j] != null) {
						if (map[i][j].isAisleSeat() == true) {
							if (map[i][j].isOccupied() == false) {
								map[i][j].occupy();
								s = map[i][j].getLocation();
								return s;
							}

						}

					}
				}
			}
			int aisleSeatOccupiedCounter = 0;
			for (int i = firstRowBegins; i < businessRowBegins; i++) {
				for (int j = 0; j < numColumns; j++) {
					if (map[i][j] != null) {
						if (map[i][j].isAisleSeat() == true
								&& map[i][j].isOccupied() == true) {
							aisleSeatOccupiedCounter++;
						}
					}
				}
			}
			if (aisleSeatOccupiedCounter == aisleCounter) {
				for (int i = firstRowBegins; i < businessRowBegins; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isOccupied() == false) {
								map[i][j].occupy();
								s = map[i][j].getLocation();
								return s;
							}

						}
					}
				}
			}
		}
		return s;
	}

	/**
	 * Finds a Coach seat according to preference. returns Seat label for the
	 * assigned seat.
	 */

	@Override
	public String reserveCoachSeat(boolean prefersWindow) {
		String s = null;
		boolean allSeatsEmpty = false;
		String a = Integer.toString(startCoach);

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].getLocation()
							.substring(0, map[i][j].getLocation().length() - 1)
							.equals(a)) {
						coachRowBegins = i;
					}
				}
			}
		}

		int windowCounter = 0;
		for (int i = coachRowBegins; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].isWindowSeat() == true) {
						windowCounter++;
					}
				}

			}
		}

		int aisleCounter = 0;
		for (int i = coachRowBegins; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].isAisleSeat() == true) {
						aisleCounter++;
					}
				}

			}
		}

		int numberOfCoachSeats = 0;

		for (int i = coachRowBegins; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					numberOfCoachSeats++;
				}
			}
		}

		int numberOfCoachSeatsOccupied = 0;

		for (int i = coachRowBegins; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null && map[i][j].isOccupied() == false) {
					numberOfCoachSeatsOccupied++;
				}
			}
		}

		if (numberOfCoachSeats == numberOfCoachSeatsOccupied) {
			allSeatsEmpty = true;
		}

		for (int i = coachRowBegins; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null && map[i][j].isOccupied() == true) {
					mostRecentCoachRow = i;
				}
			}
		}

		if (allSeatsEmpty == true) {

			if (prefersWindow == true) {

				for (int i = coachRowBegins; i < numRows; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isWindowSeat() == true) {
								if (map[i][j].isOccupied() == false) {
									map[i][j].occupy();
									s = map[i][j].getLocation();
									return s;
								}
							}
						}
					}
				}
				int windowSeatOccupiedCounter = 0;
				for (int i = coachRowBegins; i < numRows; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isWindowSeat() == true
									&& map[i][j].isOccupied() == true) {
								windowSeatOccupiedCounter++;
							}
						}
					}
				}
				if (windowSeatOccupiedCounter == windowCounter) {
					for (int i = coachRowBegins; i < numRows; i++) {
						for (int j = 0; j < numColumns; j++) {
							if (map[i][j] != null) {
								if (map[i][j].isOccupied() == false) {
									map[i][j].occupy();
									s = map[i][j].getLocation();
									return s;
								}

							}
						}
					}
				}
			}

			else if (prefersWindow == false) {
				for (int i = coachRowBegins; i < numRows; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isAisleSeat() == true) {
								if (map[i][j].isOccupied() == false) {
									map[i][j].occupy();
									s = map[i][j].getLocation();
									return s;
								}

							}

						}
					}
				}
				int aisleSeatOccupiedCounter = 0;
				for (int i = coachRowBegins; i < numRows; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isAisleSeat() == true
									&& map[i][j].isOccupied() == true) {
								aisleSeatOccupiedCounter++;
							}
						}
					}
				}
				if (aisleSeatOccupiedCounter == aisleCounter) {
					for (int i = coachRowBegins; i < numRows; i++) {
						for (int j = 0; j < numColumns; j++) {
							if (map[i][j] != null) {
								if (map[i][j].isOccupied() == false) {
									map[i][j].occupy();
									s = map[i][j].getLocation();
									return s;
								}

							}
						}
					}
				}
			}
		}

		else if (allSeatsEmpty == false) {

			if (prefersWindow == true) {

				for (int i = mostRecentCoachRow; i < numRows; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isWindowSeat() == true) {
								if (map[i][j].isOccupied() == false) {
									map[i][j].occupy();
									s = map[i][j].getLocation();
									return s;
								}
							}
						}
					}
				}
				int windowSeatOccupiedCounter = 0;
				for (int i = mostRecentCoachRow; i < numRows; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isWindowSeat() == true
									&& map[i][j].isOccupied() == true) {
								windowSeatOccupiedCounter++;
							}
						}
					}
				}
				if (windowSeatOccupiedCounter == windowCounter) {
					for (int i = mostRecentCoachRow; i < numRows; i++) {
						for (int j = 0; j < numColumns; j++) {
							if (map[i][j] != null) {
								if (map[i][j].isOccupied() == false) {
									map[i][j].occupy();
									s = map[i][j].getLocation();
									return s;
								}

							}
						}
					}
				}
			}

			else if (prefersWindow == false) {
				for (int i = mostRecentCoachRow; i < numRows; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isAisleSeat() == true) {
								if (map[i][j].isOccupied() == false) {
									map[i][j].occupy();
									s = map[i][j].getLocation();
									return s;
								}

							}

						}
					}
				}
				int aisleSeatOccupiedCounter = 0;
				for (int i = mostRecentCoachRow; i < numRows; i++) {
					for (int j = 0; j < numColumns; j++) {
						if (map[i][j] != null) {
							if (map[i][j].isAisleSeat() == true
									&& map[i][j].isOccupied() == true) {
								aisleSeatOccupiedCounter++;
							}
						}
					}
				}
				if (aisleSeatOccupiedCounter == aisleCounter) {
					for (int i = mostRecentCoachRow; i < numRows; i++) {
						for (int j = 0; j < numColumns; j++) {
							if (map[i][j] != null) {
								if (map[i][j].isOccupied() == false) {
									map[i][j].occupy();
									s = map[i][j].getLocation();
									return s;
								}

							}
						}
					}
				}
			}
		}
		return s;

	}

	/**
	 * Returns String 2d array of Seat Labels
	 */
	public String[][] getSeatMap() {

		return stringMap;

	}

	/**
	 * Returns String 2d array of boolean according to occupation of seat
	 */

	@Override
	public boolean[][] getSeatOccupationMap() {
		seatOccupationMap = new boolean[numRows][numColumns];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {

				if (map[i][j] == null || map[i][j].isOccupied() == false) {
					seatOccupationMap[i][j] = false;
				}
				if (map[i][j] != null && map[i][j].isOccupied() == true) {
					seatOccupationMap[i][j] = true;
				}

			}
		}
		return seatOccupationMap;
	}

	/**
	 * Checks coach capacity
	 * 
	 * @return true if the coach capacity is reached
	 */
	public boolean coachAtCap() {
		int coachSeats = 0;
		for (int i = coachRowBegins; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					coachSeats++;
				}
			}
		}
		if (!(coachSeats == 0)) {
			coachCapacity = (getNumInCoach() / coachSeats) * 100;
			if (coachCapacity > COACH_CAP) {
				return true;
			}
			return false;
		}
		return false;

	}

	/**
	 * Makes a seat at given location unoccupied
	 * 
	 * @param labels
	 *            Seat label for a seat to be made unoccupied
	 */
	public void freeSeat(String labels) {

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].getLocation().equalsIgnoreCase(labels)) {
						map[i][j].clear();
					}
				}

			}
		}
	}

	/**
	 * counts the number of occupied coach seat
	 * 
	 * @return count
	 */
	private int getNumInCoach() {
		coachSeatsReserved = 0;
		for (int i = coachRowBegins; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (map[i][j] != null) {
					if (map[i][j].isOccupied() == true) {
						coachSeatsReserved++;
					}
				}

			}
		}
		return coachSeatsReserved;
	}

	/**
	 * Reads and store different data from the given text file.
	 * 
	 * @param readFile
	 */
	private void readFromFile(Scanner readFile) {

		numRows = readFile.nextInt();
		readFile.nextLine();

		seatLabels = readFile.nextLine();

		int numberOfAisles = readFile.nextInt();
		readFile.nextLine();

		startFirstClass = readFile.nextInt();
		readFile.nextLine();

		startBusiness = readFile.nextInt();
		readFile.nextLine();

		startCoach = readFile.nextInt();
		readFile.nextLine();

		numColumns = seatLabels.length() + numberOfAisles;

		map = new Seat[numRows][numColumns];
		stringMap = new String[numRows][numColumns];
		int rows = 0;
		int cols = 0;
		while (readFile.hasNextLine() && rows < numRows) {

			while (readFile.hasNext() && cols < numColumns) {
				for (int i = 0; i < numRows; i++) {
					for (int j = 0; j < numColumns; j++) {
						stringMap[i][j] = readFile.next();
						if (stringMap[i][j].equalsIgnoreCase("XXX")) {
							stringMap[i][j] = null;
						}
					}
				}
				cols++;
			}
			rows++;
		}

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {

				if (stringMap[i][j] == null) {
					map[i][j] = null;
					if (!(j == 0) && !(j == numColumns - 1)) {
						if (!(stringMap[i][j + 1] == null)) {
							if (stringMap[i][j + 1].charAt(stringMap[i][j + 1]
									.length() - 1) == (seatLabels
									.charAt(seatLabels.length() - 1))) {
								map[i][j + 1] = new Seat(stringMap[i][j + 1],
										true, true);
							}
							if (!(stringMap[i][j + 1]
									.charAt(stringMap[i][j + 1].length() - 1) == (seatLabels
									.charAt(seatLabels.length() - 1)))) {
								map[i][j + 1] = new Seat(stringMap[i][j + 1],
										false, true);
							}

						}
						if (!(stringMap[i][j - 1] == null)) {
							if (stringMap[i][j - 1].charAt(stringMap[i][j - 1]
									.length() - 1) == (seatLabels.charAt(0))) {
								map[i][j - 1] = new Seat(stringMap[i][j - 1],
										true, true);
							}
							if (!(stringMap[i][j - 1]
									.charAt(stringMap[i][j - 1].length() - 1) == (seatLabels
									.charAt(0)))) {
								map[i][j - 1] = new Seat(stringMap[i][j - 1],
										false, true);
							}

						}
					}
				}
				if (!(stringMap[i][j] == null)) {
					if (!(j == 0) && !(j == numColumns - 1)) {
						if (!(stringMap[i][j + 1] == null)
								&& !(stringMap[i][j - 1] == null)) {
							map[i][j] = new Seat(stringMap[i][j], false, false);
						}
					}
					if (j == 0) {
						if (!(stringMap[i][j + 1] == null)) {
							map[i][j] = new Seat(stringMap[i][j], true, false);
						}
					}
					if (j == numColumns - 1) {
						if (!(stringMap[i][j - 1] == null)) {
							map[i][j] = new Seat(stringMap[i][j], true, false);
						}
					}
				}
			}
		}
	}

}
