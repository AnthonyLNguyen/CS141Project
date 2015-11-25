package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

public class Bullet extends AbstractPowerUp implements Serializable{
	public void effect(Player p) {

	}

	
	
	/*
	 * this shows the Bullet to the Map
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (isHidden)
			return "*";
		else
			return "B";
	}
}
