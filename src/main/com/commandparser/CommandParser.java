package com.commandparser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.config.Commands;
import com.entity.Card;
import com.entity.Country;
import com.entity.Hmap;
import com.entity.Player;
import com.exception.InvalidMap;
import com.mapparser.MapCommands;
import com.mapparser.MapReader;
import com.mapparser.MapVerifier;
import com.mapparser.MapWriter;
import com.model.PlayerModel;


/**
 * This class reads, parses the command line string from user input.
 *
 * @author Parth
 * @author Mehul
 */
public class CommandParser {

    Hmap rootMap;
    MapWriter mapWriter;
    PlayerModel playerModel;

    String editFilePath = "";
    boolean isReinfoceArmiesAssigned = false;

    // default constructor to initialize members
    public CommandParser() {
        this.mapWriter = new MapWriter();
        this.playerModel = new PlayerModel();
        this.rootMap = new Hmap();
    }

    /**
     * Setter method for the map object.
     *
     * @param map object
     * @return root map
     */
    private Hmap setMap(Hmap map) {
        return this.rootMap = map;
    }

    /**
     * Get map object
     *
     * @return the map
     */
    private Hmap getMap() {
        return rootMap;
    }

    /**
     * Parses the String and calls the related map edit commands.
     *
     * @param command User input Command/String
     * @return true if command is processed correctly, false otherwise
     */
    public boolean processMapEditCommands(String command) {

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
                            return false;
                        }

