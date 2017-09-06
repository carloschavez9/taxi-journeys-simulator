package interfaces;

import java.util.List;

import utils.Logger.LoggerType;

/**
 * Interface for a manager of a list of objects
 */
public interface ObjectListManager<T> {

	/**
	 * Gets size of list
	 * @return
	 */
	public int getListSize();

	/**
	 * Gets an object at the specified index
	 * @param index
	 * @throws Exception
	 */
	public T get(int index) throws Exception;

	/**
	 * Gets the list
	 * @return
	 */
	public List<T> getList();

	/**
	 * Adds an object to the list
	 * @param object
	 */
	public void add(T object);

	/**
	 * Orders the list
	 */
	public void orderList();

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
