/**
 * 
 */
package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9159641963363214314L;
	private Map gameMap;
	private boolean isFinished;
	private boolean loss = false;
	private boolean isWon = false;
	private ArrayList<Object> entities = new ArrayList<Object>();
	private Player player = new Player();
	private int amountNinjas;
	private ArrayList<Ninja> ninjas = new ArrayList<Ninja>();
	private int imoves = 0;
	private int moveLooked = -1;
	private boolean debugMode = false; 
	private boolean diagonalVision = false;
	private boolean ninjaAIOn = false;

	/**
	 * The amount of moves steps in the game. Used to keep of duration of
	 * powerups.
	 */
	private int moveCount = 0;

	/**
	 * Manages all of the conditions that can happen in a turn.
	 * 
	 * @param row
	 * @param col
	 */
	public void takeTurn(int row, int col) {
		int newRow = player.getRow() + row;
		int newCol = player.getCol() + col;
		if (newRow <= 8 && newCol <= 8 && newRow >= 0 && newCol >= 0) {
			boolean playerCanMove = true;

			int collisionType = gameMap.playerCollision(newRow, newCol);
			switch (collisionType) {
			case 1:
				if (!player.isInvincible()) {
					killPlayer();
					playerCanMove = false;
				} else {
					playerCanMove = false;
					collisionType = 3;
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
				if (gameMap.isPlayerAboveRoom(player) && gameMap.getObject(newRow, newCol) instanceof Room) {
					if (((Room) gameMap.getObject(newRow, newCol)).getHasDocument()) {
						System.out.println("You found the document. Whoopdie freakin do.");
						((Room) gameMap.getObject(newRow, newCol)).setActivated(true);
						isWon = true;
						isFinished = true;
					} else
						System.out.println("The room is empty, but you are filled with determination.");
					((Room) gameMap.getObject(newRow, newCol)).setActivated(true);
				} else {
					System.out.println("CAN'T ENTER ROOM FROM THIS DIRECTION");
				}
				playerCanMove = false;
				break;
			default:
				break;
			}

			if (collisionType != 3) {
				if (!debugMode && playerCanMove)
					if (player.isInvincible()) {
						System.out.println("Player invincible for " + (5 - imoves) + " more turns.");
						imoves++;
						if (5 < imoves) {
							player.setInvincible(false);
							System.out.println("No Longer invincible");
						}
					}
				if (playerCanMove)
					movePlayer(row, col);
				if (gameMap.playerNextToNinja(player) && !player.isInvincible())
					killPlayer();
				else
					moveNinjas();
				vision();
			}
			if (playerCanMove)
				moveCount++;
		} else {
			System.out.println("Cannot move");
		}
	}

	/**
	 * Moves the character
	 * 
	 * @param row
	 * @param col
	 */
	public void movePlayer(int row, int col) {
		int newRow = player.getRow() + row;
		int newCol = player.getCol() + col;
		gameMap.moveObject(player.getRow(), player.getCol(), newRow, newCol);
		player.setCol(newCol);
		player.setRow(newRow);
		vision();
	}

	/**
	 * Reduces {@link #player} life by 1 and changes the position to (8,0)
	 */
	public void killPlayer() {
		System.out.println("You were mortally stabbed!");
		// System.out.println("\n" + getMap().toString());

		player.setNumLives(player.getNumLives() - 1);
		if (player.getNumLives() == 0)
			loss = true;
		gameMap.moveObject(player.getRow(), player.getCol(), 8, 0);
		player.setCol(0);
		player.setRow(8);
		vision();
	}

	public boolean isFinished() {
		return isFinished;
	}

	/**
	 * Randomly moves the the ninjas in array {@link #ninjas}
	 */
	public void moveNinjas() {
		boolean hasMoved;
		for (Ninja n : ninjas) {
			hasMoved = false;
			if (ninjaAIOn) {
				hasMoved = smartNinjaMove(n);
			}
			if (!hasMoved) {
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
		if (debugMode)
			showAll();
	}

	private int inNinjaSight(Ninja n) {
		int row = n.getRow();
		int col = n.getCol();
		for (int i = row; i >= 0; i--) {
			if (gameMap.getObject(i, col) instanceof Room)
				break;
			if (gameMap.getObject(i, col) instanceof Player) 
				return 1;
		}
		row = n.getRow();
		col = n.getCol();
		for (int i = row; i <= 8; i++) {
			if (gameMap.getObject(i, col) instanceof Room)
				break;
			if (gameMap.getObject(i, col) instanceof Player) 
				return 3;
		}
		row = n.getRow();
		col = n.getCol();
		for (int i = col; i <= 8; i++) {
			if (gameMap.getObject(row, i) instanceof Room)
				break;
			if (gameMap.getObject(row, i) instanceof Player) 
				return 2;
		}
		row = n.getRow();
		col = n.getCol(); 
		for (int i = col; i >= 0; i--) {
			if (gameMap.getObject(row, i) instanceof Room)
				break;
			if (gameMap.getObject(row, i) instanceof Player)
				return 4;	
		}
		return 0;
	}

	public boolean smartNinjaMove(Ninja n) {
		boolean hasMoved = false;
		switch (inNinjaSight(n)) {
		case 0:
			break;
		case 1:
			if (gameMap.whereCanMove(n)[2]) {
				gameMap.moveObject(n.getRow(), n.getCol(), n.getRow() - 1, n.getCol());
				n.setRow(n.getRow() - 1);
				hasMoved = true;
			}
			break;
		case 2:
			if (gameMap.whereCanMove(n)[1]) {
				gameMap.moveObject(n.getRow(), n.getCol(), n.getRow(), n.getCol() + 1);
				n.setCol(n.getCol() + 1);
				hasMoved = true;
			}
			break;
		case 3:
			if (gameMap.whereCanMove(n)[0]) {
				gameMap.moveObject(n.getRow(), n.getCol(), n.getRow() + 1, n.getCol());
				n.setRow(n.getRow() + 1);
				hasMoved = true;
			}
			break;
		case 4:
			if (gameMap.whereCanMove(n)[3]) {
				gameMap.moveObject(n.getRow(), n.getCol(), n.getRow(), n.getCol() - 1);
				n.setCol(n.getCol() - 1);
				hasMoved = true;
			}
			break;
		}
		return hasMoved;
	}

	public Game(int ninjas) {
		gameMap = new Map();
		amountNinjas = ninjas;
	}

	/**
	 * Game stats
	 * 
	 * @return A string of game statistics
	 */
	public String stats() {
		return "Moves:" + moveCount + "\n" + "Ammo:" + player.getNumBullets() + "\n" + "Lives:" + player.getNumLives()
				+ "\n" + "Level:" + (amountNinjas - 5) + "\n" + "Ninjas:" + getNumNinjas();
	}

	public int getMoveCount() {
		return moveCount;
	}

	public int getAmountNinjas() {
		return amountNinjas;
	}

	/**
	 * Uses the look ability
	 */
	public void playerLook(int dir) {
		boolean used = false;
		if (moveLooked == moveCount) {
			System.out.println("You can only use this ability once per turn.");
			used = true;
		}
		if (moveLooked != moveCount)
			moveLooked = moveCount;
		if (!used) {
			int row = player.getRow();
			int col = player.getCol();
			boolean lookTraveled = false;
			Boolean ninjaAhead = false;
			switch (dir) {
			case 1:
				while (!lookTraveled) {
					for (int i = row; i > 0; i--) {
						if (gameMap.getObject(i, col) instanceof Ninja) {
							gameMap.revealObject(i, col);
							ninjaAhead = true;
							lookTraveled = true;
						}
					}
					lookTraveled = true;
				}
				break;
			case 2:
				while (!lookTraveled) {
					for (int i = row; i < 8; i++) {
						if (gameMap.getObject(i, col) instanceof Ninja) {
							gameMap.revealObject(i, col);
							ninjaAhead = true;
							lookTraveled = true;
						}
					}
					lookTraveled = true;
				}
				break;
			case 3:
				while (!lookTraveled) {
					for (int i = col; i < 8; i++) {
						if (gameMap.getObject(row, i) instanceof Ninja) {
							gameMap.revealObject(row, i);
							ninjaAhead = true;
							lookTraveled = true;
						}
					}
					lookTraveled = true;
				}
				break;
			case 4:
				while (!lookTraveled) {
					for (int i = col; i > 0; i--) {
						if (gameMap.getObject(row, i) instanceof Ninja) {
							gameMap.revealObject(row, i);
							ninjaAhead = true;
							lookTraveled = true;
						}
					}
					lookTraveled = true;
				}
				break;
			}
			if (ninjaAhead)
				System.out.println("Ninja ahead!");
			else
				System.out.println("All clear!");
		}
	}

	/**
	 * Creates a field of vision around the player
	 */
	public void vision() {
		int row = player.getRow();
		int col = player.getCol();
		boolean[] block = { false, false, false, false };
		if (!debugMode) {
			hideAll();
		}
		boolean[] isEmpty = gameMap.playerVision(player);

		if (!(isEmpty[0] || row + 1 > 8)) {
			gameMap.revealObject(row + 1, col);
			if (!(gameMap.getObject(row + 1, col) instanceof EmptySpace))
				block[0] = true;
		}
		if (!(isEmpty[1] || col + 1 > 8)) {
			gameMap.revealObject(row, col + 1);
			if (!(gameMap.getObject(row, col + 1) instanceof EmptySpace))
				block[1] = true;
		}
		if (!(isEmpty[2] || row - 1 < 0)) {
			gameMap.revealObject(row - 1, col);
			if (!(gameMap.getObject(row - 1, col) instanceof EmptySpace))
				block[2] = true;
		}
		if (!(isEmpty[3] || col - 1 < 0)) {
			gameMap.revealObject(row, col - 1);
			if (!(gameMap.getObject(row, col - 1) instanceof EmptySpace))
				block[3] = true;
		}

		if (!(isEmpty[4] || row + 2 > 8) && !block[0])
			if (!(gameMap.getObject(row + 1, col) instanceof Room))
				gameMap.revealObject(row + 2, col);
		if (!(isEmpty[5] || col + 2 > 8) && !block[1])
			if (!(gameMap.getObject(row, col + 1) instanceof Room))
				gameMap.revealObject(row, col + 2);
		if (!(isEmpty[6] || row - 2 < 0) && !block[2])
			if (!(gameMap.getObject(row - 1, col) instanceof Room))
				gameMap.revealObject(row - 2, col);
		if (!(isEmpty[7] || col - 2 < 0) && !block[3])
			if (!(gameMap.getObject(row, col - 1) instanceof Room))
				gameMap.revealObject(row, col - 2);

		if (diagonalVision) {
			if ((row - 1 > 0 && col + 1 < 8))
				if (!(gameMap.getObject(row - 1, col + 1) instanceof Room))
					gameMap.revealObject(row - 1, col + 1);
			if ((row + 1 < 8 && col + 1 < 8))
				if (!(gameMap.getObject(row + 1, col + 1) instanceof Room))
					gameMap.revealObject(row + 1, col + 1);
			if ((row + 1 < 8 && col - 1 > 0))
				if (!(gameMap.getObject(row + 1, col - 1) instanceof Room))
					gameMap.revealObject(row + 1, col - 1);
			if ((row - 1 > 0 && col - 1 > 0))
				if (!(gameMap.getObject(row - 1, col - 1) instanceof Room))
					gameMap.revealObject(row - 1, col - 1);
		}
	}

	/**
	 * Activates a powerup depending on what powerup is given.
	 * @param power
	 */
	public void playerPowerup(int power) {
		switch (power) {
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

		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				gameMap.addObject(i, j, new EmptySpace());

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
			ninjas.add(new Ninja());
			entities.add(ninjas.get(i));
		}

		// Powerups
		entities.add(new Invincibility());
		entities.add(new Bullet());
		entities.add(new Radar());

		gameMap.randomlyAddObjects(entities);
	}

	public Map getGameMap() {
		return gameMap;
	}

	/**
	 * Activates debug mode
	 */
	public void showAll() {
		gameMap.revealAll();
		debugMode = true;
		player.setInvincible(true);
	}

	public int getNumNinjas() {
		int numNin = 0;
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if (gameMap.getObject(i, j) instanceof Ninja)
					++numNin;
		return numNin;
	}

	/**
	 * Deactivates debug mode
	 */
	public void hideAll() {
		gameMap.unrevealAll();
		if (debugMode)
			player.setInvincible(false);
		debugMode = false;
	}

	/**
	 * Tests if {@link edu.cpp.cs.cs141.classproject.Player#getNumBullets(int)}
	 * is greater than 0. Then subtracts it by one. Removes an assassin from the
	 * map if there is one in front of the player.
	 */
	public void playerShoot(int dir) {
		int row = player.getRow();
		int col = player.getCol();
		boolean bulletTraveled = false;
		if (player.getNumBullets() > 0) {
			if (!debugMode)
				player.setNumBullets(player.getNumBullets() - 1);
			Ninja n = null;
			switch (dir) {
			case 1:// up
				while (!bulletTraveled) {
					for (int i = row; i >= 0; i--) {
						if (gameMap.getObject(i, col) instanceof Room) {
							System.out.println("You shot a room! ):");
							bulletTraveled = true;
							break;
						}
						if (gameMap.getObject(i, col) instanceof Ninja) {
							n = (Ninja) gameMap.removeObject(i, col);
							gameMap.addObject(i, col, new EmptySpace());
							bulletTraveled = true;
							break;
						}
					}
					bulletTraveled = true;
				}
				break;
			case 2:// down
				while (!bulletTraveled) {
					for (int i = row; i <= 8; i++) {
						if (gameMap.getObject(i, col) instanceof Room) {
							System.out.println("You shot a room! ):");
							bulletTraveled = true;
							break;
						}
						if (gameMap.getObject(i, col) instanceof Ninja) {
							n = (Ninja) gameMap.removeObject(i, col);
							gameMap.addObject(i, col, new EmptySpace());
							bulletTraveled = true;
							break;
						}
					}
					bulletTraveled = true;
				}
				break;
			case 3:// right
				while (!bulletTraveled) {
					for (int i = col; i <= 8; i++) {
						if (gameMap.getObject(row, i) instanceof Room) {
							System.out.println("You shot a room! ):");
							bulletTraveled = true;
							break;
						}
						if (gameMap.getObject(row, i) instanceof Ninja) {
							n = (Ninja) gameMap.removeObject(row, i);
							gameMap.addObject(row, i, new EmptySpace());
							bulletTraveled = true;
							break;
						}
					}
					bulletTraveled = true;
				}
				break;
			case 4:// left
				while (!bulletTraveled) {
					for (int i = col; i >= 0; i--) {
						if (gameMap.getObject(row, i) instanceof Room) {
							System.out.println("You shot a room! ):");
							bulletTraveled = true;
							break;
						}
						if (gameMap.getObject(row, i) instanceof Ninja) {
							n = (Ninja) gameMap.removeObject(row, i);
							gameMap.addObject(row, i, new EmptySpace());
							bulletTraveled = true;
							break;
						}
					}
					bulletTraveled = true;
				}
				break;
			}
			if (n == null)
				System.out.println("You missed. Work on your aim.");
			else {
				ninjas.remove(n);
				System.out.println("You hit someone! Ninja killed.");
				vision();
				if (debugMode)
					showAll();
			}
		} else
			System.out.println("No Bullets");
	}

	public boolean getLoss() {
		return loss;
	}

	public Map getMap() {
		return gameMap;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	/**
	 * Activates an alternative vision that includes diagonals
	 */
	public void setDiagonalVision(boolean diagonalVision) {
		this.diagonalVision = diagonalVision;
	}

	public boolean isWon() {
		// TODO Auto-generated method stub
		return isWon;
	}

	public Player getPlayer() {
		return player;
	}
	
	public boolean getNinjaAI(){
		return ninjaAIOn;
	}
	
	public void setNinjaAI(boolean t){
		ninjaAIOn = t;
	}
}
