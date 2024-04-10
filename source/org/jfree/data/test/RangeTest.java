package org.jfree.data.test;

import org.jfree.data.Range;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.security.InvalidParameterException;

public class RangeTest {

    private Range rangeObjectUnderTest;
    private Range constrainRangeObjectUnderTest;
    private Range combineRange1;
    private Range combineRange2;
    private Range expandObjectUnderTest;
    private Range shiftObjectUnderTest;

    @Before
    public void setUp() throws Exception {
        rangeObjectUnderTest = new Range(0, 5);
        constrainRangeObjectUnderTest = new Range(0,10);
        combineRange1 = null;
        combineRange2 = null;
        expandObjectUnderTest = null;
        shiftObjectUnderTest = new Range(0, 10);
    }

    @After
    public void tearDown() throws Exception {
        rangeObjectUnderTest = null;
        constrainRangeObjectUnderTest = null;
        combineRange1 = null;
        combineRange2 = null;
        expandObjectUnderTest = null;
        shiftObjectUnderTest = null;
    }

    @Test
    public void testValidRangeIntersects() {
        assertTrue(rangeObjectUnderTest.intersects(0, 5.0));
    }
    
    
    @Test
    public void testRangePartiallyOutsideUpperBound() {
        // This test expects true because the provided range partially overlaps with the upper bound of rangeObjectUnderTest
        assertTrue(rangeObjectUnderTest.intersects(3.0, 5.0));
    }

    @Test
    public void testValidRangeNoIntersection() {
        assertFalse(rangeObjectUnderTest.intersects(-1, 0.0));
    }

    @Test
    public void testValidRangeBoundary() {
        assertTrue(rangeObjectUnderTest.intersects(2.0, 2.0));
    }

    @Test
    public void testValidRangeCloseBoundaries() {
        assertTrue(rangeObjectUnderTest.intersects(3.5, 3.7));
    }

    @Test
    public void testBoundaryCaseWithEqualBoundsIntersects() {
        assertTrue(rangeObjectUnderTest.intersects(2.0, 2.0));
    }

    @Test
    public void testBoundaryCaseWithEqualBoundsNoIntersection() {
        assertFalse(rangeObjectUnderTest.intersects(0.0, 0.0));
    }

    @Test
    public void testBoundaryCaseWithEqualBoundsCloseBoundaries() {
        assertTrue(rangeObjectUnderTest.intersects(3.0, 3.001));
    }

    @Test
    public void testInvalidRangeIntersects() {
        assertFalse(rangeObjectUnderTest.intersects(5.0, 0.0));
    }

    @Test
    public void testInvalidRangeNoIntersection() {
        assertFalse(rangeObjectUnderTest.intersects(1.0, -1.0));
    }

    @Test
    public void testInvalidRangeCloseBoundaries() {
        assertFalse(rangeObjectUnderTest.intersects(3.7, 3.5));
    }

    @Test
    public void testBoundaryCaseWithEqualBoundsIntersectsAgain() {
        assertTrue(rangeObjectUnderTest.intersects(2.0, 2.0));
    }

    @Test
    public void testBoundaryCaseWithEqualBoundsNoIntersectionAgain() {
        assertFalse(rangeObjectUnderTest.intersects(0.0, 0.0));
    }

    @Test
    public void testBoundaryCaseWithEqualBoundsCloseBoundariesAgain() {
        assertTrue(rangeObjectUnderTest.intersects(-1.0, -1.0));
    }

    // Additional Boundary Tests
    @Test
    public void testLowerBoundMinusOneDecimal() {
        assertTrue(rangeObjectUnderTest.intersects(-1.1, 0.0));
    }

    @Test
    public void testUpperBoundPlusOneDecimal() {
        assertFalse(rangeObjectUnderTest.intersects(5.0, 5.1));
    }

    @Test
    public void testLowerBoundEqualsLowerBound() {
        assertTrue(rangeObjectUnderTest.intersects(0.0, 5.0));
    }

    @Test
    public void testUpperBoundEqualsUpperBound() {
        assertTrue(rangeObjectUnderTest.intersects(0.0, 5.0));
    }

    @Test
    public void testLowerBoundEqualsUpperBoundMinusOneDecimal() {
        assertFalse(rangeObjectUnderTest.intersects(4.9, 5.0));
    }

    @Test
    public void testUpperBoundEqualsLowerBoundPlusOneDecimal() {
        assertTrue(rangeObjectUnderTest.intersects(0.0, 0.1));
    }
    
    
    
      // Starting tests for the constrains() method
    
