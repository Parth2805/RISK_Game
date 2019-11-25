package com.strategy;


import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.utilities.GameUtilities;

import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Stack;

/**
 * 
 * @author xxx
 */
public class RandomS extends Observable implements Strategy {

    @Override
    public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

        GameUtilities.gamePlayShowmap(map);

        System.out.println("---------Fortifying---------");
        Random rand= new Random();
        int n=rand.nextInt(player.getAssignedCountry().size());
        int n1=rand.nextInt(player.getArmies())+1;
        player.getAssignedCountry().get(n).setArmy(player.getAssignedCountry().get(n).getArmy()+n1);
        player.setArmies(player.getArmies()-n1);
        System.out.println("Reinforced:"+player.getAssignedCountry().get(n).getName());
        GameUtilities.gamePlayShowmap(map);
        System.out.println("---------Done Fortification---------");

        if(player.getArmies()>0){

            return false;

        }else
            return true;
    }

    @Override
    public boolean attackPhase(Hmap map, Player player, Stack<Card> cardsStack) {

//        boolean countryFoundToAttackWith=false;

//        while(countryFoundToAttackWith==false){
//
//            int n=new Random().nextInt(player.getAssignedCountry().size());
//            Country countryToAttackWith = player.getAssignedCountry().get(n);
//            if(countryToAttackWith.getArmy()==1){
//
//            }else{
//
//                List<Country> adjacentCountries = GameUtilities.getDefendingCountry(countryToAttackWith);
//
//                if(adjacentCountries.size()!=0){
//
//                    int n1 = new Random().nextInt(adjacentCountries.size());
//                    Country countryToAttackTo = adjacentCountries.get(n1);
//
//
//
//
//
//                }
//                else{
//
//                }
//            }
//
//
//
//
//        }
        int n=new Random().nextInt(player.getAssignedCountry().size());

        if(player.getAssignedCountry().get(n).getArmy()==1||GameUtilities.getDefendingCountries(player.getAssignedCountry().get(n)).size()==0){



        }else{

            int numOfTimestoAttack= new Random().nextInt();


        }





        return false;
    }

    @Override
    public boolean fortificationPhase(Hmap map, Player player) {
        return false;
    }
}
