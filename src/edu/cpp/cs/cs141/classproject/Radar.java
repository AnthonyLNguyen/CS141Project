package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

public class Radar extends AbstractPowerUp implements Serializable{
	public void effect(Player p) {

	}

	/*
	 * This shows Radar to the map
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (isHidden)
			return "*";
		else
			return "R";
	}
}
