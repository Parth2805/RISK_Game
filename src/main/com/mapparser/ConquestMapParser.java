package com.mapparser;

import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * Parser interface for conquest map
 *  
 * @author Komal
 */
public interface ConquestMapParser {
	public Hmap readConquestMapFile(Hmap map, File file);
	public Hmap writeConquestMapFile(Hmap map, File file);
}
