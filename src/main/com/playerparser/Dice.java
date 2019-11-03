package com.playerparser;

import com.entity.Country;

import java.util.List;

public class Dice {

    public int numberOfDiceUsedByAttacker;

    public Country attackingCountry;

    public Country defendingCountry;

    public List<Integer> attackerDiceValues;

    public List<Integer> defendingDiceValues;

    public Dice(Country attackingCountry,Country defendingCountry){

        this.attackingCountry=attackingCountry;
        this.defendingCountry=defendingCountry;


    }


}
