package com.models;

import java.util.*;

import com.config.Commands;
import com.controller.GameController;
import com.entity.*;
import com.config.Config;

/**
* This class is handles the behaviour of players.
* @author Parth
*
*/
public class PlayerModel {

	private ArrayList<Player> playersList;
	private static int[] numOfArmies = { Config.CONFIG_ARMIES_TWO_PLAYER, Config.CONFIG_ARMIES_THREE_PLAYER,
			Config.CONFIG_ARMIES_FOUR_PLAYER, Config.CONFIG_ARMIES_FIVE_PLAYER, Config.CONFIG_ARMIES_SIX_PLAYER };

	/**
	 * This is the default constructor of Player Model.
	 */
	public PlayerModel() {
		this.playersList = new ArrayList<Player>();
	}

	/**
	 * Get players list
	 * @return list of players
	 */
	public ArrayList<Player> getPlayersList() {
		return playersList;
	}

	/**
	 * Setter method for the player list.
	 * @param playersList array list of players
	 */
	public void setPlayersList(ArrayList<Player> playersList) {
		this.playersList = playersList;
	}

	/**
	 * This method removes the player from game.
	 * @param playerName name of the player
	 * @return true if player gets removed, false otherwise
	 */
	public boolean removePlayer(String playerName) {

		for (Player player : playersList) {
			if (player.getName().equalsIgnoreCase(playerName)) {
				playersList.remove(player);
				System.out.println("Player: " + playerName + " removed from the game");
				return true;
			}
		}

		System.out.println("Player: " + playerName + " does not exist in the game");

		return false;
	}

	/**
	 * This method creates the new player.
	 * @param playerName name of the player
	 * @return true if player gets created, false otherwise
	 */
	public boolean createPlayer(String playerName) {

		int id = playersList.size();

		if (id >= 6) {
			System.out.println("Exception: Maximum number of players = 6. Can't create more players");
			return false;
		}

		Player newPlayer = new Player(id + 1, playerName);

		if (playersList.contains(newPlayer)) {
			System.out.println("Exception: Player: " + playerName + " already exists in the game");
			return false;
		}

		playersList.add(newPlayer);
		System.out.println("Player: " + playerName + " is added in the game");

		return true;
	}

	/**
	 * This method allocates armies to players.
	 * @return true if armies are assigned to more then 1 player, false otherwise
	 */
	public boolean assignArmiesToAllPlayers() {
		int armiesCount = 0;
		int numPlayers = playersList.size();

		if (numPlayers >= 2) {

			armiesCount = numOfArmies[numPlayers - 2];

			for (Player player : playersList)
				player.setArmies(armiesCount);

			System.out.println("Assigned " + armiesCount + " armies to " + numPlayers + " players");

			return true;
		} else {
			System.out.println("Please create atleast 2 players to play the game.");
		}

		return false;
	}

	/**
	 * This method places all armies.
	 */
	public void placeAll() {

		for (Player p : getPlayersList()) {

			System.out.println("Placing armies for player: " + p.getName());
			while (p.getArmies() > 0) {

				Country con = p.getAssignedCountry().get(getRandomNumber(p.getAssignedCountry().size() - 1));
				con.setArmy(con.getArmy() + 1);
				p.setArmies(p.getArmies() - 1);
			}
		}
	}

	/**
	 * This method generates random number from 1 to number.
	 * @param number number up to which find random numbers to be generated, from 0 to number
	 * @return random number from 1 to number, including number
	 * 
	 */
	public static int getRandomNumber(int number) {
		return new Random().nextInt(number + 1);
	}

	/**
	 * This method places armies.
	 * @param map main map
	 * @param player player object
	 * @param countryName name of the country
	 * @return true if army gets placed, false otherwise
	 */
	public boolean placeArmy(Hmap map, Player player, String countryName) {

		int playerArmies = player.getArmies();

		if (!isCountryBelongToPlayer(map, player, countryName)) {
			return false;
		}

		if (playerArmies <= 0) {
			System.out.println("The player: " + player.getName() + " does not have any army left");
			return false;
		}

		for (Country c : player.getAssignedCountry()) {
			if (c.getName().equalsIgnoreCase(countryName)) {
				c.setArmy(c.getArmy() + 1);
				player.setArmies(playerArmies - 1);
				System.out.println(player.getName() + ": assigned 1 Army to " + c.getName());
				return true;
			}
		}

		return false;
	}

