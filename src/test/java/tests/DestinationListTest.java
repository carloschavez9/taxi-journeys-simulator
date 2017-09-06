package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import exceptions.DuplicateIDException;
import model.Destination;
import model.DestinationList;

/**
 * Manages tests of the class DestinationList
 */
public class DestinationListTest {

    /**
     * Destination 1 for the tests
     */
    private Destination destination1_;
    /**
     * Destination 2 for the tests
     */
    private Destination destination2_;
    /**
     * DestinationList for the tests
     */
    private DestinationList destinationList_;

    /**
     * Initialisation before the tests
     *
     * @throws IllegalArgumentException
     * @throws DuplicateIDException
     */
    @Before
    public void setUp() throws IllegalArgumentException, DuplicateIDException {
        // Create destinations and list
        destination1_ = new Destination(DestinationTest.NAME_DESTINATION1, DestinationTest.DISTANCE_DESTINATION1);
        assertNotNull("The destination shouldn't be null", destination1_);

        destination2_ = new Destination(DestinationTest.NAME_DESTINATION2, DestinationTest.DISTANCE_DESTINATION2);
        assertNotNull("The destination shouldn't be null", destination2_);

        destinationList_ = new DestinationList();
        assertNotNull("The destination list shouldn't be null", destinationList_);

        destinationList_.add(DestinationTest.NAME_DESTINATION1, destination1_);
    }

    /**
     * Finalise after the tests
     */
    @After
    public void tearDown() {
        destination1_ = null;
        destination2_ = null;
        destinationList_ = null;
    }

    /**
     * Test on the constructor
     */
    @Test
    public void ConstructorTest() {
        // Valid test
        DestinationList destinationList = new DestinationList();
        assertNotNull("The destination list shouldn't be null", destinationList);
    }

    /**
     * Test on addDestination
     */
    @Test
    public void addDestinationTest() {
        // Valid test
        try {
            destinationList_.add(DestinationTest.NAME_DESTINATION2, destination2_);
            assertTrue("No error is expected", true);
        } catch (IllegalArgumentException e) {
            fail("The destination is valid");
        } catch (DuplicateIDException e) {
            fail("The destination is valid");
        }

        // Invalid test: null destination
        try {
            destinationList_.add(null, null);
            fail("The destination is invalid");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage(), true);
        } catch (DuplicateIDException e) {
            fail("The destination is valid");
        }
        // Invalid test: duplicate destination
        try {
            destinationList_.add(DestinationTest.NAME_DESTINATION1, destination1_);
            fail("The destination is invalid");
        } catch (IllegalArgumentException e) {
            fail("The destination is valid");
        } catch (DuplicateIDException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    /**
     * Test on getDestination
     */
    @Test
    public void getDestinationTest() {
        // Valid test
        Destination value = destinationList_.get(DestinationTest.NAME_DESTINATION1);
        assertEquals("Values should be equal", value, destination1_);

        // Invalid test
        value = destinationList_.get("");
        assertTrue("Values should be null", value == null);
    }
}
