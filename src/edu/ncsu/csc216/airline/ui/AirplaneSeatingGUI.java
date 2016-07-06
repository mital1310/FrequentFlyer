package edu.ncsu.csc216.airline.ui;

import java.awt.event.*;
import java.awt.*;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import edu.ncsu.csc216.airline.airplane.Flight;
import edu.ncsu.csc216.airline.airplane.SeatManager;

import edu.ncsu.csc216.airline.passengers.Business;
import edu.ncsu.csc216.airline.passengers.Coach;
import edu.ncsu.csc216.airline.passengers.FirstClass;
import edu.ncsu.csc216.airline.passengers.FlightReservation;
import edu.ncsu.csc216.airline.passengers.Manifest;

import java.awt.Color;

/**
 * A graphical user interface for an airplane reservation system.
 * 
 * @author Jo Perry
 * @version 2.0
 */
public class AirplaneSeatingGUI extends JFrame implements ActionListener {

	// Window, button, and scrollpane strings
	private final static String TITLE = "Flight Seat Reservation System";
	private final static String RESERVATIONS = "Reservations";
	private final static String DELETE = "Delete Selected Reservation";
	private final static String ADD = "Add New Reservation";
	private final static String QUIT = "Quit";
	private final static String FIRST_CLASS = "First Class";
	private final static String BUSINESS = "Business";
	private final static String COACH = "Coach";
	private final static String NAME = "Name: ";
	private final static String PREFERENCE = "Seat Preference: ";
	private final static String TICKET_KIND = "Ticket: ";
	private final static String BLANK = "";
	private final static Color NOT_A_SEAT = Color.black;
	private final static Color OCCUPIED_SEAT = Color.green;
	private final static Color EMPTY_SEAT = Color.yellow;
	private final static String[] RESERVATION_CLASS = { FIRST_CLASS, BUSINESS,
			COACH };

	// Size constants for the window and scroll panes
	private final static int FRAME_WIDTH = 800; // Width of main window
	private final static int FRAME_HEIGHT = 700; // Height of main window
	private static final int RESERVATION_LENGTH = 100; // Height of reservation
														// display
	private static final int PAD = 10; // Panel padding

	// Panels, Boxes, and Borders
	private TitledBorder bdrReservations = new TitledBorder(RESERVATIONS);
	private JPanel pnlSeats = new JPanel(); // Shows airplane seating map
	private JPanel pnlReservations = new JPanel(); // Shows reservations
	private JPanel pnlPassenger = new JPanel(); // Holds new passenger data
												// entry widgets
	private GridBagLayout gridbag = new GridBagLayout(); // Applied to
															// pnlPassenger
	private GridBagConstraints c = new GridBagConstraints(); // Constraints on
																// gridbag

	// Buttons
	private JButton btnDelete = new JButton(DELETE); // To remove a reservation
	private JButton btnAdd = new JButton(ADD); // To add a new passenger
												// reservation
	private JButton btnQuit = new JButton(QUIT); // To quit the application

	// Labels for new reservation/passenger widget display
	private final JLabel lblName = new JLabel(NAME);
	private final JLabel lblSeatChoice = new JLabel(PREFERENCE);
	private final JLabel lblTicket = new JLabel(TICKET_KIND);
	private final JLabel lblBlank = new JLabel(BLANK);

	// Field, combo box, radio buttons for new passenger reservation entry
	private JTextField txtName = new JTextField(); // For entry of passenger
													// name
	private JComboBox cmbTicketType = new JComboBox(RESERVATION_CLASS); // Choice
																		// of
																		// reservation
																		// types
	private JRadioButton rbtnWindow = new JRadioButton("Window"); // Window seat
																	// preference
																	// (default)
	private JRadioButton rbtnAisle = new JRadioButton("Aisle"); // Aisle seat
																// preference
	private ButtonGroup btnGrpPreference = new ButtonGroup(); // Groups seat
																// preferences
	private JLabel[] lblPassenger = { lblName, lblTicket, lblSeatChoice,
			lblBlank }; // Labels on new passenger widgets
	private Component[] cmpPassenger = { txtName, cmbTicketType, rbtnWindow,
			rbtnAisle }; // New passenger widgets

	// Seating chart display
	private JTextField[][] txtSeats;
	private Font font = new Font("Dialog", Font.PLAIN, 9);

