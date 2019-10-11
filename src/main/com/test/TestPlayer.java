package com.test;
import static org.junit.Assert.*;
import org.junit.*;
import com.entity.Country;
import com.entity.Player;

/**
 * Test all methods in Player class.
 * @author  Maryam
 * @author Mahmoudreza
 */

public class TestPlayer {

	Player ply = null;

	@BeforeClass
	public void beforeAll() {
		System.out.println("This is test for ...");
	}

	@Before
	public void beforePlayer() {
		ply = new Player(1,"Test");
	}

	@After
	public void afterPlayer(){
		System.out.println("After each method");
	}

	@Test
	public void testEqual() {
		boolean value = ply.equals(ply);
		boolean correctValue = true;
		assertEquals(correctValue, value);
	}

	@AfterClass
	public void afterAll() {
		System.out.println("All tests performed");
	}

}
