package com.buddy.sdk.unittests.utils;

import java.io.InputStream;

public class InstrumentationTestCase
{
	public void assertTrue(boolean bool)
	{
		org.junit.Assert.assertTrue(bool);
	}
	
	public void assertFalse(boolean bool)
	{
		org.junit.Assert.assertFalse(bool);
	}
	
	public void assertEquals(double expected, double actual, int delta)
	{
		org.junit.Assert.assertEquals(expected, actual, delta);
	}
	
	public void assertEquals(String expected, String actual, String delta)
	{
		org.junit.Assert.assertEquals(expected, actual, delta);
	}
	
	public void assertEquals(int expected, int actual)
	{
		org.junit.Assert.assertEquals(expected, actual);
	}
	
	public void assertEquals(int expected, long actual)
	{
		org.junit.Assert.assertEquals(expected, actual);
	}
		
	public void assertEquals(String expected, String actual)
	{
		org.junit.Assert.assertEquals(expected, actual);
	}
	
	public void assertNotNull(Object object)
	{
		org.junit.Assert.assertNotNull(object);
	}

	public void assertNull(Object object)
	{
		org.junit.Assert.assertNull(object);
	}

	public void fail(String string)
	{
		org.junit.Assert.fail(string);
	}

    protected InputStream getStreamFromFile(String fileName){
    	try{
    		return this.getClass().getClassLoader().getResourceAsStream(fileName);
    	}catch(Exception e){
    		// TODO Auto-generated catch block
            e.printStackTrace();
            return null;
    	}
    }
}
