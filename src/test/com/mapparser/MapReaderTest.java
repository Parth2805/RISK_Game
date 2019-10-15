package com.mapparser;

import static org.junit.Assert.*;

import com.exception.InvalidMap;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.*;
//import com.mapparser.MapReader;
import java.io.*;
import com.entity.Hmap;

public class MapReaderTest {
    File file = null;
    MapReader mpreader = null;
    Hmap hmp = null;
    ClassLoader clLoader = null;
    //String[] invalidFiles = {"test.map","world.map","world_without_colors.map"};

   @BeforeClass
    public static void beforecl(){
       String[] invalidFiles = {"test.map","world.map","world_without_colors.map"};
   }

    @Before
    public void beforeMethod() {
        mpreader = new MapReader();
        hmp = new Hmap();
        clLoader = getClass().getClassLoader();
    }
/*
   @Test
    public void bbdA() throws InvalidMap {
       //assertEquals(mprdr.readMapFile(file));
       file = new File(loader.getResource("world.map").getFile());
       map = mprdr.readMapFile(file);
       assertNull(mprdr.readMapFile(file));

   }

    @Test (expected=InvalidMap.class)
    public void checkForCountryNotMappedMutually() throws InvalidMap {
        String[] invalidFiles = new String[2];
        file = new File(loader.getResource(invalidFiles[1]).getFile());
        mprdr.readMapFile(file);
    }
*/

@Test
    public void testValidMap() throws InvalidMap {
        file = new File(clLoader.getResource("world.map").getFile());
        hmp = mpreader.readMapFile(file);
        assertEquals(hmp.getContinents().size(),6);
    }
}
