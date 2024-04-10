package org.jfree.data;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.KeyedValues;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;



public class DataUtilitiesTest {
	
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

	//getCumulativePercentages(KeyedValues data)
		@Test
		public void testGetCumulativePercentages() {
		    DefaultKeyedValues data = new DefaultKeyedValues();
		    data.addValue("Key1", 5.0);
		    data.addValue("Key2", 9.0);
		    data.addValue("Key3", 2.0);
		    
		    KeyedValues result = DataUtilities.getCumulativePercentages(data);
		    
		    assertEquals("Cumulative percentage for Key1 should be correct", 0.3125, result.getValue("Key1").doubleValue(), 0.00001d);
		    assertEquals("Cumulative percentage for Key2 should be correct", 0.875, result.getValue("Key2").doubleValue(), 0.00001d);
		    assertEquals("Cumulative percentage for Key3 should be correct", 1.0, result.getValue("Key3").doubleValue(), 0.00001d);
		}

		@Test(expected = IllegalArgumentException.class)
	    public void testGetCumulativePercentagesWithNullData() {
	        DataUtilities.getCumulativePercentages(null);
	    }

		@Test
	    public void testGetCumulativePercentagesWithAllZeroValues() {
	        DefaultKeyedValues data = new DefaultKeyedValues();
	        data.addValue("Key1", 0.0);
	        data.addValue("Key2", 0.0);
	        data.addValue("Key3", 0.0);

	        KeyedValues result = DataUtilities.getCumulativePercentages(data);

	        assertTrue("Cumulative percentage for all keys should be NaN", Double.isNaN(result.getValue("Key1").doubleValue()));
	        assertTrue(Double.isNaN(result.getValue("Key2").doubleValue()));
	        assertTrue(Double.isNaN(result.getValue("Key3").doubleValue()));
	    }
		
		@Test
		public void testGetCumulativePercentagesWithNegativeValues() {
		    DefaultKeyedValues data = new DefaultKeyedValues();
		    data.addValue("Key1", -5.0);
		    data.addValue("Key2", -3.0);

		    KeyedValues result = DataUtilities.getCumulativePercentages(data);

		    assertTrue("Cumulative percentage should handle negative values appropriately", 
		               Double.isNaN(result.getValue("Key1").doubleValue()) ||
		               result.getValue("Key1").doubleValue() == 0.0);
		    assertTrue("Cumulative percentage should handle negative values appropriately", 
		               Double.isNaN(result.getValue("Key2").doubleValue()) ||
		               result.getValue("Key2").doubleValue() == 0.0);
		}

		@Test
		public void testGetCumulativePercentagesWithLargeNumbers() {
		    DefaultKeyedValues data = new DefaultKeyedValues();
		    data.addValue("Key1", Double.MAX_VALUE);
		    data.addValue("Key2", Double.MAX_VALUE);

		    KeyedValues result = DataUtilities.getCumulativePercentages(data);

		    assertTrue("Cumulative percentage should not exceed 1.0", 
		               result.getValue("Key1").doubleValue() <= 1.0 &&
		               result.getValue("Key2").doubleValue() <= 1.0);
		}

	    @Test
	    public void testGetCumulativePercentagesWithSingleValue() {
	        DefaultKeyedValues data = new DefaultKeyedValues();
	        data.addValue("Key1", 100.0);

	        KeyedValues result = DataUtilities.getCumulativePercentages(data);

	        assertEquals("Cumulative percentage for single value should be 1.0 (100%)", 1.0, result.getValue("Key1").doubleValue(), 0.00001d);
	    }
	    
	    @Test
	    public void testGetCumulativePercentagesWithEmptyDataset() {
	        DefaultKeyedValues data = new DefaultKeyedValues();
	        KeyedValues result = DataUtilities.getCumulativePercentages(data);
	        assertTrue("Result should be empty for an empty dataset", result.getItemCount() == 0);
	    }

	    
	    @Test
	    public void testGetCumulativePercentagesWithNullValues() {
	        DefaultKeyedValues data = new DefaultKeyedValues();
	        data.addValue("Key1", null);
	        data.addValue("Key2", 10.0);

	        KeyedValues result = DataUtilities.getCumulativePercentages(data);

	        assertEquals("Cumulative percentage for Key2 should be 1.0 (100%)", 
	                     1.0, result.getValue("Key2").doubleValue(), 0.00001d);
	    }



