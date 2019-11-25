package com.strategy;

import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.models.PlayerModel;
import com.utilities.GameUtilities;

import java.util.*;


/**
 * This class implements Aggressive Strategy methods.
 * 
 * @author Parth
 */
public class Aggressive extends Observable implements Strategy {

    PlayerModel playerModel;

    public Aggressive() {
        this.playerModel = new PlayerModel();
    }

    @Override
    public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

    	int armies = player.getArmies();
        Country countryToReinforce = GameUtilities.getCountryWithMaxArmies(player);
        
        countryToReinforce.setArmy(armies + countryToReinforce.getArmy());
        player.setArmies(0);
        
    	System.out.println("Assigned "+ armies + " armies " + "to " + countryToReinforce.getName());
	
        return true;
    }

    @Override
    public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {

		Country attackingCountry = GameUtilities.getCountryWithMaxArmies(player);
		List<Country> defendCountries = GameUtilities.getDefendingCountries(attackingCountry);

		for (Country defendCountry: defendCountries) {
	
			if (!playerModel.isAttackPossible(player)) {
				System.out.println(player + " : No more attack possible");
				return true;
			}
			
			if (attackingCountry.getArmy() <= 1) {
				System.out.println("No more attack possible with " + attackingCountry);
				break;
			}
			
			System.out.println(attackingCountry.getName() + "(" + attackingCountry.getPlayer().getName() + ""
					+ ") attacking on " + defendCountry + "(" + defendCountry.getPlayer().getName() + ")");
			
			playerModel.allOutAttackCountry(map, player, attackingCountry.getName(), defendCountry.getName(), cardsStack);
		}

        return true;
    }

    @Override
	public boolean fortificationPhase(Hmap map, Player player) {

		List<Country> countryList = player.getAssignedCountry();
		Collections.sort(countryList);

		// To find country with greatest number of armies and to 
		// reinforce with any of its neighbors
		for (int i = 0; i < countryList.size(); i++) {
			if (countryList.get(i).getAdjacentCountries().size() > 0) {
				for (int j = i + 1; j < countryList.size(); j++) {

					if (GameUtilities.isCountryConnected(map, countryList.get(i), countryList.get(j))) {

						System.out.println("Fortified country: " + countryList.get(i).getName() + " from "
								+ countryList.get(j).getName() + " with armies: " + (countryList.get(j).getArmy() - 1));
						countryList.get(i).setArmy(countryList.get(i).getArmy() + countryList.get(j).getArmy() - 1);
						countryList.get(j).setArmy(1);
						return true;
					}
				}
			}
		}

		return true;
    }
}
