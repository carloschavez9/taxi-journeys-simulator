package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import comparators.JourneyCostComparator;
import interfaces.ObjectDequeManager;
import model.Taxi.TaxiStatus;
import utils.Logger;
import utils.Logger.LoggerType;

public class TaxiList extends Observable implements ObjectDequeManager<Taxi> {

	/** List of taxis */
	private ArrayDeque<Taxi> taxis_;
	/** List of default taxis */
	private ArrayDeque<Taxi> defaultTaxis_;
	/** List of free taxis */
	private ArrayDeque<Taxi> freeTaxis_;

	/**
	 * Creates a new list of taxis
	 * @throws Exception
	 */
	public TaxiList() throws Exception {
		// Initialise variables
		taxis_ = new ArrayDeque<Taxi>();
		defaultTaxis_ = new ArrayDeque<Taxi>();
		freeTaxis_ = new ArrayDeque<Taxi>();
	}

	/**
	 * Validates the Taxi
	 * @param taxi
	 */
	private void validateTaxi(Taxi taxi) {
		// Validate empty
		if (taxi == null || taxi.getRegistrationNumber().isEmpty() || taxi.getDriverName().isEmpty())
			throw new IllegalArgumentException("The group cannot be empty");
	}

	/**
	 * Gets the size of the list
	 * @return
	 */
	public int getListSize() {
		return taxis_.size();
	}

	/**
	 * Add taxi to the end of the list of default taxis
	 * @param taxi
	 */
	public void addLastDefaultTaxi(Taxi taxi) {
		validateTaxi(taxi);
		defaultTaxis_.add(taxi);
	}

	/**
	 * Add taxi to the end of the list of free taxis
	 * @param taxi
	 */
	public void addLastFreeTaxi(Taxi taxi) {
		validateTaxi(taxi);
		freeTaxis_.add(taxi);
	}

	/**
	 * Gets the next taxi of the list of default taxis, removes from list
	 * @return
	 */
	public Taxi getNextDefaultTaxi() {
		return defaultTaxis_.poll();
	}

	/**
	 * Add taxi to the end of the list
	 * @param taxi
	 */
	public synchronized void addLast(Taxi taxi) {
		validateTaxi(taxi);
		taxis_.add(taxi);
		notifyUpdate();
	}

	/**
	 * Gets next available taxi, removing it from the list
	 * @return
	 */
	public synchronized Taxi getNext() {
		Taxi taxi = taxis_.poll();
		notifyUpdate();

		return taxi;
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
	 * Gets a string representation of the list
	 */
	@Override
	public String toString() {
		String result = String.format("{size:%d,taxis:[", getListSize());
		for (Taxi taxi : taxis_) {
			result += taxi.toString();
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
	 * Gets the deque
	 * @return
	 */
	public ArrayDeque<Taxi> getDeque() {
		return taxis_;
	}

	/**
	 * Gets the free taxis that have finished their journeys
	 * @return
	 */
	public List<Taxi> getFreeTaxis() {
		List<Taxi> result = new ArrayList<Taxi>();
		for (Taxi taxi : freeTaxis_) {
			if (taxi.getTaxiStatus().equals(TaxiStatus.FREE))
				result.add(taxi);
		}
		return result;
	}

	/**
	 * Gets the first N taxis
	 * @param n
	 * @return
	 */
	public ArrayDeque<Taxi> getNTaxis(int n) {
		if (taxis_.size() > n) {
			ArrayDeque<Taxi> newTaxis = new ArrayDeque<Taxi>();
			ArrayDeque<Taxi> copyTaxis = taxis_.clone();
			for (int i = 0; i < n; i++) {
				newTaxis.add(copyTaxis.poll());
			}
			return newTaxis;
		} else {
			return taxis_;
		}
	}
}
