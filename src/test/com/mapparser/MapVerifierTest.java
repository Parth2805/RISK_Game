package com.mapparser;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
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
	
	String continentName = "North America";
	int controlValue = 10;
	
	static HashMap<String, String> mapData = new HashMap<>();
	List<Continent> continentList;

	/**
	 * This method executed before all the methods of the class.
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("This is for testing MapVerifier Class");
		continent = new Continent();
		country = new Country();
		map = new Hmap();
	}
	
	/**
	 * This method is executed before every method of the class.
	 */
	@Before
	public void beforeTest() {
		mapData.put("author", mapAuthor);
		mapData.put("image", mapImage);
		mapData.put("wrap", mapWrap);
		mapData.put("scroll", mapScroll);
		mapData.put("warn", mapWarn);		
		map.setMapData(mapData);
		loader = getClass().getClassLoader();
		continent.setName(continentName);
		continent.setValue(controlValue);
		
		country.setName("Canada");
		country.setxCoordinate(1);
		country.setyCoordinate(2);
		
		continentList = new ArrayList<>();
		continentList.add(continent);
	}
	
	/**
   	* This method runs After All Testing 
	*/
    @AfterClass
    public static void afterPerformingTests() {
        System.out.println("The test is done");
    }

	
	/**
	 * This method tests that map is null or not.
	 * @throws InvalidMap InvalidMapException
	 */
	@Test (expected = InvalidMap.class)
	public void verifyNullMapTest() throws InvalidMap {
		System.out.println("This is a test for verifyNullMap");
		mapverifier.verifyMap(null);
	}
	
	
	/**
	 * This method verifies that map has at least one continent.
	 * @throws InvalidMap InvalidMapException
	 */
	@Test (expected = InvalidMap.class)
	public void verifyMap() throws InvalidMap {
		System.out.println("This is a test for verifyMap");
		mapverifier.verifyMap(new Hmap());
	}
	
	
	/**
	 * This method is used to verify that continent is null or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test (expected = InvalidMap.class)
	public void verifyContinentsTest() throws InvalidMap {
		System.out.println("This is a test for verifyContinents");
		map.setContinents(continentList);
		mapverifier.verifyContinents(map);	
	}
	
	
	/**
	 * This method is used to test if a continent is a sub-graph or not.
	 * @throws InvalidMap invalid map exception.
	 */
	@Test
	public void isMapConnectedGraphTest() throws InvalidMap {	
		System.out.println("This is a test for Map Connected Graph");
		assertFalse(mapverifier.isMapConnectedGraph(map));
	}
	@Test
	public void isContinentConnectedGraphTest() {
		System.out.println("This is a test for Continent Connected Graph");
		List<Country> counList = new ArrayList<>();
		counList.add(country);
		Country country2 =  new Country();
		country2.setName("Iran");
		country2.setxCoordinate(1);
		country2.setyCoordinate(2);
		counList.add(country);
		continent.setCountries(counList);
		assertEquals(MapVerifier.isContinentConnectedGraph(continent, map), true);
		
		
	}

}
