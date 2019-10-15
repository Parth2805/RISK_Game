package com.main;

import java.util.Scanner;

import com.commandparser.CommandParser;
import com.config.GameState;

public class Main {

	static GameState gameState;

	/**
	 * Get game phase
	 *
	 * @return game state
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Setter method for the game state.
	 *
	 * @param gamePhase current phase
	 */
	public static void setGameState(GameState gamePhase) {
		gameState = gamePhase;
	}
	
	public static void main(String[] args) {
		CommandParser parser = new CommandParser();
		gameState = GameState.RISK_STATE_MAP_EDITING;
		Scanner sc = new Scanner(System.in);
		System.out.println("---------- Welcome to Risk game ----------");
		String command;

		while (true) {

			switch (gameState) {

			case RISK_STATE_MAP_EDITING:
				System.out.println("Current state: Map editor (editcontinent, "
						+ "editcountry, editneighbor, showmap, savemap, editmap, validatemap, loadmap)");
				System.out.println("When map editing gets finised, Use \"loadmap filename\" command to start the game");
				System.out.println("Please enter any commands to continue ...");
				
				command = sc.nextLine();
				if (parser.processMapEditCommands(command)) {
					setGameState(GameState.RISK_STATE_GAMEPLAY_CREATE_PLAYERS);
					System.out.println("----------------------------------");
				}
				break;

			case RISK_STATE_GAMEPLAY_CREATE_PLAYERS:
				System.out.println("Current state: Gameplay create players (gameplayer -add playername -remove playername, populatecountries)");
				System.out.println("Use \"populatecountries\" command to allocate initial armies to players");
				System.out.println("Please enter any commands to continue ...");
				command = sc.nextLine();
				if (parser.processGamePlayCommands(command)) {
					setGameState(GameState.RISK_STATE_GAMEPLAY_STARTUP_PHASE);
					System.out.println("----------------------------------");
				}
				break;
				
			case RISK_STATE_GAMEPLAY_STARTUP_PHASE:
				if (parser.processGamePlayStartupCommands(sc)) {
					setGameState(GameState.RISK_STATE_GAMEPLAY_REINFORCEMENT_PHASE);
					System.out.println("----------------------------------");
				}
				break;
				
			case RISK_STATE_GAMEPLAY_REINFORCEMENT_PHASE:
				System.out.println("Current state: Gameplay reinforcement phase (reinforce, fortify)");
				command = sc.nextLine();
				parser.processGamePlayReinforcementCommands(command);
				break;

			default:
				break;
			}
		}
	}
}
