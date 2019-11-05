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
    static Country count11 = new Country();
    static Country count12 = new Country();
    int currentArmies ;


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
     * This method test the disc attack country 
     */
    @Test
    public void attackCountryDicTest() {

        boolean armies = playerModel.attackCountry(map,player, "Finland","Norway",10,3,cardStack);
        assertFalse(false);
        System.out.println("Attack test is passed");
    }

    /**
     * This method test the fortify 
     */
    @Ignore
    public void fortifyTest() {

        boolean armies = playerModel.fortifyCurrentPlayer(map, player, "India","Norway", 80);
        assertTrue(armies);

    }


}
