package exceptions;

/**
 * Manages the InvalidRegistrationNumberException exception
 */
public class InvalidRegistrationNumberException extends Exception {

	/**
	 * Creates a new InvalidRegistrationNumberException
	 * @param registrationNumber Error value
	 */
	public InvalidRegistrationNumberException(String registrationNumber) {
		super(String.format(
				"Invalid registration number, must have a length of 6 and must be in the form 'dddSSS' where d is a number between 0 and 9, and S is a letter from A to Z in upper case. Value: %s",
				registrationNumber));
	}
}
