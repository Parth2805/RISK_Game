package com.entity;

import java.util.ArrayList;
import java.util.List;

import com.entity.Continent;
import com.entity.Country;
import com.entity.Player;

public class Country {

	/**
	 * @author Mehul
	 * This class defines properties of country like its coordinates, 
	 * the continent to which country belongs, its adjacent countries 
	 * and whether its been assigned to any player or not.
	 * @see Continent 
	 */
	private String name;
	
	private int xCoordinate;
	private int yCoordinate;
	
	private Player player;
	private Continent belongToContinent;
	
	private List<String> neighborCountries;
	private List<Country> adjacentCountries;
	
	private int army;
	private boolean isProcessed;

	/**
	 * Default constructor for the country.
	 */
	public Country() {
		neighborCountries = new ArrayList<>();
		adjacentCountries = new ArrayList<>();
	}
	
	/**
	 * Parameterized constructor for the country.
	 * @param adjCountries - names of adjacent countries
	 * @param adjacentCountries - object of adjacent countries
	 */
	public Country(List<String> adjCountries, List<Country> adjacentCountries) {
		this.neighborCountries = adjCountries;
		this.adjacentCountries = adjacentCountries;
	}
	
	/**
	 * Getter method for the country armies.
	 * @return army
	 */
	public int getArmy() {
		return army;
	}
	
	/**
	 * Getter method for the player.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Setter method for the player.
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Setter method for the country armies count.
	 * @param army
	 */
	public void setArmy(int army) {
		if (army > 5000) {
			this.army = 5000;
			return;
		}
		this.army = Math.abs(army);
	}
	
	/**
	 * Getter method for the country name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter method for the country name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method for the X coordinate of the country.
	 * @return xCoordinate
	 */
	public int getxCoordinate() {
		return xCoordinate;
	}
	
	/**
	 * Setter method for the X coordinate of the country.
	 * @param xCoordinate
	 */
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	/**
	 * Getter method for the y coordinate of the country.
	 * @return yCoordinate
	 */
	public int getyCoordinate() {
		return yCoordinate;
	}
	
	/**
	 * Setter method for the y coordinate of the country.
	 * @param yCoordinate set yCoordinate
	 */
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	/**
	 * Getter method to returns continent which belongs to the country.
	 * @return belongToContinent
	 */
	public Continent getBelongToContinent() {
		return belongToContinent;
	}
	
	/**
	 * Setter method to set the continent for the country.
	 * @param belongToContinent
	 */
	public void setBelongToContinent(Continent belongToContinent) {
		this.belongToContinent = belongToContinent;
	}
	
	/**
	 * Getter method to return the adjacent countries of country.
	 * @return adjCountries
	 */
	public List<String> getAdjCountries() {
		return neighborCountries;
	}
	
	/**
	 * Setter method for the adjacent countries to the country.
	 * @param adjCountries
	 */
	public void setAdjCountries(List<String> adjCountries) {
		this.neighborCountries = adjCountries;
	}
	
	/**
	 * Getter method to return the adjacent countries to the countries.
	 * @return adjacentCountries
	 */
	public List<Country> getAdjacentCountries() {
		return adjacentCountries;
	}
	
	/**
	 * Setter method for the adjacent countries to the countries.
	 * @param adjacentCountries
	 */
	public void setAdjacentCountries(List<Country> adjacentCountries) {
		this.adjacentCountries = adjacentCountries;
	}
	
	/**
	 * Checks whether the country is processed or not.
	 * @return boolean value whether a country is processed or not
	 */
	public boolean isProcessed() {
		return isProcessed;
	}
	
	/**
	 * Setter method for the country as processed.
	 * @param isProcessed
	 */
	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Country [name=" + name +"]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */	
	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Country))
			return false;
		
		if (obj == this)
			return true;

		Country t = (Country) obj;
		
		return t.getName().equalsIgnoreCase(name);
	}
}