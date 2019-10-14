package com.commandparser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.config.Commands;
import com.config.Config;
import com.entity.Hmap;
import com.entity.Player;
import com.exception.InvalidMap;
import com.mapparser.MapReader;
import com.mapparser.MapVerifier;
import com.mapparser.MapWriter;

import static java.lang.System.exit;

/**
 * This class reads, parses the command line string from user input.
 *
 * @author Parth
 * @author Mehul
 */
public class CommandParser {

    Hmap rootMap;
    MapWriter mapWriter;
    ArrayList<Player> playersList = new ArrayList<>();

    // default constructor to initialize members
    public CommandParser() {
        this.mapWriter = new MapWriter();
    }

    /**
     * Setter method for the map object.
     *
     * @param map object
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

    public void placeArmies() {

        Scanner sc = new Scanner(System.in);
        int flag=0;
        while(flag==0){

            for (Player player : playersList) {

                String command = sc.nextLine();
                String words[]=command.split(" ");

                if(words[0].equals(Commands.MAP_COMMAND_PLACE_ARMY)){

                    String countryName=words[1];
                    //Call to function for placing army in respective Country

                }else if(words[0].equals(Commands.MAP_COMMAND_PLACE_ALL)){

                    //Randomly place all countries
                    flag=1;
                    System.out.println("End of Startup phase");
                    break;


                }


            }




        }



    }

    /**
     * Parses the String and calls the relative function.
     *
     * @param command User input Command/String to be parse
     */
    public void processCommands(String command) {

        String[] words = command.split(" ");
        String commandType = words[0], filePath = "";

        switch (commandType) {

            case Commands.MAP_COMMAND_EDIT_CONTINENT:

                for (int idx = 1; idx < words.length; idx++) {

                    if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
                        String continentName = words[idx + 1];
                        String continentValue = words[idx + 2];
                        System.out.println("add:" + words[idx + 1] + " " + words[idx + 2]);
                        // Call for adding continent with (continentName,continentvalue) as parameters
                        idx = idx + 2;

                    } else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
                        String continentName = words[idx + 1];
                        System.out.println("remove:" + words[idx + 1]);
                        // Call for removing the continent name with (continentName) as parameters
                        idx = idx + 1;

                    } else {
                        System.out.println("Check input!!");
                    }
                }
                break;

            case Commands.MAP_COMMAND_EDIT_COUNTRY:

                for (int idx = 1; idx < words.length; idx++) {

                    if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
                        String countryName = words[idx + 1];
                        String continentName = words[idx + 2];
                        System.out.println("Editcountry -add:" + continentName + " " + countryName);
                        // Call for adding the country name with (continentName,countryName) as
                        // parameters
                        idx = idx + 2;

                    } else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
                        String countryName = words[idx + 1];
                        System.out.println("Editcountry -remove:" + countryName);
                        // Call for removing the country name with (countryName) as parameter
                        idx = idx + 1;

                    } else {
                        System.out.println("Wrong input!!");
                        break;
                    }
                }
                break;

            case Commands.MAP_COMMAND_EDIT_NEIGHBOR:

                for (int idx = 1; idx < words.length; idx++) {

                    if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {
                        String countryName = words[idx + 1];
                        String nighborCountryName = words[idx + 2];
                        System.out.println("add: " + words[idx + 1] + " " + words[idx + 2]);
                        // Call for adding continent with (continentName,continentvalue) as parameters
                        idx = idx + 2;

                    } else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {
                        String countryName = words[idx + 1];
                        String nighborCountryName = words[idx + 2];
                        System.out.println("remove: " + words[idx + 1] + " " + words[idx + 2]);
                        // Call for removing the continent name with (continentName) as parameters
                        idx = idx + 2;

                    } else {
                        System.out.println("Check input!!");
                        break;
                    }
                }
                break;

            case Commands.MAP_COMMAND_SHOWMAP:

                System.out.println("Showmap");

                // Call for showmap functionW
                break;

            case Commands.MAP_COMMAND_SAVEMAP:

                filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];
                System.out.println("Save File: " + filePath);
                File outputMapFile = new File(filePath);
                mapWriter.writeMapFile(getMap(), outputMapFile);
                break;

            case Commands.MAP_COMMAND_EDITMAP:

                filePath = words[1];
                System.out.println("Edit Map:" + filePath);
                // Call for editmap(filename_edit)
                break;

            case Commands.MAP_COMMAND_VALIDATEMAP:

                System.out.println("Validatemap");
                try {
                    MapVerifier.verifyMap(getMap());
                } catch (InvalidMap e1) {
                    e1.printStackTrace();
                }
                break;

            case Commands.MAP_COMMAND_LOADMAP:

                filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\" + words[1];
                File inputMapFile = new File(filePath);
                MapReader mapReader = new MapReader();

                if (inputMapFile.exists()) {
                    try {
                        setMap(mapReader.readMapFile(inputMapFile));
                    } catch (InvalidMap e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("File does not exist: " + words[1]);
                }
                break;

            case Commands.MAP_COMMAND_GAMEPLAYER:

                for (int idx = 1; idx < words.length; idx++) {

                    if (words[idx].equals(Commands.MAP_COMMAND_OPTION_ADD)) {

                        String playerName = words[idx + 1];
                        int id = playersList.size();
                        Player newPlayer = new Player(id + 1, playerName);
                        playersList.add(newPlayer);
                        idx = idx + 1;


                    } else if (words[idx].equals(Commands.MAP_COMMAND_OPTION_REMOVE)) {

                        System.out.println("Playerlist Before:--------");
                        for (Player player : playersList) {
                            System.out.println(player.getName());
                        }


                        String playerName = words[idx + 1];
                        for (Player player : playersList) {

                            if (player.getName().equals(playerName)) {

                                playersList.remove(player);
                            }

                        }


                        System.out.println("PlayerList After:---------");
                        for (Player player : playersList) {
                            System.out.println(player.getName());
                        }

                        idx = idx + 1;

                    }else{

                        System.out.println("Wrong Input!!");
                        break;
                    }


                }

                break;
            case Commands.MAP_COMMAND_POPULATE_COUNTRIES:


                if (playersList.size() == 2) {

                    for (Player player : playersList) {

                        player.setArmies(Config.CONFIG_ARMIES_TWO_PLAYER);

                    }


                }
                if (playersList.size() == 3) {

                    for (Player player : playersList) {

                        player.setArmies(Config.CONFIG_ARMIES_THREE_PLAYER);

                    }
                }
                if (playersList.size() == 4) {

                    for (Player player : playersList) {

                        player.setArmies(Config.CONFIG_ARMIES_FOUR_PLAYER);

                    }
                }
                if (playersList.size() == 5) {

                    for (Player player : playersList) {

                        player.setArmies(Config.CONFIG_ARMIES_FIVE_PLAYER);

                    }
                }
                if (playersList.size() == 6) {

                    for (Player player : playersList) {

                        player.setArmies(Config.CONFIG_ARMIES_SIX_PLAYER);

                    }
                }
                System.out.println("Assigned armies to each player");
                placeArmies();

                break;

            default:
                System.out.println("Check the input!!");
                break;
        }
    }
}
