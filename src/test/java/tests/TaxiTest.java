package tests;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidDriverNameException;
import exceptions.InvalidRegistrationNumberException;
import model.Taxi;

/**
 * Manages tests of the class Taxi
 */
public class TaxiTest {

	/** Valid registration number of taxi 1 */
	public static String REGISTRATION_NUMBER_TAXI1 = "335IDJ";
	/** Valid driver name of taxi 1 */
	public static String DRIVER_NAME_TAXI1 = "Gretchen Jenkins";
	/** Valid registration number of taxi 2 */
	public static String REGISTRATION_NUMBER_TAXI2 = "043NXM";
	/** Valid driver name of taxi 2 */
	public static String DRIVER_NAME_TAXI2 = "Thor William";

	/** Taxi 1 for the tests */
	private Taxi taxi1_;
	/** Taxi 2 for the tests */
	private Taxi taxi2_;

	/**
	 * Initialisation before the tests
	 * @throws InvalidRegistrationNumberException
	 * @throws IllegalArgumentException
	 * @throws InvalidDriverNameException
	 */
	@Before
	public void setUp()
			throws IllegalArgumentException, InvalidRegistrationNumberException, InvalidDriverNameException {
		// Create Taxis
		taxi1_ = new Taxi(REGISTRATION_NUMBER_TAXI1, DRIVER_NAME_TAXI1);
		assertNotNull("The taxi shouldn't be null", taxi1_);

		taxi2_ = new Taxi(REGISTRATION_NUMBER_TAXI2, DRIVER_NAME_TAXI2);
		assertNotNull("The taxi shouldn't be null", taxi2_);
	}

	/**
	 * Finalise after the tests
	 */
	@After
	public void tearDown() {
		taxi1_ = null;
		taxi2_ = null;
	}

	/**
	 * Test on the constructor
	 */
	@Test
	public void ConstructorTest() {
		// Valid test
		try {
			String registrationNumber = "414NWO";
			String driverName = "Candace Williams";
			Taxi taxi = new Taxi(registrationNumber, driverName);
			assertNotNull("The taxi shouldn't be null", taxi);
		} catch (InvalidRegistrationNumberException e) {
			fail("The registration number is valid");
		} catch (InvalidDriverNameException e) {
			fail("The name is valid");
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}

		// Invalid test: empty registration number
		try {
			String registrationNumber = "";
			String driverName = "Elliott Bartlett";
			Taxi taxi = new Taxi(registrationNumber, driverName);
			fail("The registration number is invalid");
		} catch (InvalidRegistrationNumberException e) {
			fail("The registration number is empty");
		} catch (InvalidDriverNameException e) {
			fail("The name is valid");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), true);
		}
		// Invalid test: registration number with only numbers
		try {
			String registrationNumber = "684423";
			String driverName = "Elliott Bartlett";
			Taxi taxi = new Taxi(registrationNumber, driverName);
			fail("The registration number is invalid");
		} catch (InvalidRegistrationNumberException e) {
			assertTrue(e.getMessage(), true);
		} catch (InvalidDriverNameException e) {
			fail("The name is valid");
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}
		// Invalid test: registration number with only letters and spaces
		try {
			String registrationNumber = "AB  CD";
			String driverName = "Elliott Bartlett";
			Taxi taxi = new Taxi(registrationNumber, driverName);
			fail("The registration number is invalid");
		} catch (InvalidRegistrationNumberException e) {
			assertTrue(e.getMessage(), true);
		} catch (InvalidDriverNameException e) {
			fail("The name is valid");
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}
		// Invalid test: registration number with more than 6 characters
		try {
			String registrationNumber = "414NWO0";
			String driverName = "Elliott Bartlett";
			Taxi taxi = new Taxi(registrationNumber, driverName);
			fail("The registration number is invalid");
		} catch (InvalidRegistrationNumberException e) {
			assertTrue(e.getMessage(), true);
		} catch (InvalidDriverNameException e) {
			fail("The name is valid");
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}
		// Invalid test: registration number with less than 6 characters
		try {
			String registrationNumber = "414NW";
			String driverName = "Elliott Bartlett";
			Taxi taxi = new Taxi(registrationNumber, driverName);
			fail("The registration number is invalid");
		} catch (InvalidRegistrationNumberException e) {
			assertTrue(e.getMessage(), true);
		} catch (InvalidDriverNameException e) {
			fail("The name is valid");
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}

		// Invalid test: empty driver name
		try {
			String registrationNumber = "569VUP";
			String driverName = "";
			Taxi taxi = new Taxi(registrationNumber, driverName);
			fail("The name is invalid");
		} catch (InvalidRegistrationNumberException e) {
			fail("The registration number is valid");
		} catch (InvalidDriverNameException e) {
			fail("The driver name is emptyF");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), true);
		}
		// Invalid test: driver name with numbers
		try {
			String registrationNumber = "569VUP";
			String driverName = "569VUP";
			Taxi taxi = new Taxi(registrationNumber, driverName);
			fail("The name is invalid");
		} catch (InvalidRegistrationNumberException e) {
			fail("The registration number is valid");
		} catch (InvalidDriverNameException e) {
			assertTrue(e.getMessage(), true);
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}
		// Invalid test: driver name with no spaces
		try {
			String registrationNumber = "569VUP";
			String driverName = "ElliottBartlett";
			Taxi taxi = new Taxi(registrationNumber, driverName);
			fail("The name is invalid");
		} catch (InvalidRegistrationNumberException e) {
			fail("The registration number is valid");
		} catch (InvalidDriverNameException e) {
			assertTrue(e.getMessage(), true);
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}
		// Invalid test: driver name with more than 1 space
		try {
			String registrationNumber = "569VUP";
			String driverName = "Elli ott Bart lett";
			Taxi taxi = new Taxi(registrationNumber, driverName);
			fail("The name is invalid");
		} catch (InvalidRegistrationNumberException e) {
			fail("The registration number is valid");
		} catch (InvalidDriverNameException e) {
			assertTrue(e.getMessage(), true);
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}
	}

	/**
	 * Test on getRegistrationNumber
	 */
	@Test
	public void getRegistrationNumberTest() {
		// Valid test
		String value = taxi1_.getRegistrationNumber();
		String expected = REGISTRATION_NUMBER_TAXI1;
		assertEquals("Values should be equal", value, expected);
	}

	/**
	 * Test on getDriverName
	 */
	@Test
	public void getDriverNameTest() {
		// Valid test
		String value = taxi1_.getDriverName();
		String expected = DRIVER_NAME_TAXI1;
		assertEquals("Values should be equal", value, expected);
	}

	/**
	 * Test on compareTo
	 */
	@Test
	public void compareToTest() {
		// Valid test
		int value = taxi1_.compareTo(taxi2_);
		int expected = 0;
		assertTrue("Value should be less than zero", value < expected);
	}
}
