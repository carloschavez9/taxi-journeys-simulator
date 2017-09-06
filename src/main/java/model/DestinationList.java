package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import exceptions.DuplicateIDException;
import interfaces.ObjectMapManager;
import utils.Logger;
import utils.Utilities;
import utils.Logger.LoggerType;

/**
 * Manages operations over the list of destinations
 */
public class DestinationList extends Observable implements ObjectMapManager<String, Destination> {

	/** List of destination names */
	private ArrayList<String> destinationNames_;
	/** List of possible destinations */
	private HashMap<String, Destination> destinations_;

	/**
	 * Creates a new manager for the list of destinations
	 */
	public DestinationList() {
		// Initialise variables
		destinationNames_ = new ArrayList<String>();
		destinations_ = new HashMap<String, Destination>();
	}

	/**
	 * Validates valid destination
	 * @param destination
	 * @throws DuplicateIDException
	 */
	private void validateDestination(Destination destination) throws DuplicateIDException {
		// Validate empty or if it already exists
		if (destination == null || destination.getName().isEmpty())
			throw new IllegalArgumentException("The destination cannot be empty");
		else if (destinations_.containsKey(destination.getName()))
			throw new DuplicateIDException(destination.getName());
	}

	/**
	 * Gets destination by its name
	 * @param name Name of the destination
	 * @return
	 */
	public Destination get(String name) {
		return destinations_.get(name);
	}

	/**
	 * Gets the size of the map
	 */
	@Override
	public int getMapSize() {
		return destinations_.size();
	}
	
	/**
	 * Gets the map
	 */
	@Override
	public Map<String, Destination> getMap() {
		return destinations_;
	}

	/**
	 * Gets a random destination
	 * @return
	 */
	public Destination getRandomDestination() {
		if (destinations_.size() > 0) {
			// Get Random name from list
			int index = Utilities.generateRandomNumber(0, destinationNames_.size() - 1);
			String destinationName = destinationNames_.get(index);
			return destinations_.get(destinationName);
		} else
			return null;
	}

	/**
	 * Adds a destination to the list
	 * @param key Name of the destination
	 * @param destination Destination object with details
	 * @throws DuplicateIDException
	 * @throws IllegalArgumentException
	 */
	public void add(String key, Destination destination) throws DuplicateIDException, IllegalArgumentException {
		validateDestination(destination);

		// Add destination
		destinations_.put(key, destination);
		destinationNames_.add(key);
	}

	/**
	 * Gets a string representation of the list
	 */
	@Override
	public String toString() {
		String result = String.format("{size:%d,destinations:[", getMapSize());
		for (Destination destination : destinations_.values()) {
			result += destination.toString();
		}
		return result += "]}";
	}

	/**
	 * Logs a message
	 * @param logType
	 * @param format
	 * @param values
	 */
	@Override
	public void log(LoggerType logType, String message) {
		Logger logger_ = Logger.getInstance(logType);
		logger_.log(message);
	}

	/**
	 * Logs a message with format
	 * @param logType
	 * @param format
	 * @param values
	 */
	@Override
	public void log(LoggerType logType, String format, Object... values) {
		Logger logger_ = Logger.getInstance(logType);
		logger_.log(format, values);
	}
}
