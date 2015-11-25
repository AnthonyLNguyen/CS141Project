package edu.cpp.cs.cs141.classproject;

import java.io.*;

public class GameSave implements Serializable{

	Game gameState;
	
	public GameSave (){
		gameState = null;
	}
	public GameSave(Game g) {
		setGameState(g);
	}
	
	public void setGameState (Game g) {
		gameState = g;
	}
	
	public void saveGame(String gameStateName){
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(gameStateName + ".dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject((Object) gameState);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Game loadGame(String gameStateName) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(gameStateName + ".dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			gameState = (Game) ois.readObject();
			fis.close();
		} catch (IOException e) {
			System.out.println("That file doesn't exist!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gameState;
	}
	
	
	
	

	
}
