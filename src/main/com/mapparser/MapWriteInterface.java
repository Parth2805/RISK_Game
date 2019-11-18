package com.mapparser;

import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;

public interface MapWriteInterface {
	public void writeDominationMapFile(Hmap map, File file);
	public void writeConquestMapFile(Hmap map, File file);
}
