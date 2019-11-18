package com.mapparser;

import java.io.File;

import com.entity.Hmap;
import com.exception.InvalidMap;

/**
 * This Class for implementing adapter for domination map & conquest map.
 * 
 * @author Komal
 */
public class MapAdapter implements MapInterface{
	DominationMapParser dominationParser;
	ConquestMapParser conquestParser; 
	Hmap map;
	
	public MapAdapter(String fileType){
		   
	      if(fileType.equalsIgnoreCase("DominationFile") ){
	         DominationMapParser = new MapReader();			
	         
	      }else if (audioType.equalsIgnoreCase("ConquestFile")){
	         advancedMusicPlayer = new Mp4Player();
	      }	
	   }
	/**
	 * Read and write domination map file
	 * @param fileType Identified file type which can be domination or conquest
	 * @param file File to read
	 * @param map Object of the map which is being processed
	 */
	@Override
	public void DominationMapParser(String fileType, File file) {
		
		
	}
	
	/**
	 * Read and Write conquest map file
	 * @param fileType Identified file type which can be domination or conquest
	 * @param file File to read
	 * @param map Object of the map which is being processed
	 */
	@Override
	public void ConquestMapParser(String fileType, File file, Hmap map) {
		// TODO Auto-generated method stub
		
	}
	
	
}
