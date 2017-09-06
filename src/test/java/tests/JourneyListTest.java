package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.DuplicateIDException;
import exceptions.InvalidDriverNameException;
import exceptions.InvalidRegistrationNumberException;
import model.Destination;
import model.Journey;
import model.JourneyList;
import model.Taxi;
import model.TaxiList;

/**
 * Manages tests of the class JourneyList
 */
public class JourneyListTest {

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
	/** JourneyList for the tests */
	private JourneyList journeyList_;

	/**
	 * Initialisation before the tests
	 * @throws Exception 
	 * @throws InvalidNameException
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

		TaxiList taxiList = new TaxiList();

		// Create Journeys and list
		journey1_ = new Journey(GroupTest.GROUP_NUMBER_GROUP1, JourneyTest.PASSENGERS_JOURNEY1, destination1_, taxi1_,
				taxiList);
		assertNotNull("The journey shouldn't be null", journey1_);

		journey2_ = new Journey(GroupTest.GROUP_NUMBER_GROUP2, JourneyTest.PASSENGERS_JOURNEY2, destination2_, taxi2_,
				taxiList);
		assertNotNull("The journey shouldn't be null", journey2_);

		journeyList_ = new JourneyList();
		assertNotNull("The journey list shouldn't be null", journeyList_);

		journeyList_.add(journey1_);
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
		journeyList_ = null;
	}

	/**
	 * Test on the constructor
	 */
	@Test
	public void ConstructorTest() {
		// Valid test
		JourneyList journeyList = new JourneyList();
		assertNotNull("The journey list shouldn't be null", journeyList);
	}

	/**
	 * Test on addJourney
	 */
	@Test
	public void addJourneyTest() {
		// Valid test
		try {
			journeyList_.add(journey2_);
			assertTrue("No error is expected", true);
		} catch (IllegalArgumentException e) {
			fail("The journey is valid");
		}
		// Invalid test: null journey
		try {
			journeyList_.add(null);
			fail("The journey is invalid");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), true);
		}
	}

	/**
	 * Test on getJourneys
	 */
	@Test
	public void getJourneysTest() {
		// Valid test
		ArrayList<Journey> value = journeyList_.getList();
		assertNotNull("The journey list shouldn't be null", value);
	}
}