	// Scrolling list of reservations
	private DefaultListModel dlmReservations = new DefaultListModel();
	private JList lstReservations = new JList(dlmReservations);
	private JScrollPane scrollReservations = new JScrollPane(lstReservations);

	// Backend model
	private SeatManager myFlight; // The entity for this reservation system
	private Manifest passengers; // Passengers/reservations for this system
	private int rows; // Number of rows in the seating chart
	private int lengthOfRow; // Maximum number of seats in a row + number of
								// aisles

	/**
	 * Constructor. Sets up the user interface and initializes the backend
	 * model.
	 * 
	 * @param airplaneFileName
	 *            file with the airplane seating information
	 */
	public AirplaneSeatingGUI(String airplaneFileName) {
		// Create the backend.
		try {
			myFlight = new Flight(airplaneFileName);
			passengers = new Manifest();
			initUI();
			this.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Cannot initialize seating chart.", "Error",
					JOptionPane.ERROR_MESSAGE);
			endExecution();
		}
	}

	/**
	 * Main method -- begins program execution.
	 * 
	 * @param args
	 *            arg[0] is the data file to initialize the seating chart.
	 */
	public static void main(String[] args) {
		if (args.length == 0)
			new AirplaneSeatingGUI(null);
		else
			new AirplaneSeatingGUI(args[0]);

	}

	// ------ Controller Methods ---------------------------

	/**
	 * Defines actions to be performed on button clicks
	 * 
	 * @param e
	 *            button click (user event)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnQuit)) // Quit the application
			endExecution();
		if (e.getSource().equals(btnDelete)) { // Get index of selected item.
			int k = lstReservations.getSelectedIndex();
			try {
				passengers.removePassenger(k);
				loadReservationList();
				colorSeats();
				clearFields();
			} catch (IllegalArgumentException noSelection) {
				JOptionPane.showMessageDialog(this, noSelection.getMessage(),
						"Warning", JOptionPane.WARNING_MESSAGE);
			}
		}
		if (e.getSource().equals(btnAdd)) { // Add the new passenger
			String name = txtName.getText();
			String ticketClass = RESERVATION_CLASS[cmbTicketType
					.getSelectedIndex()];
			boolean wantsWindow = rbtnWindow.isSelected();
			try {
				FlightReservation newPassenger = null;
				if (ticketClass.equals(FIRST_CLASS))
					newPassenger = new FirstClass(name, (Flight) myFlight,
							wantsWindow);
				else if (ticketClass.equals(BUSINESS))
					newPassenger = new Business(name, (Flight) myFlight,
							wantsWindow);
				else if (ticketClass.equals(COACH))
					newPassenger = new Coach(name, (Flight) myFlight,
							wantsWindow);
				newPassenger.findSeat();
				passengers.add(newPassenger);

			} catch (IllegalArgumentException badData) {
				JOptionPane.showMessageDialog(this, badData.getMessage(),
						"Warning", JOptionPane.WARNING_MESSAGE);
			}
			loadReservationList();
			colorSeats();
			clearFields();
		}
	}

	// --------End Controller Methods ---------------------

	// ------ Private Methods -----------------------------

	/**
	 * Private method - initializes the user interface.
	 */
	private void initUI() {
		// Create the backend.
		String[][] seats = myFlight.getSeatMap();
		rows = seats.length;
		lengthOfRow = seats[0].length;
		txtSeats = new JTextField[rows][lengthOfRow];

		// Set up the panels and the list that make the UI
		setUpPanels();
		setUpLists();
		colorSeats();

		// Construct the main window and add listeners.
		setTitle(TITLE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		Container c = getContentPane();
		c.add(pnlSeats, BorderLayout.WEST);
		c.add(pnlReservations, BorderLayout.CENTER);
		setVisible(true);
		addListeners();

		// Make sure the application quits when the window is closed.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				endExecution();
				System.exit(0);
			}
		});
	}

	/**
	 * Private method - Sets up the scrolling list of reservations.
	 */
	private void setUpLists() {
		// Load the data.
		loadReservationList();
		lstReservations.setFont(new Font("Courier", Font.PLAIN, 12));
		lstReservations.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * Private method - Sets up the seating chart.
	 */
	private void setUpSeatingChart() {
		String[][] seats = myFlight.getSeatMap();
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < lengthOfRow; col++) {
				txtSeats[row][col] = new JTextField();
				if (seats[row][col] != null)
					txtSeats[row][col].setText(seats[row][col]);
				else
					txtSeats[row][col].setText("");
				txtSeats[row][col].setFont(font);
				txtSeats[row][col].setEditable(false);
				pnlSeats.add(txtSeats[row][col]);
			}
		colorSeats();
	}

	/**
	 * Private method - colors seats according to their status.
	 */
	private void colorSeats() {
		boolean[][] occupied = myFlight.getSeatOccupationMap();
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < lengthOfRow; col++) {
				if (txtSeats[row][col].getText().length() == 0)
					txtSeats[row][col].setBackground(NOT_A_SEAT);
				else if (occupied[row][col])
					txtSeats[row][col].setBackground(OCCUPIED_SEAT);
				else
					txtSeats[row][col].setBackground(EMPTY_SEAT);
			}
	}

	/**
	 * Private method - Determines the geometry of the main window.
	 */
	private void setUpPanels() {
		// Create the seating chart on the left half of the window.
		pnlSeats.setLayout(new GridLayout(rows, lengthOfRow));
		pnlSeats.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(PAD,
				PAD / 2, PAD, PAD));
		setUpSeatingChart();

		// Initialize the reservation list.
		scrollReservations.setBorder(bdrReservations);
		scrollReservations.setPreferredSize(new Dimension(FRAME_WIDTH / 2 - 4
				* (PAD), RESERVATION_LENGTH));

		// Set up the right side of the window in pnlReservations.
		pnlReservations.setLayout(new BorderLayout());
		pnlReservations.setBorder((EmptyBorder) BorderFactory
				.createEmptyBorder(PAD, PAD / 2, PAD, PAD));

		// Lay out the buttons and passenger entry fields in a grid.
		pnlPassenger.setLayout(gridbag);
		pnlPassenger.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(
				PAD, PAD / 2, PAD, PAD));

		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.ipady = 0;
		c.insets = new Insets(5, 0, 0, 0);
		c.fill = GridBagConstraints.NONE;
		pnlPassenger.add(btnDelete, c);

		c.gridy = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		pnlPassenger.add(new JSeparator(JSeparator.HORIZONTAL), c);
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		int numLabels = lblPassenger.length;
		c.anchor = GridBagConstraints.EAST;

		for (int i = 0; i < numLabels; i++) {
			c.gridx = 0;
			c.gridy = i + 3;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0.0;
			pnlPassenger.add(lblPassenger[i], c);

			c.gridx = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			pnlPassenger.add(cmpPassenger[i], c);
		}

		c.gridwidth = 2;
		c.gridy = numLabels + 3;
		pnlPassenger.add(btnAdd, c);

		c.gridy += 1;
		c.gridx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		pnlPassenger.add(new JSeparator(JSeparator.HORIZONTAL), c);

		c.gridy += 1;
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.fill = GridBagConstraints.NONE;
		pnlPassenger.add(btnQuit, c);

		// Put the reservations, buttons, fields on the right side of the
		// window.
		pnlReservations.add(scrollReservations, BorderLayout.CENTER);
		pnlReservations.add(pnlPassenger, BorderLayout.SOUTH);
	}

	/**
	 * Private Method - Adds listeners to the buttons and groups radio
	 * buttons</br> with the default window/aisle choice set to window.
	 */
	private void addListeners() {
		btnDelete.addActionListener(this);
		btnAdd.addActionListener(this);
		btnQuit.addActionListener(this);
		btnGrpPreference.add(rbtnWindow);
		btnGrpPreference.add(rbtnAisle);
		rbtnWindow.setSelected(true);
	}

	/**
	 * Private method - Clears text field and sets default passenger preference
	 * to window.
	 */
	private void clearFields() {
		txtName.setText("");
		rbtnWindow.setSelected(true);
	}

	/**
	 * Private Method - Loads the reservation list model from a string, with
	 * newline tokenizers.
	 */
	private void loadReservationList() {
		dlmReservations.clear();
		StringTokenizer st = new StringTokenizer(passengers.report(), "\n");
		while (st.hasMoreTokens()) {
			dlmReservations.addElement(st.nextToken());
		}
	}

	/**
	 * Private Method - Ends execution of the program.
	 */
	private void endExecution() {
		System.exit(0);
	}

	// ------------- End Private Methods -------------------
}
