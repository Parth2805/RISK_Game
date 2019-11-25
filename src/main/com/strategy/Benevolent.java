package com.strategy;

import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.models.PlayerModel;
import com.utilities.GameUtilities;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Stack;


/**
 * This class implements Benevolent Strategy methods.
 * 
 * @author Parth
 */
public class Benevolent extends Observable implements Strategy {

	PlayerModel playerModel;

	public Benevolent() {
		// this.addObserver(cardExchange);
		this.playerModel = new PlayerModel();
	}

	@Override
	public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

		int armies = player.getArmies();
		Country countryToReinforce = GameUtilities.getCountryWithMinArmies(player);
		
		countryToReinforce.setArmy(armies + countryToReinforce.getArmy());
        player.setArmies(0);

    	System.out.println("Assigned "+ armies + " armies " + "to " + countryToReinforce.getName());
    	
		return true;
	}

	@Override
	public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {

		System.out.println(player + " won't attack");
		return true;
	}

	@Override
	public boolean fortificationPhase(Hmap map, Player player) {

		List<Country> countryList = player.getAssignedCountry();

		Collections.sort(countryList);
		System.out.println(countryList);

		// To find country with greatest number of armies and to reinforce with any of
		// its neighbors
		for (int i = 0; i < countryList.size(); i++) {
			if (countryList.get(i).getAdjacentCountries().size() > 0) {
				for (int j = countryList.size() - 1; j > i; j--) {

					if (GameUtilities.isCountryConnected(map, countryList.get(i), countryList.get(j))) {

						System.out.println("Fortified country:" + countryList.get(j).getName() + " from "
								+ countryList.get(i).getName() + " with armies:" + (countryList.get(i).getArmy() - 1));
						countryList.get(j).setArmy(countryList.get(j).getArmy() + countryList.get(i).getArmy() - 1);
						countryList.get(i).setArmy(1);
						return true;
					}
				}
			}
		}

		return true;
	}
}
