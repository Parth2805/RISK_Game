package com.mapparser;

import java.io.File;
import java.io.FileNotFoundException;

import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * This is a interface for MapAdapter class which has Map Reader and Map Writer methods
 * @author Komal
 */
public interface MapInterface {
	
	/**
	 * Method description to Read domination and conquest map file 
	 * @throws FileNotFoundException 
	*/
	public Hmap mapReader(File file) throws InvalidMap;
	
	/**
	 * Method description to Write domination and conquest map file
	 * @throws InvalidMap 
	*/
	public void mapWriter(File file, Hmap map) throws InvalidMap;
}
