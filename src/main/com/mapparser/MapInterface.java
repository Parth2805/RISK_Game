package com.mapparser;

import java.io.File;

import com.exception.InvalidMap;

public interface MapInterface {
	public void mapRead(String fileType, File file) throws InvalidMap;
	public void mapWrite(String fileType, File file) throws InvalidMap;

}
