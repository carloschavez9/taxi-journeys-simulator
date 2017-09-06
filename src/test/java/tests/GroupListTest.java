package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayDeque;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidRegistrationNumberException;
import model.Destination;
import model.Group;
import model.GroupList;

public class GroupListTest {

	/** GroupList for the tests */
	private GroupList groupList_;
	
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
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception {
		destination1_ = new Destination(DestinationTest.NAME_DESTINATION1, DestinationTest.DISTANCE_DESTINATION1);
		assertNotNull("The destination shouldn't be null", destination1_);

		destination2_ = new Destination(DestinationTest.NAME_DESTINATION2, DestinationTest.DISTANCE_DESTINATION2);
		assertNotNull("The destination shouldn't be null", destination2_);

		// Create Taxis
		group1_ = new Group(GROUP_NUMBER_GROUP1, PASSENGER_NUMBER_GROUP1, destination1_);
		assertNotNull("The Group shouldn't be null", group1_);

		group2_ = new Group(GROUP_NUMBER_GROUP2, PASSENGER_NUMBER_GROUP2, destination2_);
		assertNotNull("The Group shouldn't be null", group2_);
		
		groupList_ = new GroupList();
		groupList_.addLast(group1_);
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
		GroupList groupList;
		try {
			groupList = new GroupList();
			assertNotNull("The group list shouldn't be null", groupList);
		} catch (Exception e) {
			fail("The group is valid");
		}
		
	}

	/**
	 * Test on addLast
	 */
	@Test
	public void addLastTest() {
		// Valid test
		try {
			groupList_.addLast(group1_);
			assertTrue("No error is expected", true);
		} catch (IllegalArgumentException e) {
			fail("The group is valid");
		}
		// Invalid test: null group
		try {
			groupList_.addLast(null);
			fail("The group is invalid");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), true);
		}
	}

	/**
	 * Test on getDeque
	 */
	@Test
	public void getDequeTest() {
		// Valid test
		ArrayDeque<Group> value = groupList_.getDeque();
		assertNotNull("The group list shouldn't be null", value);
	}
}
