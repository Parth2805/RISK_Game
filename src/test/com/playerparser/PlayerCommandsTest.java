package com.playerparser;

import static org.junit.Assert.*;
import org.junit.*;

import com.entity.Country;
import com.entity.Player;
import com.playerparser.PlayerCommands;

public class PlayerCommandsTest {
	Player player;
	PlayerCommands playerCmd;
	
	@BeforeClass
	public static void beforeAllTesting() {
		System.out.println("This is for testing Player Class");
	}

	@Before
	public void beforeTest() {
		player = new Player(4, "TestPlayer");
		playerCmd = new PlayerCommands();
	}

	@AfterClass
	public static void afterPerformingTests() {
		System.out.println("The tests are done");
	}
	
	@Test
	public void testThreeReinforceArmiesForPLayer() {
		
		for (int idx = 0; idx < 8; idx++)
			player.getAssignedCountry().add(new Country());
		
		int armies = playerCmd.countReinforcementArmies(player);
		assertEquals(3, armies);
	}
	
	@Test
	public void testReinforceArmiesCountForPLayer() {
		
		for (int idx = 0; idx < 25; idx++)
			player.getAssignedCountry().add(new Country());
		
		int armies = playerCmd.countReinforcementArmies(player);
		assertEquals(8, armies);
	}
}

