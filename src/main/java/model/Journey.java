package model;

import java.util.Observable;

import model.Taxi.TaxiStatus;
import utils.Logger;
import utils.Logger.LoggerType;

/**
 * Manages operations of a Journey
 */
public class Journey extends Observable implements Comparable<Journey>, Runnable {

	private enum JourneyStatus {
		IN_PROGRESS, FINISHED
	}

	private TaxiList taxiList_;
	/** Group number of passengers assigned to this journey */
	private int groupNumber_;
	/** Number of passengers of the journey */
	private int passengers_;
	/** Cost of the journey */
	private double cost_;
	/** Current miles of journey */
	private double currentMiles_;
	/** Destination of the journey */
	private Destination destination_;
	/** Taxi associated with the journey */
	private Taxi taxi_;
	/** Indicates the journey has finished */
	private boolean isFinished_;

	/**
	 * Creates a new journey
	 * @param groupNumber Group number of the passengers assigned to this journey
	 * @param passengers Number of passengers of the journey
	 * @param destination Destination of the journey
	 * @param taxi Taxi associated with the journey
	 * @param taxiList
	 */
	public Journey(int groupNumber, int passengers, Destination destination, Taxi taxi,TaxiList taxiList) {
		validateInputs(passengers, destination, taxi);

		// Set values
		taxiList_ = taxiList;
		groupNumber_ = groupNumber;
		passengers_ = passengers;
		destination_ = destination;
		taxi_ = taxi;
		isFinished_ = false;
		cost_ = 0.0;
		currentMiles_ = 0.0;
	}

	/**
	 * Validates inputs
	 * @param passengers
	 * @param defaultJourneyTime
	 * @param calculateTimeForJourney
	 * @param destination
	 * @param taxi
	 */
	private void validateInputs(int passengers, Destination destination, Taxi taxi) {
		// Validate values
		if (passengers <= 0)
			throw new IllegalArgumentException("The number of passengers must be greater than zero");

		if (destination == null || destination.getName().isEmpty())
			throw new IllegalArgumentException("The destination cannot be empty");

		if (taxi == null || taxi.getRegistrationNumber().isEmpty())
			throw new IllegalArgumentException("The taxi cannot be empty");
	}

	/**
	 * Computes the cost of the Journey. 10 pounds as initial charge for the first 2 miles. For each extra tenth of a mile, 0.2 is added. Cost multiplied depending on number of passengers.
	 * @throws InterruptedException
	 */
	public void computeCost() throws InterruptedException {
		double distance = destination_.getDistance();
		cost_ = 10.0; // Initial cost of journey
		// Initial miles of journey
		while (distance > 0.1) {
			TaxiServiceModel.simulateProcessingTime();
			distance -= 1;
			currentMiles_ += 1;
			cost_ += 2;
			notifyUpdate();
		}

		TaxiServiceModel.simulateProcessingTime();
		double missing = destination_.getDistance() - currentMiles_;
		currentMiles_ = destination_.getDistance();
		cost_ += missing;
		// Cost multiplied depending on number of passengers
		cost_ *= (10 + passengers_) / 10;
		isFinished_ = true;
		notifyUpdate();
	}

	private JourneyStatus getStatus() {
		if (!isFinished_)
			return JourneyStatus.IN_PROGRESS;
		else
			return JourneyStatus.FINISHED;
	}

	/**
	 * Notifies observers that the list changed
	 */
	private void notifyUpdate() {
		setChanged();
		notifyObservers();
		clearChanged();
	}

	/**
	 * Gets the cost of the journey
	 * @return
	 */
	public Double getCost() {
		return cost_;
	}

	/**
	 * Gets the number of passengers of the journey
	 * @return
	 */
	public int getPassengers() {
		return passengers_;
	}

	/**
	 * Gets the taxi associated with the journey
	 * @return
	 */
	public Taxi getTaxi() {
		return taxi_;
	}

	/**
	 * Gets the group number of passengers assigned to this journey
	 * @return
	 */
	public int getGroupNumber() {
		return groupNumber_;
	}

	/**
	 * Indicates if the journey has finished
	 * @return
	 */
	public boolean isFinished() {
		return isFinished_;
	}

	/**
	 * Gets the destination of the journey
	 * @return
	 */
	public Destination getDestination() {
		return destination_;
	}

	@Override
	public void run() {
		try {
			taxi_.setTaxiStatus(TaxiStatus.IN_ROUTE);
			// Computes the cost of the journey
			computeCost();
			taxi_.setTaxiStatus(TaxiStatus.FREE);
			taxiList_.addLastFreeTaxi(taxi_);
			log(LoggerType.INFO, "TOTAL Journey, taxi %s, passengers %d, destination %s, distance %.1f, cost %.2f",
					taxi_.getRegistrationNumber(), getPassengers(), destination_.getName(), destination_.getDistance(),
					getCost());
		} catch (InterruptedException e) {
			log(LoggerType.ERROR, e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			log(LoggerType.ERROR, e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Gets a string of the object
	 */
	@Override
	public String toString() {
		return String.format("Destination: %s, Passengers: %s, Taxi: %s, Miles travelled: %.1f, Cost: %.2f, Status: %s",
				destination_.getName(), passengers_, taxi_.getRegistrationNumber(), currentMiles_, cost_, getStatus());
	}

	/**
	 * Compares two journeys by cost
	 * @param arg0 The Journey to compare to
	 */
	@Override
	public int compareTo(Journey arg0) {
		return arg0.getCost().compareTo(this.getCost());
	}

	/**
	 * Logs a message
	 * @param logType
	 * @param format
	 * @param values
	 */
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
	public void log(LoggerType logType, String format, Object... values) {
		Logger logger_ = Logger.getInstance(logType);
		logger_.log(format, values);
	}

	/**
	 * Gets the details of the journey
	 * @return
	 */
	public String[] getDetails() {
		String[] details = { taxi_.getDriverName(), taxi_.getRegistrationNumber(), destination_.getName(),
				Integer.toString(passengers_), Double.toString(destination_.getDistance()),
				Double.toString(Math.round(cost_ * 100.0) / 100.0), getStatus().toString() };
		return details;
	}
}