	/**
	 * This method checks armies of all players are exhausted or not.
	 * @return true if player has exhausted the armies
	 */
	public boolean isAllPlayersArmiesExhausted() {

		for (Player p : getPlayersList()) {
			if (p.getArmies() != 0) {
				return false;
			}
		}
		System.out.println("----------------------------------");
		System.out.println("All players have placed armies.");
		return true;
	}

	/**
	 * This method populates all countries.
	 * @param map map object
	 */
	public void populateCountries(Hmap map) {

		ArrayList<Country> countriesList = getCountryListFromMap(map);
		int playerNum = 0;
		Player currentPlayer;

		while (countriesList.size() != 0) {

			int chooseCountry = new Random().nextInt(countriesList.size());
			Country countryAssigned = countriesList.get(chooseCountry);

			// Get Player one by one from list and assign country
			currentPlayer = getPlayersList().get(playerNum);
			
			// Set player in assigned country in Map
			for (Continent cont : map.getContinents()) {
				for (Country c : cont.getCountries()) {
					if (c.getName().equalsIgnoreCase(countryAssigned.getName()))
						currentPlayer.setAssignedCountry(c);
				}
			}
			
			playerNum = (playerNum + 1) % getPlayersList().size();
			countriesList.remove(chooseCountry);

			// Set player in assigned country in Map
			for (Continent cont : map.getContinents()) {
				for (Country c : cont.getCountries()) {
					if (c.getName().equalsIgnoreCase(countryAssigned.getName()))
						c.setPlayer(currentPlayer);
				}
			}
		}
	}

	/**
	 * Parses the map and gets country list
	 * @param map map object
	 * @return list for countries from root Map
	 */
	public ArrayList<Country> getCountryListFromMap(Hmap map) {
		ArrayList<Country> countryListfromMap = new ArrayList<Country>();

		for (Continent c : map.getContinents()) {
			for (Country cont : c.getCountries()) {
				countryListfromMap.add(cont);
			}
		}

		return countryListfromMap;
	}

	/**
	 * It shows all countries and continents, armies on each country, ownership, and connectivity
	 * @param map main map
	 * 
	 */
	public void gamePlayShowmap(Hmap map) {

		System.out.println("----------------------------------");

		for (Continent cont : map.getContinents()) {
			for (Country c : cont.getCountries()) {
				System.out.println(c.getBelongToContinent().getName() + ": " + c.getName() + ": Army count: "
						+ c.getArmy() + ", Player: " + c.getPlayer().getName() + ", Adjacent Countries: "
						+ c.getAdjacentCountries());
			}
		}
		
		System.out.println("----------------------------------");
	}

	/**
	 * This method counts the number of reinforcement armies for the player.
	 * @param player current player object
	 * @return the number armies player will get in reinforcement
	 */
	public int countReinforcementArmies(Player player) {
		int currentArmies = player.getArmies();
		int countryCount = player.getAssignedCountry().size();
		System.out.println("Number of Countires for Player : " + player.getName() + " = " + countryCount);
		if (countryCount < 9) {
			currentArmies = currentArmies + 3;
		} else {
			currentArmies += Math.floor(countryCount / 3);
		}
		System.out.println("After reinforcement, current number of Armies for Player : " + player.getName() + " = "
				+ currentArmies);

		return currentArmies;
	}

	/**
	 * @param map main map It will put one army on every country
	 */
	public void intitializeArmiesForAllCountries(Hmap map) {

		for (Continent cont : map.getContinents()) {
			for (Country c : cont.getCountries()) {
				c.setArmy(c.getArmy() + 1);
				c.getPlayer().setArmies(c.getPlayer().getArmies() - 1);
			}
		}
	}

