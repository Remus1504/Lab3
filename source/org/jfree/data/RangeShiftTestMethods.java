package org.jfree.data;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.jfree.data.Range;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RangeShiftTestMethods {
	
	private Range shiftObjectUnderTest;

	@Before
	public void setUp() throws Exception {
		shiftObjectUnderTest = new Range(0, 10);
	}

	@After
	public void tearDown() throws Exception {
		shiftObjectUnderTest = null;
	}
	
	 @Test
	    public void testShiftRangeToRight() {
	        Range expected = new Range(5, 15);
	        Range result = Range.shift(shiftObjectUnderTest, 5.0);
	        assertEquals(expected, result);
	    }

	    @Test
	    public void testShiftRangeToLeft() {
	        Range expected = new Range(-3, 7);
	        Range result = Range.shift(shiftObjectUnderTest, -3.0);
	        assertEquals(expected, result);
	    }

	    @Test
	    public void testShiftRangeWithNoChange() {
	        Range expected = new Range(0, 10);
	        Range result = Range.shift(shiftObjectUnderTest, 0.0);
	        assertEquals(expected, result);
	    }

	  

	    @Test
	    public void testShiftRangeToLargePositiveValue() {
	        Range expected = new Range(100, 110);
	        Range result = Range.shift(shiftObjectUnderTest, 100.0);
	        assertEquals(expected, result);
	    }

	    @Test
	    public void testShiftRangeToLargeNegativeValue() {
	        Range expected = new Range(-50, -40);
	        Range result = Range.shift(shiftObjectUnderTest, -50.0);
	        assertEquals(expected, result);
	    }
	    
	   
	   
	    @Test
	    public void testShiftToLeftByFiveUnits() {
	        Range baseRange = new Range(5, 15);
	        Range shiftedRange = Range.shift(baseRange, -5.0);
	        assertEquals(new Range(0, 10), shiftedRange);
	    }

	    @Test
	    public void testShiftToRightByZeroPointFiveUnits() {
	        Range baseRange = new Range(5, 15);
	        Range shiftedRange = Range.shift(baseRange, 0.5);
	        assertEquals(new Range(5.5, 15.5), shiftedRange);
	    }

	    @Test
	    public void testShiftToLeftBySevenPointFiveUnits() {
	        Range baseRange = new Range(5, 15);
	        Range shiftedRange = Range.shift(baseRange, -7.5);
	        assertEquals(new Range(-2.5, 7.5), shiftedRange);
	    }

	    @Test
	    public void testShiftToRightByEightUnits() {
	        Range baseRange = new Range(5, 15);
	        Range shiftedRange = Range.shift(baseRange, 8.0);
	        assertEquals(new Range(13, 23), shiftedRange);
	    }

	    @Test
	    public void testShiftToLeftByTenUnits() {
	        Range baseRange = new Range(5, 15);
	        Range shiftedRange = Range.shift(baseRange, -10.0);
	        assertEquals(new Range(-5, 5), shiftedRange);
	    }

	    @Test
	    public void testShiftToRightByTwoUnits() {
	        Range baseRange = new Range(15, 25);
	        Range shiftedRange = Range.shift(baseRange, 2.0);
	        assertEquals(new Range(17, 27), shiftedRange);
	    }

	    @Test
	    public void testShiftToLeftByThreePointFiveUnits() {
	        Range baseRange = new Range(15, 25);
	        Range shiftedRange = Range.shift(baseRange, -3.5);
	        assertEquals(new Range(11.5, 21.5), shiftedRange);
	    }

	    @Test
	    public void testShiftToRightBySevenUnits() {
	        Range baseRange = new Range(15, 25);
	        Range shiftedRange = Range.shift(baseRange, 7.0);
	        assertEquals(new Range(22, 32), shiftedRange);
	    }
	    
//	    @Test
//	    public void testShiftWithNullBaseThrowsException() {
//	        try {
//	            // Attempting to shift a null Range object by 5.0,
//	            // which is expected to throw an InvalidParameterException according to the specification.
//	            Range.shift(null, 5.0);
//	            // If the above line does not throw an exception, fail the test.
//	            fail("No exception thrown. The expected outcome was: a thrown exception of type: InvalidParameterException");
//	        } catch (InvalidParameterException e) {
//	            assertTrue("Incorrect exception type thrown", e.getClass().equals(InvalidParameterException.class));
//	        }
//	    }

	
}
