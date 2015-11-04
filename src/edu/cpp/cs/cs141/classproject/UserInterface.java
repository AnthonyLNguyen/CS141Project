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
	 * Welcomes the user with {@link #printWelcomeMessage()}
	 * Starts the game prompts the user with {@link #mainMenu()}
	 */
	public void startGame() {

	}

	private void printWelcomeMessage() {

	}

	private int mainMenu() {
		return -1;
	}

	/**
	 * Prints our map. Loops the game.
	 */
	public void playGame() {

	}

}
