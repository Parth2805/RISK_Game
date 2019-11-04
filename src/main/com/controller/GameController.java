package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.config.Commands;
import com.entity.Hmap;
import com.entity.Player;
import com.exception.InvalidMap;
import com.mapparser.MapReader;
import com.mapparser.MapVerifier;
import com.mapparser.MapWriter;
import com.models.PlayerModel;


/**
 * This class reads, parses the command line string from user input.
 *
 * @author Parth
 * @author Mehul
 */
public class GameController {

	Hmap rootMap;
	MapWriter mapWriter;
	
	String editFilePath = "";
	boolean isReinfoceArmiesAssigned = false;
	
	PlayerModel playerModel;

	Player currentPlayer;
		
	// default constructor to initialize members
	public GameController() {
		this.mapWriter = new MapWriter();
		this.playerModel = new PlayerModel();
		this.rootMap = new Hmap();
	}

	/**
	 * Get the current player.
	 * 
	 * @return currentPlayer playing
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
	
	/**
	 * Setter method for the map object.
	 *
	 * @param map object
	 * @return root map
	 */
	private Hmap setMap(Hmap map) {
		return this.rootMap = map;
	}

	/**
	 * Get map object
	 *
	 * @return the map
	 */
	private Hmap getMap() {
		return rootMap;
	}

