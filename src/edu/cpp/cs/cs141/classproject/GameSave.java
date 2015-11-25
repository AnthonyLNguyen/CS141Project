package edu.cpp.cs.cs141.classproject;

import java.io.Serializable;

public class GameSave implements Serializable{

	private Map location;
	private Map places;
	private Map ninja;
	private Game game;
	private Player player;
	
	
	
	public Map getLocation() {
		return location;
	}

	public void setLocation(Map location) {
		this.location = location;
	}

	public Map getPlaces() {
		return places;
	}

	public void setPlaces(Map places) {
		this.places = places;
	}

	public Map getNinja() {
		return ninja;
	}

	public void setNinja(Map ninja) {
		this.ninja = ninja;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	} 
	
}
