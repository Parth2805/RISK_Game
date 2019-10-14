package com.mapparser;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.Ignore;

import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.exception.InvalidMap;
import com.mapparser.MapCommands;

/**
 * This is a Test Class for testing MapCommand
 * @author Maryam
 * @version 0.0.1
 *
 */
public class MapCommandsTest {
  MapCommands mapcom;
  static Continent continent;
  static Hmap map;
  String nameContinet= "Asia";
  String nameCountry="Canada";
  String color="red";
  int ctrlValue1=1;
  int ctrlValue2=2;
  static Country adjCoun; 
  static Country country;
  static Country adjCountry;
  int xCo1=1;
  int xCo2=1;
  int yCo1=2;
  int yCo2=2;
  
  static HashMap<String,String> mapData;
  
  String mapAuthor = "Robert";
  String mapImage = "world.map";
  String mapWrap = "no";
  String mapScroll = "horizontal";
  String mapWarn = "yes";
  
  /**
   	* This method runs before All Testing an run it just one time
	*/
  
  @BeforeClass
    public static void beforeAllTesting() {
    System.out.println("This is for testing MapCommand Class");
    map = new Hmap();
    mapData = new HashMap<>();
    continent = new Continent();
    country = new Country();
    adjCountry =  new Country();
    }
  
  /**
 	* This method runs before All test case and run before each test cases  
	*/
    @Before
    public void beforeTest() {
    mapData = new HashMap<>();
    mapData.put("author", mapAuthor);
    mapData.put("image", mapImage);
    mapData.put("wrap", mapWrap);
    mapData.put("scroll", mapScroll);
    mapData.put("warn", mapWarn);    
    map.setMapData(mapData);
    }
    
    /**
   	* This method runs After All Testing 
	*/
    @AfterClass
    public static void afterPerformingTests() {
        System.out.println("The test is done");
    }

    /**
   	* This method Test the remove Continent  
	*/
    @Test
    public void removeContinentTest() {
      
       System.out.println("This is a test for Remove Continet");
       assertEquals(true, mapcom.removeContinent(map,nameContinet));
    }
  
    /**
   	* This method Test the add Continent 
	*/
   @Test 
    public void addContinentTest() throws InvalidMap {
     
     System.out.println("This is a test for Add Continet it was pass");
    assertEquals(true,mapcom.addContinent(map, nameContinet, String.valueOf(ctrlValue1), color));
    }
   
   /**
  	* This method Test the add Continent   
	*/
   @Test 
   public void addContinentTest2() throws InvalidMap {
    
	    boolean output  =mapcom.addContinent(map, nameContinet, String.valueOf(ctrlValue1),color);
		assertNotNull(output);
   }
    
   /**
  	* This method Test the update Continent  
	*/
   @Ignore 
    public  void updateContinentTest() throws InvalidMap{
      System.out.println("This is a test for Update Continet"); 
      continent  = mapcom.updateContinent(continent, map, nameContinet, String.valueOf(ctrlValue2));
    		 // mapcom.updateContinent(continent, map, nameContinet, String.valueOf(ctrlValue2));
   //   assertEquals(continent.getValue(), ctrlValue1);
   //  assertEquals(continent.getValue(), ctrlValue2);
     assertEquals(continent.getName(), nameContinet);
    }
    
   /**
  	* This method Test the add country  
	*/
    @Test 
    public void addCountryTest() throws InvalidMap{
      System.out.println("This is a test for AddCountry Continet");
      boolean output=MapCommands.addCountry(map, nameCountry, nameContinet);
		assertNotNull(output);
    }
  
    /**
   	* This method Test the update country  
	*/
    @Ignore
    public void updateCountryTest() throws InvalidMap {
      System.out.println("This is a test for UpdateCountry Continet");
      country= mapcom.updateCountry(country, map, nameCountry, String.valueOf(xCo1), String.valueOf(yCo1),adjCountry ) ;
      //assertNull(mapcom.updateCountry(country, map, nameCountry, xCo1, yCo1, adjCoun));
     // assertEquals(country.getxCoordinate(), xCo1);
      assertNotNull(country);
      
    }
    
    /**
   	* This method Test map country to continent   
	*/
   @Test 
    public void mapCountryToContinentTest() {
      System.out.println("This is a test for mapCountry to Continet");
      assertNotNull(mapcom.mapCountryToContinent(continent, country));
    }

}
