package com.mapparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.entity.Hmap;
import com.exception.InvalidMap;
import com.entity.Country;
import com.entity.Continent;

/**
 * This class reads, parses the map file and sets data in corresponding objects.
 * 
 * @author Mehul
 * @author Komal
 */
public class MapReader {

	// Map class to return, once map is processed successfully.
	private Hmap map;

	// Hash-map to make sure that country belongs to only one continent
	private HashMap<String, Integer> countryBelongContinentCount = new HashMap<String, Integer>();

	// default constructor to initialize the map
	public MapReader() {
		this.map = new Hmap();
	}

	/**
	 * Get map object after processing the map file
	 * 
	 * @return the map
	 */
	private Hmap getMap() {
		return map;
	}

	/**
	 * This method reads the map file and verifies if the map is valid.
	 * 
	 * @param file The map file to be read.
	 * @return map The map object returned after reading the file.
	 * @throws InvalidMapException Throws IOException if the map is invalid.
	 */
	public Hmap readMapFile(final File file) throws InvalidMap {

		this.map = processMapFile(file);
		MapVerifier.verifyMap(map);

		return map;
	}

	/**
	 * This method is used to read and process map data
	 * 
	 * @param file file path
	 * @return map returns the map object after processing the file data
	 * @throws InvalidMap Throws InvalidMapException if map is not valid
	 */
	private Hmap processMapFile(File file) throws InvalidMap {

		Scanner mapFileReader;
		
		try {
			mapFileReader = new Scanner(new FileInputStream(file));
			StringBuilder mapString = new StringBuilder();
		
			int count = 0;
		
			// process and read map file in three steps
			while (mapFileReader.hasNext()) {
			
				String line = mapFileReader.nextLine();
				
				// ignore comment lines
				if (line.startsWith(";") || line.startsWith("name"))
					continue;
				
				if (!line.isEmpty()) {
					mapString.append(line + "#");
					count = 0;
				} else if (line.isEmpty()) {

					count++;					
					if (count == 1)
						mapString.append("\n");
					else
						count = 0;
				}
			}

			//System.out.println(mapString);
			mapFileReader = new Scanner(mapString.toString());
			map = processFilesAttribute(mapFileReader);

		} catch (IOException e) {
			System.out.println("Map File is not selected");
			System.out.println(e.getMessage());
		}
		
		return map;
	}
	
	/**
	 * This method process map attributes and call method for processing continents.
	 * @param scan of type {@link Scanner}
	 * @return Map of type {@link Map}
	 * @throws InvalidMapException throws InvalidMapException if map is not valid.
	 */
	private Hmap processFilesAttribute(Scanner scan) throws InvalidMap {

		// ignore blank lines
		String line = scan.nextLine();
		while (line.isEmpty()) {
			line = scan.nextLine();
		}

		HashMap<String, String> filesAttributeMap = new HashMap<String, String>();
		StringTokenizer tokensForMapAttribute = new StringTokenizer(line, "#");
		
		while (tokensForMapAttribute.hasMoreTokens()) {
			
			String str = tokensForMapAttribute.nextToken();
			
			//System.out.println(str);
			
			if (str.equalsIgnoreCase("[files]")) {
				continue;
			} else {
				String[] data = str.split(" ");
				filesAttributeMap.put(data[0], data[1]);
			}
		}
		
		map.setMapData(filesAttributeMap);
		
		List<Continent> continentList = parseContinents(scan);
		
		HashMap<String, Continent> continentMap = new HashMap<String, Continent>();
		for (Continent continent : continentList) {
			continentMap.put(continent.getName(), continent);
		}
		map.setContinentMap(continentMap);
		map.setContinents(continentList);
		
		return map;
	}

