package com.maingame;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import com.config.Commands;
import com.controller.GameController;
import com.entity.Card;

/**
 * This class controls the behavior of the Card Exchange View. It will display
 * all the cards owned by the current player, then allow the player to select
 * some cards to exchange.
 *
 * @author Komal
 */
public class CardExchangeView implements Observer {

	/**
	 * This method Update Observable and Argument
	 * 
	 * @param o
	 *            Observable object
	 * @param arg
	 *            an object of Object
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {

		String methodValue = (String) arg;
		GameController gameController = (GameController) o;
		Scanner sc = new Scanner(System.in);
		int cardSize = gameController.getCurrentPlayer().getCardList().size();

		// If the player selects cards, they are given the appropriate number of armies
		// as reinforcement. The player can choose
		// not to exchange cards and exit the card exchange view. If the player own 5
		// cards or more, they must exchange cards. The cards
		// exchange view should cease to exist after the cards exchange.
		if (methodValue.equals("card-exchange")) {

			System.out.println("-------- Card Exchange View ----------");
			System.out.println("Current game phase: Gameplay reinforcement (exchangecards)");
			System.out.println(
					gameController.getCurrentPlayer() + " has " + gameController.getCurrentPlayer().getCardList());

			if (cardSize >= 3) {

				if (gameController.getCardModel().checkMaxCards(gameController.getCurrentPlayer(),
						gameController.getCardsStack()))
					return;

				while (true) {

					String command = sc.nextLine();
					String[] words = command.split(" ");
					String commandType = words[0];

					switch (commandType) {

					case Commands.MAP_COMMAND_REINFORCE_OPTION_EXCHANGECARDS:

						if (words[1].equalsIgnoreCase("-none")) {
							System.out.println(gameController.getCurrentPlayer() + " has chosen not to exchange cards");
							return;
						}

						if (words.length < 4) {
							System.out.println("Invalid command, Try again !!!");
							break;
						}

						int idx[] = new int[3];

						try {
							idx[0] = Integer.parseInt(words[1]) - 1;
							idx[1] = Integer.parseInt(words[2]) - 1;
							idx[2] = Integer.parseInt(words[3]) - 1;
						} catch (Exception e) {
							System.out.println("Exception: " + e.toString());
							break;
						}

						List<Card> cardsChoosen = new ArrayList<>();
						List<Card> cardList = gameController.getCurrentPlayer().getCardList();

						for (int index : idx) {
							cardsChoosen.add(cardList.get(index));
						}

						Boolean retVal = gameController.getCardModel().areCardsvalidForExchange(cardsChoosen);

						if (retVal) {
							gameController.getCardModel().exchangeCards(gameController.getCurrentPlayer(), idx,
									cardsChoosen, gameController.getCardsStack());
							return;
						} else
							System.out.println(
									"You can only exchange when \n1.Cards of all same type or \n2.Cards of all different type");
						break;

					default:
						System.out.println("Invalid command, Try again !!!");
						break;
					}
				}
			}
		}
	}
}
