package interfaces;

import java.util.Map;

import exceptions.DuplicateIDException;
import utils.Logger.LoggerType;

/**
 * Interface for a manager of a map of objects
 */
public interface ObjectMapManager<E, T> {

	/**
	 * Gets size of map
	 * @return
	 */
	public int getMapSize();

	/**
	 * Gets an object with the specified key
	 * @param index
	 * @throws Exception
	 */
	public T get(E key) throws Exception;

	/**
	 * Gets the map
	 * @return
	 */
	public Map<E, T> getMap();

	/**
	 * Adds an object to the map
	 * @param object
	 */
	public void add(E key, T object) throws DuplicateIDException, IllegalArgumentException;

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