                        MapCommands.addContinent(getMap(), words[idx + 1], words[idx + 2], "");
                        idx = idx + 2;

                    } else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

                        if (words.length < idx + 2) {
                            System.out.println("Invalid command, Try again !!!");
                            return false;
                        }
                        MapCommands.removeContinent(getMap(), words[idx + 1]);
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
                            return false;
                        }

                        MapCommands.addCountry(getMap(), words[idx + 1], words[idx + 2]);
                        idx = idx + 2;

                    } else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

                        if (words.length < idx + 2) {
                            System.out.println("Invalid command, Try again !!!");
                            return false;
                        }

                        MapCommands.removeCountry(getMap(), words[idx + 1]);
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
                            return false;
                        }

                        MapCommands.addNeighborCountry(getMap(), words[idx + 1], words[idx + 2]);
                        idx = idx + 2;

                    } else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

                        if (words.length < idx + 3) {
                            System.out.println("Invalid command, Try again !!!");
                            return false;
                        }

                        MapCommands.removeNeighborCountry(getMap(), words[idx + 1], words[idx + 2]);
                        idx = idx + 2;

                    } else {
                        System.out.println("Invalid command, Try again !!!");
                    }
                }
                break;

            case Commands.MAP_COMMAND_SHOWMAP:
                MapCommands.mapEditorShowmap(getMap());
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
                    return true;
                } catch (InvalidMap e) {
                    System.out.println("Exception: " + e.toString());
                }
                break;

            default:
                System.out.println("Invalid command, Try again !!!");
                break;
        }
        return false;
    }

    /**
     * Parses the String and calls the related player commands.
     *
     * @param command User input Command/String
     * @return true if command is processed correctly, false otherwise
     */
    public boolean processGamePlayCreatePlayerCommands(String command) {

        String[] words = command.split(" ");
        String commandType = words[0];

        switch (commandType) {

            case Commands.MAP_COMMAND_SHOWMAP:
                MapCommands.mapEditorShowmap(getMap());
                break;

            case Commands.MAP_COMMAND_GAMEPLAYER:

                for (int idx = 1; idx < words.length; idx++) {
                    if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {

                        if (words.length < idx + 2) {
                            System.out.println("Invalid command, Try again !!!");
                            return false;
                        }

                        String playerName = words[idx + 1];
                        playerModel.createPlayer(playerName);
                        idx = idx + 1;

                    } else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

                        if (words.length < idx + 2) {
                            System.out.println("Invalid command, Try again !!!");
                            return false;
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

                playerModel.setCountryList(playerModel.getCountryListFromMap(getMap()));

                if (playerModel.assignArmiesToPlayers()) {

                    playerModel.populateCountries(getMap());
                    playerModel.intitializeArmiesForAllCountries();

                    for (Player p : playerModel.getPlayersList()) {
                        int countryCount = p.getAssignedCountry().size();
                        System.out.println("Number of Countries for Player : " + p.getName() + " = " + countryCount);
                    }

                    playerModel.setCurrentPlayer(playerModel.getPlayersList().get(0));
                    playerModel.setCountryMap(playerModel.getCountryMapFromList(playerModel.getCountryList()));
                    return true;
                }
                break;

            default:
                System.out.println("Invalid command, Try again !!!");
                break;
        }

        return false;
    }

    /**
     * Parses the String and calls the related game play startup commands.
     *
     * @param sc scanner object
     * @return true if command is processed correctly, false otherwise
     */
    public boolean processGamePlayStartupCommands(Scanner sc) {


        System.out.println("Current state: Gameplay startup phase (placearmy, placeall, showmap)");
        System.out.println("Current Player: " + playerModel.getCurrentPlayer().getName() +
                ", number of armies left = " + playerModel.getCurrentPlayer().getArmies());

        String command = sc.nextLine();
        String[] words = command.split(" ");
        String commandType = words[0];

        switch (commandType) {

            case Commands.MAP_COMMAND_SHOWMAP:
                playerModel.gamePlayShowmap();
                break;

            case Commands.MAP_COMMAND_PLACE_ARMY:

                if (words.length < 2) {
                    System.out.println("Invalid command, Try again !!!");
                    break;
                }

                if (playerModel.placeArmy(words[1])) {
                    playerModel.changeCurrentPlayer();
                }

                if (playerModel.isAllPlayersArmiesExhausted()) {
                    playerModel.setCurrentPlayer(playerModel.getPlayersList().get(0));
                    return true;
                }
                break;

            case Commands.MAP_COMMAND_PLACE_ALL:
                playerModel.placeAll();
                playerModel.setCurrentPlayer(playerModel.getPlayersList().get(0));
                return true;

            default:
                System.out.println("Invalid command, Try again !!!");
                break;
        }

        return false;
    }

    /**
     * Parses the String and calls the related game play reinforcement commands.
     *
     * @param sc scanner object
     * @return true if command is processed correctly, false otherwise
     */
    public boolean processGamePlayReinforcementCommands(Scanner sc) {

        if (!isReinfoceArmiesAssigned) {
            playerModel.assignReinforceArmiesToPlayers();
            isReinfoceArmiesAssigned = true;
        }

        System.out.println("Current state: Gameplay reinforcement phase (reinforce, showmap,exchangecards)");
        System.out.println("Current Player: " + playerModel.getCurrentPlayer().getName()
                + ", Armies left for reinforcement = " + playerModel.getCurrentPlayer().getArmies());


        //Check if player has 5 cards and tells to exchange until less than 5 cards
        if (playerModel.getCurrentPlayer().getCardList().size() == 5) {

            System.out.println("You have 5(max) cards, need to exchange!!");

            System.out.println("Cards List:");
            int i = 1;
            for (Card cards : playerModel.getCurrentPlayer().getCardList()) {

                System.out.println(i + "." + cards);
                i++;
            }
            System.out.println("Countries owned");
            for (Country c : playerModel.getCurrentPlayer().getAssignedCountry()) {

                System.out.println(c.getName());
            }

            Scanner sc1 = new Scanner(System.in);

            while (playerModel.getCurrentPlayer().getCardList().size() == 5) {
                System.out.println("Enter the cards(index) to exchange:");
                String input = sc1.nextLine();
                String tempwords[] = input.split(" ");
                if (tempwords[0].equalsIgnoreCase(Commands.MAP_COMMAND_REINFORCE_OPTION_EXCHANGECARDS)) {

                    if (tempwords.length == 5 && tempwords[4].equalsIgnoreCase("-none")) {

                        System.out.println("Need to Exchange Cards!!");

                    } else {

                        if(playerModel.getCurrentPlayer().getCardList().size()<3){
                            System.out.println("Have less than 3 cards, cant exchange");
                            return false;
                        }
                        int idx[] = new int[3];
                        idx[0] = Integer.parseInt(tempwords[1]) - 1;
                        idx[1] = Integer.parseInt(tempwords[2]) - 1;
                        idx[2] = Integer.parseInt(tempwords[3]) - 1;
                        List<Card> cardschoosen = new ArrayList<>();

                        List<Card> cardlist = playerModel.getCurrentPlayer().getCardList();

                        for (int index : idx) {

                            cardschoosen.add(cardlist.get(index));

                        }

                        int ans = playerModel.areCardsvalidForExchange(cardschoosen);
                        if (ans == 1) {
                            playerModel.exchangeCards(idx, cardschoosen);

                        } else {

                            System.out.println("Only exchange 1.Cards of all same type or 2.Cards of all different type");
                        }

                    }
                } else {

                    System.out.println("Invalid Input Command!!");
                }
            }
            System.out.println("Current state: Gameplay reinforcement phase (reinforce, showmap,exchangecards)");
            System.out.println("Current Player: " + playerModel.getCurrentPlayer().getName()
                    + ", Armies left for reinforcement = " + playerModel.getCurrentPlayer().getArmies());
        }

        String command = sc.nextLine();
        String[] words = command.split(" ");
        String commandType = words[0];

        switch (commandType) {

            case Commands.MAP_COMMAND_SHOWMAP:
                playerModel.gamePlayShowmap();
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
                    return false;
                }

                if (numberOfArmies <= 0) {
                    System.out.println("You have entered invalid number of armies.");
                    return false;
                }

                if (!playerModel.isCountryBelongToPlayer(playerModel.getCurrentPlayer(), countryName))
                    return false;

                if (playerModel.reinforceArmiesForCurrentPlayer(countryName, numberOfArmies))
                    return true;
                break;
            case Commands.MAP_COMMAND_REINFORCE_OPTION_EXCHANGECARDS:

                if(playerModel.getCurrentPlayer().getCardList().size()<3){

                    System.out.println("Need more than 3 cards to exchange, only have "+playerModel.getCurrentPlayer().getCardList().size());
                    return false;
                }
                if (words.length == 5 && words[4].equalsIgnoreCase("-none")) {

                    return false;

                } else {


                    int idx[] = new int[3];
                    idx[0] = Integer.parseInt(words[1]) - 1;
                    idx[1] = Integer.parseInt(words[2]) - 1;
                    idx[2] = Integer.parseInt(words[3]) - 1;
                    List<Card> cardschoosen = new ArrayList<>();

                    List<Card> cardlist = playerModel.getCurrentPlayer().getCardList();

                    for (int index : idx) {

                        cardschoosen.add(cardlist.get(index));

                    }

                    int ans = playerModel.areCardsvalidForExchange(cardschoosen);
                    if (ans == 1) {
                        playerModel.exchangeCards(idx, cardschoosen);

                    } else {

                        System.out.println("Only exchange 1.Cards of all same type or 2.Cards of all different type");
                    }

                }

                break;
            default:
                System.out.println("Invalid command, Try again !!!");
                break;
        }

        return false;
    }

    public void cardsInitialize() {

        playerModel.allocateCardsToCountry();


    }

	public boolean processGamePlayAttackCommands(Scanner sc){

        String command = sc.nextLine();
        String words[]=command.split(" ");
        System.out.println("Current state: Gameplay Attack phase (attack,defend,attackmove, showmap)");
        System.out.println("Current Player: " + playerModel.getCurrentPlayer().getName());

        switch(words[0]){

            case Commands.MAP_COMMMAND_ATTACK:

                if(words.length==5){

                    if(words[4].equalsIgnoreCase(Commands.MAP_COMMAND_ATTACK_OPTION_ALLOUT)){






                    }else if(words[4].equalsIgnoreCase(Commands.MAP_COMMAND_ATTACK_OPTION_NOATTACK)){

                        //Going to next phase
                        return true;



                    }else{

                        System.out.println("Invalid Input!!");
                        return false;
                    }


                }else{

                    String attackingCountry=words[1];
                    String defendingCountry=words[2];
                    int numofdice= Integer.parseInt(words[3]);

                    playerModel.attackphase(attackingCountry,defendingCountry,numofdice);




                }

                break;

            case Commands.MAP_COMMAND_SHOWMAP:
                playerModel.gamePlayShowmap();
                break;

            default:

                System.out.println("Invalid Input");
                break;

        }
        return false;
	}


    /**
     * Parses the String and calls the related game play fortify commands.
     *
     * @param sc scanner object
     * @return true if command is processed correctly, false otherwise
     */
    public boolean processGamePlayFortifyCommands(Scanner sc) {
        System.out.println("Current state: Gameplay fortify phase (fortify, showmap)");
        System.out.println("Current Player: " + playerModel.getCurrentPlayer().getName());

        boolean isForifyDone = false;
        String command = sc.nextLine();
        String[] words = command.split(" ");
        String commandType = words[0];

        switch (commandType) {

            case Commands.MAP_COMMAND_SHOWMAP:
                playerModel.gamePlayShowmap();
                break;

            case Commands.MAP_COMMAND_FORTIFY:

                if (words.length < 2) {
                    System.out.println("Invalid command length. Try again !!!");
                    return false;
                }

                if (words[1].equalsIgnoreCase(Commands.MAP_COMMAND_FORTIFY_OPTION_NONE)) {
                    System.out.println("You have chosen to skip fortify.");
                    isForifyDone = true;
                } else {

                    if (words.length < 4) {
                        System.out.println("Invalid command length. Try again !!!");
                        return false;
                    }

                    int numArmies = 0;

                    try {
                        numArmies = Integer.parseInt(words[3]);
                    } catch (Exception e) {
                        System.out.println("Exception: " + e.toString());
                        return false;
                    }

                    if (numArmies <= 0) {
                        System.out.println("Exception: Invalid number of armies");
                        return false;
                    }

                    if (playerModel.fortifyCurrentPlayer(words[1], words[2], numArmies))
                        isForifyDone = true;
                }

                if (isForifyDone) {
                    // check all players have played
                    if (playerModel.isLastPlayer(playerModel.getCurrentPlayer())) {
                        isReinfoceArmiesAssigned = false;
                        System.out.println("***** All players have played. Going back to reinforcement again *****");
                    }
                    playerModel.changeCurrentPlayer();
                }
                break;

            default:
                System.out.println("Invalid command, Try again !!!");
                break;
        }

        return isForifyDone;
    }



}
