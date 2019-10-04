package com.main;
import java.io.File;

import com.exception.InvalidMap;
import com.mapparser.MapReader;

public class Main {

	public static void main(String args[])
	{
		MapReader mapReader = new MapReader();
		String fpath = System.getProperty("user.dir") + "\\src\\main\\resources\\world.map";
		File worldMap = new File(fpath);
		try {
			mapReader.readMapFile(worldMap);
			
		} catch (InvalidMap e) {
			e.printStackTrace();
		}
	}
}
