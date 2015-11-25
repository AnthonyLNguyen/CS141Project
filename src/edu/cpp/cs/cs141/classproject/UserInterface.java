package edu.cpp.cs.cs141.classproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class UserInterface {
	private Game gameEngine;
	private GameSave gs;
	private Scanner userinput;
	private boolean gameLoaded = false;

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
				endGame();
				break;
			case 2:
				help();
				break;
			case 3:
				quit = true;
				System.out.println("GAME QUIT");
				endGame();
				break;
			case 4: 
				gameLoaded = true;
				System.out.println("What is the name of the save you would like to load? (Don't include extention)");
				String saveName = userinput.next();
				gs = new GameSave();
				gameEngine = gs.loadGame(saveName);
				playGame();
				endGame();
				break;
			default:
				System.out.println("Invalid option. Try again...");
				break;
			}
		}
	}

	public void endGame(){
		System.out.println("THANKS FOR PLAYING");
		userinput.close();
		System.exit(0);
	}
	public void help() {
		System.out.println("=======SYMBOLS======\n"
				+ "[ ]: Light\n"
				+ "[*]: Darkness\n"
				+ "[N]: Enemy\n"
				+ "[=]: Unopened Room\n"
				+ "[X]: Dud Room\n"
				+ "[$]: Briefcase Room\n"
				+ "[B]: Extra Bullet Power-Up\n"
				+ "[I]: Invincibility Power-Up\n"
				+ "[R]: Reveal Briefcase Power-Up\n");
	}

	/**
	 * @return The option chosen whether to start the game or quit.
	 */
	public void menuSelection() {
		int option;
		System.out.println("Select an option:\n\t1. Continue Moving.\n\t2. Shoot.\n\t3. Look.\n\t4. Save Game.\n\t5. Quit Game.");

		option = userinput.nextInt();
		userinput.nextLine();
		switch(option){
		case 1:
			playerMove();
			break;
		case 2:
			System.out.println("Enter a Direction to SHOOT: 1- UP | 2- DOWN | 3- RIGHT | 4- LEFT");
			gameEngine.playerShoot(userinput.nextInt());
			break;
		case 3:
			System.out.println("Enter a Direction to LOOK: 1- UP | 2- DOWN | 3- RIGHT | 4- LEFT");
			gameEngine.playerLook(userinput.nextInt());
			break;
		case 4:
			System.out.println("Enter a name for the save file");
			String saveName = userinput.next();
			gs = new GameSave(gameEngine);
			gs.saveGame(saveName);
			System.out.println("Game has been saved! \nThe save state is called " + saveName);
		case 5:
			endGame();
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
		System.out.println("Select an option:\n\t1. Start New Game.\n\t2. Help.\n\t3. Quit.\n\t4. Load Game.");

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
		if (!gameLoaded)
			gameEngine.generateMap();
		gameEngine.vision();
		while(!gameEngine.isFinished()){
			System.out.println( "\n"+ gameEngine.getMap().toString());
			gameEngine.stats();
			playerMove();
		}
	}


	
	/**
	 * Prompts the user for a command
	 */
	public void playerMove() {
		System.out.println("Enter a Command: 1- MOVE UP | 2- MOVE DOWN | 3- MOVE RIGHT | 4- MOVE LEFT | 5- MENU | 6- HELP");
		int direction = userinput.nextInt();
		switch (direction) {
		case 1:
			gameEngine.takeTurn(-1, 0);
			break;
		case 2:
			gameEngine.takeTurn(1, 0);
			break;
		case 3:
			gameEngine.takeTurn(0, 1);
			break;
		case 4:
			gameEngine.takeTurn(0, -1);
			break;
		case 5:
			menuSelection();
			break;
		case 6:
			help();
			break;
		case 42:
			gameEngine.showAll();
			gameEngine.setDebugMode(true);
			break;
		case 24:
			gameEngine.hideAll();
			gameEngine.setDebugMode(false);
			gameEngine.vision();
			break;
		default:
			System.out.println("Invalid option. Try again...");
			break;
		}
	}
}
