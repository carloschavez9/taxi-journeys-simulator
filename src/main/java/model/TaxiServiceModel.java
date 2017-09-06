package model;

import java.util.ArrayList;
import java.util.Observable;
import utils.FileManager;
import utils.Logger;
import utils.Utilities;
import utils.Logger.LoggerType;
import view.CheapestJourneyListView;
import view.DearestJourneyListView;
import view.FreeTaxisListView;
import view.GroupListView;
import view.JourneyListView;
import view.TaxiListView;
import view.WindowListView;

/**
 * Manages the taxi service model
 */
public class TaxiServiceModel extends Observable implements Runnable {

	/** File path for destinations */
	private static final String FILEPATH_DESTINATIONS = "data/destinations.csv";
	/** File path for taxis */
	private static final String FILEPATH_TAXIS = "data/taxis.csv";
	/** Processing time multiplier */
	private static double PROCESSING_TIME_MULTIPLIER = 0.0;

	/** List of destinations */
	private DestinationList destinationList_;
	/** List of windows */
	private WindowList windowList_;
	/** List of groups */
	private GroupList groupList_;
	/** List of taxis */
	private TaxiList taxiList_;
	/** List of journeys */
	private JourneyList journeyList_;
	/** Minimum group size */
	private int minGroupSize_;
	/** Maximum group size */
	private int maxGroupSize_;
	/** Minimum taxi size */
	private int minTaxiSize_;
	/** Maximum taxi size */
	private int maxTaxiSize_;
	/** Indicates if the simulation is finished */
	private boolean isFinished_;

	/**
	 * Creates a new TaxiServiceModel
	 * @param numWindows Number of windows
	 * @param numGroups Number of groups
	 * @param minGroupSize Minimum number of passengers per group
	 * @param maxGroupSize Maximum number of passengers per group
	 * @param numTaxis Number of taxis
	 * @param minTaxiSize Minimum number of passengers per taxi
	 * @param maxTaxiSize Minimum number of passengers per taxi
	 * @param processingTimeMultiplier Processing time multiplier in seconds
	 * @throws Exception
	 */
	public TaxiServiceModel(int numWindows, int numGroups, int minGroupSize, int maxGroupSize, int numTaxis,
			int minTaxiSize, int maxTaxiSize, int processingTimeMultiplier) throws Exception {

		validateInputs(numWindows, numGroups, minGroupSize, maxGroupSize, numTaxis, minTaxiSize, maxTaxiSize,
				processingTimeMultiplier);
		minGroupSize_ = minGroupSize;
		maxGroupSize_ = maxGroupSize;
		minTaxiSize_ = minTaxiSize;
		maxTaxiSize_ = maxTaxiSize;
		isFinished_ = false;
		// initialise variables
		destinationList_ = new DestinationList();
		taxiList_ = new TaxiList();
		journeyList_ = new JourneyList();
		groupList_ = new GroupList();
		windowList_ = new WindowList();
		setProcessingTimeMultiplier(processingTimeMultiplier * 1000);

		// Load information from files
		FileManager.loadFileDestinationInformation(FILEPATH_DESTINATIONS, getDestinationList_());
		FileManager.loadFileTaxiInformation(FILEPATH_TAXIS, getTaxiList());

		// Create group list and add elements
		for (int i = 0; i < numGroups; i++) {
			int passengers = Utilities.generateRandomNumber(minGroupSize, maxGroupSize);
			Destination destination = getDestinationList_().getRandomDestination();
			Group group = new Group(getGroupList().getNextGroupNumber(), passengers, destination);
			groupList_.addLast(group);
		}

		// Add taxis to list
		for (int i = 0; i < numTaxis; i++) {
			int passengers = Utilities.generateRandomNumber(minTaxiSize, maxTaxiSize);
			Taxi taxi = getTaxiList().getNextDefaultTaxi();
			taxi.setMaxPassengers(passengers);
			taxiList_.addLast(taxi);
		}

		// Create window list and add elements
		for (int i = 0; i < numWindows; i++) {
			Window window = new Window(i + 1, getTaxiList(), getGroupList(), journeyList_);
			windowList_.add(window, i);
		}
	}

	/**
	 * Sets the processing time multiplier
	 * @param processingTimeMultiplier
	 */
	public static void setProcessingTimeMultiplier(double processingTimeMultiplier) {
		PROCESSING_TIME_MULTIPLIER = processingTimeMultiplier;
	}

	/**
	 * Simulates the time for a process
	 * @throws InterruptedException
	 */
	public static void simulateProcessingTime() throws InterruptedException {
		long processingTime = Math.round(PROCESSING_TIME_MULTIPLIER);
		Thread.sleep(processingTime);
	}

