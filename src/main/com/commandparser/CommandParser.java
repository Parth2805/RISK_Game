package com.commandparser;

import java.io.File;
import java.io.IOException;
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
import com.playerparser.PlayerCommands;

/**
 * This class reads, parses the command line string from user input.
 *
 * @author Parth
 * @author Mehul
 */
public class CommandParser {

	Hmap rootMap;
	MapWriter mapWriter;
	PlayerCommands playerModel;
	String editFilePath = "";

	// default constructor to initialize members
	public CommandParser() {
		this.mapWriter = new MapWriter();
		this.playerModel = new PlayerCommands();
		this.rootMap = new Hmap();
	}
	
	/**
	 * Setter method for the map object.
	 *
	 * @param map object
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
	 * @param command User input Command/String to be parse
	 */
	public boolean processMapEditCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0], filePath = "";
		MapReader mapReader;

		switch (commandType) {

		case Commands.MAP_COMMAND_EDIT_CONTINENT:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					MapCommands.addContinent(getMap(), words[idx + 1], words[idx + 2], "");
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					MapCommands.removeContinent(getMap(), words[idx + 1]);
					idx = idx + 1;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_EDIT_COUNTRY:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					MapCommands.addCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					MapCommands.removeCountry(getMap(), words[idx + 1]);
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
					MapCommands.removeNeighborCountry(getMap(), words[idx + 1], words[idx + 2]);
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

			if (!editFilePath.isEmpty()) {
				if (!editFilePath.equals(filePath)) {
					System.out.println("Please give same filename as you have given in editmap.");
					break;
				}
			}
			
			System.out.println("Saving File at: " + filePath);
			File outputMapFile = new File(filePath);
			mapWriter.writeMapFile(getMap(), outputMapFile);
			break;

		case Commands.MAP_COMMAND_EDITMAP:

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
				System.out.println("Given map file does not exist. New Map file has been created.");
				try {
					editMapFile.createNewFile();
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

			mapReader = new MapReader();
			filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];
			File inputMapFile = new File(filePath);

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
	
	/**
	 * Parses the String and calls the related game play commands.
	 * 
	 * @param command User input Command/String to be parse
	 */
	public boolean processGamePlayCreatePlayerCommands(String command) {

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
				playerModel.intitializeArmiesForAllCountries();
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
	 * Parses the String and calls the related game play startup commands.
	 * 
	 * @param command User input Command/String to be parse
	 */
	public boolean processGamePlayStartupCommands(Scanner sc) {

		System.out.println("Current state: Gameplay startup phase (placearmy, placeall, showmap)");
		System.out.println("Current Player: " + playerModel.getCurrentPlayer().getName());
		
		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			playerModel.gamePlayShowmap();
			break;
		
		case Commands.MAP_COMMAND_PLACE_ARMY:
			
			if (playerModel.placeArmy(getMap(), words[1])) {
				// TODO skip player if there are no more armies for his
				int currentPlayerIdx = playerModel.getPlayersList().indexOf(playerModel.getCurrentPlayer());
				int totalPlayers = playerModel.getPlayersList().size();
				playerModel.setCurrentPlayer(playerModel.getPlayersList().get((currentPlayerIdx + 1) % totalPlayers));
			}
			
			if (playerModel.isAllPlayersArmiesExhausted(playerModel.getPlayersList()))
				return true;
			break;	
			
		case Commands.MAP_COMMAND_PLACE_ALL:
			playerModel.placeAll();
			return false;
			
		default:
			break;
		}
		
		return false;
	}	
	
	/**
	 * Parses the String and calls the related game play reinforcement commands.
	 * 
	 * @param command User input Command/String to be parse
	 */
	public boolean processGamePlayReinforcementCommands(String command) {

		String words[]=command.split(" ");

		if(words[0].equalsIgnoreCase(Commands.MAP_COMMAND_REINFORCE)){

			String countryName=words[1];
			int numberOfArmies=Integer.parseInt(words[2]);
			for(Player p:playerModel.getPlayersList()){
				Player currentPlayer=p;
				while(currentPlayer.getArmies()!=0){
					//showmap
					if(currentPlayer.getArmies()<numberOfArmies){
						//formula
						System.out.println("You dont have enough army");
					} else {
						currentPlayer.setArmies(currentPlayer.getArmies()-numberOfArmies);
						List<Country> countryList=currentPlayer.getAssignedCountry();
						for(Country c:countryList){
							if(c.getName().equalsIgnoreCase(countryName)){
							//	currentPlayer.getAssignedCountry().
							}
						}
					}
				}
			}
		}
		
		return false;
	}
}
