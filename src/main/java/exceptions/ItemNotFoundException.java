package exceptions;

/**
 * Manages the ItemNotFoundException exception
 */
public class ItemNotFoundException extends Exception {

	/**
	 * Creates a new ItemNotFoundException
	 * @param id
	 */
	public ItemNotFoundException(String item) {
		super("Item not found in list: %s" + item);
	}
}