	/**
	 * Parses the String and calls the related map edit commands.
	 * 
	 * @param command User input Command/String
	 * @return true if command is processed correctly, false otherwise
	 */
	public boolean processMapEditCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0], filePath = "";
		MapReader mapReader;
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();

		switch (commandType) {

		case Commands.MAP_COMMAND_EDIT_CONTINENT:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					
					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return false;
					}
					
					MapContoller.addContinent(getMap(), words[idx + 1], words[idx + 2], "");
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					
					if (words.length < idx + 2) {
						System.out.println("Invalid command, Try again !!!");
						return false;
					}
					MapContoller.removeContinent(getMap(), words[idx + 1]);
					idx = idx + 1;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_EDIT_COUNTRY:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					
					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return false;
					}
					
					MapContoller.addCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					
					if (words.length < idx + 2) {
						System.out.println("Invalid command, Try again !!!");
						return false;
					}
					
					MapContoller.removeCountry(getMap(), words[idx + 1]);
					idx = idx + 1;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_EDIT_NEIGHBOR:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					
					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return false;
					}
					
					MapContoller.addNeighborCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					
					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return false;
					}
					
					MapContoller.removeNeighborCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_SHOWMAP:
			MapContoller.mapEditorShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_SAVEMAP:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}

			filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];

			// save map file should be similar to the one which was edited previously
			if (!editFilePath.isEmpty()) {
				if (!editFilePath.equals(filePath)) {
					System.out.println("Please give same filename as you have given in editmap.");
					break;
				}
			}
			
			try {
				MapVerifier.verifyMap(getMap());
			} catch (InvalidMap e1) {
				System.out.println("Exception: " + e1.toString());
				break;
			}
				
			System.out.println("Saving File at: " + filePath);
			File outputMapFile = new File(filePath);
						
			mapWriter.writeMapFile(getMap(), outputMapFile);
			break;

		case Commands.MAP_COMMAND_EDITMAP:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}
			
			editFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];
			File editMapFile = new File(editFilePath);
			mapReader = new MapReader();

			if (editMapFile.exists()) {
				try {
					setMap(mapReader.readMapFile(editMapFile));
				} catch (InvalidMap e) {
					System.out.println("Exception: " + e.toString());
				}
			} else {
				try {
					editMapFile.createNewFile();
					System.out.println("Given map file does not exist. New Map file has been created.");
				} catch (IOException e) {
					System.out.println("Exception: " + e.toString());
				}
			}
			break;

		case Commands.MAP_COMMAND_VALIDATEMAP:

			try {
				MapVerifier.verifyMap(getMap());
			} catch (InvalidMap e1) {
				System.out.println("Exception: " + e1.toString());
			}
			break;

		case Commands.MAP_COMMAND_LOADMAP:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}
			
			if (null == classloader.getResource(words[1])) {
				System.out.println("Exception: File does not exist: " + words[1]);
				break;
			}
			
			File inputMapFile = new File(classloader.getResource(words[1]).getFile().replace("%20", " "));
			mapReader = new MapReader();
			
			try {
				setMap(mapReader.readMapFile(inputMapFile));
				return true;
			} catch (InvalidMap e) {
				System.out.println("Exception: " + e.toString());
			}
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}
		return false;
	}

	/**
	 * Parses the String and calls the related player commands.
	 * 
	 * @param command User input Command/String
	 * @return true if command is processed correctly, false otherwise
	 */
	public boolean processGamePlayCreatePlayerCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			MapContoller.mapEditorShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_GAMEPLAYER:

			for (int idx = 1; idx < words.length; idx++) {
				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					
					if (words.length < idx + 2) {
						System.out.println("Invalid command, Try again !!!");
						return false;
					}
					
					String playerName = words[idx + 1];
					playerModel.createPlayer(playerName);
					idx = idx + 1;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					
					if (words.length < idx + 2) {
						System.out.println("Invalid command, Try again !!!");
						return false;
					}
					
					String playerName = words[idx + 1];
					playerModel.removePlayer(playerName);
					idx = idx + 1;

				} else {
					System.out.println("Invalid command, Try again !!!");
					break;
				}
			}
			break;

		case Commands.MAP_COMMAND_POPULATE_COUNTRIES:

			// Assign armies according the players count
			if (playerModel.assignArmiesToAllPlayers()) {

				playerModel.populateCountries(getMap());
				playerModel.intitializeArmiesForAllCountries(getMap());

				for (Player p : playerModel.getPlayersList()) {
					int countryCount = p.getAssignedCountry().size();
					System.out.println("Number of Countries for Player : " + p.getName() + " = " + countryCount);
				}
				
				setCurrentPlayer(playerModel.getPlayersList().get(0));				
				return true;
			}
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}

		return false;
	}

	/**
	 * Parses the String and calls the related game play startup commands.
	 * 
	 * @param sc scanner object
	 * @return true if command is processed correctly, false otherwise
	 */
	public boolean processGamePlayStartupCommands(Scanner sc) {

		System.out.println("Current game phase: Gameplay startup phase (placearmy, placeall, showmap)");
		System.out.println("Current Player: " + getCurrentPlayer().getName() + 
				", number of armies left = " + getCurrentPlayer().getArmies());

		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			playerModel.gamePlayShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_PLACE_ARMY:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}
			
			if (playerModel.placeArmy(getMap(), getCurrentPlayer(), words[1])) {
				changeCurrentPlayer();
			}

			if (playerModel.isAllPlayersArmiesExhausted()) {
				setCurrentPlayer(playerModel.getPlayersList().get(0));
				return true;
			}
			break;

		case Commands.MAP_COMMAND_PLACE_ALL:
			playerModel.placeAll();
			setCurrentPlayer(playerModel.getPlayersList().get(0));				
			return true;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}

		return false;
	}

	/**
	 * Parses the String and calls the related game play reinforcement commands.
	 * 
	 * @param sc scanner object
	 * @return true if command is processed correctly, false otherwise
	 */
	public boolean processGamePlayReinforcementCommands(Scanner sc) {

		if (!isReinfoceArmiesAssigned) {
			playerModel.assignReinforceArmiesToPlayers();
			isReinfoceArmiesAssigned = true;
		}

		System.out.println("Current game phase: Gameplay reinforcement phase (reinforce, showmap)");
		System.out.println("Current Player: " + getCurrentPlayer().getName()
				+ ", Armies left for reinforcement = " + getCurrentPlayer().getArmies());

		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			playerModel.gamePlayShowmap(getMap());
			break;
		
		case Commands.MAP_COMMAND_REINFORCE:
			
			if (words.length < 3) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}
			
			String countryName = words[1];
			int numberOfArmies = 0;
			
			try {
				numberOfArmies = Integer.parseInt(words[2]);
			} catch (Exception e) {
				System.out.println("Exception: " + e.toString());
				return false;
			}
			
			if (numberOfArmies <= 0) {
				System.out.println("You have entered invalid number of armies.");
				return false;
			}

			if (!playerModel.isCountryBelongToPlayer(getMap(), getCurrentPlayer(), countryName))
				return false;

			if (playerModel.reinforceArmiesForCurrentPlayer(getCurrentPlayer(), countryName, numberOfArmies))
				return true;
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}

		return false;
	}

	/**
	 * Parses the String and calls the related game play fortify commands.
	 * 
	 * @param sc scanner object
	 * @return true if command is processed correctly, false otherwise
	 */
	public boolean processGamePlayFortifyCommands(Scanner sc) {
		System.out.println("Current game phase: Gameplay fortify phase (fortify, showmap)");
		System.out.println("Current Player: " + getCurrentPlayer().getName());
		
		boolean isForifyDone = false;
		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			playerModel.gamePlayShowmap(getMap());
			break;
		
		case Commands.MAP_COMMAND_FORTIFY:
			
			if (words.length < 2) {
				System.out.println("Invalid command length. Try again !!!");
				return false;
			}
			
			if (words[1].equalsIgnoreCase(Commands.MAP_COMMAND_FORTIFY_OPTION_NONE)) {
				System.out.println("You have chosen to skip fortify.");
				isForifyDone = true;
			} else {
				
				if (words.length < 4) {
					System.out.println("Invalid command length. Try again !!!");
					return false;
				}

				int numArmies = 0;
				
				try {
					numArmies = Integer.parseInt(words[3]);
				} catch (Exception e) {
					System.out.println("Exception: " + e.toString());
					return false;
				}
				
				if (numArmies <= 0) {
					System.out.println("Exception: Invalid number of armies");
					return false;
				}
				
				if (playerModel.fortifyCurrentPlayer(getMap(), getCurrentPlayer(), words[1], words[2], numArmies)) 	
					isForifyDone = true;				
			}
			
			if (isForifyDone) { 
				// check all players have played
				if (playerModel.isLastPlayer(getCurrentPlayer())) {
					isReinfoceArmiesAssigned = false;
					System.out.println("***** All players have played. Going back to reinforcement again *****");
				}
				changeCurrentPlayer();	
			}
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}

		return isForifyDone;
	}
	
	/**
	 * This will change the current player
	 */
	public void changeCurrentPlayer() {
		int currentPlayerIdx = playerModel.getPlayersList().indexOf(getCurrentPlayer());
		int totalPlayers = playerModel.getPlayersList().size();
		setCurrentPlayer(playerModel.getPlayersList().get((currentPlayerIdx + 1) % totalPlayers));
	}
}
