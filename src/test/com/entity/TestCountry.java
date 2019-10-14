package com.entity;
import com.entity.Country;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.*;


public class TestCountry {
	Country cn = null;

    @BeforeClass
    public static void beforeAllTesting() {
        System.out.println("This is for testing Country Class");
    }
    @Before
    public void beforeTest() {
        cn = new Country();
    }

    @AfterClass
    public static void afterPerformingTests() {
        System.out.println("The test is done");
    }

    @Test
    public void testGetArmy() {
        assertEquals( 0,cn.getArmy());
        System.out.println("'assertEquals' test for getArmy method is passed");

        assertTrue( cn.getArmy()==0);
        System.out.println("'assertTrue' test for getArmy method is passed");

        assertNotEquals( 6000,cn.getArmy());
        System.out.println("'assertNotEquals' test for getArmy method is passed");
    }

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
