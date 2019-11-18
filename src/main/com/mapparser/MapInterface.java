package com.mapparser;

import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * This is a interface for map reader and writer
 * @author Komal
 */
public interface MapInterface {
	public void DominationMapParser(String fileType, File file);
	public void ConquestMapParser(String fileType, File file, Hmap map);

}
