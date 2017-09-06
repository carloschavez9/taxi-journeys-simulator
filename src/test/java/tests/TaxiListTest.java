package tests;

import java.util.ArrayDeque;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.Taxi;
import model.TaxiList;

/**
 * Manages tests of the class TaxiList
 */
public class TaxiListTest {

    /**
     * Taxi 1 for the tests
     */
    private Taxi taxi1_;
    /**
     * Taxi 2 for the tests
     */
    private Taxi taxi2_;
    /**
     * TaxiList for the tests
     */
    private TaxiList taxiList_;

    /**
     * Initialisation before the tests
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        // Create Taxis
        taxi1_ = new Taxi(TaxiTest.REGISTRATION_NUMBER_TAXI1, TaxiTest.DRIVER_NAME_TAXI1);
        assertNotNull("The taxi shouldn't be null", taxi1_);

        taxi2_ = new Taxi(TaxiTest.REGISTRATION_NUMBER_TAXI2, TaxiTest.DRIVER_NAME_TAXI2);
        assertNotNull("The taxi shouldn't be null", taxi2_);

        taxiList_ = new TaxiList();
        assertNotNull("The taxi list shouldn't be null", taxiList_);

        taxiList_.addLast(taxi1_);
    }

    /**
     * Finalise after the tests
     */
    @After
    public void tearDown() {
        taxi1_ = null;
        taxi2_ = null;
        taxiList_ = null;
    }

    /**
     * Test on the constructor
     */
    @Test
    public void ConstructorTest() {
        // Valid test
        TaxiList taxiList;
        try {
            taxiList = new TaxiList();
            assertNotNull("The taxi list shouldn't be null", taxiList);
        } catch (Exception e) {
            fail("The taxi is valid");
        }

    }

    /**
     * Test on the constructor
     */
    @Test
    public void addLastTest() {
        // Valid test
        try {
            taxiList_.addLast(taxi2_);
            assertTrue("No error is expected", true);
        } catch (IllegalArgumentException e) {
            fail("The taxi is valid");
        }

        // Invalid test
        try {
            taxiList_.addLast(null);
            fail("The journey is invalid");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage(), true);
        }
    }

    /**
     * Test on getNext
     */
    @Test
    public void getNextTest() {
        // Valid test
        try {
            Taxi taxi = taxiList_.getNext();
            assertNotNull("The taxi shouldn't be null", taxi);
        } catch (IllegalArgumentException e) {
            fail("The registration number is invalid");
        }
    }

    /**
     * Test on getDeque
     */
    @Test
    public void getDequeTest() {
        // Valid test
        ArrayDeque<Taxi> value = taxiList_.getDeque();
        assertNotNull("The taxi list shouldn't be null", value);
    }

}
