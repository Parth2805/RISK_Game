package com.maingame;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import com.config.GameState;
import com.controller.GameController;


public class Main implements Observer {

	static GameState gamePhase;
	GameController gameController = new GameController(this);

	/**
	 * Get game phase
	 * @return gameState
	 */
	public static GameState getGamePhase() {
		return gamePhase;
	}

	/**
	 * Setter method for the game state.
	 * @param gamePhase current phase
	 */
	public static void setGamePhase(GameState gameState) {
		gamePhase = gameState;
	}
	
	public static void main(String[] args) {
		
		Main mainView = new Main();
		setGamePhase(GameState.RISK_STATE_MAP_EDITING);
		
		Scanner sc = new Scanner(System.in);
		System.out.println("---------- Welcome to Risk game ----------");
		String command;

		while (true) {

			switch (getGamePhase()) {

			case RISK_STATE_MAP_EDITING:
				
				System.out.println("Current game phase: Map editor (editcontinent, "
						+ "editcountry, editneighbor, showmap, savemap, editmap, validatemap, loadmap)");
				System.out.println("When map editing gets finised, Use \"loadmap filename\" command to start the game");
				System.out.println("Please enter any commands to continue ...");
				
				command = sc.nextLine();
				mainView.gameController.processMapEditCommands(command);
				break;

			case RISK_STATE_GAMEPLAY_CREATE_PLAYERS:
				
				System.out.println("Current game phase: Gameplay create players (gameplayer -add playername -remove playername, populatecountries, showmap)");
				System.out.println("Use \"populatecountries\" command to allocate initial armies to players");
				System.out.println("Please enter any commands to continue ...");
				
				command = sc.nextLine();
				mainView.gameController.processGamePlayCreatePlayerCommands(command);
				break;
				
			case RISK_STATE_GAMEPLAY_STARTUP_PHASE:
				mainView.gameController.processGamePlayStartupCommands(sc);
				break;
				
			case RISK_STATE_GAMEPLAY_REINFORCEMENT_PHASE:
				mainView.gameController.processGamePlayReinforcementCommands(sc);
				break;
				
			case RISK_STATE_GAMEPLAY_ATTACK_PHASE:
				mainView.gameController.processGamePlayAttackCommands(sc);
				break;
				
			case RISK_STATE_GAMEPLAY_FORTIFICATION_PHASE:
				mainView.gameController.processGamePlayFortifyCommands(sc);
				break;

			default:
				break;
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {		
		String methodValue = (String) arg;

		if (methodValue.equals("loadmap")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_CREATE_PLAYERS);
			System.out.println("----------------------------------");
		}
		else if (methodValue.equals("populatecountries")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_STARTUP_PHASE);
			System.out.println("----------------------------------");
		}
		else if (methodValue.equals("placeall")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_REINFORCEMENT_PHASE);
			System.out.println("----------------------------------");
		}
		else if (methodValue.equals("reinforcedone")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_ATTACK_PHASE);
			System.out.println("----------------------------------");
		}
		else if (methodValue.equals("attackdone")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_FORTIFICATION_PHASE);
			System.out.println("----------------------------------");
		}
		else if (methodValue.equals("fortifydone")) {
			setGamePhase(GameState.RISK_STATE_GAMEPLAY_REINFORCEMENT_PHASE);
			System.out.println("----------------------------------");
		}else if (methodValue.equals("gameover")) {
			setGamePhase(GameState.RISK_STATE_MAP_EDITING);
			System.out.println("----------------------------------");
		}
	}
}
