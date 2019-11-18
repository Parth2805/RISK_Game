package com.mapparser;

import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * Parser for conquest map
 *  
 * @author Komal
 */
public class ConquestMapParser {
	
	MapReader mapReader;
	MapWriter mapWriter;
	Hmap map;
	
	/**
	 * 
	 */
	public ConquestMapParser() {
		this.mapReader = new MapReader();
		this.mapWriter = new MapWriter();
		this.map = new Hmap();
	}
	
	/**
	 * This method read conquest map
	 * @param map Hmap object
	 * @param file File to be read
	 * @return map returns the map object after processing the file data
	 * @throws InvalidMap 
	 */
	public Hmap readConquestMapFile(Hmap map, File file) throws InvalidMap {
		map = mapReader.readConquestMapFile(file);
		return map;
	}
	
	/**
	 * This method write conquest map
	 * @param map Hmap object
	 * @param file File to be read
	 */
	public void writeConquestMapFile(Hmap map, File file) {
		mapWriter.writeConquestMapFile(map, file);
	}
}
