package com.mapparser;

import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * This is a interface for map reader and writer
 * @author Komal
 */
public interface MapInterface {
	public Hmap mapReader(String fileType, File file) throws InvalidMap;
	public void mapWriter(String fileType, File file, Hmap map);

}
