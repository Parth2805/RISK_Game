package com.test;
import static org.junit.Assert.*;

import org.junit.Test;

import com.entity.Country;
import com.entity.Player;

public class testPlayer {
	/**
	 * Test equal method in player class.
	 * @author  Maryam
	 */
	@Test
	public void testEqual() {
		Player p = new Player(1, "sara");

		boolean value = p.equals(p);
		boolean correctValue = true;
		assertEquals(correctValue, value);
	}

}
