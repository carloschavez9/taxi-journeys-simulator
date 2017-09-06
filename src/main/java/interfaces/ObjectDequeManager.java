package interfaces;

import java.util.Deque;

import utils.Logger.LoggerType;

/**
 * Interface for a manager of a deque of objects
 */
public interface ObjectDequeManager<T> extends ObjectObservable {

	/**
	 * Gets size of deque
	 * @return
	 */
	public int getListSize();

	/**
	 * Gets next object on the deque
	 */
	public T getNext();

	/**
	 * Gets the deque
	 * @return
	 */
	public Deque<T> getDeque();

	/**
	 * Adds an object to the last position of the deque
	 * @param object
	 */
	public void addLast(T object);

	/**
	 * Logs a message
	 * @param logType
	 * @param format
	 * @param values
	 */
	public void log(LoggerType logType, String message);

	/**
	 * Logs a message with format
	 * @param logType
	 * @param format
	 * @param values
	 */
	public void log(LoggerType logType, String format, Object... values);
}
