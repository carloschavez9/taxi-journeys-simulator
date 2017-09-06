package exceptions;

/**
 * Manages the InvalidLineColumnsException exception
 */
public class InvalidLineColumnsException extends Exception {

	/**
	 * Creates a new InvalidLineColumnsException
	 * @param expectedColumns Number of expected columns
	 * @param columns Number of columns found
	 */
	public InvalidLineColumnsException(int expectedColumns, int columns) {
		super(String.format("Invalid line format. Expected columns: %s, found: %s", expectedColumns, columns));
	}
}
