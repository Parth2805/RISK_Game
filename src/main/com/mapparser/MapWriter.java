/**
 * 
 */
package com.mapparser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.sql.ConnectionEventListener;

import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;

/**
 * @author Komal
 * This class is responsible to write the map file when user creates the map.
 * 
 */
public class MapWriter {

	/**
	 * This method writes the map details to the map file.
	 * 
	 * @param map
	 *            object of the map which is being processed
	 * @param file
	 *            file path
	 */
	public void writeMapFile(Hmap map, File file) {

		FileWriter fileWriter;
		try {
			if (map == null) {
				System.out.println("Map Object is NULL!");
			}

			String content = parseMapAndReturnString(map);
			fileWriter = new FileWriter(file, false);
			fileWriter.write(content);
			fileWriter.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * This method processes the map by calling three different methods and makes a
	 * string to be written in the map file.
	 * 
	 * @param map
	 *            object of the map which is being processed
	 * @return String to be written in the map file
	 */
	private String parseMapAndReturnString(Hmap map) {
		StringBuilder content = new StringBuilder();
		content = processMapAttribute(map);
		content.append(processContinent(map));
		content.append(processCountries(map));
		content.append(processBorders(map));
		return content.toString();
	}

	/**
	 * This method process the map attributes.
	 * 
	 * @param map
	 *            object of the map which is being processed
	 * @return a String that contains the map properties.
	 */
	private StringBuilder processMapAttribute(Hmap map) {
		StringBuilder mapAttribute = new StringBuilder();
		mapAttribute.append("[files]");
		mapAttribute.append("\n");

		for (Entry<String, String> keymap : map.getMapData().entrySet()) {
			mapAttribute.append(keymap.getKey() + " " + keymap.getValue());
			mapAttribute.append("\n");
		}

		return mapAttribute;
	}

	/**
	 * This method processes the continents.
	 * 
	 * @param map
	 *            object of the map which is being processed
	 * @return a string that contains details of the continents that will eventually
	 *         be written in the map file.
	 */
	private StringBuilder processContinent(Hmap map) {
		StringBuilder continentData = new StringBuilder();
		continentData.append("\n");
		continentData.append("[continents]");
		continentData.append("\n");

		for (Continent continent : map.getContinents()) {
			continentData.append(continent.getName() + " " + continent.getValue() + " " + continent.getColor());
			continentData.append("\n");
		}
		return continentData;
	}

	/**
	 * This method processes the countries.
	 * 
	 * @param map
	 *            object of the map that is being processed
	 * @return a string that contains details of the countries that will eventually
	 *         be written in the map file.
	 */
	private StringBuilder processCountries(Hmap map) {
		StringBuilder countryData = new StringBuilder();
		countryData.append("\n");
		countryData.append("[countries]");
		countryData.append("\n");
		List<Continent> continentList = new ArrayList<Continent>();

		for (Continent continent : map.getContinents()) {
			List<Country> countriesList = continent.getCountries();
			if (countriesList != null) {
				for (Country country : countriesList) {
					countryData.append(countriesList.size() + 1 + country.getName() + " "
							+ continentList.indexOf(country.getBelongToContinent()) + " " + country.getxCoordinate()
							+ " " + country.getyCoordinate());

				}
				countryData.append("\n");
			}
		}
		return countryData;
	}

	/**
	 * This method processes the countries.
	 * 
	 * @param map
	 *            object of the map that is being processed
	 * @return a string that contains details of the countries that will eventually
	 *         be written in the map file.
	 */
	private StringBuilder processBorders(Hmap map) {
		StringBuilder borderData = new StringBuilder();
		borderData.append("\n");
		borderData.append("[borders]");
		borderData.append("\n");
		Continent continent = new Continent();
		
		List<Country> countriesList = continent.getCountries();
		if (countriesList != null) {
			for(Country country : countriesList) {
				borderData.append(country.getName() );
				for (Country adjacentCountries : country.getAdjacentCountries()) {
					borderData.append(" ");
					borderData.append(countriesList.indexOf(adjacentCountries.getName()));
				}
			}
			borderData.append("\n");
		}			
		return borderData;
	}

}
