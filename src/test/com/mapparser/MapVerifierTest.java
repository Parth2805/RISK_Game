package com.mapparser;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.exception.InvalidMap;
import com.mapparser.MapVerifier;
import com.mapparser.MapReader;

/**
 * This is the test class for MapVerifier. {@link MapVerifier}
 * 
 * @author Mahmoudreza
 * @author Maryam
 * @version 0.0.1
 */
public class MapVerifierTest {
	MapVerifier mapverifier;
	static Continent continent;
	static Country country;
	static Hmap map;
	ClassLoader loader;

	String mapAuthor = "Maryam";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";

	String continentName = "North-America";
	int controlValue = 10;

	static HashMap<String, String> mapData = new HashMap<>();
	List<Continent> continentList;

	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("This is for testing MapVerifier Class");
	}

	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void beforeTest() {
		continent = new Continent();
		country = new Country();
		map = new Hmap();
		map.setMapData(mapData);
		continent.setName(continentName);
		continent.setValue(controlValue);
		country.setName("Quebec");
		country.setxCoordinate(1);
		country.setyCoordinate(1);
		continentList = new ArrayList<>();
		continentList.add(continent);
	}

	/**
	 * This method runs After All Testing
	 */
	@AfterClass
	public static void afterClassTests() {
		System.out.println("The map verify test is done");
	}

	/**
	 * This method tests that map is null or not.
	 * @throws InvalidMap Invalid Map
	 */
	@Test(expected = InvalidMap.class)
	public void verifyNullMapTest() throws InvalidMap {
		mapverifier.verifyMap(null);
		System.out.println("This is a test for verifyNullMap");
	}

	/**
	 * This method verifies that map has at least one continent.
	 * @throws InvalidMap Invalid Map
	 */
	@Test(expected = InvalidMap.class)
	public void verifyMap() throws InvalidMap {
		mapverifier.verifyMap(new Hmap());
		System.out.println("The Unit Test for verifying Map is performed");
	}

	/**
	 * This method is used to verify that continent is null or not.
	 * @throws InvalidMap Invalid Map
	 */
	@Test(expected = InvalidMap.class)
	public void verifyContinentsTest() throws InvalidMap {
		map.setContinents(continentList);
		mapverifier.verifyContinents(map);
		System.out.println("The Unit Test for verifying Continents is performed");
	}

	/**
	 * This method is used to test if a continent is a sub-graph or not.
	 * @throws InvalidMap Invalid Map
	 */
	@Test
	public void isMapNotConnectedGraphTest() throws InvalidMap {
		assertFalse(mapverifier.isMapConnectedGraph(map));
		System.out.println("This Unit Test for Map_Connected_Graph is performed");
	}

	/**
	 * This method is used to test the continent is connected to graph or not.
	 */
	@Test
	public void isContinentConnectedGraphTest() {

		System.out.println("This is a test for Continent Connected Graph");
		List<Country> countryList = new ArrayList<>();
		countryList.add(country);
		Country country2 = new Country();
		country2.setName("Iran");
		country2.setxCoordinate(1);
		country2.setyCoordinate(2);
		countryList.add(country2);
		country2.getAdjacentCountries().add(country);
		country.getAdjacentCountries().add(country2);
		continent.setCountries(countryList);
		assertEquals(true, MapVerifier.isContinentConnectedGraph(continent, map));
		System.out.println("The Unit Test for Continent_Connected_Graph is performed");
	}
	
	/**
	 * This method is used to test the continent is connected to graph or not.
	 */
	@Test
	public void isContinentNotConnectedGraphTest() {

		System.out.println("This is a test for Continent not Connected Graph");
		List<Country> countryList = new ArrayList<>();
		countryList.add(country);
		Country country2 = new Country();
		country2.setName("Iran");
		country2.setxCoordinate(1);
		country2.setyCoordinate(2);
		countryList.add(country2);
		Country country3 = new Country();
		country3.setName("India");
		country3.setxCoordinate(1);
		country3.setyCoordinate(2);
		countryList.add(country3);
		country2.getAdjacentCountries().add(country);
		country.getAdjacentCountries().add(country2);
		continent.setCountries(countryList);
		assertNotEquals(true, MapVerifier.isContinentConnectedGraph(continent, map));
		System.out.println("The Unit Test for Continent_Not_Connected_Graph is performed");
	}
	
	/**
	 * This method is used to test the map is connected to graph or not.
	 */
	@Test
	public void isMapConnectedGraphTest() {

		System.out.println("This is a test for Continent not Connected Graph");
		List<Country> countryList = new ArrayList<>();
		countryList.add(country);
		Country country2 = new Country();
		country2.setName("Iran");
		country2.setxCoordinate(1);
		country2.setyCoordinate(2);
		countryList.add(country2);
		country2.getAdjacentCountries().add(country);
		country.getAdjacentCountries().add(country2);
		continent.setCountries(countryList);
		Country country3 = new Country();
		country3.setName("India");
		country3.setxCoordinate(4);
		country3.setyCoordinate(6);
		List<Country> countryList1 = new ArrayList<>();
		countryList1.add(country3);
		Continent continent2 = new Continent();
		continent2.setName("Asia");
		continent2.setValue(11);
		continentList.add(continent2);
		continent2.setCountries(countryList1);
		country3.getAdjacentCountries().add(country);
		country.getAdjacentCountries().add(country3);
		country3.getAdjacentCountries().add(country2);
		country2.getAdjacentCountries().add(country3);
		map.setContinents(continentList);
		assertEquals(MapVerifier.isMapConnectedGraph(map), true);
		System.out.println("The Unit Test for Map_Connected_Graph is performed");
	}
}
