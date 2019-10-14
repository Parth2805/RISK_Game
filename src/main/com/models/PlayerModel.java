package com.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.entity.Player;
import com.config.Commands;
import com.config.Config;


public class PlayerModel {

	private ArrayList<Player> playersList;
	private static int[] numOfArmies = { Config.CONFIG_ARMIES_TWO_PLAYER, Config.CONFIG_ARMIES_THREE_PLAYER,
			Config.CONFIG_ARMIES_FOUR_PLAYER, Config.CONFIG_ARMIES_FIVE_PLAYER, Config.CONFIG_ARMIES_SIX_PLAYER };
	/**
	 * This is the default constructor of Player Model.
	 */
	public PlayerModel() {
		playersList = new ArrayList<Player>();
	}
	
	/**
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
	
	/*
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
	public void assignArmiesToPlayers() {
		int armiesCount = 0;
		int numPlayers = playersList.size();

		armiesCount = numOfArmies[numPlayers - 2];

		for (Player player : playersList) {
			player.setArmies(armiesCount);
		}
		System.out.println("Assigned " + numOfArmies + " armies to " + numPlayers + " players");
	}

	/**
	 * This method places armies.
	 * 
	 * @param playerName name of the player
	 * @return true if player gets created, false otherwise
	 */
	public void placeArmies() {

		Scanner sc = new Scanner(System.in);
		int flag = 0;
		
		while (flag == 0) {

			for (Player player : playersList) {

				String command = sc.nextLine();
				String words[] = command.split(" ");

				if (words[0].equals(Commands.MAP_COMMAND_PLACE_ARMY)) {

					String countryName = words[1];
					// Call to function for placing army in respective Country

				} else if (words[0].equals(Commands.MAP_COMMAND_PLACE_ALL)) {

					// Randomly place all countries
					flag = 1;
					System.out.println("End of Startup phase");
					break;
				}
			}
		}
	}
}
