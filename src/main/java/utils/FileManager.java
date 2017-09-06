package utils;

import java.io.*;
import javax.naming.InvalidNameException;
import exceptions.DuplicateIDException;
import exceptions.InvalidDriverNameException;
import exceptions.InvalidLineColumnsException;
import exceptions.InvalidRegistrationNumberException;
import model.Destination;
import model.DestinationList;
import model.Taxi;
import model.TaxiList;
import utils.Logger.LoggerType;

/**
 * Manages operations on files
 */
public class FileManager {

	/**
	 * Creates a new File Manager
	 */
	protected FileManager() {
	}

	/**
	 * Loads the taxi information file
	 * @param pathName Path of the file
	 * @param taxiList List of taxis
	 * @throws IOException
	 * @throws InvalidLineColumnsException
	 * @throws InvalidRegistrationNumberException
	 * @throws InvalidDriverNameException 
	 */
	public static void loadFileTaxiInformation(String pathName, TaxiList taxiList)
			throws IOException, InvalidLineColumnsException, InvalidRegistrationNumberException, InvalidDriverNameException {
		log(LoggerType.DEBUG, "Loading file: %s", pathName);
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new FileReader(pathName)); // Create reader
			String inputLine = buff.readLine(); // Read first line
			// Read lines
			while (inputLine != null) {
				// Do not process empty lines or if they start with #
				if (inputLine.isEmpty() || inputLine.startsWith("#")) {
					inputLine = buff.readLine();
					continue;
				}

				// Split line into parts
				String[] data = inputLine.split(",");
				if (data.length != 2)
					throw new InvalidLineColumnsException(2, data.length);
				// Create taxi object with line parts
				Taxi taxi = new Taxi(data[0].trim(), data[1].trim());
				// Add to list
				taxiList.addLastDefaultTaxi(taxi);
				// Read next line
				inputLine = buff.readLine();
			}
			log(LoggerType.DEBUG, "Loading done.");
		} catch (FileNotFoundException e) {
			log(LoggerType.ERROR, e.toString());
			throw e;
		} catch (IOException e) {
			log(LoggerType.ERROR, e.toString());
			throw e;
		} catch (InvalidLineColumnsException e) {
			log(LoggerType.ERROR, e.toString());
			throw e;
		} catch (InvalidRegistrationNumberException e) {
			log(LoggerType.ERROR, e.toString());
			throw e;
		} catch (InvalidDriverNameException e) {
			log(LoggerType.ERROR, e.toString());
			throw e;
		} finally {
			try {
				if (buff != null)
					buff.close();
			} catch (IOException e) {
				// Don't do anything
			}
		}
	}

	/**
	 * Loads the destination information file
	 * @param pathName Path of the file
	 * @param destinationList List of destinations
	 * @throws IOException
	 * @throws InvalidLineColumnsException
	 * @throws DuplicateIDException
	 */
	public static void loadFileDestinationInformation(String pathName, DestinationList destinationList)
			throws IOException, InvalidLineColumnsException, DuplicateIDException {
		log(LoggerType.DEBUG, "Loading file: %s", pathName);
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new FileReader(pathName)); // Create reader
			String inputLine = buff.readLine(); // Read first line
			// Read lines
			while (inputLine != null) {
				// Do not process empty lines or if they start with #
				if (inputLine.isEmpty() || inputLine.startsWith("#")) {
					inputLine = buff.readLine();
					continue;
				}

				// Split line into parts
				String[] data = inputLine.split(",");
				if (data.length != 2)
					throw new InvalidLineColumnsException(2, data.length);
				double distance = Double.parseDouble(data[1].trim());
				// Create destination object with line parts
				Destination destination = new Destination(data[0].trim(), distance);
				// Add to list
				destinationList.add(data[0].trim(), destination);
				// Read next line
				inputLine = buff.readLine();
			}
			log(LoggerType.DEBUG, "Loading done.");
		} catch (FileNotFoundException e) {
			log(LoggerType.ERROR, e.toString());
			throw e;
		} catch (IOException e) {
			log(LoggerType.ERROR, e.toString());
			throw e;
		} catch (InvalidLineColumnsException e) {
			log(LoggerType.ERROR, e.toString());
			throw e;
		} catch (IllegalArgumentException e) {
			log(LoggerType.ERROR, e.toString());
			throw e;
		} catch (DuplicateIDException e) {
			log(LoggerType.ERROR, e.toString());
			throw e;
		} finally {
			try {
				if (buff != null)
					buff.close();
			} catch (IOException e) {
				// Don't do anything
			}
		}
	}

	/**
	 * Logs a message
	 * @param logType
	 * @param format
	 * @param values
	 */
	public static void log(LoggerType logType, String message) {
		Logger logger_ = Logger.getInstance(logType);
		logger_.log(message);
	}

	/**
	 * Logs a message with format
	 * @param logType
	 * @param format
	 * @param values
	 */
	public static void log(LoggerType logType, String format, Object... values) {
		Logger logger_ = Logger.getInstance(logType);
		logger_.log(format, values);
	}
}
