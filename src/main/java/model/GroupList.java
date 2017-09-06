package model;

import java.util.ArrayDeque;
import java.util.Observable;
import interfaces.ObjectDequeManager;
import utils.Logger;
import utils.Logger.LoggerType;

public class GroupList extends Observable implements ObjectDequeManager<Group> {

	/** List of groups */
	private ArrayDeque<Group> groups_;
	/** Current number for new groups */
	private int groupCounter_;

	/**
	 * Creates a new list of groups
	 * @throws Exception
	 */
	public GroupList() throws Exception {
		// Initialise variables
		groups_ = new ArrayDeque<Group>();
		groupCounter_ = 0;
	}

	/**
	 * Validates the Group
	 * @param group
	 */
	private void validateGroup(Group group) {
		// Validate empty
		if (group == null || group.getGroupNumber() <= 0 || group.getGroupDestination() == null)
			throw new IllegalArgumentException("The group cannot be empty");
	}
	
	/**
	 * Gets the next number for a group
	 * @return
	 */
	public int getNextGroupNumber() {
		groupCounter_ += 1;
		return groupCounter_;
	}

	/**
	 * Gets the size of the list
	 * @return
	 */
	public int getListSize() {
		return groups_.size();
	}

	/**
	 * Add group to the end of the list. Synchronised for thread-safe
	 * @param taxi
	 */
	public synchronized void addLast(Group group) {
		validateGroup(group);
		groups_.add(group);
		notifyUpdate();
	}

	/**
	 * Gets next available group, removing it from the list. Synchronised for thread-safe
	 * @return
	 */
	public synchronized Group getNext() {
		Group group = groups_.poll();
		notifyUpdate();

		return group;
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
		String result = String.format("{size:%d,groups:[", getListSize());
		for (Group group : groups_) {
			result += group.toString();
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
	public ArrayDeque<Group> getDeque() {
		return groups_;
	}

	/**
	 * Gets the first N groups
	 * @param n
	 * @return
	 */
	public ArrayDeque<Group> getNGroups(int n) {
		if (groups_.size() > n) {
			ArrayDeque<Group> newGroups = new ArrayDeque<Group>();
			ArrayDeque<Group> copyGroups = groups_.clone();
			for (int i = 0; i < n; i++) {
				newGroups.add(copyGroups.poll());
			}
			return newGroups;
		} else {
			return groups_;
		}

	}
}
