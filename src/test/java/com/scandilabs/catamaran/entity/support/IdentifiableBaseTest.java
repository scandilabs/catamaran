package com.scandilabs.catamaran.entity.support;

import java.io.IOException;

import junit.framework.Assert;
import junit.framework.TestCase;

public class IdentifiableBaseTest extends TestCase {

    public void testIdComparisions() throws IOException {
    	TestIdentifiable id1 = new TestIdentifiable();
    	id1.setId(-1L);
    	TestIdentifiable id2 = new TestIdentifiable();
    	id2.setId(-1L);
    	Assert.assertTrue(id1.equals(id2));
    	
    	id2.setId(-2L);
    	Assert.assertFalse(id1.equals(id2));
  
    	//null check
    	id1.setId(null);
    	id2.setId(null);
    	Assert.assertFalse(id1.equals(id2));
    	
    	id1.setId(null);
    	id2.setId(120L);
    	Assert.assertFalse(id1.equals(id2));

    	id1.setId(120L);
    	id2.setId(null);
    	Assert.assertFalse(id1.equals(id2));

    	id1.setId(0L);
    	id2.setId(new Long(15));
    	Assert.assertFalse(id1.equals(id2));

    	id1.setId(20L);
    	id2.setId(0L);
    	Assert.assertFalse(id1.equals(id2));
    	
    	id1.setId(new Long(15));
    	id2.setId(new Long(15));
    	Assert.assertTrue(id1.equals(id2));
    	
    	id1.setId(new Long(15334));
    	id2.setId(new Long(15334));
    	Assert.assertTrue(id1.equals(id2));
    }
}
