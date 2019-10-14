package com.commandparser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.config.Commands;
import com.config.Config;
import com.config.GameState;

import java.util.List;

import com.entity.Continent;
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
 * This class reads, parses the command line string from user input.
 *
 * @author Parth
 * @author Mehul
 */
public class CommandParser {

	Hmap rootMap;
	MapWriter mapWriter;
	PlayerModel playerModel;

	// default constructor to initialize members
	public CommandParser() {
		this.mapWriter = new MapWriter();
		this.playerModel = new PlayerModel();
	}

	/**
	 * Setter method for the map object.
	 *
	 * @param map
	 *            object
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
	 * @param command
	 *            User input Command/String to be parse
	 */
	public boolean processGamePlayCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

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

			playerModel.assignArmiesToPlayers();
			playerModel.placeArmies();
			return true;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;			
		}
		
		return false;
	}
	
	/**
	 * Parses the String and calls the related map edit commands.
	 * 
	 * @param command
	 *            User input Command/String to be parse
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

			for (Continent c : getMap().getContinents()) {
				System.out.println("--------------------------------");
				System.out.println("Continent: " + c.getName() + " having following countries");

				for (Country con : c.getCountries()) {
					System.out.print(con.getName() + ": ");
					List<String> adjCountries = con.getNeighborCountries();

					for (int i = 0; i < adjCountries.size(); i++) {
						System.out.print(adjCountries.get(i));

						if (i != adjCountries.size() - 1)
							System.out.print(", ");
					}
					System.out.println();
				}
			}
			System.out.println("--------------------------------");
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
