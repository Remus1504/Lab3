package org.jfree.data;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class calculateColumnTotalTest {

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
	
	//calculateColumnTotal(Values2D data, int column)
		@Test
		public void testCalculateColumnTotalWithValidColumn() {
		    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
		    data.addValue(1.0, "Row1", "Column1");
		    data.addValue(2.0, "Row2", "Column1");
		    assertEquals("The sum of the column values should be 3.0", 3.0, DataUtilities.calculateColumnTotal(data, 0), 0.0000001d);
		}

		@Test(expected = NullPointerException.class)
		public void testCalculateColumnTotalWithNullData() {
		    DataUtilities.calculateColumnTotal(null, 0);
		}
		
		@Test
		public void testCalculateColumnTotalWithEmptyDataset() {
		    DefaultKeyedValues2D emptyData = new DefaultKeyedValues2D();
		    double result = DataUtilities.calculateColumnTotal(emptyData, 0);
		    assertEquals("Total of an empty dataset should be 0.0", 0.0, result, 0.0000001d);
		}

		@Test
		public void testCalculateColumnTotalWithSingleElementDataset() {
		    DefaultKeyedValues2D singleElementData = new DefaultKeyedValues2D();
		    singleElementData.addValue(5.5, 0, 0); 
		    double result = DataUtilities.calculateColumnTotal(singleElementData, 0);
		    assertEquals("Total of a single element dataset should be the element itself", 5.5, result, 0.0000001d);
		}

		@Test
		public void testCalculateColumnTotalWithNegativeValues() {
		    DefaultKeyedValues2D negativeValuesData = new DefaultKeyedValues2D();
		    negativeValuesData.addValue(-5.0, 0, 0);
		    negativeValuesData.addValue(-3.0, 1, 0);
		    double result = DataUtilities.calculateColumnTotal(negativeValuesData, 0);
		    assertEquals("Total with negative values should be correctly summed", -8.0, result, 0.0000001d);
		}

		@Test
		public void testCalculateColumnTotalWithMixedValues() {
		    DefaultKeyedValues2D mixedValuesData = new DefaultKeyedValues2D();
		    mixedValuesData.addValue(2.0, 0, 0);
		    mixedValuesData.addValue(-1.5, 1, 0);
		    mixedValuesData.addValue(3.5, 2, 0);
		    double result = DataUtilities.calculateColumnTotal(mixedValuesData, 0);
		    assertEquals("Total with mixed values should be correctly summed", 4.0, result, 0.0000001d);
		}

		@Test
		public void testCalculateColumnTotalWithLargeValues() {
		    DefaultKeyedValues2D largeValuesData = new DefaultKeyedValues2D();
		    largeValuesData.addValue(Double.MAX_VALUE, 0, 0);
		    largeValuesData.addValue(Double.MAX_VALUE, 1, 0);
		    double result = DataUtilities.calculateColumnTotal(largeValuesData, 0);
		    assertEquals("Total with large values should handle overflow correctly", Double.POSITIVE_INFINITY, result, 0.0000001d);
		}

		@Test
		public void testCalculateColumnTotalWithPrecision() {
		    DefaultKeyedValues2D precisionData = new DefaultKeyedValues2D();
		    precisionData.addValue(2.123456789, 0, 0);
		    precisionData.addValue(3.987654321, 1, 0);
		    double result = DataUtilities.calculateColumnTotal(precisionData, 0);
		    assertEquals("Total requiring precision should be correctly calculated", 6.11111111, result, 0.00000001d);
		}
		
		@Test(expected = IndexOutOfBoundsException.class)
		public void testCalculateColumnTotalWithNegativeColumnIndex() {
		    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
		    data.addValue(1.0, "Row1", "Column1");
		    DataUtilities.calculateColumnTotal(data, -1);
		}
		
		@Test
		public void testCalculateColumnTotalForMultipleColumns() {
		    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
		    data.addValue(1.0, "Row1", "Column1");
		    data.addValue(2.0, "Row1", "Column2");
		    data.addValue(3.0, "Row2", "Column1");
		    data.addValue(4.0, "Row2", "Column2");
		    assertEquals("Total for Column 2 should be correctly calculated", 6.0, DataUtilities.calculateColumnTotal(data, 1), 0.0000001d);
		}
		
		@Test(expected = IndexOutOfBoundsException.class)
		public void testCalculateColumnTotalWithColumnIndexOutOfBounds() {
		    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
		    data.addValue(1.0, "Row1", "Column1");
		    DataUtilities.calculateColumnTotal(data, 5); 
		}
		
		@Test
		public void testCalculateColumnTotalWithMissingValuesInRows() {
		    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
		    data.addValue(1.0, "Row1", "Column1");
		    data.addValue(null, "Row2", "Column1");
		    data.addValue(2.0, "Row3", "Column1");
		    assertEquals("Total should ignore null values", 3.0, DataUtilities.calculateColumnTotal(data, 0), 0.0000001d);
		}


		@Test
		public void testCalculateColumnTotalWithNaNValues() {
		    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
		    data.addValue(Double.NaN, "Row1", "Column1");
		    data.addValue(1.0, "Row2", "Column1");
		    double result = DataUtilities.calculateColumnTotal(data, 0);
		    assertTrue("Total should be NaN when any value is NaN", Double.isNaN(result));
		}


}
