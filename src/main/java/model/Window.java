package model;

import java.util.ArrayList;
import java.util.Observable;
import utils.Logger;
import utils.Logger.LoggerType;

/**
 * Manages a Window
 */
public class Window extends Observable implements Runnable {

	/** Possible statuses of the window */
	private enum WindowStatus {
		PAUSED, PROCESSING
	}

	/** Window number */
	private int windowNumber_;
	/** Total taxi allocations for the window */
	private int totalTaxiAllocations_;
	/** List of taxis */
	private TaxiList taxiList_;
	/** List of groups */
	private GroupList groupList_;
	/** List of journeys */
	private JourneyList journeyList_;
	/** Indicates if the window is active */
	private boolean isActive;
	/** Current group in window */
	private Group currentGroup_;
	/** Current taxi in window */
	private Taxi currentTaxi_;

	/**
	 * Creates a new window
	 * @param windowNumber Window number
	 * @param taxiList List of taxis
	 * @param groupList List of groups
	 */
	public Window(int windowNumber, TaxiList taxiList, GroupList groupList, JourneyList journeyList) {
		validateInput(windowNumber, taxiList, groupList, journeyList);

		windowNumber_ = windowNumber;
		taxiList_ = taxiList;
		groupList_ = groupList;
		journeyList_ = journeyList;
		totalTaxiAllocations_ = 0;
		isActive = true;
		currentGroup_ = null;
		currentTaxi_ = null;
	}

	/**
	 * Validates input
	 * @param windowNumber
	 * @param taxiList
	 * @param groupList
	 * @param journeyList
	 */
	private void validateInput(int windowNumber, TaxiList taxiList, GroupList groupList, JourneyList journeyList) {
		if (windowNumber <= 0)
			throw new IllegalArgumentException("Window number must be greater than zero");

		if (taxiList == null)
			throw new IllegalArgumentException("The taxi list cannot be empty");

		if (groupList == null)
			throw new IllegalArgumentException("The group list cannot be empty");

		if (journeyList == null)
			throw new IllegalArgumentException("The journey list cannot be empty");
	}

	/**
	 * Gets the window number
	 * @return
	 */
	public int getWindowNumber() {
		return windowNumber_;
	}

	/**
	 * Runs the thread
	 */
	@Override
	public void run() {
		try {
			processNextCustomer();
		} catch (InterruptedException e) {
			log(LoggerType.ERROR, e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			log(LoggerType.ERROR, e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Process the next customer
	 * @throws InterruptedException
	 * @throws Exception
	 */
	private void processNextCustomer() throws InterruptedException {
		// Work until no more groups or taxis are available
		while (groupList_.getListSize() > 0 && taxiList_.getListSize() > 0) {
			TaxiServiceModel.simulateProcessingTime();

			// Get next group and passengers within the group
			log(LoggerType.DEBUG, "Window %d, before groups %s", windowNumber_, groupList_);
			Group group = groupList_.getNext();
			log(LoggerType.DEBUG, "Window %d, after groups %s", windowNumber_, groupList_);
			if (group == null)
				break;
			currentGroup_ = group;
			notifyUpdate();
			TaxiServiceModel.simulateProcessingTime();

			int groupPassengers = group.getGroupPassengers();
			int passengers = groupPassengers;

			boolean canAssign = true;
			// Try to assign all passengers within the group
			while (passengers > 0 && canAssign) {
				// Gets next taxi
				log(LoggerType.DEBUG, "Window %d, before taxis %d", windowNumber_, taxiList_.getListSize());
				Taxi taxi = taxiList_.getNext();
				log(LoggerType.DEBUG, "Window %d, after taxis %d", windowNumber_, taxiList_.getListSize());
				if (taxi == null) {
					canAssign = false;
					break;
				} else {
					currentTaxi_ = taxi;
					notifyUpdate();
					TaxiServiceModel.simulateProcessingTime();

					int maxPassengersOnTaxi = taxi.getMaxPassengers();
					// If max number of passengers allowed on taxi is greater than the number of passengers within the group, use the number in group
					if (maxPassengersOnTaxi > passengers)
						maxPassengersOnTaxi = passengers;

					// Assign passengers of group
					passengers = group.assignPassengers(maxPassengersOnTaxi);

					// Create journey
					Journey journey = new Journey(group.getGroupNumber(), maxPassengersOnTaxi,
							group.getGroupDestination(), taxi, taxiList_);
					journeyList_.add(journey);
					Thread journeyThread = new Thread(journey);
					journeyThread.start();

					// Add passenger allocation to window
					totalTaxiAllocations_++;
					notifyUpdate();

					log(LoggerType.INFO,
							"Window %d, group %s, taxi %s, passengers allocated %d, initial group %d, total groups %d, total taxis %d",
							windowNumber_, group, taxi, maxPassengersOnTaxi, groupPassengers, groupList_.getListSize(),
							taxiList_.getListSize());
				}
			}

			currentGroup_ = null;
			currentTaxi_ = null;
			notifyUpdate();
		}

		log(LoggerType.INFO, "Window %d total taxi allocations %d", windowNumber_, totalTaxiAllocations_);
	}

	/**
	 * Notifies observers that the list changed
	 */
	private void notifyUpdate() {
		setChanged();
		notifyObservers(this);
		clearChanged();
	}

	/**
	 * Gets an array with the info for the window
	 * @return
	 */
	public String[] toStringArrayForWindow() {
		ArrayList<String> resultList = new ArrayList<String>();
		String groupInfo = "Group: None";
		String destinationInfo = "Destination: None";
		String taxiInfo = "Taxi: None";
		String totalAllocationsInfo = String.format("Total Allocations: %d", totalTaxiAllocations_);
		if (currentGroup_ != null) {
			groupInfo = String.format("Group: %d", currentGroup_.getGroupNumber());
			destinationInfo = String.format("Destination: %s", currentGroup_.getGroupDestination().getName());
		}
		if (currentTaxi_ != null)
			taxiInfo = String.format("Taxi: %s", currentTaxi_.toString());

		resultList.add(groupInfo);
		resultList.add(destinationInfo);
		resultList.add(taxiInfo);
		resultList.add(totalAllocationsInfo);

		String[] result = new String[resultList.size()];
		return resultList.toArray(result);
	}

	@Override
	public String toString() {
		String format = "Window: %d, allocations: %d";
		return String.format(format, windowNumber_, totalTaxiAllocations_);
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
}
