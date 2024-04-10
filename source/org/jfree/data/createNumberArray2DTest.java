package org.jfree.data;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.jfree.data.DataUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class createNumberArray2DTest {

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

}
