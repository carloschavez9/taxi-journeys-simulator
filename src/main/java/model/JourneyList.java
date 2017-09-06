package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import comparators.JourneyCostComparator;
import interfaces.ObjectListManager;
import utils.Logger;
import utils.Logger.LoggerType;

public class JourneyList extends Observable implements ObjectListManager<Journey>, Observer {

	/** List of journeys */
	private ArrayList<Journey> journeys_;

	/**
	 * Creates a Journey List
	 */
	public JourneyList() {
		// Initialise variables
		journeys_ = new ArrayList<Journey>();
	}

	/**
	 * Validates the journey
	 * @param journey
	 */
	private void validateJourney(Journey journey) {
		// Validate empty
		if (journey == null || journey.getDestination() == null || journey.getTaxi() == null)
			throw new IllegalArgumentException("The destination cannot be empty");
	}

	/**
	 * Gets the size of the list
	 * @return
	 */
	public int getListSize() {
		return journeys_.size();
	}

	/**
	 * Adds a journey to the journey list
	 * @param journey Information of the journey
	 */
	public synchronized void add(Journey journey) {
		validateJourney(journey);
		journey.addObserver(this);
		journeys_.add(journey);
		notifyUpdate();
	}

	/**
	 * Gets a journey from the list at the specified index
	 * @param index
	 */
	public Journey get(int index) {
		return journeys_.get(index);
	}

	/**
	 * Notifies observers that the list changed
	 */
	public void notifyUpdate() {
		setChanged();
		notifyObservers(this);
		clearChanged();
	}

	/**
	 * Orders the list
	 */
	@Override
	public synchronized void orderList() {
		journeys_.sort(new JourneyCostComparator());
	}

	/**
	 * Gets a string representation of the list
	 */
	@Override
	public String toString() {
		String result = String.format("{size:%d,journeys:[", getListSize());
		for (Journey journey : journeys_) {
			result += journey.toString();
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

	/**
	 * Gets list
	 */
	public synchronized ArrayList<Journey> getList() {
		return journeys_;
	}

	/**
	 * Gets cheapest N journeys that have finished
	 */
	public synchronized List<Journey> getCheapestNJourneys(int total) {
		List<Journey> result = new ArrayList<Journey>();
		for (Journey journey : getList()) {
			if (journey.isFinished())
				result.add(journey);
		}
		result.sort(new JourneyCostComparator());
		if (result.size() > total)
			result = result.subList(0, total);
		return result;
	}

	/**
	 * Gets dearest N journeys that have finished
	 */
	public synchronized List<Journey> getDearestNJourneys(int total) {
		List<Journey> result = new ArrayList<Journey>();
		for (Journey journey : getList()) {
			if (journey.isFinished())
				result.add(journey);
		}
		result.sort(new JourneyCostComparator());
		Collections.reverse(result);
		if (result.size() > total)
			result = result.subList(0, total);
		return result;
	}

	@Override
	public void update(Observable o, Object arg) {
		notifyUpdate();
	}
}
