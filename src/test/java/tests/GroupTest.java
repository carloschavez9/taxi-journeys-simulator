package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import exceptions.InvalidRegistrationNumberException;
import model.Destination;
import model.Group;

public class GroupTest {

	/** Valid group number for group 1 */
	public static int GROUP_NUMBER_GROUP1 = 1;
	/** Valid number of passengers for group 1 */
	public static int PASSENGER_NUMBER_GROUP1 = 5;
	/** Valid group number for group 2 */
	public static int GROUP_NUMBER_GROUP2 = 2;
	/** Valid number of passengers for group 2 */
	public static int PASSENGER_NUMBER_GROUP2 = 3;

	/** Group 1 for the tests */
	private Group group1_;
	/** Group 2 for the tests */
	private Group group2_;

	/** Destination 1 for the tests */
	private Destination destination1_;
	/** Destination 2 for the tests */
	private Destination destination2_;

	/**
	 * Initialisation before the tests
	 * @throws InvalidRegistrationNumberException
	 * @throws IllegalArgumentException
	 */
	@Before
	public void setUp() throws IllegalArgumentException, InvalidRegistrationNumberException {
		destination1_ = new Destination(DestinationTest.NAME_DESTINATION1, DestinationTest.DISTANCE_DESTINATION1);
		assertNotNull("The destination shouldn't be null", destination1_);

		destination2_ = new Destination(DestinationTest.NAME_DESTINATION2, DestinationTest.DISTANCE_DESTINATION2);
		assertNotNull("The destination shouldn't be null", destination2_);

		// Create Taxis
		group1_ = new Group(GROUP_NUMBER_GROUP1, PASSENGER_NUMBER_GROUP1, destination1_);
		assertNotNull("The Group shouldn't be null", group1_);

		group2_ = new Group(GROUP_NUMBER_GROUP2, PASSENGER_NUMBER_GROUP2, destination2_);
		assertNotNull("The Group shouldn't be null", group2_);
	}

	/**
	 * Finalise after the tests
	 */
	@After
	public void tearDown() {
		group1_ = null;
		group2_ = null;
	}

	/**
	 * Test on the constructor
	 */
	@Test
	public void ConstructorTest() {
		// Valid test
		try {
			Group group = new Group(1, 3, destination1_);
			assertNotNull("The group shouldn't be null", group);
		} catch (IllegalArgumentException e) {
			fail("Parameters are valid");
		}

		// Invalid test
		try {
			Group group = new Group(-1, -1, null);
			fail("The registration number is invalid");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), true);
		}
	}

	/**
	 * Test on getGroupNumberTest
	 */
	public void getGroupNumberTest() {
		int value = group1_.getGroupNumber();
		int expected = GROUP_NUMBER_GROUP1;
		assertEquals("Values should be equal", value, expected);
	}

	/**
	 * Test on getGroupNumberTest
	 */
	public void getGroupPassengersTest() {
		int value = group1_.getGroupPassengers();
		int expected = PASSENGER_NUMBER_GROUP1;
		assertEquals("Values should be equal", value, expected);
	}

	/**
	 * Test on getGroupNumberTest
	 */
	public void getGroupDestinationTest() {
		Destination value = group1_.getGroupDestination();
		Destination expected = destination1_;
		assertEquals("Values should be equal", value, expected);
	}

	/**
	 * Test on getGroupNumberTest
	 */
	public void assignPassengersTest() {
		int expected = group1_.getGroupPassengers() - 1;
		int value = group1_.assignPassengers(1);
		assertEquals("Values should be equal", value, expected);
	}
}
