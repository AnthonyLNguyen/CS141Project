package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

public class Invincibility extends AbstractPowerUp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2931193593223690922L;

	public void effect(Player p) {
		p.setInvincible(true);
	}

	/*
	 * This shows the the Invincibility to the Map
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return isHidden ? "*" : "I";
	}
}
