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
		for (int i = 0; i < GRID_SIZE; i++){
			for (int j = 0; j < GRID_SIZE; j++){
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
		if (grid[row][col] == null){
			grid[row][col] = o;
			return true;
		}
		return false;	
	}
	
	public void randomlyAddObjects(ArrayList<Object> objectArray){
		while (objectArray.size() > 0){
			if (addObject((int) (Math.random() * GRID_SIZE), (int) (Math.random() * GRID_SIZE), objectArray.get(0)))
			objectArray.remove(0);
		}
	}
	
	public Object getObject (int row, int col) {
		return grid[row][col];
	}
	
	public Object removeObject(int row, int col){
		Object o = grid[row][col];
		grid[row][col] = null;
		return o;
	}
	
	public boolean moveObject(int row, int col, int newRow, int newCol){
		if (grid[row][col] != null && grid[newRow][newCol] == null){
			grid[newRow][newCol] = removeObject(row, col);
			return true;
		}
		return false;
	}
}