	/**
	 * This method processes the continents and call method to process Countries
	 * and also  map Countries and continents.
	 * @param scan scanner object which points to line of the file which is to be processed
	 * @return continentList after processing
	 * @throws InvalidMapException throws InvalidMapException if map is not valid
	 */
	private List<Continent> parseContinents(Scanner scan) throws InvalidMap {
		
		List<Continent> continentList = new ArrayList<Continent>();
		StringTokenizer tokenForContinents = new StringTokenizer(scan.nextLine(), "#");
		while (tokenForContinents.hasMoreTokens()) {
			String line = tokenForContinents.nextToken();
			if (line.equalsIgnoreCase("[continents]")) {
				continue;
			} else {
				Continent continent = new Continent();
				String[] data = line.split(" ");

				//System.out.println(line);
				continent.setName(data[0].trim().toUpperCase());
				continent.setValue(Integer.parseInt(data[1]));
				continent.setColor(data[2].trim());
				continentList.add(continent);
			}
		}
		
		List<Country> countryList = new ArrayList<Country>();
		
		if (scan.hasNext()) {
			String countryData = scan.nextLine();
 			// call processCountry for each line of country
			countryList.addAll(parseCountries(scan, countryData, continentList));
		}
		
		// here we create continent map 
		// pass it to Country method and set there only
		HashMap<String, Country> countryMap = new HashMap<String, Country>();
		for (Country t : countryList) {
			countryMap.put(t.getName(), t);
		}
		
		// Add neighbor countries in List
		for (Country country : countryList) {
			for (String adjacentCountry : country.getNeighborCountries()) {
				if (countryMap.containsKey(adjacentCountry)){
					if (country.getAdjacentCountries() == null) {
						country.setAdjacentCountries(new ArrayList<Country>());
					}
					country.getAdjacentCountries().add(countryMap.get(adjacentCountry));
				} else {
					// if particular country has adjacent country defined, but actually it doesn't exist
					throw new InvalidMap("Country: " + adjacentCountry + " not assigned to any continent.");
				}
			}
		}
		
		// Map countries and continent
		for (Continent continent : continentList) {
			HashMap<String, Country> continentTMap = new HashMap<String, Country>();
			
			for (Country country : countryList) {
				
				if (country.getBelongToContinent().equals(continent)) {
					
					if (continent.getCountries() == null) {
						continent.setCountries(new ArrayList<Country>());
						continentTMap.put(country.getName(), country);
					}
					continent.getCountries().add(country);
					continentTMap.put(country.getName(), country);
				}
			}
			continent.setCountryMap(continentTMap);
		}
	
		return continentList;
	}
	
	/**
	 * This method processes countries and check that it should be assign to only one continent
	 * @param scan scanner object which points to line of the file which is to be processed
	 * @param countryLine Line from the map file for the Country
	 * @param continentList Produces the continent list.
	 * @return countryList After processing
	 * @throws InvalidMapException Throws InvalidMapException if map is not valid
	 */
	private List<Country> parseCountries(Scanner scan, String countryLine, List<Continent> continentList) throws InvalidMap{
		
		List<Country> countryList = new ArrayList<Country>();
		List<Country> countryListWithBorders = new ArrayList<Country>();
		StringTokenizer tokenForCountry = new StringTokenizer(countryLine, "#");
		HashMap<Integer, String> countryNamesMap = new HashMap<Integer, String>();
		String bordercountryData = "";
		
		// Get borders line
		if (scan.hasNext())
			bordercountryData = scan.nextLine();
			
		while (tokenForCountry.hasMoreTokens()) {
			
			String element = tokenForCountry.nextToken();
			
			if (element.equalsIgnoreCase("[countries]")) {
				continue;
			} else {
				
				//System.out.println(element);
				Country country = new Country();
				String[] dataOfCountry = element.split(" ");
				
				// Map Index with name of country
				dataOfCountry[1] = dataOfCountry[1].trim().toUpperCase();
				countryNamesMap.put(Integer.parseInt(dataOfCountry[0]), dataOfCountry[1]);
				
				country.setName(dataOfCountry[1]);
				country.setxCoordinate(Integer.parseInt(dataOfCountry[3]));
				country.setyCoordinate(Integer.parseInt(dataOfCountry[4]));
				
				int indexContinent = Integer.parseInt(dataOfCountry[2]) - 1;
				String continentOfParsedCountry = continentList.get(indexContinent).getName();

				for (Continent continent : continentList) {
					
					if (continent.getName().equalsIgnoreCase(continentOfParsedCountry)) {
						
						country.setBelongToContinent(continent);
						
						if (countryBelongContinentCount.get(dataOfCountry[1]) == null) {
							countryBelongContinentCount.put(dataOfCountry[1], 1);
						} else {
							throw new InvalidMap("A Country "+ country.getName() +" can be assigned to only one Continent.");
						}
					}
				}
				
				if (countryBelongContinentCount.get(dataOfCountry[1]) == null)
					throw new InvalidMap("A Country must be assigned to one Continent.");
				
				countryList.add(country);
			}
		}
		
		for (Country country : countryList) {
			// Add bordering countries (neighbors)
			StringTokenizer tokenForBorder = new StringTokenizer(bordercountryData, "#");
				
			while (tokenForBorder.hasMoreTokens()) {
					
				String elementB = tokenForBorder.nextToken();
				if (elementB.equalsIgnoreCase("[borders]")) {
					continue;
				} else {
					
					String[] borderList = elementB.split(" ");
					String countyName = countryNamesMap.get(Integer.parseInt(borderList[0]));
					
					if (countyName.equalsIgnoreCase(country.getName())) {
						
						List<String> adjacentCountries = new ArrayList<String>();

						for (int idx = 1; idx < borderList.length; idx++) {
							String neighborCountry = countryNamesMap.get(Integer.parseInt(borderList[idx]));
							if (!neighborCountry.equalsIgnoreCase(countyName)) {
								adjacentCountries.add(neighborCountry);
							}
						}						
						country.setNeighborCountries(adjacentCountries);
						countryListWithBorders.add(country);
					}
				}
			}
		}
		
		return countryListWithBorders;
	}
}
