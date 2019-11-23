package com.strategy;

import java.util.ArrayList;
import java.util.List;

import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.models.PlayerModel;

/**
 * 
 * @author xxx
 */
public class Human implements Strategy {

	@Override
	public void reinforcementPhase(ArrayList<Country> countryList, Country country, Player currentPlayer,
			ArrayList<Country> countryArList, ArrayList<Country> adjCountryArList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attackPhase(ArrayList<Country> conList, ArrayList<Country> adjConList, PlayerModel playerModel,
			List<Player> playerList, ArrayList<Country> conArList, ArrayList<Country> adjConArList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean fortificationPhase(ArrayList<Country> selectedCountry, ArrayList<Country> adjCountry,
			Player currentPlayer, Hmap map, ArrayList<Country> countryArList, ArrayList<Country> adjCountryArList) {
		// TODO Auto-generated method stub
		return false;
	}

}
