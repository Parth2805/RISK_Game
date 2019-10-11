package com.commandparser;

import java.io.File;

import com.config.Commands;
import com.entity.Hmap;
import com.exception.InvalidMap;
import com.mapparser.MapReader;
import com.mapparser.MapVerifier;
import com.mapparser.MapWriter;

/**
 * This class reads, parses the command line string from user input.
 * 
 * @author Parth
 * @author Mehul
 */
public class CommandParser {

	Hmap rootMap;
	MapWriter mapWriter;

	// default constructor to initialize members
	public CommandParser() {
		this.mapWriter = new MapWriter();
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
	 * Parses the String and calls the relative function.
	 * 
	 * @param command User input Command/String to be parse
	 */
	public void processCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0], filePath = "";

		switch (commandType) {

		case Commands.MAP_COMMAND_EDIT_CONTINENT:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					String continentName = words[idx + 1];
					String continentValue = words[idx + 2];
					System.out.println("add:" + words[idx + 1] + " " + words[idx + 2]);
					// Call for adding continent with (continentName,continentvalue) as parameters
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					String continentName = words[idx + 1];
					System.out.println("remove:" + words[idx + 1]);
					// Call for removing the continent name with (continentName) as parameters
					idx = idx + 1;

				} else {
					System.out.println("Check input!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_EDIT_COUNTRY:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					String countryName = words[idx + 1];
					String continentName = words[idx + 2];
					System.out.println("Editcountry -add:" + continentName + " " + countryName);
					// Call for adding the country name with (continentName,countryName) as
					// parameters
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					String countryName = words[idx + 1];
					System.out.println("Editcountry -remove:" + countryName);
					// Call for removing the country name with (countryName) as parameter
					idx = idx + 1;

				} else {
					System.out.println("Wrong input!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_EDIT_NEIGHBOR:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
					String countryName = words[idx + 1];
					String nighborCountryName = words[idx + 2];
					System.out.println("add: " + words[idx + 1] + " " + words[idx + 2]);
					// Call for adding continent with (continentName,continentvalue) as parameters
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
					String countryName = words[idx + 1];
					String nighborCountryName = words[idx + 2];
					System.out.println("remove: " + words[idx + 1] + " " + words[idx + 2]);
					// Call for removing the continent name with (continentName) as parameters
					idx = idx + 2;

				} else {
					System.out.println("Check input!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_SHOWMAP:

			System.out.println("Showmap");

			// Call for showmap functionW
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

			System.out.println("Validatemap");
			try {
				MapVerifier.verifyMap(getMap());
			} catch (InvalidMap e1) {
				e1.printStackTrace();
			}
			break;

		case Commands.MAP_COMMAND_LOADMAP:

			filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];
			File inputMapFile = new File(filePath);
			MapReader mapReader = new MapReader();

			if (inputMapFile.exists()) {
				try {
					setMap(mapReader.readMapFile(inputMapFile));
				} catch (InvalidMap e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("File does not exist: " + words[1]);
			}
			break;

		default:
			System.out.println("Check the input!!");
			break;
		}
	}
}
