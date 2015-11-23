package edu.cpp.cs.cs141.classproject;

import java.util.Scanner;

public class UserInterface {
	private Game gameEngine;
	private Scanner userinput;

	public UserInterface(Game gameEngine) {
		this.gameEngine = gameEngine;
		userinput = new Scanner(System.in);
	}

	/**
	 * Welcomes the user with {@link #printWelcomeMessage()} Starts the game
	 * prompts the user with {@link #mainMenu()}
	 */
	public void startGame() {
		printWelcomeMessage();

		boolean quit = false;
		while (!quit) {

			switch (mainMenu()) {
			case 1:
				playGame();
				break;
			case 2:
				quit = true;
				System.out.println("GAME QUIT");
				break;
			default:
				System.out.println("Invalid option. Try again...");
				break;
			}
		}
	}

	/**
	 * @return The option chosen whether to start the game or quit.
	 */
	public void menuSelection() {
		int option;
		System.out.println("Select an option:\n" + "\t1. Continue Moving.\n" + "\t2. Shoot.\n" + "\t3. Look.");

		option = userinput.nextInt();
		userinput.nextLine();
		switch(option){
		case 1:
			playerMove();
			break;
		case 2:
			System.out.println("Enter a Direction to SHOOT: 1- UP | 2- DOWN | 3- RIGHT | 4- LEFT");
			int dir = userinput.nextInt();
			gameEngine.shoot(dir);
			break;
		case 3:
			gameEngine.playerLook();
			break;
		}

	}

	/**
	 * Prints the welcome phrase to the user, letting them know what game they
	 * are playing.
	 */
	private void printWelcomeMessage() {
		System.out.println("Welcome to Game");
	}

	/**
	 * Prints the main menu to the user and prompts for their input, and
	 * responds appropriately.
	 * 
	 * @return an integer relating to the input that they chose.
	 */
	private int mainMenu() {
		int option;
		System.out.println("Select an option:\n" + "\t1. Start New Game.\n" + "\t2. Quit.");

		option = userinput.nextInt();
		userinput.nextLine();

		return option;
	}

	/**
	 * Prints the game map and prompts the user for input on what moves he/she
	 * would like to make. It then cycles through the game loop until the game
	 * returns that it is finished.
	 */
	public void playGame() {
		gameEngine.generateMap();
		System.out.println("Hidden \n" + gameEngine.getMap().toString());
		gameEngine.showAll();
		System.out.println("Revealed \n" + gameEngine.getMap().toString());
		while(true){
			menuSelection();
			System.out.println("Player Moved \n" + gameEngine.getMap().toString());
		}
	}

	
	public void playerMove() {
		System.out.println("Enter a Direction: 1- UP | 2- DOWN | 3- RIGHT | 4- LEFT");
		int direction = userinput.nextInt();
		switch (direction) {
		case 1:
			gameEngine.movePlayer(-1, 0);
			break;
		case 2:
			gameEngine.movePlayer(1, 0);
			break;
		case 3:
			gameEngine.movePlayer(0, 1);
			break;
		case 4:
			gameEngine.movePlayer(0, -1);
			break;
		case 42:
			gameEngine.showAll();
			break;
		case 24:
			gameEngine.hideAll();
			break;
		default:
			System.out.println("Invalid option. Try again...");
			break;
		}
	}
}
