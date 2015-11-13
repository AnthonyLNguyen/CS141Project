package edu.cpp.cs.cs141.classproject;

public class Room {
	private boolean hasDocument;

	public Room(boolean hasDocument) {
		this.hasDocument = hasDocument;
	}

	public boolean getHasDocument() {
		return hasDocument;
	}
	public String toString() {
		return "=";
	}
}