    @Test
    public void testValidValueWithinRange() {
        assertEquals(3.0, constrainRangeObjectUnderTest.constrain(3.0), 0.0);
    }

    @Test
    public void testValidValueBelowLowerBound() {
        assertEquals(0.0, constrainRangeObjectUnderTest.constrain(-2.0), 0.0);
    }

    @Test
    public void testValidValueAboveUpperBound() {
        assertEquals(10.0, constrainRangeObjectUnderTest.constrain(12.0), 0.0);
    }

    @Test
    public void testBoundaryCaseLowerBoundValue() {
        assertEquals(0.0, constrainRangeObjectUnderTest.constrain(0.0), 0.0);
    }

    @Test
    public void testBoundaryCaseUpperBoundValue() {
        assertEquals(10.0, constrainRangeObjectUnderTest.constrain(10.0), 0.0);
    }

    @Test
    public void testValidValueWithinRangeDecimal() {
        assertEquals(7.5, constrainRangeObjectUnderTest.constrain(7.5), 0.0);
    }

    @Test
    public void testValidValueBelowLowerBoundDecimal() {
        assertEquals(0.0, constrainRangeObjectUnderTest.constrain(-3.5), 0.0);
    }

    @Test
    public void testValidValueAboveUpperBoundDecimal() {
        assertEquals(10.0, constrainRangeObjectUnderTest.constrain(15.5), 0.0);
    }

    @Test
    public void testBoundaryCaseLowerBoundValueDecimal() {
        assertEquals(0.0, constrainRangeObjectUnderTest.constrain(0.0), 0.0);
    }

    @Test
    public void testBoundaryCaseUpperBoundValueDecimal() {
        assertEquals(10.0, constrainRangeObjectUnderTest.constrain(10.0), 0.0);
    }

    @Test
    public void testValidValueWithinRangeNegative() {
        Range negativeRangeObject = new Range(-10, -5);
        assertEquals(-3.0, negativeRangeObject.constrain(-3.0), 0.0);
    }

    @Test
    public void testValidValueBelowLowerBoundNegative() {
        Range negativeRangeObject = new Range(-10, -5);
        assertEquals(-10.0, negativeRangeObject.constrain(-12.0), 0.0);
    }

    @Test
    public void testValidValueAboveUpperBoundNegative() {
        Range negativeRangeObject = new Range(-10, -5);
        assertEquals(-5.0, negativeRangeObject.constrain(-2.0), 0.0);
    }

    @Test
    public void testBoundaryCaseLowerBoundValueNegative() {
        Range negativeRangeObject = new Range(-10, -5);
        assertEquals(-10.0, negativeRangeObject.constrain(-10.0), 0.0);
    }

    @Test
    public void testBoundaryCaseUpperBoundValueNegative() {
        Range negativeRangeObject = new Range(-10, -5);
        assertEquals(-5.0, negativeRangeObject.constrain(-5.0), 0.0);
    }
    
    // Start of the combine() tests
    
    
    @Test
    public void testCombineBothRangesNull() {
        assertNull(Range.combine(null, null));
    }
    
    @Test
    public void testCombineFirstRangeNull() {
        Range combineRange1 = new Range(0, 5);
        Range result = Range.combine(combineRange1, null);
        assertNotNull(result); // Ensure the result is not null
        assertEquals(combineRange1, result); // Ensure the result equals combineRange2
    }


   
    @Test
    public void testCombineSecondRangeNull() {
        Range combineRange2 = new Range(0, 5);
        Range result = Range.combine(null, combineRange2);
        assertNotNull(result); // Ensure the result is not null
        assertEquals(combineRange2, result); // Ensure the result equals combineRange2
    }

 
    @Test
    public void testCombineNotBothNull() {
        assertNull(Range.combine(combineRange1, combineRange2));
    }

    @Test
    public void testCombineNotFirstNull() {
        combineRange2 = new Range(0, 5);
        assertEquals(combineRange2, Range.combine(combineRange1, combineRange2));
    }

    @Test
    public void testCombineNotSecondNull() {
        combineRange1 = new Range(0, 5);
        assertEquals(combineRange1, Range.combine(combineRange1, combineRange2));
    }

    

    @Test
    public void testCombineNotSecondLowerBoundLessThanFirst() {
        combineRange1 = new Range(0, 5);
        combineRange2 = new Range(-2, 3);
        assertEquals(new Range(-2, 5), Range.combine(combineRange1, combineRange2));
    }
    
