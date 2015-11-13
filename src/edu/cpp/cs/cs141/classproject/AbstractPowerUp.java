package edu.cpp.cs.cs141.classproject;

public abstract class AbstractPowerUp {

	protected boolean isHidden = true;
	/**
	 * This method is an abstract method that will produce the desired effect on
	 * the player.
	 */
	public abstract void effect(Player p);
	
	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	

		
}
