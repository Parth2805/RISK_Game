package com.mapparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * This Class for implementing adapter for domination map & conquest map.
 * @author Komal
 */
public class MapAdapter implements MapInterface {

	DominationMapParser dominationParser;
	ConquestMapParser conquestParser;
	Hmap map;
	
	/**
	 * default constructor to initialize members
	 */
	public MapAdapter() {
		this.dominationParser = new DominationMapParser();
		this.conquestParser = new ConquestMapParser();
	}
	
	/**
	 * default constructor to initialize members
	 * @throws FileNotFoundException 
	 */
	public static String identifyFileType(File file)  throws InvalidMap {
		
		String mapType;
		Scanner mapFileReader;
		StringBuilder mapString = new StringBuilder();
		try {
			mapFileReader = new Scanner(new FileInputStream(file));
			while (mapFileReader.hasNext()) {
				String line = mapFileReader.nextLine();
				mapString.append(line);
			}
		} catch (IOException e) {
			System.out.println("Exception: " + e.toString() + " Map File is not selected");
		}
		
		if(mapString.toString().contains("[borders]")) {
			mapType = "DominationMapFile";
		} else {
			mapType = "ConquestMapFile";
		}
		
		return mapType;
	}
	
	/**
	 * Read domination and conquest map file
	 * @param fileType Identified file type which can be domination or conquest
	 * @param file File to read
	 * @return the map
	 * @throws InvalidMap
	 * @throws FileNotFoundException 
	 */
	@Override
	public Hmap mapReader(File file) throws InvalidMap {
		
		String fileType;
		
			fileType = MapAdapter.identifyFileType(file);
		
		if (fileType.equalsIgnoreCase("DominationMapFile")) {
			map = dominationParser.readDominationMapFile(file);
		} else if (fileType.equalsIgnoreCase("ConquestMapFile")) {
			map = conquestParser.readConquestMapFile(file);
		}
		
		return map;
	}

	/**
	 * Write domination and conquest map file
	 * 
	 * @param fileType Identified file type which can be domination or conquest
	 * @param file File to read
	 * @param map Object of the map which is being processed
	 */
	@Override
	public void mapWriter(File file, Hmap map) throws InvalidMap{
		
		String fileType;
		fileType = MapAdapter.identifyFileType(file);
		
		if (fileType.equalsIgnoreCase("DominationMapFile")) {
			dominationParser.writeDominationFile(map, file);
		} else if (fileType.equalsIgnoreCase("ConquestMapFile")) {
			conquestParser.writeConquestMapFile(map, file);
		}
	}
}

/**
 * 
 * @author user
 * This class is to Parse Domination Map
 */
class DominationMapParser {

	Hmap map;
	MapReader mapReader;
	MapWriter mapWriter;

	/**
	* default constructor to initialize members
	*/
	public DominationMapParser() {
		this.mapReader = new MapReader();
		this.mapWriter = new MapWriter();
		this.map = new Hmap();
	}

	/**
	 * This method read Domination map
	 * 
	 * @param map Hmap object
	 * @param file File to be read
	 * @return map returns the map object after processing the file data
	 * @throws InvalidMap
	 */
	public Hmap readDominationMapFile(File file) throws InvalidMap {
		map = mapReader.readDominationMapFile(file);
		return map;
	}

	/**
	 * This method write Domination map
	 * @param map Hmap object
	 * @param file File to be read
	 */
	public void writeDominationFile(Hmap map, File file) {
		mapWriter.writeDominationMapFile(map, file);
	}

}

/**
 * 
 * @author user
 * This class is to Parse Conquest Map
 */

class ConquestMapParser {

	MapReader mapReader;
	MapWriter mapWriter;
	Hmap map;
	String mapType;
	
	/**
	 * default constructor to initialize members
	 */
	public ConquestMapParser() {
		this.mapReader = new MapReader();
		this.mapWriter = new MapWriter();
	}

	/**
	 * This method read conquest map
	 * 
	 * @param map Hmap object
	 * @param file File to be read
	 * @return map returns the map object after processing the file data
	 * @throws InvalidMap
	 */
	public Hmap readConquestMapFile(File file) throws InvalidMap {
		map = mapReader.readConquestMapFile(file);
		return map;
	}

	/**
	 * This method write conquest map
	 * 
	 * @param map Hmap object
	 * @param file File to be read
	 */
	public void writeConquestMapFile(Hmap map, File file) {
		mapWriter.writeConquestMapFile(map, file);
	}
}