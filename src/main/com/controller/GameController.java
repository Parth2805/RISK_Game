package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.Stack;

import com.config.Commands;
import com.entity.Card;
import com.entity.Hmap;
import com.entity.Player;
import com.exception.InvalidMap;
import com.maingame.Main;
import com.mapparser.MapReader;
import com.mapparser.MapVerifier;
import com.mapparser.MapWriter;
import com.models.CardModel;
import com.models.PlayerModel;

import static java.lang.System.exit;

/**
 * This class reads, parses the command line string from user input.
 * @author Parth
 * @author Mehul
 */
public class GameController extends Observable {

	Hmap rootMap;
    MapWriter mapWriter;
    String editFilePath = "";
    boolean isReinfoceArmiesAssigned = false;

    PlayerModel playerModel;
    CardModel cardModel;
    Player currentPlayer;
    public static Stack<Card> stackOfCards;

 // default constructor to initialize members
    public GameController(Main mainView) {
        this.mapWriter = new MapWriter();
        this.playerModel = new PlayerModel();
        this.cardModel = new CardModel();
        this.rootMap = new Hmap();
        this.addObserver(mainView);
        this.stackOfCards = new Stack<Card>();
    }


	/**
	 * Get the current player.
	 * @return player playing
	 */
	public Stack<Card> getCardsStack() {
		return stackOfCards;
	}

	/**
	 * Get the current player.
	 * @return player playing
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * This method is to set the current player.
	 * @param player Current player.
	 */
	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	/**
	 * Setter method for the map object.
	 * @param map object      
	 * @return root map
	 */
	private Hmap setMap(Hmap map) {
		return this.rootMap = map;
	}

	/**
	 * Get map object
	 * @return the map
	 */
	private Hmap getMap() {
		return rootMap;
	}

