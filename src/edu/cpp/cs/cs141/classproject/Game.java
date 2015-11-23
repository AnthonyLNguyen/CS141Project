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
		int newRow = player.getRow() + row;
		int newCol = player.getCol() + col;
		if (gameMap.playerCollision(newRow, newCol) != 3
				&& (newRow <= 8 && newCol <= 8 && newRow >= 0 && newCol >= 0)) {

			int collisionType = gameMap.playerCollision(newRow, newCol);
			switch (collisionType) {
			case 1:
				if (player.isInvincible()) {
					killPlayer();
				}
				break;
			case 2:
				System.out.println("POWER UP!");
				if (gameMap.getObject(newRow, newCol) instanceof Bullet) {
					playerPowerup(1);
					System.out.println("Bullet Added");
				}
				if (gameMap.getObject(newRow, newCol) instanceof Invincibility) {
					playerPowerup(2);
					System.out.println("Player Invincible");
				}
				if (gameMap.getObject(newRow, newCol) instanceof Radar) {
					playerPowerup(3);
					System.out.println("Radar Activated");
				}
				gameMap.removeObject(newRow, newCol);
				break;
			case 3:
				System.out.println("CAN'T ENTER ROOM");
				break;
			default:
				System.out.println("");
				break;
			}

			gameMap.moveObject(player.getRow(), player.getCol(), newRow, newCol);
			player.setCol(newCol);
			player.setRow(newRow);
			moveNinjas();
			vision();
			if (gameMap.playerNextToNinja(player))
				killPlayer();
			moveCount++;

		} else {
			System.out.println("Cannot move");
		}
	}

	public void killPlayer() {
		System.out.println("You were mortally stabbed!");
		System.out.println("\n" + getMap().toString());
		player.setNumLives(player.getNumLives() - 1);
		gameMap.moveObject(player.getRow(), player.getCol(), 8, 0);
		player.setCol(0);
		player.setRow(8);
		vision();
	}

	public void moveNinjas() {
		for (Ninja n : ninjas) {

			boolean[] moveableSpaces = gameMap.whereCanMove(n);
			int choice = (int) (Math.random() * 4);
			if (moveableSpaces[0] || moveableSpaces[1] || moveableSpaces[2] || moveableSpaces[3]) {
				while (!moveableSpaces[choice]) {
					choice = (int) (Math.random() * 4);
				}
			} else
				choice = -1;

			switch (choice) {
			case 0:
				gameMap.moveObject(n.getRow(), n.getCol(), n.getRow() + 1, n.getCol());
				n.setRow(n.getRow() + 1);
				break;
			case 1:
				gameMap.moveObject(n.getRow(), n.getCol(), n.getRow(), n.getCol() + 1);
				n.setCol(n.getCol() + 1);
				break;
			case 2:
				gameMap.moveObject(n.getRow(), n.getCol(), n.getRow() - 1, n.getCol());
				n.setRow(n.getRow() - 1);
				break;
			case 3:
				gameMap.moveObject(n.getRow(), n.getCol(), n.getRow(), n.getCol() - 1);
				n.setCol(n.getCol() - 1);
				break;
			default:
				break;
			}
		}
	}

	public Game() {
		gameMap = new Map();
	}

	/**
	 * Uses the look ability
	 */
	public void playerLook(int dir) {
		int row = player.getRow();
		int col = player.getCol();
		switch (dir) {
		case 1:
			break;
		}
	}

	public void vision() {
		int row = player.getRow();
		int col = player.getCol();
		boolean[] block = { false, false, false, false };
		hideAll();
		boolean[] isEmpty = gameMap.playerVision(player);
		if (!(isEmpty[0] || row + 1 > 8)) {
			gameMap.revealObject(row + 1, col);
			block[0] = true;
		}
		if (!(isEmpty[1] || col + 1 > 8)) {
			gameMap.revealObject(row, col + 1);
			block[1] = true;
		}
		if (!(isEmpty[2] || row - 1 < 0)) {
			gameMap.revealObject(row - 1, col);
			block[2] = true;
		}
		if (!(isEmpty[3] || col - 1 < 0)) {
			gameMap.revealObject(row, col - 1);
			block[3] = true;
		}
		if (!(isEmpty[4] || row + 2 > 8) && !block[0]) 
			gameMap.revealObject(row + 2, col);
		if (!(isEmpty[5] || col + 2 > 8) && !block[1]) 
			gameMap.revealObject(row, col + 2);
		if (!(isEmpty[6] || row - 2 < 0) && !block[2]) 
			gameMap.revealObject(row - 2, col);
		if (!(isEmpty[7] || col - 2 < 0) && !block[3]) 
			gameMap.revealObject(row, col - 2);
	}

	public void playerPowerup(Object power) {
		int p = 0;
		if (power instanceof Bullet)
			p = 1;
		if (power instanceof Invincibility)
			p = 2;
		if (power instanceof Radar)
			p = 3;

		switch (p) {
		case 1:
			player.setNumBullets(player.getNumBullets() + 1);
			break;
		case 2:
			player.setInvincible(true);
			break;
		case 3:
			gameMap.radar();
			break;
		default:
			break;
		}
	}

	/**
	 * Creates the map and randomly places objects into the map such as ninjas
	 * and powerups
	 */
	public void generateMap() {

		gameMap.addObject(player.getRow(), player.getCol(), player);

		for (int i = 0; i <= 8; i++)
			for (int j = 0; j <= 8; j++)
				gameMap.addObject(i, j, new EmptySpace());
		
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

	public void hideAll() {
		gameMap.unrevealAll();
	}

	/**
	 * Tests if {@link edu.cpp.cs.cs141.classproject.Player#getNumBullets(int)}
	 * is greater than 0. Then subtracts it by one. Removes an assassin from the
	 * map if there is one in front of the player.
	 */
	public void shoot(int dir) {
		int row = player.getRow();
		int col = player.getCol();
		boolean bulletTraveled = false;
		if (player.getNumBullets() > 0) {
			switch (dir) {
			case 1:
				while (!bulletTraveled) {
					for (int i = row; i < 8; i++) {
						if (gameMap.getObject(i, col) instanceof Ninja) {
							gameMap.removeObject(i, col);
							bulletTraveled = true;
							System.out.println("Ninja Killed");
						}
					}
					bulletTraveled = true;
				}
				break;
			case 2:
				while (!bulletTraveled) {
					for (int i = row; i > 0; i--) {
						if (gameMap.getObject(i, col) instanceof Ninja) {
							gameMap.removeObject(i, col);
							bulletTraveled = true;
							System.out.println("Ninja Killed");
						}
					}
					bulletTraveled = true;
				}
				break;
			case 3:
				while (!bulletTraveled) {
					for (int i = col; i < 8; i++) {
						if (gameMap.getObject(row, i) instanceof Ninja) {
							gameMap.removeObject(row, i);
							bulletTraveled = true;
							System.out.println("Ninja Killed");
						}
					}
					bulletTraveled = true;
				}
				break;
			case 4:
				while (!bulletTraveled) {
					for (int i = col; i > 0; i--) {
						if (gameMap.getObject(row, i) instanceof Ninja) {
							gameMap.removeObject(row, i);
							bulletTraveled = true;
							System.out.println("Ninja Killed");
						}
					}
					bulletTraveled = true;
				}
				break;
			}
		} else
			System.out.println("No Bullets");
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
