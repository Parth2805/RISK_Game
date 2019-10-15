package com.models;

import java.util.*;

import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.config.Config;


public class PlayerModel {

	private ArrayList<Country> countryList;
	private ArrayList<Player> playersList;
	private static int[] numOfArmies = { Config.CONFIG_ARMIES_TWO_PLAYER, Config.CONFIG_ARMIES_THREE_PLAYER,
			Config.CONFIG_ARMIES_FOUR_PLAYER, Config.CONFIG_ARMIES_FIVE_PLAYER, Config.CONFIG_ARMIES_SIX_PLAYER };
	Player currentPlayer;

	/**
	 * This is the default constructor of Player Model.
	 */
	public PlayerModel() {
		this.playersList = new ArrayList<Player>();
		this.countryList = new ArrayList<Country>();
	}
	
	/**
	 * Get method for country list.
	 */
	public ArrayList<Country> getCountryList() {
		return countryList;
	}

	/**
	 * Setter method for the map object.
	 *
	 * @param countryList list of country
	 */
	public void setCountryList(ArrayList<Country> countryList) {
		this.countryList = countryList;
	}
	
	/**
	 * Get the current player.
	 * @return player playing
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * This method is to set the current player.
	 * 
	 * @param player Current player.
	 */
	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}
		
	public ArrayList<Country> getCountryList(Hmap map) {
		
		for (Continent c: map.getContinents()) {
			for (Country country: c.getCountries()) {
				countryList.add(country);
			}
		}
		
		return countryList;
	}
	
	/**
=======
>>>>>>> added get set country methods
	 * Get players list
	 *
	 * @return list of players
	 */
	public ArrayList<Player> getPlayersList() {
		return playersList;
	}
	
	/**
	 * Setter method for the player list.
	 *
	 * @param playersList array list of players
	 */
	public void setPlayersList(ArrayList<Player> playersList) {
		this.playersList = playersList;
	}
	
	/**
	 * This method removes the player from game.
	 * 
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
	 * 
	 * @param playerName name of the player
	 * @return true if player gets created, false otherwise
	 */
	public boolean createPlayer(String playerName) {
		
		int id = playersList.size();
		Player newPlayer = new Player(id + 1, playerName);
		
		if (playersList.contains(newPlayer)) {
			System.out.println("Player: " + playerName + " already exists in the game");
			return false;
		}
		
		playersList.add(newPlayer);
		System.out.println("Player: " + playerName + " is added in the game");
		
		return true;
	}
	
	/**
	 * This method allocates armies to players.
	 */
	public boolean assignArmiesToPlayers() {
		int armiesCount = 0;
		int numPlayers = playersList.size();

		if (numPlayers >= 2) {
			
			armiesCount = numOfArmies[numPlayers - 2];
			for (Player player : playersList)
				player.setArmies(armiesCount);
	
			System.out.println("Assigned " + armiesCount + " armies to " + numPlayers + " players");
			return true;
		}
		else {
			System.out.println("Please create atleast 2 players to play the game.");
		}
		
		return false;
	}

	/**
	 * This method places armies.
	 * 
	 * @return true if player gets created, false otherwise
	 */
	public boolean placeAll() {

		return false;
	}
	
	/**
	 * This method places armies.
	 * 
	 * @param playerName name of the player
	 * @return true if player gets created, false otherwise
	 */
	public boolean placeArmies(Hmap map, String countryName) {

		int playerArmies = currentPlayer.getArmies();

		if (playerArmies > 0) {
		
		} else {
			System.out.println("The player: " + currentPlayer.getName() + " does not have any army left");
		}
		
		if (isAllPlayersArmiesExhausted(getPlayersList())) {
			return true;
		}
		
		return false;
	}

	/**
	 * This method checks if players armies is exhausted.
	 * 
	 * @param players object of player
	 * @return returns true if player has exhausted the armies
	 */
	public static boolean isAllPlayersArmiesExhausted(ArrayList<Player> players) {
		for (Player p : players) {
			if (p.getArmies() != 0) {
				return false;
			}
		}
		return true;
	}

	public void assignCountries(Hmap rootmap) {

		String temp[]= rootmap.getCountriesIdxMap().keySet().toArray(new String[0]);
		List<String> countriesList=Arrays.asList(temp);
		System.out.println(countriesList.toString());

		int playernumber=0;
		while(countriesList.size()!=0){

			int chooseCountry = new Random().nextInt(countriesList.size());
			String countryAssigned=countriesList.get(chooseCountry);
			Country country=new Country();
			country.setName(countryAssigned);
			playersList.get(playernumber).setAssignedCountry(country);
			playernumber=(playernumber+1)%playersList.size();

			//countriesList.remove();


		}
		for(Player players:playersList){

			System.out.println(players.getAssignedCountry());


		}
	}
	
	/**
	 * Parses the map and gets country list
	 * 
	 * @param map map object
	 */
	public ArrayList<Country> getCountryListFromMap(Hmap map) {
		ArrayList<Country> countryListfromMap = new ArrayList<Country>();
		
		for (Continent c: map.getContinents()) {
			for (Country cont: c.getCountries()) {
				countryListfromMap.add(cont);
			}
		}
		
		return countryListfromMap;
	}
}
