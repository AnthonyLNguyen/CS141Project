package edu.cpp.cs.cs141.classproject;

public class Invincibility extends AbstractPowerUp {

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
