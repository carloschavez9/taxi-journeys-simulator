package exceptions;

/**
 * Manages the DuplicateIDException exception
 */
public class DuplicateIDException extends Exception {

	/**
	 * Creates a new DuplicateIDException
	 * @param id
	 */
	public DuplicateIDException(String id) {
		super("Duplicate id: %s" + id);
	}
}
