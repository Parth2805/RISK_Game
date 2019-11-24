package com.strategy;


import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.models.PlayerModel;
import com.utilities.GameUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

/**
 * 
 * @author xxx
 */
public class Aggressive extends Observable implements Strategy {



    @Override
    public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

        Country countryToReinforce= GameUtilities.getCountryWithMaxArmies(player);

        countryToReinforce.setArmy(player.getArmies());
        System.out.println("Reinforced Country:"+countryToReinforce.getName()+" with total army:"+countryToReinforce.getArmy());
        System.out.println("------Reinforcement complete-------");
        return false;
    }

    @Override
    public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {

        Country countryToAttackWith = GameUtilities.getCountryWithMaxArmies(player);

        List<Country> countryList = countryToAttackWith.getAdjacentCountries();

        for(Country c:countryList){

            if(countryToAttackWith.getArmy()<=1){
                System.out.println("Cant Attack Anymore with this country");
                break;
            }else
            if(!c.getPlayer().equals(player)){

                String attackingCountry=countryToAttackWith.getName();
                String defendingCountry=c.getName();
                playerModel.allOutAttackCountry(map,player,attackingCountry,defendingCountry,cards);
            }

        }

        return false;
    }

    @Override
    public boolean fortificationPhase(Hmap map, Player player) {
        return false;
    }
}
