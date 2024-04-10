package org.jfree.data;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.jfree.data.DataUtilities;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class createNumberArrayTest {

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
}