	/**
	 * This will do reinforcement
	 * @param player current player
	 * @param countryName name of the country
	 * @param numberOfArmie number of armies
	 * @return true if reinforcement is done, false otherwise
	 */
	public boolean reinforceArmiesForCurrentPlayer(Player player, String countryName, int numberOfArmies) {

		int currentArmies = player.getArmies();

		if (currentArmies < numberOfArmies) {
			System.out.println("You dont have enough army to reinforce: Your armies count = " + player.getArmies());
			return false;
		}

		for (Country c : player.getAssignedCountry()) {
			if (c.getName().equalsIgnoreCase(countryName)) {
				c.setArmy(c.getArmy() + numberOfArmies);
				player.setArmies(currentArmies - numberOfArmies);
			}
		}

		if (player.getArmies() == 0) {
			System.out.println("Reinforcement has been completed. You can now attack the countries.");
			return true;
		}

		return false;
	}

	/**
	 * This will assign armies to all players in Reinforcement
	 */
	public void assignReinforceArmiesToPlayers() {
		
		for (Player p : getPlayersList()) {
			int reinforeArmies = countReinforcementArmies(p);
			p.setArmies(reinforeArmies);
		}
	}

	/**
	 * This method will fortify for current player
	 * @param map map object
	 * @param player player object
	 * @param fromCountry from country name
	 * @param toCountry to country name
	 * @param armiesCount number of armies
	 * @return true if fortification is successful, false otherwise
	 */
	public boolean fortifyCurrentPlayer(Hmap map, Player player, String fromCountry, String toCountry,
			int armiesCount) {

		if (!isCountryBelongToPlayer(map, player, fromCountry))
			return false;

		if (!isCountryBelongToPlayer(map, player, toCountry))
			return false;

		int fromCountryArmyCount = map.getCountryMap().get(fromCountry).getArmy();
		int toCountryArmyCount = map.getCountryMap().get(toCountry).getArmy();

		if (armiesCount >= fromCountryArmyCount) {
			System.out.println("Exception: Given army count should be less than fromCountry: " + fromCountry
					+ "'s current armies which is = " + fromCountryArmyCount);
			return false;
		}

		if (isCountriesAdjacent(map, fromCountry, toCountry)) {

			for (Continent cont : map.getContinents()) {
				// Update Armies count for fortification
				for (Country c : cont.getCountries()) {
					if (c.getName().equalsIgnoreCase(toCountry))
						c.setArmy(toCountryArmyCount + armiesCount);

					if (c.getName().equalsIgnoreCase(fromCountry))
						c.setArmy(fromCountryArmyCount - armiesCount);
				}
			}

			map.getCountryMap().get(toCountry).setArmy(toCountryArmyCount + armiesCount);
			map.getCountryMap().get(fromCountry).setArmy(fromCountryArmyCount - armiesCount);

			return true;

		} else {
			System.out.println("Exception: fromCountry: " + fromCountry + " toCountry: " + toCountry + " are not adjacent.");
		}

		return false;
	}

	/**
	 * This method will fortify for current player
	 * @param map main map
	 * @param currentPlayer current player
	 * @param country name of to country
	 * @return true if country belong to given player
	 */
	public boolean isCountryBelongToPlayer(Hmap map, Player currentPlayer, String country) {

		if (map.getCountryMap().get(country) == null) {
			System.out.println("Error: Given country " + country + " does not exist in map");
			return false;
		}

		if (map.getCountryMap().get(country).getPlayer().getName().equalsIgnoreCase(currentPlayer.getName()))
			return true;

		// TODO print here ?
		System.out.println("Error: Given country " + country + " does not belong " + currentPlayer);
		return false;
	}

