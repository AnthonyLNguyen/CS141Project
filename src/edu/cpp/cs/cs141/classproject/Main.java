package edu.cpp.cs.cs141.classproject;

public class Main {
	public static void main(String[] args) {
		int level = 1;
		while (true) {
			UserInterface u = new UserInterface(new Game(5 + level));
			u.startGame();
			level++;
		}
	}

}