    @Test
    public void testCombineFirstRangeUpperBoundLessThanSecond() {
        combineRange1 = new Range(3, 8);
        combineRange2 = new Range(0, 5);
        assertEquals(new Range(0, 8), Range.combine(combineRange1, combineRange2));
    }


    @Test
    public void testCombineSecondRangeUpperBoundLessThanFirst() {
        combineRange1 = new Range(6, 10);
        combineRange2 = new Range(0, 5);
        assertEquals(new Range(0, 10), Range.combine(combineRange1, combineRange2));
    }

    
    
 

    @Test
    public void testCombineFirstRangeOverlapsWithSecond() {
        combineRange1 = new Range(5, 10);
        combineRange2 = new Range(0, 5);
        assertEquals(new Range(0, 10), Range.combine(combineRange1, combineRange2));
    }

    @Test
    public void testCombineRangesEqual() {
        combineRange1 = new Range(0, 5);
        combineRange2 = new Range(0, 5);
        assertEquals(new Range(0, 5), Range.combine(combineRange1, combineRange2));
    }

    @Test
    public void testCombineBothRangesEqual() {
        combineRange1 = new Range(2, 3);
        combineRange2 = new Range(2, 3);
        assertEquals(new Range(2, 3), Range.combine(combineRange1, combineRange2));
    }

   
    //Test Expand

