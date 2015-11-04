/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

public class Game {
	private Map gameMap;
	private boolean isFinished;
	private boolean win;
	/**
	 * The amount of moves steps in the game.
	 */
	private int moveCount;

	/**
	 * Moves the character
	 * 
	 * @param row
	 * @param col
	 */
	public void move(int row, int col) {

	}

	/**
	 * Tests if {@link edu.cpp.cs.cs141.classproject.Player#getNumBullets(int)}
	 * is greater than 0. Then subtracts it by one. Removes an assassin from the
	 * map if there is one in front of the player.
	 */
	public void shoot() {
	}

	/**
	 * Generates the rooms and randomly makes only one of the rooms
	 * {@link edu.cpp.cs.cs141.classproject.Room#getHasDocument()} as
	 * {@code true}
	 */
	public void generateRooms() {
	}

	/**
	 * @return An integer that will be put into a switch that will determine the
	 *         correct UI output for the given case.
	 */
	public int turn() {
		return 0;
	}

	/**
	 * Exports the save game state as a .dat file in the workspace directory.
	 */
	public void saveGame() {
	}

	/**
	 * Imports a save game state from a previously saved .dat file in the
	 * workspace directory.
	 * 
	 * @param file
	 *            the name of the save file
	 */
	public void loadGame(String file) {
	}
}
