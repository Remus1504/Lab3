package org.jfree.data;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class getCumulativePercentagesTest {

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

	    
}
