package edu.cpp.cs.cs141.classproject;

import java.util.ArrayList;

public class Map {

	public static final int GRID_SIZE = 9;

	private Object[][] grid = new Object[GRID_SIZE][GRID_SIZE];

	/*
	 * Show the map to the Player
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String result = "";
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				if (grid[i][j] == null)
					result += " [*]";
				else
					result += " [" + grid[i][j].toString() + "]";
			}
			result += "\n";
		}
		return result;
	}

	public boolean addObject(int row, int col, Object o) {
		// prevents ninjas from spawning too close to the player
		if (grid[row][col] == null && !((row > 3 && col < 4) && o instanceof Ninja)) {
			grid[row][col] = o;
			return true;
		}
		return false;
	}

	public boolean canMove(int row, int col)
	{
		// since the and operator returns false if the first one is false we can do this
		if((row <= 8 && col <= 8 && row >= 0 && col >= 0) && grid[row][col] == null)
			return true;
		return false;
	}
	
	public boolean[] whereCanMove(Ninja n){
		boolean[] b = {true, true, true, true};
		if (n.getRow() + 1 > 8 || grid[n.getRow() + 1][n.getCol()] != null)
			b[0] = false;
		if (n.getCol() + 1 > 8 || grid[n.getRow()][n.getCol() + 1] != null)
			b[1] = false;
		if (n.getRow() - 1 < 0 || grid[n.getRow() - 1][n.getCol()] != null)
			b[2] = false;
		if (n.getCol() - 1 < 0 || grid[n.getRow()][n.getCol() - 1] != null)
			b[3] = false;
		return b;
	}
	
	public void randomlyAddObjects(ArrayList<Object> objectArray) {
		while (objectArray.size() > 0) {
			int row = (int) (Math.random() * GRID_SIZE);
			int col = (int) (Math.random() * GRID_SIZE);
			if (objectArray.get(0) instanceof Ninja){
				((Ninja) (objectArray.get(0))).setRow(row);
				((Ninja) (objectArray.get(0))).setCol(col);	
			}
			if (addObject(row, col, objectArray.get(0)))
				objectArray.remove(0);
		}
	}

	/**
	 * Changes an object's boolean hidden to true.
	 * 
	 * @param row
	 *            Row of the object you want revealed
	 * @param col
	 *            Row of the object you want revealed
	 */
	public void revealObject(int row, int col) {
		if (grid[row][col] != null) {
			if (grid[row][col] instanceof AbstractPowerUp)
				((AbstractPowerUp) grid[row][col]).setHidden(false);
			if (grid[row][col] instanceof Ninja)
				((Ninja) grid[row][col]).setHidden(false);
			if (grid[row][col] instanceof Room)
				((Room) grid[row][col]).setHidden(false);
		}
	}

	/**
	 * Uses {@link #revealObject(int, int)} on all parts of the grid.
	 */
	public void revealAll() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				revealObject(i, j);
	}

	public Object getObject(int row, int col) {
		return grid[row][col];
	}

	/**
	 * @param row
	 *            Row of the object you want removed
	 * @param col
	 *            Column of the object you want removed
	 * @return The object that was removed
	 */
	public Object removeObject(int row, int col) {
		Object o = grid[row][col];
		grid[row][col] = null;
		return o;
	}

	/**
	 * Moves an object from a chosen location int row and int col to int newRow
	 * and int newCol
	 * 
	 * @param row
	 *            Row of the object you want moved
	 * @param col
	 *            Column of the object you want moved
	 * @param newRow
	 *            New row location
	 * @param newCol
	 *            New column location
	 * @return if the object was able to move
	 */
	public boolean moveObject(int row, int col, int newRow, int newCol) {
		if (grid[row][col] != null && grid[newRow][newCol] == null) {
			grid[newRow][newCol] = removeObject(row, col);
			return true;
		}
		return false;
	}

	public Object[][] getGrid() {
		return grid;
	}
}
