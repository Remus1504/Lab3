package org.jfree.data;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class calculateRowTotalTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateRowTotalWithValidRow() {
	    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
	    data.addValue(1.0, "Row1", "Column1");
	    data.addValue(2.0, "Row1", "Column2");
	    assertEquals("The sum of the row values should be 3.0", 3.0, DataUtilities.calculateRowTotal(data, 0), 0.0000001d);
	}

	@Test(expected = NullPointerException.class)
	public void testCalculateRowTotalWithNullData() {
	    DataUtilities.calculateRowTotal(null, 0);
	}
	
	@Test
	public void testCalculateRowTotalWithEmptyDataset() {
	    DefaultKeyedValues2D emptyData = new DefaultKeyedValues2D();
	    double result = DataUtilities.calculateRowTotal(emptyData, 0);
	    assertEquals("Total of an empty dataset should be 0.0", 0.0, result, 0.0000001d);
	}

	
	@Test
	public void testCalculateRowTotalWithSingleElementDataset() {
	    DefaultKeyedValues2D singleElementData = new DefaultKeyedValues2D();
	    singleElementData.addValue(5.5, 0, 0); 
	    double result = DataUtilities.calculateRowTotal(singleElementData, 0);
	    assertEquals("Total of a single element dataset should be the element itself", 5.5, result, 0.0000001d);
	}

	
	@Test
	public void testCalculateRowTotalWithNegativeValues() {
	    DefaultKeyedValues2D negativeValuesData = new DefaultKeyedValues2D();
	    negativeValuesData.addValue(-5.0, 0, 0);
	    negativeValuesData.addValue(-3.0, 0, 1);
	    double result = DataUtilities.calculateRowTotal(negativeValuesData, 0);
	    assertEquals("Total with negative values should be correctly summed", -8.0, result, 0.0000001d);
	}

	
	@Test
	public void testCalculateRowTotalWithMixedValues() {
	    DefaultKeyedValues2D mixedValuesData = new DefaultKeyedValues2D();
	    mixedValuesData.addValue(2.0, 0, 0);
	    mixedValuesData.addValue(-1.5, 0, 1);
	    mixedValuesData.addValue(3.5, 0, 2);
	    double result = DataUtilities.calculateRowTotal(mixedValuesData, 0);
	    assertEquals("Total with mixed values should be correctly summed", 4.0, result, 0.0000001d);
	}

	
	@Test
	public void testCalculateRowTotalWithLargeValues() {
	    DefaultKeyedValues2D largeValuesData = new DefaultKeyedValues2D();
	    largeValuesData.addValue(Double.MAX_VALUE, 0, 0);
	    largeValuesData.addValue(Double.MAX_VALUE, 0, 1);
	    double result = DataUtilities.calculateRowTotal(largeValuesData, 0);
	    assertEquals("Total with large values should handle overflow correctly", Double.POSITIVE_INFINITY, result, 0.0000001d);
	}

	@Test
	public void testCalculateRowTotalWithPrecision() {
	    DefaultKeyedValues2D precisionData = new DefaultKeyedValues2D();
	    precisionData.addValue(2.123456789, 0, 0);
	    precisionData.addValue(3.987654321, 0, 1);
	    double result = DataUtilities.calculateRowTotal(precisionData, 0);
	    double expected = 2.123456789 + 3.987654321; // Explicit calculation for clarity
	    double delta = 0.0000001d; // Adjusted for potential floating-point arithmetic issues
	    assertEquals("Expected value in row 0, column 0", 2.123456789, precisionData.getValue(0, 0).doubleValue(), 0.00000001d);
	    assertEquals("Expected value in row 0, column 1", 3.987654321, precisionData.getValue(0, 1).doubleValue(), 0.00000001d);
	    assertEquals("Total requiring precision should be correctly calculated", expected, result, delta);
	}


	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testCalculateRowTotalWithInvalidRowIndex() {
	    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
	    data.addValue(1.0, "Row1", "Column1");
	    DataUtilities.calculateRowTotal(data, -1);
	}

	@Test
	public void testCalculateRowTotalWithNaNValues() {
	    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
	    data.addValue(Double.NaN, 0, 0);
	    data.addValue(Double.NaN, 0, 1);
	    double result = DataUtilities.calculateRowTotal(data, 0);
	    assertTrue("Row total with NaN values should be NaN", Double.isNaN(result));
	}

	@Test
	public void testCalculateRowTotalWithInfinityValues() {
	    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
	    data.addValue(Double.POSITIVE_INFINITY, 0, 0);
	    data.addValue(Double.NEGATIVE_INFINITY, 0, 1);
	    double result = DataUtilities.calculateRowTotal(data, 0);
	    assertTrue("Row total with infinity values should handle overflow", Double.isInfinite(result));
	}

	@Test
	public void testCalculateRowTotalWithMixedNaNAndNumericValues() {
	    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
	    data.addValue(Double.NaN, 0, 0);
	    data.addValue(1.0, 0, 1);
	    double result = DataUtilities.calculateRowTotal(data, 0);
	    assertEquals("Total should ignore NaN values and sum only numeric values", 1.0, result, 0.0000001d);
	}

	@Test
	public void testCalculateRowTotalConsistency() {
	    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
	    data.addValue(1.0, 0, 0);
	    data.addValue(2.0, 0, 1);
	    double firstCall = DataUtilities.calculateRowTotal(data, 0);
	    double secondCall = DataUtilities.calculateRowTotal(data, 0);
	    assertEquals("Row total should be consistent across calls", firstCall, secondCall, 0.0);
	}

	@Test
	public void testCalculateRowTotalWithLargeNumberOfColumns() {
	    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
	    for (int col = 0; col < 10000; col++) {
	        data.addValue(1.0, 0, col);
	    }
	    double result = DataUtilities.calculateRowTotal(data, 0);
	    assertEquals("Row total for large number of columns", 10000.0, result, 0.0);
	}

	@Test
	public void testCalculateRowTotalWithAllZeroValues() {
	    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
	    data.addValue(0.0, 0, 0);
	    data.addValue(0.0, 0, 1);
	    double result = DataUtilities.calculateRowTotal(data, 0);
	    assertEquals("Row total with all zeros should be zero", 0.0, result, 0.0);
	}
	
	
}
