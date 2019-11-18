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
	 */
	@Override
	public Hmap mapReader(String fileType, File file) throws InvalidMap {
		if(fileType.equalsIgnoreCase("DominationFile")) {
			map = dominationParser.readDominationMapFile(file);
		} else if (fileType.equalsIgnoreCase("ConquestFile")) {
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
	public void mapWriter(String fileType, File file, Hmap map) throws InvalidMap {
		if(fileType.equalsIgnoreCase("DominationFile")) {
			map = dominationParser.writeDominationMapFile(file);
		} else if (fileType.equalsIgnoreCase("ConquestFile")) {
			map = conquestParser.writeConquestMapFile(map, file);
		}		
	}
	
	
}
