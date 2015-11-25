package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

public class Invincibility extends AbstractPowerUp implements Serializable{

	public void effect(Player p) {
		p.setInvincible(true);
	}

	/*
	 * This shows the the Invincibility to the Map
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (isHidden)
			return "*";
		else
			return "I";
	}
}
