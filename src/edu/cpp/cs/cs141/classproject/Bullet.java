package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

public class Bullet extends AbstractPowerUp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8954951924198031896L;



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
