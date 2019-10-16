package com.entity;

import java.io.*;
import java.util.*;
import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;

/**
 * This class describes Map and its list of continents
 * @author Mehul
 * @see Country
 * @see Continent
 */
public class Hmap {

	private List<Continent> continents;
	
	private HashMap<String, String> mapData;
	private HashMap<String, Continent> continentMap;
	private HashMap<String, Integer> countriesIdxMap;

	
	/**
	 * This is the default constructor of Hmap.
	 */
	public Hmap() {
		mapData = new HashMap<String, String>();
		continents = new ArrayList<Continent>();
		continentMap = new HashMap<String,Continent>();
		countriesIdxMap = new HashMap<String,Integer>();
	}
	
	/**
	 * This is parameterized constructor for map.
	 * @param newMap The new map object.
	 */
	public Hmap(Hmap newMap) {
		mapData = new HashMap<String, String>(newMap.mapData);
		continents = new ArrayList<Continent>(newMap.continents);
		continentMap = new HashMap<String,Continent>(newMap.continentMap);
		countriesIdxMap = new HashMap<String,Integer>(newMap.countriesIdxMap);
	}
	
	/**
	 * Returns the map data.
	 * @return mapData
	 */
	public HashMap<String, String> getMapData() {
		return mapData;
	}
	
	/**
	 * This sets the map data.
	 * @param mapData
	 */
	public void setMapData(HashMap<String, String> mapData) {
		this.mapData = mapData;
	}
	
	/**
	 * It returns list of continents.
	 * @return the continents
	 */
	public List<Continent> getContinents() {
		return continents;
	}
	
	/**
	 * This sets the continents.
	 * @param continents
	 */
	public void setContinents(List<Continent> continents) {
		this.continents = continents;
	}
	
	/**
	 * Returns the continent maps
	 * @return countriesIdxMap
	 */
	public HashMap<String, Integer> getCountriesIdxMap() {
		return countriesIdxMap;
	}
	
	/**
	 * This sets the continent map.
	 * @param countriesIdxMap
	 */
	public void setCountriesIdxMap(HashMap<String, Integer> countriesIdxMap) {
		this.countriesIdxMap = countriesIdxMap;
	}
	
	/**
	 * Returns the continent maps
	 * @return scontinentMap
	 */
	public HashMap<String, Continent> getContinentMap() {
		return continentMap;
	}
	
	/**
	 * This sets the continent map.
	 * @param continentMap
	 */
	public void setContinentMap(HashMap<String, Continent> continentMap) {
		this.continentMap = continentMap;
	}

	/**
	 * The update change method is used for observers.
	 */
	public void setChangedForMap() {
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Hmap [ mapData = " + mapData + ", continents = " + continents + ", continentMap = " + continentMap + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		ObjectOutputStream output_obj = null;
		ObjectInputStream input_obj = null;
		Hmap clonedMap = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			output_obj = new ObjectOutputStream(bos);
			
			// serialize and pass the object
			output_obj.writeObject(this);
			output_obj.flush();
			
			ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			input_obj = new ObjectInputStream(bin);
			clonedMap = (Hmap) input_obj.readObject();
		} catch (Exception e) {
			System.out.println("Exception in ObjectCloner = " + e.toString());
		} finally {
			try {
				output_obj.close();
				input_obj.close();
			} catch (IOException e) {
				// Auto-generated catch block
				System.out.println("Exception: " + e.toString());
			}
		}
		
		return clonedMap;
	}	
}
