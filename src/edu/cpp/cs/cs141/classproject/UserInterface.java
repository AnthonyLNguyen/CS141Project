package edu.cpp.cs.cs141.classproject;

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
	
	public int chooseMode(){
		System.out.println("Please choose an option: \n"
				+ "\t[1]Text Based"
				+ "\n\t[2]GUI Based");
		return userinput.nextInt();
	}

	/**
	 * Welcomes the user with {@link #printWelcomeMessage()} Starts the game
	 * prompts the user with {@link #mainMenu()}
	 */
	public void launch() {
		int choice = 1;
		if(gameEngine.getAmountNinjas() == 6){
			printWelcomeMessage();
			choice = mainMenu();
		}
		boolean quit = false;
		while (!quit) {

			switch (choice) {
			case 1:
				quit = true;
				playGame();
				break;
			case 2:
				quit = true;
				help();
				userinput.nextLine();
				launch();
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
				quit = true;
				playGame();
				break;
			default:
				System.out.println("Invalid option. Try again...");
				break;
			}
		}
	}

	/**
	 * Ends the game and exits
	 */
	public void endGame(){
		if (gameEngine.getLoss())
			System.out.println("GAME OVER");
		System.out.println("THANKS FOR PLAYING");
		userinput.close();
		System.exit(0);
	}
	
	
	/**
	 * Prints our the rules
	 */
	public void help() {
		System.out.println("=======RULES========\n"
				+ "The game is played on a 9x9 board.\n"
				+ "The building is pitch black.\n"
				+ "Every level will increase the number of ninjas roaming around.\n"
				+ "The goal is find which room contains the briefcase. Rooms can only be entered from the top.\n"
				+ "You are given one bullet per game plus a bullet is spawned randomly on the floor.\n\n");
		System.out.println("=======SYMBOLS======\n"
				+ "[ ]: Light\n"
				+ "[*]: Darkness\n"
				+ "[N]: Enemy\n"
				+ "[=]: Unopened Room\n"
				+ "[X]: Dud Room\n"
				+ "[$]: Briefcase Room\n"
				+ "[B]: Extra Bullet Power-Up\n"
				+ "[I]: Invincibility Power-Up\n"
				+ "[R]: Reveal Briefcase Power-Up\n\n"
				+ "Type 42 To enter debug mode. 24 to exit debug mode.");
	}

	/**
	 * @return The option chosen whether to start the game or quit.
	 */
	public void actionMenu() {
		int option;
		System.out.println("Select an option:\n\t"
				+ "1. Continue Moving.\n\t"
				+ "2. Shoot.\n\t"
				+ "3. Look.\n\t"
				+ "4. Save and Quit Game.\n\t"
				+ "5. Toggle Hard Mode.\n\t"
				+ "6. Quit Game.");

		option = userinput.nextInt();
		userinput.nextLine();
		switch(option){
		case 1:
			playerMove();
			break;
		case 2:
			System.out.println("Enter a Direction to SHOOT: 1- UP | 2- DOWN | 3- RIGHT | 4- LEFT");
			gameEngine.playerShoot(userinput.nextInt());
			userinput.nextLine();
			break;
		case 3:
			System.out.println("Enter a Direction to LOOK: 1- UP | 2- DOWN | 3- RIGHT | 4- LEFT");
			gameEngine.playerLook(userinput.nextInt());
			userinput.nextLine();
			break;
		case 4:
			System.out.println("Enter a name for the save file");
			String saveName = userinput.next();
			gs = new GameSave(gameEngine);
			gs.saveGame(saveName);
			System.out.println("Game has been saved! \nThe save state is called " + saveName);
			break;
		case 5: 
			gameEngine.setNinjaAI(!gameEngine.getNinjaAI());
			System.out.println(gameEngine.getNinjaAI() ? "Hard mode on." : "Hard mode off.");
			break;
		case 6:
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
		System.out.println("Select an option:\n\t"
				+ "1. Start New Game.\n\t"
				+ "2. Help.\n\t"
				+ "3. Quit.\n\t"
				+ "4. Load Game.");

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
		System.out.println("LEVEL "  + (gameEngine.getAmountNinjas()-5) + " START\n"
				+ "There are " + gameEngine.getNumNinjas() + " ninjas lurking around!");
		gameEngine.vision();
		while(!gameEngine.isFinished()){
			System.out.println( "\n"+ gameEngine.getMap().toString());
			System.out.println(gameEngine.stats());
			playerMove();
			if (gameEngine.getLoss())
				endGame();
		}
	}


	
	/**
	 * Prompts the user for a command
	 */
	public void playerMove() {
		System.out.println("Enter a Command: 1- MOVE UP | 2- MOVE DOWN | 3- MOVE RIGHT | 4- MOVE LEFT | 5- ACTION MENU | 6- HELP");
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
			actionMenu();
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