	/**
	 * Validates inputs
	 * @param numWindows
	 * @param numGroups
	 * @param minGroupSize
	 * @param maxGroupSize
	 * @param numTaxis
	 * @param minTaxiSize
	 * @param maxTaxiSize
	 * @param processingTimeMultiplier
	 */
	private void validateInputs(int numWindows, int numGroups, int minGroupSize, int maxGroupSize, int numTaxis,
			int minTaxiSize, int maxTaxiSize, int processingTimeMultiplier) {

		if (numWindows <= 0)
			throw new IllegalArgumentException("Number of windows must be greater than zero");

		if (numGroups <= 0)
			throw new IllegalArgumentException("Number of groups must be greater than zero");

		if (numTaxis <= 0)
			throw new IllegalArgumentException("Number of taxis must be greater than zero");

		if (minGroupSize <= 0)
			throw new IllegalArgumentException("Minimum number of group size must be greater than zero");

		if (minTaxiSize <= 0)
			throw new IllegalArgumentException("Minimum number of taxi size must be greater than zero");

		if (minGroupSize > maxGroupSize)
			throw new IllegalArgumentException("Maximum number of group size must be greater or equal than minimum");

		if (minTaxiSize > maxTaxiSize)
			throw new IllegalArgumentException("Maximum number of taxi size must be greater or equal than minimum");

		if (processingTimeMultiplier <= 0)
			throw new IllegalArgumentException("Processing time multiplier must be greater than zero");
	}

	/**
	 * Sets the taxi list
	 * @param taxiList_
	 */
	public void setTaxiList(TaxiList taxiList_) {
		this.taxiList_ = taxiList_;
	}

	/**
	 * Sets the group list
	 * @param groupList_
	 */
	public void setGroupList(GroupList groupList_) {
		this.groupList_ = groupList_;
	}

	public boolean isFinished() {
		return isFinished_;
	}

	/**
	 * Runs the thread. Gets all windows, starts them, and waits until all of them can end.
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(250);
			ArrayList<Thread> threads = new ArrayList<Thread>();
			log(LoggerType.INFO, "TaxiServiceModel running");
			for (int i = 0; i < getWindowList().getListSize(); i++) {
				Window window = getWindowList().get(i);
				Thread windowThread = new Thread(window);
				log(LoggerType.DEBUG, "TaxiServiceModel. started window: %d", window.getWindowNumber());
				windowThread.start();
				threads.add(windowThread);

				Thread.sleep(250);
			}

			// Wait for all threads to finish
			for (Thread t : threads) {
				t.join();
			}

			isFinished_ = true;

			log(LoggerType.INFO, "TaxiServiceModel finished running");
		} catch (Exception e) {
			log(LoggerType.ERROR, e.toString());
			e.printStackTrace();
		}
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
	 * Gets the window list
	 * @return
	 */
	public WindowList getWindowList() {
		return windowList_;
	}

	/**
	 * Adds a taxi observer
	 * @param o
	 */
	public void addTaxiObserver(TaxiListView o) {
		taxiList_.addObserver(o);
		taxiList_.notifyUpdate();
	}

	/**
	 * Adds a window observer
	 * @param o
	 */
	public void addWindowObserver(WindowListView o) {
		windowList_.addObserver(o);
		windowList_.notifyUpdate();
	}

	/**
	 * Adds a group observer
	 * @param o
	 */
	public void addGroupObserver(GroupListView o) {
		groupList_.addObserver(o);
		groupList_.notifyUpdate();
	}

	/**
	 * Adds a journey observer
	 * @param o
	 */
	public void addJourneyObserver(JourneyListView o) {
		journeyList_.addObserver(o);
		journeyList_.notifyUpdate();
	}

	/**
	 * Adds the cheapest journey observer
	 * @param o
	 */
	public void addCheapestJourneyObserver(CheapestJourneyListView o) {
		journeyList_.addObserver(o);
		journeyList_.notifyUpdate();
	}

	/**
	 * Adds the dearest journey observer
	 * @param o
	 */
	public void addDearestJourneyObserver(DearestJourneyListView o) {
		journeyList_.addObserver(o);
		journeyList_.notifyUpdate();
	}
	
	/**
	 * Adds the free taxis observer
	 * @param o
	 */
	public void addFreeTaxisObserver(FreeTaxisListView o) {
		taxiList_.addObserver(o);
		taxiList_.notifyUpdate();
	}

	/**
	 * Gets the group list
	 * @return
	 */
	public GroupList getGroupList() {
		return groupList_;
	}

	/**
	 * Gets the destination list
	 * @return
	 */
	public DestinationList getDestinationList_() {
		return destinationList_;
	}

	/**
	 * Gets the minimum group size
	 * @return
	 */
	public int getMinGroupSize_() {
		return minGroupSize_;
	}

	/**
	 * Gets the maximum group size
	 * @return
	 */
	public int getMaxGroupSize_() {
		return maxGroupSize_;
	}

	/**
	 * Gets the minimum taxi size
	 * @return
	 */
	public int getMinTaxiSize() {
		return minTaxiSize_;
	}

	/**
	 * Gets the maximum taxi size
	 * @return
	 */
	public int getMaxTaxiSize() {
		return maxTaxiSize_;
	}

	/**
	 * Gets the taxi list
	 * @return
	 */
	public TaxiList getTaxiList() {
		return taxiList_;
	}
}
