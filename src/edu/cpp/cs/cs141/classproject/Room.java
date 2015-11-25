package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

public class Room implements Serializable{
	private boolean hasDocument;
	private boolean isHidden = true;
	private boolean isActivated = false;

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public Room(boolean hasDocument) {
		this.hasDocument = hasDocument;
	}

	public boolean getHasDocument() {
		return hasDocument;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public String toString() {
		if (isActivated && hasDocument)
			return "$";		
		if (isHidden)
			return "=";
		else if (hasDocument)
			return "$";
		else
			return "X";
	}
}
