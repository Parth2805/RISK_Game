package com.entity;

import com.entity.Player;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPlayer {
	 Player ply = null;

	    @BeforeClass
	    public static void beforeAllTesting() {
	        System.out.println("This is for testing Player Class");
	    }
	    @Before
	    public void beforeTest() {
	        ply = new Player(10,"Player");
	    }

	    @AfterClass
	    public static void afterPerformingTests() {
	        System.out.println("The test is done");
	    }

	    @Test
	    public void testGetId() {
	        assertEquals( 10,ply.getId());
	        System.out.println("'assertEquals' test for getId method is passed");

	        assertTrue( ply.getId()==10);
	        System.out.println("'assertTrue' test for getId method is passed");

	        assertNotEquals( 12,ply.getId());
	        System.out.println("'assertNotEquals' test for getId method is passed");
	    }
	/*
	    @Test
	    public void testGetPlayer() {
	        assertNull(cn.getPlayer());
	        System.out.println("'assertNull' test for getPlayer method is passed");

	        assertNotEquals("Name of the Player", cn.getPlayer());
	        System.out.println("'assertNotEqual' test for getPlayer method is passed");
	    }
	/*
	    @Test
	    public void testEquals() {
	        assertTrue(cn.equals(t));
	    }
	*/

}
