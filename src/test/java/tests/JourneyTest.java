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

import exceptions.InvalidDriverNameException;
import exceptions.InvalidRegistrationNumberException;
import model.Destination;
import model.Journey;
import model.Taxi;
import model.TaxiList;

/**
 * Manages tests of the class Journey
 */
public class JourneyTest {

	/** Valid passengers for journey 1 */
	public static int PASSENGERS_JOURNEY1 = 2;
	/** Valid passengers for journey 2 */
	public static int PASSENGERS_JOURNEY2 = 3;
	/** Valid cost (floor approximation) for journey 1 */
	public static double COST_JOURNEY1 = 25.2;
	/** Valid cost (floor approximation) for journey 2 */
	public static double COST_JOURNEY2 = 26.1;
	/** Taxi list */
	private TaxiList taxiList_;
	/** Destination 1 for the tests */
	private Destination destination1_;
	/** Destination 2 for the tests */
	private Destination destination2_;
	/** Taxi 1 for the tests */
	private Taxi taxi1_;
	/** Taxi 2 for the tests */
	private Taxi taxi2_;
	/** Journey 1 for the tests */
	private Journey journey1_;
	/** Journey 2 for the tests */
	private Journey journey2_;

	/**
	 * Initialisation before the tests
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Create destinations
		destination1_ = new Destination(DestinationTest.NAME_DESTINATION1, DestinationTest.DISTANCE_DESTINATION1);
		assertNotNull("The destination shouldn't be null", destination1_);

		destination2_ = new Destination(DestinationTest.NAME_DESTINATION2, DestinationTest.DISTANCE_DESTINATION2);
		assertNotNull("The destination shouldn't be null", destination2_);

		// Create Taxis
		taxi1_ = new Taxi(TaxiTest.REGISTRATION_NUMBER_TAXI1, TaxiTest.DRIVER_NAME_TAXI1);
		assertNotNull("The taxi shouldn't be null", taxi1_);

		taxi2_ = new Taxi(TaxiTest.REGISTRATION_NUMBER_TAXI2, TaxiTest.DRIVER_NAME_TAXI2);
		assertNotNull("The taxi shouldn't be null", taxi2_);

		taxiList_ = new TaxiList();

		// Create Journeys
		journey1_ = new Journey(GroupTest.GROUP_NUMBER_GROUP1, PASSENGERS_JOURNEY1, destination1_, taxi1_, taxiList_);
		journey1_.computeCost();
		assertNotNull("The journey shouldn't be null", journey1_);

		journey2_ = new Journey(GroupTest.GROUP_NUMBER_GROUP2, PASSENGERS_JOURNEY2, destination2_, taxi2_, taxiList_);
		journey2_.computeCost();
		assertNotNull("The journey shouldn't be null", journey2_);
	}

	/**
	 * Finalise after the tests
	 */
	@After
	public void tearDown() {
		destination1_ = null;
		destination2_ = null;
		taxi1_ = null;
		taxi2_ = null;
		journey1_ = null;
		journey2_ = null;
	}

	/**
	 * Test on the constructor
	 */
	@Test
	public void ConstructorTest() {
		// Valid test
		try {
			int groupNumber = 1;
			int passengers = 1;
			Journey journey = new Journey(groupNumber, passengers, destination1_, taxi1_, taxiList_);
			assertNotNull("The journey shouldn't be null", journey);
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}

		// Invalid test: passengers
		try {
			int groupNumber = 1;
			int passengers = 0;
			Journey journey = new Journey(groupNumber, passengers, destination1_, taxi1_, taxiList_);
			fail("The number of passengers is invalid");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), true);
		}
		// Invalid test: empty destination
		try {
			int groupNumber = 1;
			int passengers = 1;
			Journey journey = new Journey(groupNumber, passengers, null, taxi1_, taxiList_);
			fail("The destination is invalid");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), true);
		}
		// Invalid test: empty taxi
		try {
			int groupNumber = 1;
			int passengers = 1;
			Journey journey = new Journey(groupNumber, passengers, destination1_, null, taxiList_);
			fail("The taxi is invalid");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), true);
		}
	}

	/**
	 * Test on getCost
	 */
	@Test
	public void getCostTest() {
		// Valid test
		double value = Math.floor(journey1_.getCost() * 100) / 100; // floor approximation
		double expected = COST_JOURNEY1;
		assertEquals("Values should be equal", value, expected, 0);

		// Invalid test
		value = journey2_.getCost();
		expected = 0.0;
		assertNotEquals("Values should not be equal", value, expected);
	}

	/**
	 * Test on getPassengers
	 */
	@Test
	public void getPassengersTest() {
		// Valid test
		int value = journey1_.getPassengers();
		int expected = PASSENGERS_JOURNEY1;
		assertEquals("Values should be equal", value, expected);

		// Invalid test
		value = journey2_.getPassengers();
		expected = 0;
		assertNotEquals("Values should not be equal", value, expected);
	}

	/**
	 * Test on getTaxi
	 */
	@Test
	public void getTaxiTest() {
		// Valid test
		Taxi value = journey1_.getTaxi();
		Taxi expected = taxi1_;
		assertEquals("Values should be equal", value, expected);

		// Invalid test
		value = journey2_.getTaxi();
		expected = null;
		assertNotEquals("Values should not be equal", value, expected);
	}

	/**
	 * Test on getDestination
	 */
	@Test
	public void getDestinationTest() {
		// Valid test
		Destination value = journey1_.getDestination();
		Destination expected = destination1_;
		assertEquals("Values should be equal", value, expected);

		// Invalid test
		value = journey2_.getDestination();
		expected = null;
		assertNotEquals("Values should not be equal", value, expected);
	}

	/**
	 * Test on compareTo
	 */
	@Test
	public void compareToTest() {
		// Valid test
		int value = journey1_.compareTo(journey2_);
		int expected = 0;
		assertTrue("Value should be greater than zero", value > expected);

		// Invalid test
		value = journey2_.compareTo(journey1_);
		expected = 0;
		assertFalse("Value should be less than zero", value > expected);
	}
}
