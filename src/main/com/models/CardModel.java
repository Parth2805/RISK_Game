package com.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import com.config.CardType;
import com.config.Commands;
import com.controller.GameController;
import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;

/**
 * This class is handles the behavior of the card.
 *
 * @author Mehul
 */
public class CardModel {


    private List<Card> cardForExchange;
	public void setNumberOfTimesCardExchanged() {
		cardExchanged += 5;
	}
	public int getCardExchanged() {
		return cardExchanged;
	}
	private static int cardExchanged = 5;
    /**
     * Gets the cards to be exchanged.
     *
     * @return the cardsToBeExchange
     */
    public List<Card> getCardsToBeExchange() {
        return cardForExchange;
    }

    /**
     * Sets the cards to be exchanged.
     *
     * @param cardsToBeExchange the cardsToBeExchange to set
     */
    public void setCardsToBeExchange(List<Card> cardsToBeExchange) {
        this.cardForExchange = cardsToBeExchange;
    }

    /**
     * Allocate cards to country 
     * 
     */
    public void allocateCardsToCountry(Hmap map, Stack<Card> stackOfCards) {

        ArrayList<Country> countryList = map.getCountries();
        ArrayList<CardType> cardList = new ArrayList<>();
        int eachUniqueCards = countryList.size() / 3;
        
        cardList.addAll(Collections.nCopies(eachUniqueCards, CardType.valueOf("CAVALRY")));
        cardList.addAll(Collections.nCopies(eachUniqueCards, CardType.valueOf("ARTILLERY")));
        cardList.addAll(Collections.nCopies(eachUniqueCards, CardType.valueOf("INFANTRY")));

        int left = countryList.size() - cardList.size();

        if (left > 0) {
            for (int i = 0; i < left; i++) {
                cardList.add(CardType.values()[(int) (Math.random() * CardType.values().length)]);
            }
        }

        int i = 0;

        for (Country country : countryList) {
            Card card = new Card(cardList.get(i++));
            card.setCountryToWhichCardBelong(country);
            stackOfCards.push(card);
        }

        Collections.shuffle(stackOfCards);
        for (Card cards : stackOfCards) {
            System.out.println(cards);
        }
    }

    /**
     * exchange of cards between players
     * @param cardlist list of cards
     * 
     */    
    public void exchangeCards(Player player, int idx[], List<Card> cardlist, Stack<Card> cardStack) {

        for (int index : idx) {
            for (Country c : player.getAssignedCountry()) {
                if (c.getName().equalsIgnoreCase(cardlist.get(index).getCountryToWhichCardBelong().getName())) {
                    player.setArmies(player.getArmies() + 2);
                    break;
                }
            }
        }

        player.setArmies(player.getArmies() + getCardExchanged());
        setNumberOfTimesCardExchanged();

        for (Card card : cardlist) {
            // Removing the exchanged cards from players hand
            player.getCardList().remove(card);
        	// Adding cards back to deck
            cardStack.push(card);
        }
    }

    /**
    * validates card for exchange
    * @param player scanner object
    * @return number of cards 
  	*
    */
    public boolean areCardsvalidForExchange(List<Card> cardlist) {

        if (cardlist.size() == 3) {
            int infantry = 0, cavalry = 0, artillery = 0;
            
            for (Card card : cardlist) {
                if (card.getCardKind().toString().equals(CardType.CAVALRY.toString())) {
                    infantry++;
                } else if (card.getCardKind().toString().equals(CardType.INFANTRY.toString())) {
                    cavalry++;
                } else if (card.getCardKind().toString().equals(CardType.ARTILLERY.toString())) {
                    artillery++;
                }
            }
            
            // if all are of different kind or all are of same 
            // kind then only, player can exchange cards for army.
            if ((infantry == 1 && cavalry == 1 && artillery == 1) 
            		|| infantry == 3 || cavalry == 3 || artillery == 3) {
                return true;
            }
        }
        
        return false;
    }

    /**
    * Parses the String and calls the related game play startup commands.
    * @param player scanner object
    * 
    */
    public boolean checkMaxCards(Player player, Stack<Card> cardStack) {

        // Check if player has 5 cards and tells to exchange until less than 5 cards
        if (player.getCardList().size() >= 5) {

            int i = 1;

            System.out.println("You have 5(max) cards, need to exchange!!");
            System.out.println("Cards List:");

            for (Card cards : player.getCardList()) {
                System.out.println(i + "." + cards);
                i++;
            }
            System.out.println("Countries owned::");

            for (Country c : player.getAssignedCountry()) {
                System.out.println(c.getName());
            }

            Scanner sc1 = new Scanner(System.in);

            while (player.getCardList().size() == 5) {
                System.out.println("Enter the cards(index) to exchange:");
                String input = sc1.nextLine();
                String tempwords[] = input.split(" ");

                if (tempwords[0].equalsIgnoreCase(Commands.MAP_COMMAND_REINFORCE_OPTION_EXCHANGECARDS)) {

                    if (tempwords[1].equalsIgnoreCase("-none")) {
                        System.out.println("Need to Exchange Cards!!");
                    } else {
                        if (player.getCardList().size() < 3) {
                            System.out.println("Have less than 3 cards, cant exchange");
                            return false;
                        }
                        int idx[] = new int[3];
                        idx[0] = Integer.parseInt(tempwords[1]) - 1;
                        idx[1] = Integer.parseInt(tempwords[2]) - 1;
                        idx[2] = Integer.parseInt(tempwords[3]) - 1;
                        List<Card> cardschoosen = new ArrayList<>();

                        List<Card> cardlist = player.getCardList();

                        for (int index : idx) {
                            cardschoosen.add(cardlist.get(index));
                        }

                        if (areCardsvalidForExchange(cardschoosen)) {
                            exchangeCards(player, idx, cardschoosen, cardStack);
                        } else {
                            System.out.println("Only exchange 1.Cards of all same type or 2.Cards of all different type");
                        }
                    }
                } else {
                	System.out.println("Invalid Input Command!!");
                }
            }
        }
        return false;
    }
}