	/**
	 * Parses the String and calls the related map edit commands.
	 * @param command User input Command/String          
	 */
	public void processMapEditCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0], filePath = "";
		MapReader mapReader;
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();

		switch (commandType) {

			case Commands.MAP_COMMAND_EDIT_CONTINENT:

				for (int idx = 1; idx < words.length; idx++) {

					if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {

						if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return;
						}

						MapContoller.addContinent(getMap(), words[idx + 1], words[idx + 2], "");
						idx = idx + 2;

					} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

						if (words.length < idx + 2) {
							System.out.println("Invalid command, Try again !!!");
							return;
						}
						MapContoller.removeContinent(getMap(), words[idx + 1]);
						idx = idx + 1;

					} else {
						System.out.println("Invalid command, Try again !!!");
					}
				}
			break;

		case Commands.MAP_COMMAND_EDIT_COUNTRY:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {

					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					MapContoller.addCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

					if (words.length < idx + 2) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					MapContoller.removeCountry(getMap(), words[idx + 1]);
					idx = idx + 1;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_EDIT_NEIGHBOR:

			for (int idx = 1; idx < words.length; idx++) {

				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {

					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					MapContoller.addNeighborCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

					if (words.length < idx + 3) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					MapContoller.removeNeighborCountry(getMap(), words[idx + 1], words[idx + 2]);
					idx = idx + 2;

				} else {
					System.out.println("Invalid command, Try again !!!");
				}
			}
			break;

		case Commands.MAP_COMMAND_SHOWMAP:
			MapContoller.mapEditorShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_SAVEMAP:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}

			filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];

			// save map file should be similar to the one which was edited previously
			if (!editFilePath.isEmpty()) {
				if (!editFilePath.equals(filePath)) {
					System.out.println("Please give same filename as you have given in editmap.");
					break;
				}
			}

			try {
				MapVerifier.verifyMap(getMap());
			} catch (InvalidMap e1) {
				System.out.println("Exception: " + e1.toString());
				break;
			}

			System.out.println("Saving File at: " + filePath);
			File outputMapFile = new File(filePath);

			mapWriter.writeMapFile(getMap(), outputMapFile);
			break;

		case Commands.MAP_COMMAND_EDITMAP:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}

			editFilePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];
			File editMapFile = new File(editFilePath);
			mapReader = new MapReader();

			if (editMapFile.exists()) {
				try {
					setMap(mapReader.readMapFile(editMapFile));
				} catch (InvalidMap e) {
					System.out.println("Exception: " + e.toString());
				}
			} else {
				try {
					editMapFile.createNewFile();
					System.out.println("Given map file does not exist. New Map file has been created.");
				} catch (IOException e) {
					System.out.println("Exception: " + e.toString());
				}
			}
			break;

		case Commands.MAP_COMMAND_VALIDATEMAP:

			try {
				MapVerifier.verifyMap(getMap());
			} catch (InvalidMap e1) {
				System.out.println("Exception: " + e1.toString());
			}
			break;

		case Commands.MAP_COMMAND_LOADMAP:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}

			if (null == classloader.getResource(words[1])) {
				System.out.println("Exception: File does not exist: " + words[1]);
				break;
			}

			File inputMapFile = new File(classloader.getResource(words[1]).getFile().replace("%20", " "));
			mapReader = new MapReader();

			try {
				setMap(mapReader.readMapFile(inputMapFile));

				// Update View
				setChanged();
				notifyObservers("loadmap");

			} catch (InvalidMap e) {
				System.out.println("Exception: " + e.toString());
			}
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}
	}

	/**
	 * Parses the String and calls the related player commands.
	 * 
	 * @param command
	 *            User input Command/String
	 */
	public void processGamePlayCreatePlayerCommands(String command) {

		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			MapContoller.mapEditorShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_GAMEPLAYER:

			for (int idx = 1; idx < words.length; idx++) {
				if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {

					if (words.length < idx + 2) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					String playerName = words[idx + 1];
					playerModel.createPlayer(playerName);
					idx = idx + 1;

				} else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

					if (words.length < idx + 2) {
						System.out.println("Invalid command, Try again !!!");
						return;
					}

					String playerName = words[idx + 1];
					playerModel.removePlayer(playerName);
					idx = idx + 1;

				} else {
					System.out.println("Invalid command, Try again !!!");
					break;
				}
			}
			break;

		case Commands.MAP_COMMAND_POPULATE_COUNTRIES:

			// Assign armies according the players count
			if (playerModel.assignArmiesToAllPlayers()) {

				playerModel.populateCountries(getMap());
				playerModel.intitializeArmiesForAllCountries(getMap());

				for (Player p : playerModel.getPlayersList()) {
					int countryCount = p.getAssignedCountry().size();
					System.out.println("Number of Countries for Player : " + p.getName() + " = " + countryCount);
				}

				setCurrentPlayer(playerModel.getPlayersList().get(0));

				// Update View
				setChanged();
				notifyObservers("populatecountries");
			}
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}
	}

	/**
	 * Parses the String and calls the related game play startup commands.
	 * 
	 * @param sc
	 *            scanner object
	 */
	public void processGamePlayStartupCommands(Scanner sc) {

		System.out.println("Current game phase: Gameplay startup phase (placearmy, placeall, showmap)");
		System.out.println("Current Player: " + getCurrentPlayer().getName() + ", number of armies left = "
				+ getCurrentPlayer().getArmies());

		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			playerModel.gamePlayShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_PLACE_ARMY:

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				break;
			}

			if (playerModel.placeArmy(getMap(), getCurrentPlayer(), words[1])) {
				changeCurrentPlayer();
			}

			if (playerModel.isAllPlayersArmiesExhausted()) {
				setCurrentPlayer(playerModel.getPlayersList().get(0));

				// Update View
				setChanged();
				notifyObservers("placeall");
			}
			break;

		case Commands.MAP_COMMAND_PLACE_ALL:
			playerModel.placeAll();
			cardsInitialize();
			setCurrentPlayer(playerModel.getPlayersList().get(0));
			// Update View
			setChanged();
			notifyObservers("placeall");
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}
	}

	/**
	 * Parses the String and calls the related game play reinforcement commands.
	 * 
	 * @param sc
	 *            scanner object
	 */
	public void processGamePlayReinforcementCommands(Scanner sc) {

		if (!isReinfoceArmiesAssigned) {
			playerModel.assignReinforceArmiesToPlayers();
			isReinfoceArmiesAssigned = true;
		}

		System.out.println("Current game phase: Gameplay reinforcement phase (reinforce, showmap, exchangecards)");
		System.out.println("Current Player: " + getCurrentPlayer().getName() + ", Armies left for reinforcement = "
				+ getCurrentPlayer().getArmies());

		cardModel.checkMaxCards(getCurrentPlayer());

		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			playerModel.gamePlayShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_REINFORCE:

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
				return;
			}

			if (numberOfArmies <= 0) {
				System.out.println("Error: You have entered invalid number of armies.");
				return;
			}

			if (!playerModel.isCountryBelongToPlayer(getMap(), getCurrentPlayer(), countryName))
				return;

			if (playerModel.reinforceArmiesForCurrentPlayer(getCurrentPlayer(), countryName, numberOfArmies)) {
				// Update View
				setChanged();
				notifyObservers("reinforcedone");
			}
			break;

		case Commands.MAP_COMMAND_REINFORCE_OPTION_EXCHANGECARDS:

			if (getCurrentPlayer().getCardList().size() < 3) {

				System.out.println(
						"Need more than 3 cards to exchange, only have " + getCurrentPlayer().getCardList().size());
				return;
			}
			if (words.length == 5 && words[4].equalsIgnoreCase("-none")) {
				return;

			} else {

				int idx[] = new int[3];
				idx[0] = Integer.parseInt(words[1]) - 1;
				idx[1] = Integer.parseInt(words[2]) - 1;
				idx[2] = Integer.parseInt(words[3]) - 1;
				List<Card> cardschoosen = new ArrayList<>();

				List<Card> cardlist = getCurrentPlayer().getCardList();

				for (int index : idx) {
					cardschoosen.add(cardlist.get(index));
				}

//				int ans = 0;
				int ans = cardModel.areCardsvalidForExchange(cardschoosen);
				if (ans == 1) {
					//cardModel.exchangeCards(idx, cardschoosen);

				} else {

					System.out.println("Only exchange 1.Cards of all same type or 2.Cards of all different type");
				}

			}
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}
	}

	/**
	 * Parses the String and calls the related game play fortify commands.
	 * 
	 * @return true if command is processed correctly, false otherwise
	 */
	public void cardsInitialize() {
		cardModel.allocateCardsToCountry(getMap(), getCardsStack());
	}

	/**
	 * Parses the String and calls the related game play fortify commands.
	 * 
	 * @param sc
	 *            scanner object
	 * @return true if command is processed correctly, false otherwise
	 */
	public void processGamePlayAttackCommands(Scanner sc) {

		System.out.println("Current phase: Gameplay Attack phase (attack, defend, attackmove, showmap)");
		System.out.println("Current Player: " + getCurrentPlayer().getName());

		String command = sc.nextLine();
		String words[] = command.split(" ");

		switch (words[0]) {

		case Commands.MAP_COMMMAND_ATTACK:

			// Player may decide to attack or not to attack again. If attack not possible, attack automatically ends. 1

			if (words.length < 2) {
				System.out.println("Invalid command, Try again !!!");
				return;
			}
			
			for (String w: words) {
				if (w.equalsIgnoreCase(Commands.MAP_COMMAND_ATTACK_OPTION_NOATTACK)) {
					System.out.println(getCurrentPlayer() + " has chosen not to attack");
					// Going to next phase - Update View
					setChanged();
					notifyObservers("attackdone");
					return;
				}
			}

			if (words.length < 4) {
				System.out.println("Invalid command, Try again !!!");
				return;
			}
			
			if (words.length >= 5) {
				
				// Attack with allout mode
				if (words[4].equalsIgnoreCase(Commands.MAP_COMMAND_ATTACK_OPTION_ALLOUT)) {


					int numOfDice = 0;
					String attackingCountry = words[1];
					String defendingCountry = words[2];
					try {
						numOfDice = Integer.parseInt(words[3]);
					} catch (Exception e) {
						System.out.println("Exception: " + e.toString());
						return;
					}

					playerModel.alloutattackCountry(getMap(), getCurrentPlayer(), attackingCountry, defendingCountry, numOfDice);
					if(playerModel.winGame(getCurrentPlayer(),rootMap.getCountries())){
						System.out.println("Player:"+getCurrentPlayer().getName()+" won the game!!!!!!!");
						//exit(0);
						setChanged();
						notifyObservers("gameover");
					}else{
					// Going to next phase - Update View
					setChanged();
					notifyObservers("attackdone");
					}
					break;
				}
			} else {

				int numOfDice = 0;
				String attackingCountry = words[1];
				String defendingCountry = words[2];
				
				try {
					numOfDice = Integer.parseInt(words[3]);
				} catch (Exception e) {
					System.out.println("Exception: " + e.toString());
					return;
				}

				playerModel.attackCountry(getMap(), getCurrentPlayer(), attackingCountry, defendingCountry, numOfDice);
			}
			break;

		case Commands.MAP_COMMAND_SHOWMAP:
			playerModel.gamePlayShowmap(getMap());
			break;

		default:
			System.out.println("Invalid Input");
			break;

		}
	}

	/**
	 * Parses the String and calls the related game play fortify commands.
	 * 
	 * @param sc
	 *            scanner object
	 * @return true if command is processed correctly, false otherwise
	 */
	public void processGamePlayFortifyCommands(Scanner sc) {
		
		System.out.println("Current game phase: Gameplay fortify phase (fortify, showmap)");
		System.out.println("Current Player: " + getCurrentPlayer().getName());

		boolean isForifyDone = false;
		String command = sc.nextLine();
		String[] words = command.split(" ");
		String commandType = words[0];

		switch (commandType) {

		case Commands.MAP_COMMAND_SHOWMAP:
			playerModel.gamePlayShowmap(getMap());
			break;

		case Commands.MAP_COMMAND_FORTIFY:

			if (words.length < 2) {
				System.out.println("Invalid command length. Try again !!!");
				return;
			}

			if (words[1].equalsIgnoreCase(Commands.MAP_COMMAND_FORTIFY_OPTION_NONE)) {
				System.out.println(getCurrentPlayer() + " has chosen to skip fortify.");
				// Update View
				setChanged();
				notifyObservers("fortifydone");
				isForifyDone = true;
				
			} else {

				if (words.length < 4) {
					System.out.println("Invalid command length. Try again !!!");
					return;
				}

				int numArmies = 0;

				try {
					numArmies = Integer.parseInt(words[3]);
				} catch (Exception e) {
					System.out.println("Exception: " + e.toString());
					return;
				}

				if (numArmies <= 0) {
					System.out.println("Exception: Invalid number of armies");
					return;
				}

				if (playerModel.fortifyCurrentPlayer(getMap(), getCurrentPlayer(), words[1], words[2], numArmies))
					isForifyDone = true;
			}

			if (isForifyDone) {
				
				// check all players have played
				if (playerModel.isLastPlayer(getCurrentPlayer())) {
					isReinfoceArmiesAssigned = false;
					System.out.println("***** All players have played. Going back to reinforcement again *****");
					
					// Update View
					setChanged();
					notifyObservers("fortifydone");
				}
				
				changeCurrentPlayer();
			}
			break;

		default:
			System.out.println("Invalid command, Try again !!!");
			break;
		}
	}

	/**
	 * This will change the current player
	 */
	public void changeCurrentPlayer() {
		int currentPlayerIdx = playerModel.getPlayersList().indexOf(getCurrentPlayer());
		int totalPlayers = playerModel.getPlayersList().size();
		setCurrentPlayer(playerModel.getPlayersList().get((currentPlayerIdx + 1) % totalPlayers));
	}
}