    @Test
    public void testExpandNullRange() {
        try {
            Range expandedRange = Range.expand(null, 0.25, 0.5);
            fail("No exception thrown. The expected outcome was: a thrown exception of type: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertFalse("Incorrect exception type thrown", e.getClass().equals(IllegalArgumentException.class));
        }
    }

  
    @Test
    public void testExpandInvalidMargins() {
        try {
            Range expandedRange = Range.expand(expandObjectUnderTest, -0.5, 1.5);
            fail("No exception thrown. The expected outcome was: a thrown exception of type: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue("Incorrect exception type thrown", e.getClass().equals(IllegalArgumentException.class));
        }
    }

    // SECT-6: Range with same lower and upper bounds
    @Test
    public void testExpandRangeWithSameBounds() {
        Range range = new Range(5, 5);
        Range expandedRange = Range.expand(range, 0, 0);
        assertEquals(range, expandedRange);
    }

    // SECT-7: Range with negative bounds and margins
    @Test
    public void testExpandNegativeBoundsAndMargins() {
        Range range = new Range(0, 10);
        Range expandedRange = Range.expand(range, -0.5, 0.5);
        assertEquals(new Range(-5, 15), expandedRange);
    }

    // SECT-8: Range with negative and positive margins
    @Test
    public void testExpandNegativePositiveMargins() {
        Range range = new Range(-3, 3);
        Range expandedRange = Range.expand(range, -0.25, 0.25);
        assertEquals(new Range(-4.5, 4.5), expandedRange);
    }

    // SECT-9: Range with inconsistent bounds and margins
    @Test
    public void testExpandInconsistentBoundsAndMargins() {
        try {
            Range range = new Range(5, 3);
            Range expandedRange = Range.expand(range, 0.2, 0.1);
            fail("No exception thrown. The expected outcome was: a thrown exception of type: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue("Incorrect exception type thrown", e.getClass().equals(IllegalArgumentException.class));
        }
    }

    // SECT-10: Range with positive bounds and margins
    @Test
    public void testExpandPositiveBoundsAndMargins() {
        Range range = new Range(1, 5);
        Range expandedRange = Range.expand(range, 0.5, 0.5);
        assertEquals(new Range(0, 7), expandedRange);
    }

    // SECT-11: Range with positive and negative bounds and margins
    @Test
    public void testExpandPositiveNegativeBoundsAndMargins() {
        try {
            Range range = new Range(8, 2);
            Range expandedRange = Range.expand(range, -0.25, 0.25);
            fail("No exception thrown. The expected outcome was: a thrown exception of type: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue("Incorrect exception type thrown", e.getClass().equals(IllegalArgumentException.class));
        }
    }

    // SECT-12: Range with zero bounds and margins
    @Test
    public void testExpandZeroBoundsAndMargins() {
        Range range = new Range(0, 0);
        Range expandedRange = Range.expand(range, 0, 0);
        assertEquals(range, expandedRange);
    }

    // SECT-13: Range with negative bounds and zero margins
    @Test
    public void testExpandNegativeBoundsAndZeroMargins() {
        Range range = new Range(-5, -1);
        Range expandedRange = Range.expand(range, 0, 0);
        assertEquals(range, expandedRange);
    }

    // SECT-14: Range with positive bounds and zero margins
    @Test
    public void testExpandPositiveBoundsAndZeroMargins() {
        Range range = new Range(1, 5);
        Range expandedRange = Range.expand(range, 0, 0);
        assertEquals(range, expandedRange);
    }

    // SECT-15: Range with zero bounds and positive margins
    @Test
    public void testExpandZeroBoundsAndPositiveMargins() {
        Range range = new Range(0, 0);
        Range expandedRange = Range.expand(range, 0.25, 0.25);
        assertEquals(new Range(-0.25, 0.25), expandedRange);
    }

    // SECT-16: Range with negative bounds and positive margins
    @Test
    public void testExpandNegativeBoundsAndPositiveMargins() {
        Range range = new Range(-3, -1);
        Range expandedRange = Range.expand(range, 0.25, 0.5);
        assertEquals(new Range(-3.25, -0.5), expandedRange);
    }

    // SECT-17: Range with positive bounds and negative margins
    @Test
    public void testExpandPositiveBoundsAndNegativeMargins() {
        try {
            Range range = new Range(1, 5);
            Range expandedRange = Range.expand(range, -0.5, -0.25);
            fail("No exception thrown. The expected outcome was: a thrown exception of type: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue("Incorrect exception type thrown", e.getClass().equals(IllegalArgumentException.class));
        }
    }

    // SECT-18: Range with negative bounds and positive margins
    @Test
    public void testExpandNegativeBoundsAndPositiveNegativeMargins() {
        Range range = new Range(-3, -1);
        Range expandedRange = Range.expand(range, -0.5, 0.5);
        assertEquals(new Range(-4.5, 0.5), expandedRange);
    }

    // SECT-19: Range with positive bounds and negative margins
    @Test
    public void testExpandPositiveBoundsAndNegativePositiveMargins() {
        try {
            Range range = new Range(1, 5);
            Range expandedRange = Range.expand(range, -0.25, 0.5);
            fail("No exception thrown. The expected outcome was: a thrown exception of type: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue("Incorrect exception type thrown", e.getClass().equals(IllegalArgumentException.class));
        }
    }

    // SECT-20: Range with positive bounds and zero margins
    @Test
    public void testExpandPositiveBoundsAndZeroMarginsExpand() {
        Range range = new Range(1, 5);
        Range expandedRange = Range.expand(range, 0, 0);
        assertEquals(range, expandedRange);
    }
    
    @Test
    public void testNullRangeArgumentForFirstMethod() {
        try {
            Range result = Range.expand(null, 0.25, 0.5);
            
        } catch (InvalidParameterException e ) {
        	assertTrue("Incorrect exception type thrown", e.getClass().equals(InvalidParameterException.class));
        }
    }
    
    //shift method tests
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
    public void testNegativeBoundsAndPositiveNegativeMarginsShift() {
        Range range = new Range(-3, -1);
        Range expandedRange = Range.expand(range, -0.5, 0.5);
        assertEquals(new Range(-4.5, 0.5), expandedRange);
    }

    @Test
    public void testPositiveBoundsAndNegativePositiveMarginShift() {
        try {
            Range range = new Range(1, 5);
            Range expandedRange = Range.expand(range, -0.25, 0.5);
            fail("No exception thrown. The expected outcome was: a thrown exception of type: IllegalArgumentException");
        } catch (InvalidParameterException e) {
            assertTrue("Incorrect exception type thrown", e.getClass().equals(InvalidParameterException.class));
        }
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
    
    @Test
    public void testShiftWithNullBaseThrowsException() {
        try {
            // Attempting to shift a null Range object by 5.0,
            // which is expected to throw an InvalidParameterException according to the specification.
            Range.shift(null, 5.0);
            // If the above line does not throw an exception, fail the test.
            fail("No exception thrown. The expected outcome was: a thrown exception of type: InvalidParameterException");
        } catch (InvalidParameterException e) {
            assertTrue("Incorrect exception type thrown", e.getClass().equals(InvalidParameterException.class));
        }
    
    }
    
    @Test
    public void testShiftWithNullBaseThrowsExceptionIllgealArgument() {
        try {
            // Attempting to shift a null Range object by 5.0,
            // which is expected to throw an InvalidParameterException according to the specification.
            Range.shift(null, 5.0);
            // If the above line does not throw an exception, fail the test.
            fail("No exception thrown. The expected outcome was: a thrown exception of type: InvalidParameterException");
        } catch (IllegalArgumentException e) {
            assertTrue("Incorrect exception type thrown", e.getClass().equals(IllegalArgumentException.class));
        }
    
    }
    
    
}


