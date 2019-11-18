package com.mapparser;

import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * Parser for domination map
 *  
 * @author Komal
 */
public class DominationMapParser{

	Hmap map;
	MapReader mapReader;
	MapWriter mapWriter;
	
	/**
	 * 
	 */
	public DominationMapParser() {
		this.mapReader = new MapReader();
		this.mapWriter = new MapWriter();
		this.map = new Hmap();
	}
	
	/**
	 * This method read Domination map
	 * @param map Hmap object
	 * @param file File to be read
	 * @return map returns the map object after processing the file data
	 * @throws InvalidMap 
	 */
	public Hmap readDominationMapFile(Hmap map, File file) throws InvalidMap{
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
