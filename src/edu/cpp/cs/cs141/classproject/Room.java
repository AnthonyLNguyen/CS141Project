package edu.cpp.cs.cs141.classproject;

public class Room {
	private boolean hasDocument;
	private boolean isHidden = true;

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
		if (isHidden)
			return "=";
		else if (hasDocument)
			return "$";
		else
			return "X";
	}
}
