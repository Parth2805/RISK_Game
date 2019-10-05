package com.main;
import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;
import com.mapparser.MapReader;
import com.mapparser.MapWriter;


public class Main {

	public static void main(String args[])
	{
		Hmap map = new Hmap();
		MapReader mapReader = new MapReader();
		MapWriter write = new MapWriter();
		String fpath = System.getProperty("user.dir") + "\\src\\main\\resources\\world.map";
		File worldMap = new File(fpath);
		//Manual world map
		String mfpath = System.getProperty("user.dir") + "\\src\\main\\resources\\mworld.map";
		File mWorldMap = new File(mfpath);
		try {
			mapReader.readMapFile(worldMap);
	    	write.writeMapFile(map, mWorldMap);
			
		} catch (InvalidMap e) {
			e.printStackTrace();
		}
	}
}
