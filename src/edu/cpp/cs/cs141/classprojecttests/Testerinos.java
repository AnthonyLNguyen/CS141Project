package edu.cpp.cs.cs141.classprojecttests;

import edu.cpp.cs.cs141.classproject.*;

public class Testerinos {
	
	public static void main(String[] args) {
		testGenerateMap();
	}
	
	public static void testMapPrint(){
		Map m = new Map();
		m.addObject((int) (Math.random() * 9), (int) (Math.random() * 9), ((Object)new Ninja()));
		m.addObject((int) (Math.random() * 9), (int) (Math.random() * 9), ((Object)new Ninja()));
		m.addObject((int) (Math.random() * 9), (int) (Math.random() * 9), ((Object)new Ninja()));
		m.addObject((int) (Math.random() * 9), (int) (Math.random() * 9), ((Object)new Ninja()));
		System.out.println(m.toString());
	}
	
	public static void testMapRemove(){
		Map m = new Map();
		m.addObject(0, 0, new Ninja());
		System.out.println(m.toString());
		m.removeObject(0, 0);
		System.out.println(m.toString());
	}
	
	public static void testMapMove(){
		Map m = new Map();
		m.addObject(0, 0, new Ninja());
		int row = 0, col = 0;
		for (int i = 0; i < Map.GRID_SIZE; i++){
			for (int j = 0; j < Map.GRID_SIZE; j++){
				System.out.println(m.toString());
				m.moveObject(row, col, i, j);
				row = i;
				col = j;
			}
		}
	}
	
	public static void testGenerateMap(){
		Game g = new Game();
		g.generateMap();
		g.showAll();
		System.out.println(g.getMap().toString());
	}
}
