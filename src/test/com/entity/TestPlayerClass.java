package com.entity;

import org.junit.*;

import static org.junit.Assert.*;

/**
 * This is a Test Class for testing card
 * @author Maryam
 * @author Mahmoudreza
 * @version 0.0.1
 */
public class TestPlayerClass {
 
  Player ply = null;

  /**
   * This method runs before all test methods only one times
   */
  @BeforeClass
  public static void beforeAllTesting() {
    System.out.println("This is for testing Player Class");
  }

  /**
   * This Method runs before test methods 
   */
  @Before
  public void beforeTest() {
    ply = new Player(10,"Player");
  }

  /**
   * This method run after all test methods only one time.
   */
  @AfterClass
  public static void afterPerformingTests() {
      System.out.println("The test is done");
  }

  /**
   * This method test Id for player.
   */
  @Test
  public void testGetId() {
      assertEquals( 10,ply.getId());
      System.out.println("'assertEquals' test for getId method is passed");

      assertTrue( ply.getId()==10);
      System.out.println("'assertTrue' test for getId method is passed");

      assertNotEquals( 12,ply.getId());
      System.out.println("'assertNotEquals' test for getId method is passed");
  }
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