	    @Test
	    public void testGetCumulativePercentagesWithMixedValuesLeadingToZeroTotal() {
	        DefaultKeyedValues data = new DefaultKeyedValues();
	        data.addValue("Key1", -10.0);
	        data.addValue("Key2", 10.0);

	        KeyedValues result = DataUtilities.getCumulativePercentages(data);

	        assertTrue("Cumulative percentage should be NaN or handle zero total appropriately", 
	                   Double.isNaN(result.getValue("Key1").doubleValue()) && 
	                   Double.isNaN(result.getValue("Key2").doubleValue()));
	    }


	    @Test
	    public void testGetCumulativePercentagesWithVeryLargeNumberOfItems() {
	        DefaultKeyedValues data = new DefaultKeyedValues();
	        for (int i = 0; i < 10000; i++) { 
	            data.addValue("Key" + i, 1.0);
	        }
	        KeyedValues result = DataUtilities.getCumulativePercentages(data);

	        assertEquals("Cumulative percentage for the last key should be 1.0", 1.0, result.getValue("Key9999").doubleValue(), 0.00001d);

	        double expectedCumulativePercentageForFirstKey = 1.0 / 10000;
	        assertEquals("Cumulative percentage for the first key should be correct", expectedCumulativePercentageForFirstKey, result.getValue("Key0").doubleValue(), 0.00001d);

	        double expectedCumulativePercentageFor5000thKey = 5000.0 / 10000;
	        assertEquals("Cumulative percentage for the 5000th key should be correct", expectedCumulativePercentageFor5000thKey, result.getValue("Key4999").doubleValue(), 0.00001d);
	    }


	    
	    @Test
	    public void testGetCumulativePercentagesConsistency() {
	        DefaultKeyedValues data = new DefaultKeyedValues();
	        data.addValue("Key1", 5.0);
	        data.addValue("Key2", 10.0);
	        KeyedValues result1 = DataUtilities.getCumulativePercentages(data);
	        KeyedValues result2 = DataUtilities.getCumulativePercentages(data);
	        assertEquals("Consecutive calls should yield consistent results", result1, result2);
	    }
	
		//createNumberArray(double[] data)
		@Test
		public void testCreateNumberArrayWithPositiveNumbers() {

		    double[] data = new double[]{1.0, 2.0, 3.0}; 
		    Number[] expected = new Number[]{1.0, 2.0, 3.0}; 


		    Number[] actual = DataUtilities.createNumberArray(data);


		    assertArrayEquals("The Number array should match the double array", expected, actual);
		}

		@Test(expected = IllegalArgumentException.class)
		public void testCreateNumberArrayWithNullData() {
		    DataUtilities.createNumberArray(null);
		}
		
		@Test
		public void testCreateNumberArrayWithSpecialValues() {
		    double[] data = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
		    Number[] expected = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};

		    Number[] actual = DataUtilities.createNumberArray(data);

