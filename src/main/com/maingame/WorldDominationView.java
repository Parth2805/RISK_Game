package com.maingame;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.controller.GameController;
import com.entity.Continent;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;


/**
 * This class controls the behavior of the World Domination View
 * <ul>
 * <li>the percentage of the map controlled by every player </li>
 * <li>the continents controlled by every player</li>
 * <li>the total number of armies owned by every player.</li>
 * </ul>
 *
 * @author Mahmoudreza
 */
public class WorldDominationView implements Observer {

    /**
     * Get list of continents owned by player
     *
     * @param player player object
     * @return continentList continent List
     */
    public ArrayList<String> getContinentOwnedByPlayer(Player player) {
        ArrayList<String> continentList = new ArrayList<String>();
        Boolean isAllCountriesOwned;

        for (Country c : player.getAssignedCountry()) {

            isAllCountriesOwned = true;
            Continent continent = c.getBelongToContinent();

            for (Country country : continent.getCountries()) {
                if (!country.getPlayer().getName().equalsIgnoreCase(player.getName()))
                    isAllCountriesOwned = false;
            }

            if (isAllCountriesOwned)
                continentList.add(continent.getName());
        }

        return continentList;
    }

}