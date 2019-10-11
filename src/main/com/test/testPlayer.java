package com.test;
import static org.junit.Assert.*;
import org.junit.*;


import com.entity.Country;
import com.entity.Player;

public class testPlayer {
	/**
	 * Test all methods in Player class.
	 * @author  Maryam
	 * @author Mahmoudreza
	 */
	Player ply = null;
	@BeforeClass
	public void beforeAll() {
		System.out.println("This is test for ...");
	}
	@Before
	//we need to edit
	public void beforePlayer() {
		ply = new Player(1,"Test");
	}
	@Test
	public void testEqual() {
		boolean value = ply.equals(ply);
		boolean correctValue = true;
		assertEquals(correctValue, value);
	}
	@Test
	//--> ...
	public void test(){

	}

}
