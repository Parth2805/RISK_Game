package com.mapparser;

import java.io.File;

import com.exception.InvalidMap;

public class MapAdaptor implements MapInterface{
	MapReadInterface mapReader;
	MapWriteInterface mapWriter;
	
	public MapAdaptor(String fileType) {
		mapReader = new MapReader();
		mapWriter = new MapWriter();
	}

	@Override
	public void mapRead(String fileType, File file) throws InvalidMap {
		if(fileType.equalsIgnoreCase("DominationFile")) {
			mapReader.readDominationMapFile(file);
		} else if (fileType.equalsIgnoreCase("ConquestFile")) {
			mapReader.readConquestMapFile(file);
		}
	}

	@Override
	public void mapWrite(String fileType, File file) throws InvalidMap {
		if(fileType.equalsIgnoreCase("DominationFile")) {
			mapWriter.writeDominationMapFile(null, file);
		} else if (fileType.equalsIgnoreCase("ConquestFile")) {
			mapWriter.writeConquestMapFile(null, file);
		}
	}
	
	
}
