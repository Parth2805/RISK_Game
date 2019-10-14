package com.entity;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.*;
import com.entity.Continent;


public class TestContinent {
    Continent cont = null;

    @BeforeClass
    public static void beforeAllTesting() {
        System.out.println("This is before testing");
    }
    @Before
    public void beforeTest() {
        cont = new Continent();
    }

    @AfterClass
    public static void afterPerformingTests() {
        System.out.println("The test is done");
    }

    @Test
    public void testGetColor() {
        assertNull(cont.getColor());
        System.out.println("'assertNull' test for getColor method is passed");
        assertNotEquals("Red", cont.getColor());
        System.out.println("'assertNotEqual' test for getColor method is passed");
    }

    @Test
    public void testGetName() {
        assertNull(cont.getName());
        System.out.println("'assertNull' test for getName method is passed");
        assertNotEquals("Advance Programming", cont.getName());
        System.out.println("'assertNotEqual' test for getName method is passed");
    }

}
