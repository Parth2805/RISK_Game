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
	 * @param file - The map file to be read.
	 * @return map - The map object returned after reading the file.
	 * @throws InvalidMapException - Throws IOException if the map is invalid.
	 */
	public Hmap readMapFile(final File file) throws InvalidMap {

		this.map = processMapFile(file);
		// MapVerifier.verifyMap(map);

		return map;
	}

	/**
	 * This method is used to read and process map data
	 * 
	 * @param file - file path
	 * @return map - returns the map object after processing the file data
	 * @throws InvalidMap - Throws InvalidMapException if map is not valid
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
				if (line.startsWith(";"))
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

			System.out.println(mapString);
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
		
		HashMap<String, String> filesAttributeMap = new HashMap<String, String>();
		StringTokenizer tokensForMapAttribute = new StringTokenizer(scan.nextLine(), "#");
		
		while (tokensForMapAttribute.hasMoreTokens()) {
			
			String str = tokensForMapAttribute.nextToken();
			System.out.println(str);
			
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
	 * @param scan - scanner object which points to line of the file which is to be processed
	 * @return continentList after processing
	 * @throws InvalidMapException - throws InvalidMapException if map is not valid
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
				continent.setName(data[0].trim().toUpperCase());
				System.out.println(data[0]);
				continent.setValue(Integer.parseInt(data[1]));
				continent.setColor(data[2].trim());
				continentList.add(continent);
			}
		}
		
		/*
		List<Country> countryList = new ArrayList<Country>();
		while (scan.hasNext()) {
			String countryData = scan.nextLine();
			// call processCountry for each line of country
			countryList.addAll(parseCountries(countryData, continentList));
		}
		
		//here you can create continent map 
		//pass it to Country method and set there only
		
		HashMap<String, Country> countryMap = new HashMap<String, Country>();
		for (Country t : countryList) {
			countryMap.put(t.getName(), t);
		}
		
		//Map neighbour Country object to Country
		for(Country country : countryList) {
			for(String adjacentCountry : country.getAdjCountries()) {
				if(countryMap.containsKey(adjacentCountry)){
					if (country.getAdjacentCountries() == null) {
						country.setAdjacentCountries(new ArrayList<Country>());
					}
					country.getAdjacentCountries().add(countryMap.get(adjacentCountry));
				}else {
					//if particular country has adjacent country defined, but actually it doesn't exist
					throw new InvalidMap("Country: " + adjacentCountry + " not assigned to any continent.");
				}
			}
			
		}
		
		//Map countries and continent
		for(Continent continent : continentList) {
			HashMap<String, Country> continentTMap = new HashMap<String, Country>();
			for(Country country : countryList) {
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
	*/
		return continentList;
	}
}
