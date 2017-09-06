package model;

public class Group {
	/** Number of the group */
	private int groupNumber_;
	/** Number of passengers in group */
	private int passengers_;
	/** Destination of the group */
	private Destination destination_;

	/**
	 * Creates a new group
	 * @param groupNumber Number of the group
	 * @param passengers Number of passengers in the group
	 * @param destination Destination
	 */
	public Group(int groupNumber, int passengers, Destination destination) {
		validateInputs(groupNumber, passengers, destination);

		groupNumber_ = groupNumber;
		passengers_ = passengers;
		destination_ = destination;
	}

	/**
	 * Validates inputs
	 * @param groupNumber
	 * @param passengers
	 * @param destination
	 */
	private void validateInputs(int groupNumber, int passengers, Destination destination) {
		if (groupNumber <= 0)
			throw new IllegalArgumentException("Number of group must be greater than zero");

		if (passengers <= 0)
			throw new IllegalArgumentException("Number of passengers must be greater than zero");

		if (destination == null || destination.getName().isEmpty())
			throw new IllegalArgumentException("The destination cannot be empty");
	}

	/**
	 * Gets the group number
	 * @return
	 */
	public int getGroupNumber() {
		return groupNumber_;
	}

	/**
	 * Gets the passengers from a group
	 * @return
	 */
	public int getGroupPassengers() {
		return passengers_;
	}
	
	/**
	 * Gets the destination from a group
	 * @return
	 */
	public Destination getGroupDestination() {
		return destination_;
	}

	/**
	 * Assigns passengers of the group to a taxi
	 * @param passengers Number of passengers to assign
	 * @return
	 */
	public int assignPassengers(int passengers) {
		passengers_ = passengers_ - passengers;
		return passengers_;
	}
	public String toFormattedString() {
		return String.format("Group Number %d; Number of Passengers %d; Destination %s", groupNumber_, passengers_, destination_.toString());
	}
	
	@Override
	public String toString() {
		return String.format("GroupNumber %d, Passengers %d, Destination: %s", groupNumber_, passengers_, destination_.getName());
	}
}
