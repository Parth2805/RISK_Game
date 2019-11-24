package com.strategy;


import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.models.CardModel;
import com.models.PlayerModel;
import com.utilities.GameUtilities;

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

        List<Country> countryToFortify = player.getAssignedCountry();

        //To find country with greatest number of armoes and to reinforce with any of its neighbors

        System.out.println("Done Fortification");
        return false;
    }
}
