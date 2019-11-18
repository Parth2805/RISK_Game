package com.mapparser;

import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * This Class for implementing adapter for domination map & conquest map.
 * 
 * @author Komal
 */
public class MapAdapter implements MapInterface{
	DominationMapParser dominationParser;
	ConquestMapParser conquestParser; 
	Hmap map;

	/**
	 * Read domination and conquest map file
	 * @param fileType Identified file type which can be domination or conquest
	 * @param file File to read
	 * @return the map
	 * @throws InvalidMap 
	 */
	@Override
	public Hmap mapReader(String fileType, File file) throws InvalidMap {
		if(fileType.equalsIgnoreCase("DominationFile")) {
			dominationParser = new DominationMapParser();
			map = dominationParser.readDominationMapFile(map, file);
		} else if (fileType.equalsIgnoreCase("ConquestFile")) {
			conquestParser = new ConquestMapParser();
			map = conquestParser.readConquestMapFile(map, file);
		}
		return map;
	}
	/**
	 * Write domination and conquest map file
	 * @param fileType Identified file type which can be domination or conquest
	 * @param file File to read
	 * @param map Object of the map which is being processed
	 */
	@Override
	public void mapWriter(String fileType, File file, Hmap map) {
		if(fileType.equalsIgnoreCase("DominationFile")) {
			dominationParser = new DominationMapParser();
			dominationParser.writeDominationFile(map, file);
		} else if (fileType.equalsIgnoreCase("ConquestFile")) {
			conquestParser.writeConquestMapFile(map, file);
		}		
	}
	
	
}
