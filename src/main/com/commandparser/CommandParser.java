package com.commandparser;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.config.Commands;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.exception.InvalidMap;
import com.mapparser.MapCommands;
import com.mapparser.MapReader;
import com.mapparser.MapVerifier;
import com.mapparser.MapWriter;
import com.models.PlayerModel;

/**
 * This class reads, parses the command lines string from user input.
 * @author Parth
 * @author Mehul
 */
public class CommandParser {

	Hmap rootMap;
	MapWriter mapWriter;
	PlayerModel playerModel;

	/**
	 * default constructor to initialize members
	 */
	public CommandParser() {
		this.mapWriter = new MapWriter();
		this.playerModel = new PlayerModel();
		this.rootMap = new Hmap();
	}
	
	/**
	 * Setter method for the map object.
	 * @param map object
	 * @return map
	 */
	private Hmap setMap(Hmap map) {
		return this.rootMap = map;
	}

	/**
	 * Get map object
	 * @return map
	 */
	private Hmap getMap() {
		return rootMap;
	}
	
	/**
	 * Parses the String and calls the related game play reinforcement commands.
	 * @param command User input Command/String to be parse.
	 * @return false
	 */
	public boolean processGamePlayReinforcementCommands(String command) {

		String words[] = command.split(" ");

		if (words[0].equalsIgnoreCase(Commands.MAP_COMMAND_REINFORCE)) {

			String countryName = words[1];
			int numberOfArmies=Integer.parseInt(words[2]);
			for (Player p:playerModel.getPlayersList()) {
				Player currentPlayer = p;

				while(currentPlayer.getArmies()!=0){

					//showmap
					if (currentPlayer.getArmies() < numberOfArmies) {
						//formula
						System.out.println("You dont have enough army");

					} else {

						currentPlayer.setArmies(currentPlayer.getArmies()-numberOfArmies);
						List<Country> countryList = currentPlayer.getAssignedCountry();
						for (Country c:countryList) {

							if (c.getName().equalsIgnoreCase(countryName)) {

							//	currentPlayer.getAssignedCountry().

							}
						}
					}
				}
			}

		}

		return false;
	}
	
	/**
	 * Parses the String and calls the related game play startup commands.
	 * @param sc User input Command/String to be parse.
	 * @return false
	 */
	public boolean processGamePlayStartupCommands(Scanner sc) {

		System.out.println("Current state: Gameplay startup phase (placearmy, placeall");
		System.out.println("Current Player: " + playerModel.getCurrentPlayer().getName());
		
		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_PLACE_ARMY:
			String countryName = words[1];
			
			if (playerModel.placeArmies(getMap(), countryName)) {
				return true;
			}

			// TODO skip player if there are no more armies for his
			int currentPlayerIdx = playerModel.getPlayersList().indexOf(playerModel.getCurrentPlayer());
			int totalPlayers = playerModel.getPlayersList().size();
			
			playerModel.setCurrentPlayer(playerModel.getPlayersList().get((currentPlayerIdx + 1) % totalPlayers));
			break;	
			
		case Commands.MAP_COMMAND_PLACE_ALL:
			if (playerModel.placeAll()) {
				return true;
			}	
			break;	
			
		default:
			break;
		}
		
		return false;
	}	
	
	/**
	 * Parses the String and calls the related game play commands.
	 * @param command User input Command/String to be parse
	 * @return false
	 */
	public boolean processGamePlayCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			MapCommands.mapEditorShowmap(getMap());
			break;
		
		case Commands.MAP_COMMAND_GAMEPLAYER:

			for (int idx = 1; idx < words.length; idx++) {
				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					String playerName = words[idx + 1];
					playerModel.createPlayer(playerName);
					idx = idx + 1;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
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
			playerModel.setCountryList(playerModel.getCountryListFromMap(getMap()));
		
			if (playerModel.assignArmiesToPlayers()) {
				playerModel.populateCountries(getMap());
				playerModel.setCurrentPlayer(playerModel.getPlayersList().get(0));
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
	 * Parses the String and calls the related map edit commands.
	 * @param command User input Command/String to be parse.
	 * @return false
	 */
	public boolean processMapEditCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0], filePath = "";

		switch (commandType) {

		case Commands.MAP_COMMAND_EDIT_CONTINENT:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					String continentName = words[idx + 1];
					String continentValue = words[idx + 2];
					MapCommands.addContinent(getMap(), continentName, continentValue, "");
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					String continentName = words[idx + 1];
					MapCommands.removeContinent(getMap(), continentName);
					idx = idx + 1;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_EDIT_COUNTRY:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					String countryName = words[idx + 1];
					String continentName = words[idx + 2];
					MapCommands.addCountry(getMap(), countryName, continentName);
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					String countryName = words[idx + 1];
					MapCommands.removeCountry(getMap(), countryName);
					idx = idx + 1;

				} else {
					System.out.println("Wrong input!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_EDIT_NEIGHBOR:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					MapCommands.addNeighborCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					String countryName = words[idx + 1];
					String nbrCountryName = words[idx + 2];
					MapCommands.removeNeighborCountry(getMap(), countryName, nbrCountryName);
					idx = idx + 2;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_SHOWMAP:
			MapCommands.mapEditorShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_SAVEMAP:

			filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];
			System.out.println("Save File: " + filePath);
			File outputMapFile = new File(filePath);
			mapWriter.writeMapFile(getMap(), outputMapFile);
			break;

		case Commands.MAP_COMMAND_EDITMAP:

			filePath = words[1];
			System.out.println("Edit Map:" + filePath);
			// Call for editmap(filename_edit)
			break;

		case Commands.MAP_COMMAND_VALIDATEMAP:

			try {
				MapVerifier.verifyMap(getMap());
			} catch (InvalidMap e1) {
				System.out.println("Exception: " + e1.toString());
			}
			break;

		case Commands.MAP_COMMAND_LOADMAP:

			filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];
			File inputMapFile = new File(filePath);
			MapReader mapReader = new MapReader();

			if (inputMapFile.exists()) {
				try {
					setMap(mapReader.readMapFile(inputMapFile));
					return true;
				} catch (InvalidMap e) {
					System.out.println("Exception: " + e.toString());
				}
			} else {
				System.out.println("Exception: File does not exist: " + words[1]);
			}
			break;
			
		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}
		return false;
	}

}