	/**
	 * This method will fortify for current player
	 * @param map map object
	 * @param fromCountry. name of from country
	 * @param toCountry name of to country
	 * @return true if countries are adjacent, false otherwise
	 */
	public boolean isCountriesAdjacent(Hmap map, String fromCountry, String toCountry) {

		for (String nbrCountry : map.getCountryMap().get(fromCountry).getNeighborCountries()) {
			if (nbrCountry.equalsIgnoreCase(toCountry)) {
				for (String origCountry : map.getCountryMap().get(toCountry).getNeighborCountries()) {
					if (origCountry.equalsIgnoreCase(fromCountry))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * This method will formulate country hashmap.
	 * @param countryList list of countries
	 * @return country hashmap
	 */
	public Map<String, Country> getCountryMapFromList(ArrayList<Country> countryList) {
		Map<String, Country> countryMap = new TreeMap<String, Country>(String.CASE_INSENSITIVE_ORDER);
		
		for (Country c : countryList)
			countryMap.put(c.getName(), c);

		return countryMap;
	}

	/**
	 * This method checks whether current player is the last player or not.
	 * @param currentPlayer current player
	 * @return true if current player is the last player, false otherwise
	 */
	public boolean isLastPlayer(Player currentPlayer) {

		String lastPlayerName = getPlayersList().get(getPlayersList().size() - 1).getName();

		if (currentPlayer.getName().equalsIgnoreCase(lastPlayerName))
			return true;

		return false;
	}

	/**
	 * This implements attach phase.
	 * @return true if current player is the last player, false otherwise
	 */
	public boolean attackCountry(Hmap map, Player player, String attackingCountry, String defendingCountry, int numOfDice) {

		Country attackCountry;
		Country defendCountry;
		
		if (numOfDice > 3) {
			System.out.println("Error: Can attack only with 1-3 dice");
			return false;
		}

		// check if attacking country belongs to player
		if (!isCountryBelongToPlayer(map, player, attackingCountry))
			return false;

		// check if defending country does not belongs to same player
		if (isCountryBelongToPlayer(map, player, defendingCountry)) {
			System.out.println("Error: Can't attack becuase attacking country: " + attackingCountry + " and defending country " + defendingCountry + " belongs to same" + player);
			return false;
		}
		
		// check if defending country belongs to neighbor
		if (!isCountriesAdjacent(map, attackingCountry, defendingCountry)) {
			System.out.println("Error: Can't attack to this country as its not your neighbor");
			return false;
		}
		
		attackCountry = map.getCountryMap().get(attackingCountry);
		defendCountry = map.getCountryMap().get(defendingCountry);

		// Check armies count
		if (attackCountry.getArmy() <= 1) {
			System.out.println("Error: Can't attack with " + attackingCountry + " country as it has only one army (need > 1 army to attack");
			return false;
		} 
		
		// Check dice count
		if (attackCountry.getArmy() <= numOfDice) {
			System.out.println("Error: Can't attack because your (attack armies count = " +
					(attackCountry.getArmy() - 1)  + ") < (num of dice = " + numOfDice + ")");
			return false;
		}

		String defenderPlayerName = defendCountry.getPlayer().getName();
        Player defenderplayer = null;
  
        for (Player p: playersList) {
            if (p.getName().equalsIgnoreCase(defenderPlayerName))
            	defenderplayer = p;
        }

		// Do attack now
        int numOfDefenderDice = getDefenderDice(defenderplayer, defendCountry);
    
        DiceModel diceModel = new DiceModel(attackCountry, defendCountry, numOfDice, numOfDefenderDice);
        diceModel.rolldice();
        diceModel.getResultAfterRoll();
        if(defendCountry.getArmy()==0){

			attackCountry.getPlayer().setCardList(GameController.stackOfCards.pop());
			modifyDefendingCountryOwnerShip(defendCountry,attackCountry);
		}
        // Is game over for defender player?
        
        // Is game win by attacker?
        
        // Move country
        
		return false;
	}
	
	/**
	 * This gets the Dice values of defender.
	 * @return Dice value of defender
	 */
	public int getDefenderDice(Player player, Country defendCountry) {
	
		int numOfDice = 0;

	    while (true) {
	        Scanner sc = new Scanner(System.in);
	        System.out.println("Defending Player: " + player.getName());
	        System.out.println("Use \"defend numdice\" command");
	
			String command = sc.nextLine();
			String words[] = command.split(" ");
	
			switch (words[0]) {
	
			case Commands.MAP_COMMAND_DEFEND:
				
				if (words.length < 2) {
					System.out.println("Invalid command, Try again !!!");
				}
				
				try {
					numOfDice = Integer.parseInt(words[1]);
				} catch (Exception e) {
					System.out.println("Exception: " + e.toString());
					break;
				}
				
				if (numOfDice > 2) {
					System.out.println("Error: number of dice should be less than 3");
					break;
				}
				
				if (player.getArmies() < numOfDice) {
					System.out.println("Error: Can't defend with your (defend armies count = " +
							(defendCountry.getArmy())  + ") < (num of dice = " + numOfDice + ")");
					break;
				}
				
				return numOfDice;
				
			default:
				System.out.println("Invalid command, Try again !!!");
				break;
			}
        }
	}
	/**
	 * This method all out attack country 
	 * @param map map object
	 * @param player player object
	 * @param attackingCountry  attack country
	 * @param defendingCountry define the country 
	 * @param numOfDice number of dice
	 */

	public void alloutattackCountry(Hmap map, Player player, String attackingCountry, String defendingCountry, int numOfDice) {

		Country attackCountry = map.getCountryMap().get(attackingCountry);
		Country defendCountry = map.getCountryMap().get(defendingCountry);

		// check if attacking country belongs to player
		if (!isCountryBelongToPlayer(map, player, attackingCountry))
			return;

		// check if defending country does not belongs to same player
		if (isCountryBelongToPlayer(map, player, defendingCountry)) {
			System.out.println("Error: Can't attack becuase attacking country: " + attackingCountry + " and defending country " + defendingCountry + " belongs to same" + player);
			return;
		}

		// check if defending country belongs to neighbor
		if (!isCountriesAdjacent(map, attackingCountry, defendingCountry)) {
			System.out.println("Error: Can't attack to this country as its not your neighbor");
			return;
		}

		while(true){


			if(attackCountry.getArmy()==1){
				break;
			}
			if(defendCountry.getArmy()<=0){
				break;
			}
			int numOfDefenderDice=2;
			numOfDice=3;

			// Check armies count
			if (attackCountry.getArmy() <= 3) {

				numOfDice=attackCountry.getArmy()-1;

			}
			if(defendCountry.getArmy()<=2){

				numOfDefenderDice=defendCountry.getArmy();
			}



			// Do attack now
			DiceModel diceModel = new DiceModel(attackCountry, defendCountry, numOfDice, numOfDefenderDice);
			diceModel.rolldice();
			diceModel.getResultAfterRoll();
		}

		if(defendCountry.getArmy()==0){

			attackCountry.getPlayer().setCardList(GameController.stackOfCards.pop());
			modifyDefendingCountryOwnerShip(defendCountry,attackCountry);
		}
	}

	/**
	 * This method check win game
	 * @param player object player
	 * @param totalCoutries total country
	 * @return true if player size is equal to total country size ,otherwise false
	 */
	public boolean winGame(Player player,List<Country> totalCoutries){


		if(player.getAssignedCountry().size()==totalCoutries.size()){

			return true;
		}
//		for(Country pc:playerCountryList){
//
//			for(Country tc:playerCountryList){
//
//				if(pc.getName().equals(tc.getName())){
//					count++;
//				}
//
//			}
//
//		}
		return false;

	}

	/**
	 * This method modify country owner ship
	 * @param defendingCountry define country 
	 * @param attackingCountry attack country
	 */
	public void modifyDefendingCountryOwnerShip(Country defendingCountry,Country attackingCountry) {

		List<Country> defendersCountries = defendingCountry.getPlayer().getAssignedCountry();

		defendersCountries.remove(defendingCountry);

		defendingCountry.setPlayer(attackingCountry.getPlayer());
		attackingCountry.getPlayer().getAssignedCountry().add(defendingCountry);

	}
}
