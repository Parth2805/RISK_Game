package com.strategy;


import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.models.CardModel;
import com.models.PlayerModel;
import com.utilities.GameUtilities;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Stack;

/**
 * 
 * @author xxx
 */
public class Benevolent extends Observable implements Strategy  {

    boolean isShowMapCommand = false;
    PlayerModel playerModel;
    //    Player currentPlayer;
    CardModel cardModel;
    Stack<Card> cardsStack;

    public Benevolent() {
//        this.addObserver(cardExchange);
        this.playerModel = new PlayerModel();
        this.cardsStack = new Stack<Card>();
        this.cardModel = new CardModel();
    }

    @Override
    public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

        System.out.println("-------Reinforcement Phase---------");
        Country countryToReinforce= GameUtilities.getCountryWithMinArmies(player);
        countryToReinforce.setArmy(player.getArmies());


        System.out.println("Reinforced Country:"+countryToReinforce.getName()+" with total army:"+countryToReinforce.getArmy());
        System.out.println("------Reinforcement complete-------");
        return true;
    }

    @Override
    public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {

        System.out.println("Benevolent player wont attack");
        return true;
    }

    @Override
    public boolean fortificationPhase(Hmap map, Player player) {

        System.out.println("-----Fortifying---------");

        List<Country> countryList = player.getAssignedCountry();

        Collections.sort(countryList);
        System.out.println(countryList);

        //To find country with greatest number of armies and to reinforce with any of its neighbors
        for(int i=0;i<countryList.size();i++){

            if(countryList.get(i).getAdjacentCountries().size()>0){


                for(int j=countryList.size();j>i;j--){

                    if(GameUtilities.isCountryConnected(map,countryList.get(i),countryList.get(j))){

                        System.out.println("Fortified country:"+countryList.get(j).getName()+" from "+countryList.get(i).getName()+" with armies:"+(countryList.get(i).getArmy()-1));
                        countryList.get(j).setArmy(countryList.get(j).getArmy()+countryList.get(i).getArmy()-1);
                        countryList.get(i).setArmy(1);
                        return true;

                    }
                }

//                List<Country> adjacentCountries = c.getAdjacentCountries();
//                Collections.sort(adjacentCountries);
//
//                if(adjacentCountries.get(0).getArmy()==1){
//
//                }else{
//                    System.out.println("Fortified country:"+c.getName()+" from "+adjacentCountries.get(0).getName()+" with armies:"+(adjacentCountries.get(0).getArmy()-1));
//                    c.setArmy(c.getArmy()+adjacentCountries.get(0).getArmy()-1);
//                    adjacentCountries.get(0).setArmy(1);
//                    return true;
//                }
            }
        }
        System.out.println("Done Fortification");
        return false;
    }
}
