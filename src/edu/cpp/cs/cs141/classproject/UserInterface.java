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

			switch (menuSelection()) {
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
	public int menuSelection() {
		int option;
		System.out.println("Select an option:\n" + "\t1. Start New Game.\n" + "\t2. Quit.");

		option = userinput.nextInt();
		userinput.nextLine();

		return option;
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
		return -1;
	}

	/**
	 * Prints the game map and prompts the user for input on what moves he/she
	 * would like to make. It then cycles through the game loop until the game
	 * returns that it is finished.
	 */
	public void playGame() {

	}

}
