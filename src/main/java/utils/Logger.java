package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Manages the logger for the taxi service
 */
public class Logger {

	/**
	 * Types of logs
	 */
	public enum LoggerType {
		INFO, ERROR, DEBUG
	}

	/** Logger file path */
	private static final String LOGGER_PATH_FORMAT = "logs/log_%s_%s.log";

	/** Singleton loggers */
	private static HashMap<LoggerType, Logger> taxiServiceLoggers_ = null;
	/** File for the logger */
	private File file_;
	/** Indicates if the log can write */
	private boolean canWrite;
	/** Path of the log */
	private String logPathName_;

	/**
	 * Creates a new logger
	 * @param logType
	 */
	protected Logger(LoggerType logType) {
		try {
			canWrite = false;
			LocalDateTime ldt = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
			String text = ldt.format(formatter);
			logPathName_ = String.format(LOGGER_PATH_FORMAT, logType, text);
			file_ = new File(logPathName_);
			if (!file_.exists())
				file_.createNewFile();
			canWrite = true;
		} catch (IOException e) {
			System.out.println("Error while creating the log file, no logs will be recorded during this session.");
			e.printStackTrace();
		}
	}

	/**
	 * Gets the path name of the logs
	 * @return
	 */
	public String getLogPathName() {
		return logPathName_;
	}

	/**
	 * Gets the instance of the logger
	 * @param logType
	 * @return
	 */
	public synchronized static Logger getInstance(LoggerType logType) {
		if (taxiServiceLoggers_ == null)
			taxiServiceLoggers_ = new HashMap<LoggerType, Logger>();
		if (!taxiServiceLoggers_.containsKey(logType))
			taxiServiceLoggers_.put(logType, new Logger(logType));

		return taxiServiceLoggers_.get(logType);
	}

	/**
	 * Logs a message
	 * @param message
	 */
	public synchronized void log(String message) {
		if (canWrite) {
			try {
				BufferedWriter bufferedWriter_ = new BufferedWriter(new FileWriter(file_, true));
				bufferedWriter_.write(message);
				bufferedWriter_.newLine();
				bufferedWriter_.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Logs a message with format
	 * @param format
	 * @param strings
	 */
	public synchronized void log(String format, Object... strings) {
		if (canWrite) {
			try {
				BufferedWriter bufferedWriter_ = new BufferedWriter(new FileWriter(file_, true));
				bufferedWriter_.write(String.format(format, strings));
				bufferedWriter_.newLine();
				bufferedWriter_.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
