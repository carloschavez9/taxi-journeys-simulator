package exceptions;

/**
 * Manages the InvalidDriverNameException exception
 */
public class InvalidDriverNameException extends Exception {

	/**
	 * Creates a new InvalidDriverNameException
	 * @param name Error value
	 */
	public InvalidDriverNameException(String name) {
		super(String.format(
				"Invalid driver name, it cannot contain numbers and has to be in the form of 'Name Surname'. Value: %s",
				name));
	}
}
