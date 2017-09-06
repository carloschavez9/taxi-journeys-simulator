package model;

import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.InvalidDriverNameException;
import exceptions.InvalidRegistrationNumberException;

public class Taxi implements Comparable<Taxi> {

	public enum TaxiStatus {
		READY, IN_ROUTE, FREE
	}

	/** Registration number of the taxi */
	private String registrationNumber_;
	/** Name of driver */
	private String driverName_;
	/** Maximum number of passengers allowed in taxi */
	private int maxPassengers_;
	/** Destinations visited by driver */
	private TreeSet<Destination> destinationsVisited_;
	/** The current status of the taxi */
	private TaxiStatus taxiStatus_;

	/**
	 * Creates a new taxi
	 * @param registrationNumber Registration number
	 * @param driverName Driver name
	 * @throws InvalidRegistrationNumberException
	 * @throws InvalidDriverNameException
	 */
	public Taxi(String registrationNumber, String driverName)
			throws InvalidRegistrationNumberException, InvalidDriverNameException {
		validateInputs(registrationNumber, driverName);

		// Initialise variables
		destinationsVisited_ = new TreeSet<Destination>();
		registrationNumber_ = registrationNumber;
		driverName_ = driverName.trim();
		maxPassengers_ = 1;
		taxiStatus_ = TaxiStatus.READY;
	}

	/**
	 * Validates inputs
	 * @param registrationNumber Registration number
	 * @param driverName Driver name
	 * @throws InvalidRegistrationNumberException
	 * @throws InvalidDriverNameException
	 */
	private void validateInputs(String registrationNumber, String driverName)
			throws InvalidRegistrationNumberException, InvalidDriverNameException {
		// Validate empty
		if (registrationNumber == null || registrationNumber.isEmpty())
			throw new IllegalArgumentException("The registration number cannot be empty");
		// Validate empty
		if (driverName == null || driverName.isEmpty())
			throw new IllegalArgumentException("The driver name cannot be empty");

		// Validate format for registration number, dddSSS (3 numbers and 3 letters)
		String pattern = "^([0-9]{3})([A-Z]{3})$";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(registrationNumber);
		if (!matcher.find())
			throw new InvalidRegistrationNumberException(registrationNumber);

		// Get name and surname
		String[] names = driverName.split(" ");
		if (names.length != 2)
			throw new InvalidDriverNameException(driverName);

		// Validate format of name, cannot contain numbers
		pattern = "^([0-9]+)$";
		regex = Pattern.compile(pattern);
		matcher = regex.matcher(driverName);
		if (matcher.find())
			throw new InvalidDriverNameException(driverName);
	}

	/**
	 * Gets the taxi status
	 * @return
	 */
	public TaxiStatus getTaxiStatus() {
		return taxiStatus_;
	}

	/**
	 * Sets the taxi status
	 * @param taxiStatus
	 */
	public void setTaxiStatus(TaxiStatus taxiStatus) {
		taxiStatus_ = taxiStatus;
	}

	/**
	 * Gets registration number of taxi
	 * @return
	 */
	public String getRegistrationNumber() {
		return registrationNumber_;
	}

	/**
	 * Gets maximum number of passengers of taxi
	 * @return
	 */
	public int getMaxPassengers() {
		return maxPassengers_;
	}

	/**
	 * Gets the driver name in the form 'Name Surname'
	 * @return
	 */
	public String getDriverName() {
		return driverName_;
	}

	/**
	 * Sets the maximum number of passengers of taxi
	 * @param maxPassengers
	 */
	public void setMaxPassengers(int maxPassengers) {
		maxPassengers_ = maxPassengers;
	}

	/**
	 * Adds a destination to the list of visited destinations
	 * @param destination
	 */
	public void addDestination(Destination destination) {
		destinationsVisited_.add(destination);
	}

	/**
	 * Compares 2 taxis by registration number
	 */
	@Override
	public int compareTo(Taxi arg0) {
		return arg0.getRegistrationNumber().compareTo(this.getRegistrationNumber());
	}

	/**
	 * Gets a string representation of the list
	 */
	@Override
	public String toString() {
		return String.format("RegistrationNumber: %s, Driver: %s, MaxPassengers: %d, Status: %s", registrationNumber_,
				driverName_, maxPassengers_, taxiStatus_);
	}
}
