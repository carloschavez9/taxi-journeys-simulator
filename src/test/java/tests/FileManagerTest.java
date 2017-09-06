package tests;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

import javax.naming.InvalidNameException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.DuplicateIDException;
import exceptions.InvalidDriverNameException;
import exceptions.InvalidLineColumnsException;
import exceptions.InvalidRegistrationNumberException;
import model.DestinationList;
import model.JourneyList;
import model.TaxiList;
import utils.FileManager;

/**
 * Manages tests of the class FileManager
 */
public class FileManagerTest {

	private static String FILE_TAXIS_VALID = "data/taxis.csv";
	private static String FILE_DESTINATIONS_VALID = "data/destinations.csv";

	private static String FILE_NOISE = "data/input_tests/file_noise.csv";
	private static String FILE_INVALIDCOLUMNS = "data/input_tests/file_invalidcolumns.csv";

	private static String FILE_TAXIS_INVALID1 = "data/input_tests/taxis_invalid1.csv";
	private static String FILE_TAXIS_INVALID2 = "data/input_tests/taxis_invalid2.csv";
	private static String FILE_DESTINATIONS_INVALID = "data/input_tests/destinations_invalid.csv";

	/** Taxi list with all taxis and their information */
	private TaxiList taxiList_;
	/** Destination list with all the destinations and their information */
	private DestinationList destinationList_;

	/**
	 * Initialisation before the tests
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception {
		taxiList_ = new TaxiList();
		destinationList_ = new DestinationList();
	}

	/**
	 * Finalise after the tests
	 */
	@After
	public void tearDown() {
		taxiList_ = null;
		destinationList_ = null;
	}

	/**
	 * Test on invalid files
	 */
	@Test
	public void invalidFilesTest() {
		// Invalid test: file with noise
		try {
			FileManager.loadFileTaxiInformation(FILE_NOISE, taxiList_);
			fail("File is invalid");
		} catch (InvalidRegistrationNumberException e) {
			assertTrue("File is invalid", true);
		} catch (InvalidLineColumnsException e) {
			assertTrue("File is invalid", true);
		} catch (IOException e) {
			assertTrue("File is invalid", true);
		} catch (InvalidDriverNameException e) {
			assertTrue("File is invalid", true);
		}

		// Invalid test: file invalid columns
		try {
			FileManager.loadFileTaxiInformation(FILE_INVALIDCOLUMNS, taxiList_);
			fail("File is invalid");
		} catch (InvalidRegistrationNumberException e) {
			fail(e.getMessage());
		} catch (InvalidDriverNameException e) {
			fail(e.getMessage());
		} catch (InvalidLineColumnsException e) {
			assertTrue(e.getMessage(), true);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test on the loadFileTaxiInformation
	 */
	@Test
	public void loadFileTaxiInformationTest() {
		// Valid test
		try {
			FileManager.loadFileTaxiInformation(FILE_TAXIS_VALID, taxiList_);
			assertTrue("File is valid", true);
		} catch (InvalidRegistrationNumberException e) {
			fail(e.getMessage());
		} catch (InvalidDriverNameException e) {
			fail(e.getMessage());
		} catch (InvalidLineColumnsException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Invalid test: File with invalid registration number
		try {
			FileManager.loadFileTaxiInformation(FILE_TAXIS_INVALID1, taxiList_);
			fail("File is invalid");
		} catch (InvalidRegistrationNumberException e) {
			assertTrue(e.getMessage(), true);
		} catch (InvalidDriverNameException e) {
			fail(e.getMessage());
		} catch (InvalidLineColumnsException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Invalid test: File with invalid driver name
		try {
			FileManager.loadFileTaxiInformation(FILE_TAXIS_INVALID2, taxiList_);
			fail("File is invalid");
		} catch (InvalidRegistrationNumberException e) {
			fail(e.getMessage());
		} catch (InvalidDriverNameException e) {
			assertTrue(e.getMessage(), true);
		} catch (InvalidLineColumnsException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test on the loadFileDestinationInformation
	 */
	@Test
	public void loadFileDestinationInformationTest() {
		// Valid test
		try {
			FileManager.loadFileDestinationInformation(FILE_DESTINATIONS_VALID, destinationList_);
			assertTrue("File is valid", true);
		} catch (NumberFormatException e) {
			fail(e.getMessage());
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (DuplicateIDException e) {
			fail(e.getMessage());
		} catch (InvalidLineColumnsException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		// Invalid test: File with invalid distance
		try {
			FileManager.loadFileDestinationInformation(FILE_DESTINATIONS_INVALID, destinationList_);
			fail("File is invalid");
		} catch (NumberFormatException e) {
			assertTrue(e.getMessage(), true);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		} catch (DuplicateIDException e) {
			fail(e.getMessage());
		} catch (InvalidLineColumnsException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
