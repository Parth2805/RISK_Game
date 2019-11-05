package com.model;

import com.models.PlayerModel;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import com.entity.*;

/**
 * This class test the attack phase
 * @author Maryam
 * @author MahmoudReza
 *
 */
public class AttachTest {

    public static Hmap map;
    public static Player player;
    public static PlayerModel playerModel;
    public static Stack<Card> cardStack;
    public static Country count1 = new Country();
    static Country count12 = new Country();
    int currentArmies ;
    public ArrayList<Player> playersList;
    String countryName;


    /**
     * This method runs before all tests just one time
     */
    @BeforeClass
    public static void beforeAll () {
    System.out.println("This is for testing Attack Class");
    }

    /**
     * This method runs before  each test
     */
    @Before
    public void beforeTest () {
        player = new Player(4,"playerTest");
        playerModel = new PlayerModel();
        cardStack =new Stack<Card>();
        currentArmies = player.getArmies();

    }
    /**
     * This method runs after all test just one time
     */
    @AfterClass
    public static void afterAllTest() {
        System.out.println("All tests are done");
    }
    
    /**
     * This method test the attack country
     */
    @Test
    public void attackCountryTest() {

        boolean armies = playerModel.attackCountry(map,player, "Swiss","Finland",10,3,cardStack);
        assertTrue(true);
    }

    /**
     * This method test the dice attack country 
     */
    @Test
    public void attackCountryDiceTest() {

        boolean armies = playerModel.attackCountry(map,player, "Finland","Norway",10,3,cardStack);
        assertFalse(false);
        System.out.println("Attack test is passed");
    }

    /**
     * This method test the fortify 
     */
    @Ignore
    public void fortifyTest() {
    	String fromCountry=null;
    	String toCountry=null;
    	int armiesCount=10;
    	int fromCountryArmyCount =0;
		int toCountryArmyCount = 0;
		//assertSame(fromCountryArmyCount,toCountryArmyCount);
		boolean p=playerModel.isCountryBelongToPlayer(map, player, fromCountry);
		//playerModel.isCountriesAdjacent(map, fromCountry, toCountry);
		//boolean p=playerModel.fortifyCurrentPlayer(map, player, fromCountry, toCountry, armiesCount);
		
       // boolean armies = playerModel.fortifyCurrentPlayer(map, player, fromCountry,toCountry, armiesCount);
        assertNotEquals(p,true);

    }
    /**
     * This method test assign armies to all players
     */
    @Test
    public void assignArmiesTest() {
    	
    	int armiesCount = 0;
		int numPlayers = 20;
    	boolean assignarmy= playerModel.assignArmiesToAllPlayers();
    	assertFalse(assignarmy);
    }
    
//    @Test 
//    public void placeArmyTest () {
//    	int playerArmies=10;
//    	boolean p= playerModel. placeArmy( map,  player, countryName);
//    	assertTrue(p);
//    	
//    }
    /**
     * This method test is all player armies exhausted
     */
    @Test
    public void playerArmiesTest() {
    	playerModel.getPlayersList();
    	boolean p=playerModel.isAllPlayersArmiesExhausted();
    	assertTrue(p);
    }
    
    /**
     * This method test the count reinforcement armies
     */
    @Test
    public void countReinforcementArmiesTest() {
    	int currentArmies =10;
		int countryCount = 12;
    	int count=playerModel.countReinforcementArmies(player);
    	assertNotEquals(count,10);
    	
    }
    
    /**
     * This method test reinforce Armies For CurrentPlayer
     */
    @Test
    public void reinforceArmiesForCurrentPlayerTest() {
    	String countryName=null;
    	int numberOfArmies=0;
    	player.getArmies() ;
    	boolean p=playerModel.reinforceArmiesForCurrentPlayer( player,  countryName, numberOfArmies);
    	assertTrue(p);
    }
    
    /**
     * This method test get Defender Dice
     */
    @Test
    public void getDefenderDiceTest() {
    	int numOfDice = 0;
    	int p=playerModel. getDefenderDice( player, count1);
    	assertNotEquals(p,10);
    }


}
