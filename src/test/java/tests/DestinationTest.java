package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Destination;

/**
 * Manages tests of the class Destination
 */
public class DestinationTest {

	/** Valid name for destination 1 */
	public static String NAME_DESTINATION1 = "The Sheraton Grand Hotel";
	/** Valid distance for destination 1 */
	public static Double DISTANCE_DESTINATION1 = 7.2;
	/** Valid name for destination 2 */
	public static String NAME_DESTINATION2 = "Apex Waterloo Place Hotel";
	/** Valid distance for destination 2 */
	public static Double DISTANCE_DESTINATION2 = 8.1;

	/** Destination 1 for the tests */
	private Destination destination1_;
	/** Destination 2 for the tests */
	private Destination destination2_;

	/**
	 * Initialisation before the tests
	 * @throws IllegalArgumentException
	 */
	@Before
	public void setUp() throws IllegalArgumentException {
		destination1_ = new Destination(NAME_DESTINATION1, DISTANCE_DESTINATION1);
		assertNotNull("The destination shouldn't be null", destination1_);

		destination2_ = new Destination(NAME_DESTINATION2, DISTANCE_DESTINATION2);
		assertNotNull("The destination shouldn't be null", destination2_);
	}

	/**
	 * Finalise after the tests
	 */
	@After
	public void tearDown() {
		destination1_ = null;
		destination2_ = null;
	}

	/**
	 * Test on the constructor
	 */
	@Test
	public void ConstructorTest() {
		// Valid test
		try {
			String name = "The Kitchin";
			double distance = 9.4;
			Destination destination = new Destination(name, distance);
			assertNotNull("The destination shouldn't be null", destination);
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}

		// Invalid test: destination name
		try {
			String name = ""; // Invalid
			double distance = 5.8;
			Destination destination = new Destination(name, distance);
			fail("The name is invalid");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), true);
		}
		// Invalid test: destination distance
		try {
			String name = "The Kitchin";
			double distance = -1.3; // Invalid
			Destination destination = new Destination(name, distance);
			fail("The distance is invalid");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), true);
		}
	}

	/**
	 * Test on getName
	 */
	@Test
	public void getNameTest() {
		// Valid test
		String value = destination1_.getName();
		String expected = NAME_DESTINATION1;
		assertEquals("Values should be equal", value, expected);

		// Invalid test
		value = destination2_.getName();
		expected = "";
		assertNotEquals("Values should not be equal", value, expected);
	}

	/**
	 * Test on getDistance
	 */
	@Test
	public void getDistanceTest() {
		// Valid test
		double value = destination1_.getDistance();
		double expected = DISTANCE_DESTINATION1;
		assertEquals("Values should be equal", value, expected, 0);

		// Invalid test
		value = destination2_.getDistance();
		expected = 0.0;
		assertNotEquals("Values should not be equal", value, expected);
	}
	
	/**
	 * Test on compareTo
	 */
	@Test
	public void compareToTest() {
		// Valid test
		int value = destination1_.compareTo(destination2_);
		int expected = 0;
		assertTrue("Value should be less than zero", value < expected);

		// Invalid test
		value = destination2_.compareTo(destination1_);
		expected = 0;
		assertFalse("Value should be greater than zero", value < expected);
	}
}
