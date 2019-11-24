package com.strategy;


import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.maingame.CardExchangeView;
import com.models.CardModel;
import com.models.PlayerModel;
import com.utilities.GameUtilities;

import java.util.*;

/**
 * 
 * @author xxx
 */
public class Aggressive extends Observable implements Strategy {

    boolean isShowMapCommand = false;
    PlayerModel playerModel;
//    Player currentPlayer;
    CardModel cardModel;
    Stack<Card> cardsStack;

    public Aggressive() {
//        this.addObserver(cardExchange);
        this.playerModel = new PlayerModel();
        this.cardsStack = new Stack<Card>();
        this.cardModel = new CardModel();
    }

    @Override
    public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

        System.out.println("----------Map before reinforce----------");
        GameUtilities.gamePlayShowmap(map);
        System.out.println("-------Reinforcement Phase---------");
        Country countryToReinforce= GameUtilities.getCountryWithMaxArmies(player);
        countryToReinforce.setArmy(player.getArmies()+countryToReinforce.getArmy());


        System.out.println("Reinforced Country:"+countryToReinforce.getName()+" with total army:"+countryToReinforce.getArmy());
        System.out.println("------Reinforcement complete-------");
        System.out.println("----------Map after reinforce----------");
        GameUtilities.gamePlayShowmap(map);
        return true;
    }

    @Override
    public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {
        GameUtilities.gamePlayShowmap(map);
        Country countryToAttackWith = GameUtilities.getCountryWithMaxArmies(player);

        List<Country> countryList = countryToAttackWith.getAdjacentCountries();

        for(Country c:countryList){

            if(!playerModel.isAttackPossible(player)){
                System.out.println("No more attack possible");
                return true;
            }else
            if(countryToAttackWith.getArmy()<=1){
                System.out.println("Cant Attack Anymore with this country");
                break;
            }else
            if(!c.getPlayer().equals(player)){

                String attackingCountry=countryToAttackWith.getName();
                String defendingCountry=c.getName();
                playerModel.allOutAttackCountry(map,player,attackingCountry,defendingCountry,cardsStack);
            }
        }
        System.out.println("Out of for loop:");
        return true;
    }

    @Override
    public boolean fortificationPhase(Hmap map, Player player) {
        GameUtilities.gamePlayShowmap(map);
        System.out.println("-----Fortifying---------");

        List<Country> countryList = player.getAssignedCountry();

        Collections.sort(countryList);

        System.out.println(countryList);


        //To find country with greatest number of armies and to reinforce with any of its neighbors
        for(int i=0;i<countryList.size();i++){

            if(countryList.get(i).getAdjacentCountries().size()>0){


                for(int j=i+1;j<countryList.size();j++){

                    if(GameUtilities.isCountryConnected(map,countryList.get(i),countryList.get(j))){

                        System.out.println("Fortified country:"+countryList.get(i).getName()+" from "+countryList.get(j).getName()+" with armies:"+(countryList.get(j).getArmy()-1));
                        countryList.get(i).setArmy(countryList.get(i).getArmy()+countryList.get(j).getArmy()-1);
                        countryList.get(j).setArmy(1);
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
        return true;
    }
}
