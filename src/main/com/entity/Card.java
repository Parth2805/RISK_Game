package com.entity;

import com.config.CardType;
import com.entity.Country;

/**
 * This is the main class for the card.
 * @author Mehul
 */
public class Card {

	CardType cardType;
	
	private Country countryToWhichCardBelong;

	/**
	 * @return the type of the card
	 */
	public CardType getCardKind() {
		return cardType;
	}

	/**
	 * Parameterized Constructor for Card
	 * @param cardType reference to get cardType enum
	 */
	
	public Card(CardType cardType){
		this.cardType = cardType;
	}
	
	/**
	 * @param cardType sets the kind of card
	 */
	public void setCardKind(CardType cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return countryToWhichCardBelong
	 */
	public Country getCountryToWhichCardBelong() {
		return countryToWhichCardBelong;
	}

	/**
	 * @param countryToWhichCardBelong
	 */
	public void setCountryToWhichCardBelong(Country countryToWhichCardBelong) {
		this.countryToWhichCardBelong = countryToWhichCardBelong;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Card [cardType = " + cardType + ", CountryofCard = " + countryToWhichCardBelong + "]";
	}
}
