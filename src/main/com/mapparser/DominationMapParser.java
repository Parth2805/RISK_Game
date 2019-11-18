package com.mapparser;

import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * Parser interface for domination map
 *  
 * @author Komal
 */
public interface DominationMapParser  {
	public Hmap readDominationMapFile(final File file) throws InvalidMap;
	public Hmap writeDominationMapFile(final File file) throws InvalidMap;
	
}
