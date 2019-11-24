package com.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.models.PlayerModel;


/**
 * This is the interface for all strategies.
 * 
 * @author Mehul
 */
public interface Strategy {

	/**
	 * This method is responsible for reinforcement phase.
	 * 
	 * @param Hmap map object
	 * @param player current player
	 * @param cardsStack stack of cards
	 */
	public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack);
	
	
	/**
	 * This method implements the attack phase of the strategy.
	 * @param conList listview of country which belong to a player
	 * @param adjConList adjacent country listview for a particular country
	 * @param playerModel object of {@link PlayerModel}
	 * @param playerList list of players
	 * @param conArList List of country available to player.
	 * @param adjConArList List of adjacent country.
	 * @throws InvalidGameAction throws InvalidGameAction if move is not valid
	 */
	void attackPhase(ArrayList<Country> conList, ArrayList<Country> adjConList,
			PlayerModel playerModel, List<Player> playerList,
			ArrayList<Country> conArList,ArrayList<Country> adjConArList);

	
	/**
	 * This method implements the fortification phase of the strategy.
	 * @param selectedCountry List of selected countries for the fortification phase.
	 * @param adjCountry List of neighbour countries for fortification phase.
	 * @param currentPlayer  current player object.
	 * @param map  current map loader.
	 * @param countryArList List of countries.
	 * @param adjCountryArList List of neighbour countries.
	 * @return Returns true if fortification phase is possible.
	 */
	boolean fortificationPhase(ArrayList<Country> selectedCountry, ArrayList<Country> adjCountry, Player currentPlayer, 
			Hmap map, ArrayList<Country> countryArList,ArrayList<Country> adjCountryArList);

}
