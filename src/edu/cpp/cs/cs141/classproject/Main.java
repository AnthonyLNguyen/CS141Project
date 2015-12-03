package edu.cpp.cs.cs141.classproject;

public class Main {
	public static void main(String[] args) {
		boolean gameStarted = false;
		int level = 1;
		while (true) {
			UserInterface u = new UserInterface(new Game(5 + level));
			int modeChoice = 1;
			if (!gameStarted) {
				modeChoice = u.chooseMode();
				gameStarted = true;
			}
				switch (modeChoice) {
				case 1:
					u.startGame();
					level++;
					break;
				case 2:
					GraphicalUserInterface m = new GraphicalUserInterface();
					m.launch();
					break;
				}
			
		}
	}

}
