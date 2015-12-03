package edu.cpp.cs.cs141.classproject;

public class Main {
	public static void main(String[] args) {
		boolean gameStarted = false;
		int level = 1;
		while (true) {
			UserInterface TEXT = new UserInterface(new Game(5 + level));
			int modeChoice = 1;
			if (!gameStarted) {
				modeChoice = TEXT.chooseMode();
				gameStarted = true;
			}
				switch (modeChoice) {
				case 1:
					TEXT.launch();
					level++;
					break;
				case 2:
					GraphicalUserInterface GUI = new GraphicalUserInterface();
					GUI.launch();
					break;
				}
			
		}
	}

}
