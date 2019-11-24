package com.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.Stack;

import com.config.Commands;
import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.maingame.CardExchangeView;
import com.models.CardModel;
import com.models.PlayerModel;
import com.utilities.GameUtilities;

/**
 * This class implements Human Strategy methods.
 * 
 * @author Mehul
 */
public class Human extends Observable implements Strategy {

	boolean isShowMapCommand = false;
	PlayerModel playerModel;
	Player currentPlayer;
	CardModel cardModel;
	Stack<Card> cardsStack;

	public Human(CardExchangeView cardExchange) {
		this.addObserver(cardExchange);
		this.playerModel = new PlayerModel();
		this.cardsStack = new Stack<Card>();
		this.cardModel = new CardModel();
	}
	
	/**
	 * Get the current player.
	 * 
	 * @return currentPlayer playing
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Get the Card model.
	 * 
	 * @return card model object
	 */
	public CardModel getCardModel() {
		return cardModel;
	}
	
	/**
	 * This method is to set the current player.
	 * 
	 * @param player Current player.
	 */
	public void setCurrentPlayer(Player player) {
		this.currentPlayer = player;
	}
	
	/**
	 * Set the stack of cards.
	 * 
	 * @param stackOfCards stack of cards.
	 */
	public void setCardsStack(Stack<Card> stackOfCards) {
		this.cardsStack = stackOfCards;
	}
	
	/**
	 * Get the stack of cards.
	 * 
	 * @return stack of cards
	 */
	public Stack<Card> getCardsStack() {
		return cardsStack;
	}
	
	@Override
	public boolean reinforcementPhase(Hmap map, Player player, Stack<Card> cardsStack) {

		setCurrentPlayer(player);
		setCardsStack(cardsStack);
		
		if (!isShowMapCommand) {
			// Card exchange view
			setChanged();
			notifyObservers("card-exchange");
		} 
		
		System.out.println("Current game phase: Gameplay reinforcement phase (reinforce countryname num, showmap)");
		System.out.println("Current Player: " + getCurrentPlayer().getName() + 
				" (" + getCurrentPlayer().getPlayerStrategyName() + ")"  
				+ ", Armies left for reinforcement = " + getCurrentPlayer().getArmies());
		
		Scanner sc = new Scanner(System.in);
		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SKIP:
			// changeCurrentPlayer();
			break;

		case Commands.MAP_COMMAND_SHOWMAP:
			isShowMapCommand = true;
			GameUtilities.gamePlayShowmap(map);
			break;

		case Commands.MAP_COMMAND_REINFORCE:

			isShowMapCommand = false;
			if (words.length < 3) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}

			String countryName = words[1];
			int numberOfArmies = 0;

			try {
				numberOfArmies = Integer.parseInt(words[2]);
			} catch (Exception e) {
				System.out.println("Exception: " + e.toString());
				return false;
			}

			if (numberOfArmies <= 0) {
				System.out.println("Error: You have entered negative number of armies.");
				return false;
			}

			if (!GameUtilities.isCountryBelongToPlayer(map, player, countryName)) {
				System.out.println("Error: Given country " + countryName + " does not belong to " + player);
				return false;
			}

			if (playerModel.reinforceArmiesForCurrentPlayer(player, countryName, numberOfArmies))
				return true;

			break;
			
		default:
			isShowMapCommand = false;
			System.out.println("Invalid command, Try again !!!");
			break;
		}

		return false;
	}

	@Override
	public void attackPhase(ArrayList<Country> conList, ArrayList<Country> adjConList, PlayerModel playerModel,
			List<Player> playerList, ArrayList<Country> conArList, ArrayList<Country> adjConArList) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean fortificationPhase(ArrayList<Country> selectedCountry, ArrayList<Country> adjCountry,
			Player currentPlayer, Hmap map, ArrayList<Country> countryArList, ArrayList<Country> adjCountryArList) {
		// TODO Auto-generated method stub
		return false;
	}

}
