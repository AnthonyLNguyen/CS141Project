package edu.cpp.cs.cs141.classproject;

public class Ninja {
	private int row;
	private int col;

	private boolean isHidden = true;


	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}



	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * Moves the ninja to a random adjacent spot to its current location.
	 */
	public void move() {

	}


	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
	public boolean isHidden() {
		return isHidden;
	}

	/*
	 * this show Ninja to the Map
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (isHidden)
			return "*";
		else
			return "N";
	}
}
