package edu.cpp.cs.cs141.classproject;

public class Map {

	public static final int GRID_SIZE = 4;

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
					result += " [X] ";
				result += " [" + grid[i][j].toString() + "] ";
			}
			result += "\n";
		}
		return result;
	}

	public void addObject(int row, int col, Object o) {
	}
	
}
