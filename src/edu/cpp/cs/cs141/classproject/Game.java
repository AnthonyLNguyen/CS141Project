/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.util.ArrayList;

public class Game {
	private Map gameMap;
	private boolean isFinished;
	private boolean win;
	private ArrayList<Object> entities = new ArrayList<Object>();
	private Player player = new Player();
	private int amountNinjas = 6;
	private Ninja[] ninjas = new Ninja[amountNinjas];
	/**
	 * The amount of moves steps in the game. Used to keep of duration of
	 * powerups.
	 */
	private int moveCount = 0;

	/**
	 * Moves the character
	 * 
	 * @param row
	 * @param col
	 */
	public void movePlayer(int row, int col) {
		if (!gameMap.canMove(row,col)){
			//placeholder
		}
		gameMap.moveObject(player.getRow(), player.getCol(), player.getRow() + row, player.getCol() + col);
		player.setCol(player.getCol() + col);
		player.setRow(player.getRow() + row);
		moveCount++;
	}
	
	public void moveNinjas() {
		for (Ninja n : ninjas) {
			int row = n.getRow();
			int col = n.getCol();
			boolean move = true;
			while (move) {
				if (Math.random() > .5) {
					if (Math.random() > .5)
						if (gameMap.canMove(n.getRow() + 1, col)) {
							row = n.getRow() + 1;
							move = false;
						} else if (gameMap.canMove(n.getRow() - 1, col)) {
							row = n.getRow() - 1;
							move = false;
						}
				} else {
					if (Math.random() > .5)
						if (gameMap.canMove(row, n.getCol() - 1)) {
							col = n.getCol() - 1;
							move = false;
						} else if (gameMap.canMove(row, n.getCol() + 1)) {
							col = n.getCol() + 1;
							move = false;
						} else
							move = false;
				}
			}
			gameMap.moveObject(n.getRow(), n.getCol(), row, col);
			System.out.println(n.getRow() + "," + n.getCol() + " to" + row + "," + col);
			n.setCol(col);
			n.setRow(row);
			moveCount++;
		}
	}

	public Game() {
		gameMap = new Map();
	}

	/**
	 * Uses the look ability
	 */
	public void playerLook() {
	}

	/**
	 * Creates the map and randomly places objects into the map such as ninjas
	 * and powerups
	 */
	public void generateMap() {

		gameMap.addObject(player.getRow(), player.getCol(), player);

		// Rooms
		// adds an empty room to predetermined locations
		for (int i = 1; i <= 7; i += 3)
			for (int j = 1; j <= 7; j += 3)
				gameMap.addObject(i, j, new Room(false));
		// change a random room to true
		int randRow = (((int) (Math.random() * 3)) * 3) + 1;
		int randCol = (((int) (Math.random() * 3)) * 3) + 1;
		gameMap.removeObject(randRow, randCol);
		gameMap.addObject(randRow, randCol, new Room(true));

		// Ninjas
		// 6 ninjas
		for (int i = 0; i < amountNinjas; i++) {
			ninjas[i] = new Ninja();
			entities.add(ninjas[i]);
		}

		// Powerups
		entities.add(new Invincibility());
		entities.add(new Bullet());
		entities.add(new Radar());

		gameMap.randomlyAddObjects(entities);
	}

	public void showAll() {
		gameMap.revealAll();
	}

	/**
	 * Tests if {@link edu.cpp.cs.cs141.classproject.Player#getNumBullets(int)}
	 * is greater than 0. Then subtracts it by one. Removes an assassin from the
	 * map if there is one in front of the player.
	 */
	public void shoot() {
	}

	/**
	 * @return An integer that will be put into a switch that will determine the
	 *         correct UI output for the given case.
	 */
	public int turn() {
		return 0;
	}

	/**
	 * Exports the save game state as a file in the workspace directory.
	 */
	public void saveGame() {
	}

	/**
	 * Imports a save game state from a previously saved file in the workspace
	 * directory.
	 * 
	 * @param file
	 *            the name of the save file
	 */
	public void loadGame(String file) {
	}

	public Map getMap() {
		return gameMap;
	}
}
