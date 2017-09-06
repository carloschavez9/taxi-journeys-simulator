package model;

/**
 * Manages operations of a destination
 */
public class Destination implements Comparable<Destination>{
	/** Name of the destination */
	private String name_;
	/** Distance of the destination from the central point */
	private double distance_;

	/**
	 * Creates a new destination with details
	 * @param name Name of the destination
	 * @param distance Distance from the central point to the destination
	 * @throws IllegalArgumentException
	 */
	public Destination(String name, double distance) throws IllegalArgumentException {
		// Validate values
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("The destination name cannot be empty");
		else if (distance <= 0)
			throw new IllegalArgumentException("The destination distance must be a valid value");

		// Set values
		name_ = name;
		distance_ = distance;
	}

	/**
	 * Gets the name of the destination
	 * @return
	 */
	public String getName() {
		return name_;
	}

	/**
	 * Gets the distance of the destination
	 * @return
	 */
	public double getDistance() {
		return distance_;
	}

	/**
	 * Gets a string of the object
	 */
	public String toString() {
		return String.format("{%s,%.1f}", getName(), getDistance());
	}

	/**
	 * Compares two destinations using the name
	 * @param arg0 The Destination to compare to
	 */
	@Override
	public int compareTo(Destination arg0) {
		return arg0.getName().compareTo(this.getName());
	}
}
