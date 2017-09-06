package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import comparators.WindowIDComparator;
import exceptions.DuplicateIDException;
import interfaces.ObjectListManager;
import utils.Logger;
import utils.Logger.LoggerType;

/**
 * Manages a WindowList
 */
public class WindowList extends Observable implements ObjectListManager<Window>, Observer {

	/** List of windows */
	private ArrayList<Window> windowList_;

	/**
	 * Creates a new WindowList
	 */
	public WindowList() {
		windowList_ = new ArrayList<Window>();
	}

	/**
	 * Validates the index
	 * @param index
	 */
	private void validateIndex(int index) {
		if (index < 0)
			throw new IllegalArgumentException("Index must be greater or equal to zero");
	}

	/**
	 * Validates the index to see if the object already exists
	 * @param index
	 * @throws DuplicateIDException
	 */
	private void validateObjectInList(int index) throws DuplicateIDException {
		if (windowList_.size() > index && windowList_.get(index) != null)
			throw new DuplicateIDException("Window already exists");
	}

	/**
	 * Validates if the window is empty
	 * @param index
	 * @throws Exception
	 */
	private void validateObject(Window window) {
		if (window == null)
			throw new IllegalArgumentException("The window cannot be empty");
	}

	/**
	 * Adds a window to the next possible position in list
	 * @param window
	 */
	public void add(Window window) {
		validateObject(window);
		for (int i = 0; i < windowList_.size(); i++) {
			if (windowList_.get(i) == null) {
				windowList_.add(i, window);
				return;
			}
		}
	}

	/**
	 * Adds a window to the given index
	 * @param window
	 * @param index
	 * @throws DuplicateIDException
	 */
	public void add(Window window, int index) throws DuplicateIDException {
		validateIndex(index);
		validateObjectInList(index);
		validateObject(window);
		windowList_.add(index, window);
		window.addObserver(this);
		notifyUpdate();
	}

	/**
	 * Gets the size of the list
	 * @return
	 */
	public int getListSize() {
		return windowList_.size();
	}

	/**
	 * Get window in a given index
	 * @param index
	 * @return
	 */
	public Window get(int index) {
		validateIndex(index);
		return windowList_.get(index);
	}

	/**
	 * Gets the list
	 */
	public ArrayList<Window> getList() {
		return windowList_;
	}

	/**
	 * Notifies observers that the list changed
	 */
	public void notifyUpdate() {
		setChanged();
		notifyObservers();
		clearChanged();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		notifyUpdate();
	}

	/**
	 * Orders the list
	 */
	@Override
	public void orderList() {
		Collections.sort(windowList_, new WindowIDComparator());
	}

	/**
	 * Gets a string representation of the list
	 */
	@Override
	public String toString() {
		String result = String.format("{size:%d,windows:[", getListSize());
		for (Window window : windowList_) {
			result += window.toString();
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
