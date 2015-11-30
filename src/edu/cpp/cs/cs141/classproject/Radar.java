package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

public class Radar extends AbstractPowerUp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2239537330405901105L;

	public void effect(Player p) {

	}

	/*
	 * This shows Radar to the map
	 * 
	 * @see java.lang.Object#toString()
	 */
	
	
	public String toString() {
		return isHidden ? "*" : "R";
	}
}
