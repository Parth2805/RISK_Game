package com.main;

import java.util.Scanner;

import com.config.GameState;
import com.controller.GameController;

public class Main {

	static GameState gamePhase;

	/**
	 * Get game phase
	 * @return gameState
	 */
	public GameState getGameState() {
		return gamePhase;
	}

	/**
	 * Setter method for the game state.
	 * @param gamePhase current phase
	 */
	public static void setGameState(GameState gameState) {
		gamePhase = gameState;
	}
	
	public static void main(String[] args) {
		GameController parser = new GameController();
		gamePhase = GameState.RISK_STATE_MAP_EDITING;
		Scanner sc = new Scanner(System.in);
		System.out.println("---------- Welcome to Risk game ----------");
		String command;

		while (true) {

			switch (gamePhase) {

			case RISK_STATE_MAP_EDITING:
				
				System.out.println("Current game phase: Map editor (editcontinent, "
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
				
				System.out.println("Current game phase: Gameplay create players (gameplayer -add playername -remove playername, populatecountries, showmap)");
				System.out.println("Use \"populatecountries\" command to allocate initial armies to players");
				System.out.println("Please enter any commands to continue ...");
				
				command = sc.nextLine();
				
				if (parser.processGamePlayCreatePlayerCommands(command)) {
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
				if (parser.processGamePlayReinforcementCommands(sc)) {
					setGameState(GameState.RISK_STATE_GAMEPLAY_FORTIFICATION_PHASE);
					System.out.println("----------------------------------");
				}
				break;
				
			case RISK_STATE_GAMEPLAY_FORTIFICATION_PHASE:
				if (parser.processGamePlayFortifyCommands(sc)) {
					setGameState(GameState.RISK_STATE_GAMEPLAY_REINFORCEMENT_PHASE);
					System.out.println("----------------------------------");
				}

			default:
				break;
			}
		}
	}
}