		    assertArrayEquals("Array should handle NaN and infinity correctly", expected, actual);
		}

		@Test
		public void testCreateNumberArrayWithLargeSize() {
		    double[] data = new double[1000000];
		    Arrays.fill(data, 1.0); 

		    Number[] actual = DataUtilities.createNumberArray(data);

		    assertEquals("Array size should match the input data size", data.length, actual.length);
		    for (Number number : actual) {
		        assertNotNull("Element should not be null", number);
		        assertEquals("Element value should be 1.0", 1.0, number.doubleValue(), 0.0000001d);
		    }
		}

		
		@Test
		public void testCreateNumberArrayWithZeros() {
		    double[] data = new double[]{0.0, 0.0, 0.0};
		    Number[] expected = {0.0, 0.0, 0.0};

		    Number[] actual = DataUtilities.createNumberArray(data);

		    assertArrayEquals("Array of zeros should be handled correctly", expected, actual);
		}

		
		@Test
		public void testCreateNumberArrayWithNegativeValues() {
		    double[] data = {-1.0, -2.0, -3.0};
		    Number[] expected = {-1.0, -2.0, -3.0};

		    Number[] actual = DataUtilities.createNumberArray(data);

		    assertArrayEquals("Array should correctly handle negative values", expected, actual);
		}

		
		@Test
		public void testCreateNumberArrayWithMixedValues() {
		    double[] data = {-1.0, 0.0, 1.0, Double.NaN};
		    Number[] expected = {-1.0, 0.0, 1.0, Double.NaN};

		    Number[] actual = DataUtilities.createNumberArray(data);

		    assertArrayEquals("Array should correctly handle a mix of values", expected, actual);
		}

		

		@Test
	    public void testCreateNumberArrayWithVerySmallValues() {
	        double[] data = {Double.MIN_VALUE, -Double.MIN_VALUE};
	        Number[] expected = {Double.MIN_VALUE, -Double.MIN_VALUE};
	        Number[] actual = DataUtilities.createNumberArray(data);
	        assertArrayEquals("Array should correctly handle very small values", expected, actual);
	    }

	    @Test
	    public void testCreateNumberArrayWithSequentialValues() {
	        double[] data = new double[100];
	        Number[] expected = new Number[100];
	        for (int i = 0; i < data.length; i++) {
	            data[i] = i + 1;
	            expected[i] = Double.valueOf(i + 1);
	        }

	        Number[] actual = DataUtilities.createNumberArray(data);
	        assertArrayEquals("Array should correctly handle sequential values", expected, actual);
	    }

	    @Test
	    public void testCreateNumberArrayWithDuplicatedValues() {
	        double[] data = {1.0, 2.0, 2.0, 3.0};
	        Number[] expected = {1.0, 2.0, 2.0, 3.0};
	        Number[] actual = DataUtilities.createNumberArray(data);
	        assertArrayEquals("Array should correctly handle duplicated values", expected, actual);
	    }

	    @Test
	    public void testCreateNumberArrayWithOneElement() {
	        double[] data = {42.0};
	        Number[] expected = {42.0};
	        Number[] actual = DataUtilities.createNumberArray(data);
	        assertArrayEquals("Array with one element should be handled correctly", expected, actual);
	    }

	    @Test
	    public void testCreateNumberArrayWithValuesCloseToDoubleMax() {
	        double[] data = {Double.MAX_VALUE - 1, Double.MAX_VALUE};
	        Number[] expected = {Double.MAX_VALUE - 1, Double.MAX_VALUE};
	        Number[] actual = DataUtilities.createNumberArray(data);
	        assertArrayEquals("Array should handle values close to Double.MAX_VALUE correctly", expected, actual);
	    }

	    @Test(timeout = 1000) 
	    public void testCreateNumberArrayPerformance() {
	        double[] data = new double[10000000]; 
	        Arrays.fill(data, 1.0);
	        DataUtilities.createNumberArray(data); 

	    }

	    @Test
	    public void testCreateNumberArrayWithAllZeroValues() {
	        double[] data = new double[10];
	        Number[] expected = new Number[10];
	        Arrays.fill(data, 0.0);
	        Arrays.fill(expected, 0.0);
	        Number[] actual = DataUtilities.createNumberArray(data);
	        assertArrayEquals("Array with all zero values should be handled correctly", expected, actual);
	    }

	    @Test
	    public void testCreateNumberArrayConsistency() {
	        double[] data = {1.0, 2.0, 3.0, 4.0, 5.0};
	        Number[] firstCall = DataUtilities.createNumberArray(data);
	        Number[] secondCall = DataUtilities.createNumberArray(data);
	        assertArrayEquals("Consecutive calls should yield consistent results", firstCall, secondCall);
	    }
	    
	  //createNumberArray2D(double[][] data)
	  		@Test
	  		public void testCreateNumberArray2DWithMixedValues() {
	  		    double[][] data = new double[][]{{1.0, -1.0}, {2.0, -2.0}};
	  		    Number[][] expected = new Number[][]{{1.0, -1.0}, {2.0, -2.0}};
	  		    assertArrayEquals("The 2D Number array should match the 2D double array", expected, DataUtilities.createNumberArray2D(data));
	  		}

	  		@Test(expected = IllegalArgumentException.class)
	  		public void testCreateNumberArray2DWithNullData() {
	  		    DataUtilities.createNumberArray2D(null);
	  		}

	  		
	  		@Test
	  		public void testCreateNumberArray2DWithSpecialValues() {
	  		    double[][] data = {
	  		        {Double.NaN, Double.POSITIVE_INFINITY},
	  		        {Double.NEGATIVE_INFINITY, Double.NaN}
	  		    };
	  		    Number[][] expected = {
	  		        {Double.NaN, Double.POSITIVE_INFINITY},
	  		        {Double.NEGATIVE_INFINITY, Double.NaN}
	  		    };
	  		    Number[][] actual = DataUtilities.createNumberArray2D(data);
	  		    assertArrayEquals("2D Array should handle NaN and infinity correctly", expected, actual);
	  		}

	  		
	  		@Test
	  		public void testCreateNumberArray2DWithLargeSize() {
	  		    double[][] data = new double[100][1000]; 
	  		    for (double[] row : data) {
	  		        Arrays.fill(row, 1.0);
	  		    }
	  		    Number[][] actual = DataUtilities.createNumberArray2D(data);
	  		    assertEquals("2D Array size should match the input data size", data.length, actual.length);
	  		    for (Number[] row : actual) {
	  		        assertNotNull("Row should not be null", row);
	  		        assertEquals("Row size should match", 1000, row.length);
	  		        for (Number number : row) {
	  		            assertNotNull("Element should not be null", number);
	  		            assertEquals("Element value should be 1.0", 1.0, number.doubleValue(), 0.0000001d);
	  		        }
	  		    }
	  		}

	  		
	  		@Test
	  		public void testCreateNumberArray2DWithZeros() {
	  		    double[][] data = {
	  		        {0.0, 0.0},
	  		        {0.0, 0.0}
	  		    };
	  		    Number[][] expected = {
	  		        {0.0, 0.0},
	  		        {0.0, 0.0}
	  		    };
	  		    Number[][] actual = DataUtilities.createNumberArray2D(data);
	  		    assertArrayEquals("2D Array of zeros should be handled correctly", expected, actual);
	  		}

	  		@Test
	  		public void testCreateNumberArray2DWithNegativeValues() {
	  		    double[][] data = {
	  		        {-1.0, -2.0},
	  		        {-3.0, -4.0}
	  		    };
	  		    Number[][] expected = {
	  		        {-1.0, -2.0},
	  		        {-3.0, -4.0}
	  		    };
	  		    Number[][] actual = DataUtilities.createNumberArray2D(data);
	  		    assertArrayEquals("2D Array should correctly handle negative values", expected, actual);
	  		}

	  		
	  		@Test
	  		public void testCreateNumberArray2DWithMixedValuesIncludingSpecial() {
	  		    double[][] data = {
	  		        {-1.0, 0.0, 1.0},
	  		        {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}
	  		    };
	  		    Number[][] expected = {
	  		        {-1.0, 0.0, 1.0},
	  		        {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}
	  		    };
	  		    Number[][] actual = DataUtilities.createNumberArray2D(data);
	  		    assertArrayEquals("2D Array should correctly handle a mix of values, including special floating-point values", expected, actual);
	  		}

	  		@Test
	  		public void testCreateNumberArray2DWithEmptyInnerArrays() {
	  		    double[][] data = new double[][]{{}, {}};
	  		    Number[][] expected = new Number[][]{{}, {}};
	  		    Number[][] actual = DataUtilities.createNumberArray2D(data);
	  		    assertArrayEquals("2D Array with empty inner arrays should be handled correctly", expected, actual);
	  		}

	  		@Test
	  		public void testCreateNumberArray2DWithVaryingLengths() {
	  		    double[][] data = {{1.0, 2.0}, {3.0}};
	  		    Number[][] expected = {{1.0, 2.0}, {3.0}};
	  		    Number[][] actual = DataUtilities.createNumberArray2D(data);
	  		    assertArrayEquals("2D Array with varying lengths of inner arrays should be handled correctly", expected, actual);
	  		}

	  		@Test
	  		public void testCreateNumberArray2DWithSpecialValuesIncludingNaN() {
	  		    double[][] data = {
	  		        {1.0, Double.NaN},
	  		        {Double.NaN, 3.0}
	  		    };
	  		    Number[][] expected = {
	  		        {1.0, Double.NaN},
	  		        {Double.NaN, 3.0}
	  		    };
	  		    Number[][] actual = DataUtilities.createNumberArray2D(data);

	  		    assertEquals("Array dimensions should match", expected.length, actual.length);
	  		    for (int i = 0; i < expected.length; i++) {
	  		        assertArrayEquals("Row " + i + " should match expected values",
	  		                          expected[i], actual[i]);
	  		    }
	  		}



	  		@Test
	  		public void testCreateNumberArray2DPerformance() {
	  		    double[][] data = new double[1000][1000];
	  		    for (int i = 0; i < data.length; i++) {
	  		        Arrays.fill(data[i], 1.0);
	  		    }
	  		    long startTime = System.currentTimeMillis();
	  		    DataUtilities.createNumberArray2D(data);
	  		    long endTime = System.currentTimeMillis();
	  		    assertTrue("Method should perform within reasonable time", (endTime - startTime) < 5000); // Example threshold of 5 seconds.
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
			
			//New Tests for Code Coverage
			
			@Test
			public void testCalculateRowTotalWithNullValues() {
			    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
			    data.addValue(1.0, "Row1", "Column1");
			    data.addValue(null, "Row1", "Column2");
			    data.addValue(2.0, "Row1", "Column3");
			    // The expected result should be 3.0 since we ignore the null value
			    assertEquals("The total of non-null values in the row should be 3.0",
			                 3.0, DataUtilities.calculateRowTotal(data, 0), 0.0000001d);
			}
			
			@Test
			public void testCalculateColumnTotalWithMixedNullAndExtremeValues() {
			    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
			    data.addValue(null, "Row1", "Column1");
			    data.addValue(Double.MAX_VALUE, "Row2", "Column1");
			    data.addValue(Double.MIN_VALUE, "Row3", "Column1");
			    // Expecting Double.MAX_VALUE since adding Double.MIN_VALUE should not change the total significantly
			    assertEquals("Column total with null and extreme values should calculate correctly",
			                 Double.MAX_VALUE, DataUtilities.calculateColumnTotal(data, 0), 0.0000001d);
			}

			@Test
			public void testCalculateRowTotalWithNullRows() {
			    DefaultKeyedValues2D data = new DefaultKeyedValues2D();
			    data.addValue(null, "Row1", "Column1");
			    data.addValue(null, "Row1", "Column2");
			    double result = DataUtilities.calculateRowTotal(data, 0);
			    assertEquals("Row total with all null values should be 0.0", 0.0, result, 0.0000001d);
			}

			@Test
			public void testCreateNumberArray2DWithNaNValues() {
			    double[][] data = new double[][]{{Double.NaN, Double.NaN}, {Double.NaN, Double.NaN}};
			    Number[][] expected = new Number[][]{{Double.NaN, Double.NaN}, {Double.NaN, Double.NaN}};
			    Number[][] actual = DataUtilities.createNumberArray2D(data);
			    for (int i = 0; i < expected.length; i++) {
			        assertArrayEquals("2D Array with NaN values should be handled correctly", expected[i], actual[i]);
			    }
			}

			@Test
			public void testGetCumulativePercentagesWithMixedSignValues() {
			    DefaultKeyedValues data = new DefaultKeyedValues();
			    data.addValue("Key1", -5.0);
			    data.addValue("Key2", 0.0);
			    data.addValue("Key3", 5.0);
			    KeyedValues result = DataUtilities.getCumulativePercentages(data);
			    assertEquals("Cumulative percentage with mixed sign values should handle zero correctly",
			                 0.0, result.getValue("Key2").doubleValue(), 0.00001d);
			    assertEquals("Cumulative percentage with mixed sign values should sum to 1.0",
			                 1.0, result.getValue("Key3").doubleValue(), 0.00001d);
			}
			
			@Test
			public void testCreateNumberArrayWithAlternatingSignLargeValues() {
			    // Create an array of large positive and negative values, alternating sign
			    double[] data = new double[100];
			    for (int i = 0; i < data.length; i++) {
			        data[i] = (i % 2 == 0) ? Double.MAX_VALUE : -Double.MAX_VALUE;
			    }

			    // Call the method under test
			    Number[] actual = DataUtilities.createNumberArray(data);

			    // Check that the array has been created correctly
			    assertEquals("Array length should match the input data length", data.length, actual.length);
			    for (int i = 0; i < actual.length; i++) {
			        assertEquals("Element value should match the input data", data[i], actual[i].doubleValue(), 0.0000001d);
			    }
			}


    
}