package com.mapparser;

import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;

public interface MapReadInterface {
	public Hmap readDominationMapFile(final File file) throws InvalidMap;
	public Hmap readConquestMapFile(final File file) throws InvalidMap;
	
}
