package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

public class EmptySpace implements Serializable{
	
	private boolean isHidden = true;
	
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
			return " ";
	}
}
