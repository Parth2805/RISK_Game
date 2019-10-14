package com.mapparser;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Scanner;

import org.junit.*;

import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.exception.InvalidMap;

public class MapReaderTest {
	MapReader mapreader;
	Hmap map=new Hmap();
	final File file = null;
	Scanner scan;
	
	@BeforeClass
    public static void beforeAllTesting() {
        System.out.println("This is for testing Player Class");
    }
    @Before
    public void beforeTest() {
     
        mapreader=new MapReader();
    	
       
    }
    
    @AfterClass
    public static void afterPerformingTests() {
        System.out.println("The test is done");
    }
    
    
    @Test
    public void readMapFileTest() throws InvalidMap {
    	
    	assertNull(mapreader.readMapFile(file));
    	
    	
    }
    
  


 
}
